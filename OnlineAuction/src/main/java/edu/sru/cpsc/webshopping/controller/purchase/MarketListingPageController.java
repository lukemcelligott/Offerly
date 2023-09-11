package edu.sru.cpsc.webshopping.controller.purchase;

import edu.sru.cpsc.webshopping.controller.EmailController;
import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.MessageDomainController;
import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.UserListDomainController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.controller.WidgetImageController;
import edu.sru.cpsc.webshopping.controller.billing.SellerRatingController;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.UserList;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetAttribute;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Manages the functionality of the MarketListing page This page is used for ing and interacting
 * with market listings
 */
@Controller
@Slf4j
public class MarketListingPageController {
  MarketListingDomainController marketListingController;
  PurchaseShippingAddressPageController shippingPage;
  MarketListing heldListing;
  UserController userController;
  WidgetImageController widgetImageController;
  MessageDomainController msgcontrol;
  EmailController emailController;
  ConfirmPurchasePageController purchaseController;
  SellerRatingController ratingController;
  UserListDomainController userListController;
  // Repositories passed to ConfirmPurchasePage
  TransactionController transController;
  WidgetController widgetController;
  private String page;
  private Widget tempWidget;
  @PersistenceContext
  EntityManager entityManager;

  public MarketListingPageController(
      MarketListingDomainController marketListingController,
      TransactionController transController,
      UserController userController,
      PurchaseShippingAddressPageController shippingPage,
      MessageDomainController msgcontrol,
      EmailController emailController,
      WidgetController widgetController,
      WidgetImageController widgetImageController,
      SellerRatingController ratingController,
      UserListDomainController userListController,
      ConfirmPurchasePageController purchaseController) {
    this.marketListingController = marketListingController;
    this.transController = transController;
    this.userController = userController;
    this.purchaseController = purchaseController;
    this.shippingPage = shippingPage;
    this.msgcontrol = msgcontrol;
    this.widgetImageController = widgetImageController;
    this.emailController = emailController;
    this.widgetController = widgetController;
    this.ratingController = ratingController;
    this.userListController = userListController;
  }

  /** Reloads the page model data */
  public void reloadModel(Model model) {
    tempWidget = heldListing.getWidgetSold();
    model.addAttribute("currListing", heldListing);
    model.addAttribute("widget", heldListing.getWidgetSold());
    model.addAttribute("category", heldListing.getWidgetSold().getCategory());

    // Check if User is the owner of the market listing
    User user = userController.getCurrently_Logged_In();
    model.addAttribute("user", user);
    // If user is the seller or an admin, give them access to modify the listing
    if (user.getId() == heldListing.getSeller().getId() || user.getRole().equals("ROLE_ADMIN"))
      model.addAttribute("isBuyer", false);
    else {
      model.addAttribute("isBuyer", true);
      Transaction newTrans = new Transaction();
      model.addAttribute("newTransaction", newTrans);
    }
    // Check if the user is able to purchase widgets
    if (user.getPaymentDetails() == null) model.addAttribute("canBuy", false);
    else model.addAttribute("canBuy", true);

    model.addAttribute("sellerRating", heldListing.getSeller().getSellerRating());
    model.addAttribute("seller", heldListing.getSeller());
  }

  /**
   * Opens the viewMarketListingPage
   *
   * @param marketListingId the id of the marketListing to view
   * @param model The page model - passed by dependency injection
   * @return the viewMarketListing page string
   */
  @RequestMapping({"/viewMarketListing/{marketListingId}"})
  public String viewMarketListingPage(@PathVariable("marketListingId") long marketListingId, Model model)
  {
    heldListing = marketListingController.getMarketListing(marketListingId);
    WidgetImage [] widgetImages = widgetImageController.getwidgetImageByMarketListing(heldListing);
    
    // TODO: Open an error page
    // TODO: Set user status by reading from a User server
    
    if(heldListing == null || heldListing.isDeleted() || heldListing.getQtyAvailable() == 0)
    {
    	return "redirect:/homePage";
    }
    
    if (heldListing.isDeleted())
    {
      throw new IllegalArgumentException("Attempted to access an invalid Market Listing!");
    }
    
    User user = userController.getCurrently_Logged_In();
    UserList myList = new UserList();
    myList.setOwner(user);
    
    String [] widgetNames = new String [widgetImages.length];
    
    //add the widget attributes to the model, such as name, description, color etc.
    Widget widget = heldListing.getWidgetSold();
    Set<WidgetAttribute> widgetAttributes = widget.getAttributes();

    for(int x = 0; x < widgetImages.length; x++)
    	widgetNames[x] = widgetImages[x].getImageName();

    //TODO: add the category stack to the model
    List<String> categoryStack = Arrays.asList("Automobiles", "Cars", widget.getCategory().getName());

    model.addAttribute("categories", categoryStack);
    model.addAttribute("images", widgetNames);
    model.addAttribute("attributes", widgetAttributes);
    reloadModel(model);
    return "viewMarketListing";
  }

  /**
   * Attempts to purchase from a MarketListing Opens the next page in the purchasing pipeline -
   * submitting the shipping address
   *
   * @param newTransaction a partially filled out Transaction holding the number of items to buy
   * @param model the page model - passed by dependency injection
   * @return the confirmShippingPage
   */
  @PostMapping({"/viewMarketListing/attemptPurchase"})
  public String attemptPurchase(
      @Validated Transaction newTransaction, BindingResult result, Model model) {
    // Initial validation - validation must also be done when updating the database
    if (newTransaction.getQtyBought() > heldListing.getQtyAvailable()) {
      System.out.println("failure 1");
      result.addError(
          new FieldError(
              "newTransaction",
              "qtyBought",
              "Cannot buy more items than the Market Listing quantity."));
    }
    // Errors found - refresh page
    if (result.hasErrors()) {
      reloadModel(model);
      return "viewMarketListing";
    }
    User user = userController.getCurrently_Logged_In();
    ShippingAddress address;
    Transaction purchaseAttempt = new Transaction();
    purchaseAttempt.setBuyer(userController.getCurrently_Logged_In());
    purchaseAttempt.setSeller(heldListing.getSeller());
    purchaseAttempt.setMarketListing(heldListing);
    purchaseAttempt.setQtyBought(newTransaction.getQtyBought());
    purchaseAttempt.setTotalPriceBeforeTaxes(
        heldListing.getPricePerItem().multiply(BigDecimal.valueOf(newTransaction.getQtyBought())));
    // Add shipping entry if user confirms purchase on next page
    purchaseAttempt.setShippingEntry(null);
    model.addAttribute("user", userController.getCurrently_Logged_In());
    return this.purchaseController.initializePurchasePage(heldListing, purchaseAttempt, model);
  }

  /**
   * Adds the current item to the User's wishlist
   *
   * @return the current viewMarketListing page
   */
  @PostMapping({"/viewMarketListing/wishlistItem"})
  public String wishlistItem() {
    userController.addToWishlist(heldListing.getWidgetSold());
    return "redirect:/viewMarketListing/" + heldListing.getId();
  }

  /**
   * Return to market listings
   *
   * @return the homePage
   */
  @PostMapping({"/viewMarketListing/returnToListings"})
  public String returnToListings() {
    return "redirect:/homePage";
  }

  /**
   * Administrative/seller functionality Edits the number of items, or its price
   *
   * @param marketListing holding the new MarketListing values
   * @return returns to the index
   */
  @PostMapping({"/viewMarketListing/editListing"})
  public String editListing(@Validated MarketListing marketListing, Model model) {
    marketListing.setSeller(heldListing.getSeller());
    marketListing.setTransactions(heldListing.getTransactions());
    marketListing.setWidgetSold(heldListing.getWidgetSold());
    marketListingController.editMarketListing(marketListing);
    return "redirect:/viewMarketListing/" + heldListing.getId();
  }

  /**
   * Deletes a market listing
   *
   * @return returns to the index
   */
  @RequestMapping({"/viewMarketListing/deleteListing/{id}"})
  public String deleteListing(@PathVariable long id, Model model) {
    // go to marketListingController and delete the listing (it has the repository there)
	marketListingController.deleteMarketListing(id);
    // redirect - if the user is an admin send to their search page
	User user = userController.getCurrently_Logged_In();
	if (user.getRole().equals("ROLE_ADMIN")
	        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
	        || user.getRole().equals("ROLE_TECHNICALSERVICE")
	        || user.getRole().equals("ROLE_SECURITY")
	        || user.getRole().equals("ROLE_SALES")
	        || user.getRole().equals("ROLE_ADMIN_SHADOW")
	        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
	        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
		return "redirect:/searchWidgetButton";
	}
	// otherwise return user home
    return "redirect:/homePage";
    // this code could potentially be improved to track where the user deleted it from and return
    // to that page specifically. look into doing that after overall site is more functional?
  }

  @RequestMapping({"/viewMarketListing/openMessage"})
  public String openMessagePane(Model model) {
    setPage("openMessage");
    reloadModel(model);
    model.addAttribute("page", page);
    return "viewMarketListing";
  }

  @RequestMapping({"/viewMarketListing/closeMessage"})
  public String closeMessagePane(Model model) {
    setPage("fail");
    reloadModel(model);
    model.addAttribute("page", page);
    return "viewMarketListing";
  }

  @RequestMapping({"/marketSendMail"})
  public String marketSendMessage(
      @RequestParam("message") String content2,
      @RequestParam("subject") String subject2,
      Model model) {
    User user = userController.getCurrently_Logged_In();
    User receiver = heldListing.getSeller();
    setPage("Success");
    model.addAttribute("page", page);

    if (subject2.length() == 0 || content2.length() == 0) {
      setPage("fail");
      reloadModel(model);
      model.addAttribute("page", page);
      return "viewMarketListing";
    }

    Message message = new Message();
    message.setOwner(user);
    message.setSender(user.getUsername());
    message.setContent(content2);
    message.setSubject(subject2);
    message.setMsgDate();
    message.setReceiverName(receiver.getUsername());
    message.setReceiver(receiver);
    msgcontrol.addMessage(message);
    emailController.messageEmail(receiver, user, message);
    reloadModel(model);
    model.addAttribute("page", page);
    return "viewMarketListing";
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }
}
