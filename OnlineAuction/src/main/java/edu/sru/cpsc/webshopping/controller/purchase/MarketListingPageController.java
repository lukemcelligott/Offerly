package edu.sru.cpsc.webshopping.controller.purchase;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sru.cpsc.webshopping.controller.EmailController;
import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.UserListDomainController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.controller.WidgetImageController;
import edu.sru.cpsc.webshopping.controller.billing.SellerRatingController;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.Bid;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.UserList;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetAttribute;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.service.AuctionService;
import edu.sru.cpsc.webshopping.service.CategoryService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.WatchlistService;
import lombok.extern.slf4j.Slf4j;

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
  EmailController emailController;
  ConfirmPurchasePageController purchaseController;
  SellerRatingController ratingController;
  UserListDomainController userListController;
  WatchlistService watchlistService;
  // Repositories passed to ConfirmPurchasePage
  TransactionController transController;
  WidgetController widgetController;
  private String page;
  private Widget tempWidget;
  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private UserService userService;
  
  
  public MarketListingPageController(
      MarketListingDomainController marketListingController,
      TransactionController transController,
      UserController userController,
      PurchaseShippingAddressPageController shippingPage,
      EmailController emailController,
      WidgetController widgetController,
      WidgetImageController widgetImageController,
      SellerRatingController ratingController,
      UserListDomainController userListController,
      ConfirmPurchasePageController purchaseController,
      WatchlistService watchlistService) {
	  this.marketListingController = marketListingController;
	  this.transController = transController;
	  this.userController = userController;
	  this.purchaseController = purchaseController;
	  this.shippingPage = shippingPage;
	  this.widgetImageController = widgetImageController;
	  this.emailController = emailController;
	  this.widgetController = widgetController;
	  this.ratingController = ratingController;
	  this.userListController = userListController;
	  this.watchlistService = watchlistService;
  }

  /** Reloads the page model data */
  public void reloadModel(Model model, User user) {

    model.addAttribute("currListing", heldListing);
    model.addAttribute("widget", heldListing.getWidgetSold());
    model.addAttribute("category", heldListing.getWidgetSold().getCategory());

    // Check if User is the owner of the market listing
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
  public String viewMarketListingPage(@PathVariable("marketListingId") long marketListingId, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    heldListing = marketListingController.getMarketListing(marketListingId);
    WidgetImage [] widgetImages = widgetImageController.getwidgetImageByMarketListing(heldListing);
    
    // TODO: Open an error page
    // TODO: Set user status by reading from a User server
    
    if(heldListing == null || heldListing.isDeleted() || heldListing.getQtyAvailable() == 0) {
    	return "redirect:/homePage";
    }
    
    if (heldListing.isDeleted()) {
      throw new IllegalArgumentException("Attempted to access an invalid Market Listing!");
    }

    UserList myList = new UserList();
    myList.setOwner(user);
    
    String [] widgetNames = new String [widgetImages.length];
    
    //add the widget attributes to the model, such as name, description, color etc.
    Widget widget = heldListing.getWidgetSold();
    Set<WidgetAttribute> widgetAttributes = widget.getAttributes();

    for(int x = 0; x < widgetImages.length; x++)
    	widgetNames[x] = widgetImages[x].getImageName();

    // Generate the category stack for the widget
    List<String> categoryStack = categoryService.generateCategoryStack(widget.getCategory());
    
    // check if the product is in the user's watchlist
    boolean foundInWatchlist = false;
    Set<MarketListing> wishlist = user.getWishlistedWidgets();
    for(MarketListing element : wishlist) {
    	if(marketListingId == element.getId()) {
    		foundInWatchlist = true;
    	}
    }

    // get how many people are watching the item
    Long userCount = watchlistService.countUsersWithMarketListingInWatchlist(marketListingId);

    model.addAttribute("categories", categoryStack);
    model.addAttribute("images", widgetNames);
    model.addAttribute("attributes", widgetAttributes);
    model.addAttribute("foundInWatchlist", foundInWatchlist);
    model.addAttribute("currentUser", user);
    model.addAttribute("userCount", userCount);

    reloadModel(model, user);
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
  public String attemptPurchase(@Validated Transaction newTransaction, BindingResult result, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
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
      reloadModel(model, user);
      return "viewMarketListing";
    }
    ShippingAddress address;
    Transaction purchaseAttempt = new Transaction();
    purchaseAttempt.setBuyer(user);
    purchaseAttempt.setSeller(heldListing.getSeller());
    purchaseAttempt.setMarketListing(heldListing);
    purchaseAttempt.setQtyBought(newTransaction.getQtyBought());
    purchaseAttempt.setTotalPriceBeforeTaxes(
        heldListing.getPricePerItem().multiply(BigDecimal.valueOf(newTransaction.getQtyBought())));
    // Add shipping entry if user confirms purchase on next page
    purchaseAttempt.setShippingEntry(null);
    model.addAttribute("user", user);
    return this.purchaseController.initializePurchasePage(heldListing, purchaseAttempt, model, principal);
  }

  /**
   * Adds the current item to the User's wishlist
   *
   * @param the market listing id of the current product
   * @return the current viewMarketListing page
   */
  @PostMapping({"/viewMarketListing/wishlistItem/{marketListingId}"})
  public String wishlistItem(@PathVariable("marketListingId") long marketListingId, Model model, Principal principal) {
	  // define held listing as the targeted listing bby passing in the market listing ID
	  heldListing = marketListingController.getMarketListing(marketListingId);
	  // call addToWishlist in UserController.java and pass in the widget assigned to heldListing

	  userController.addToWishlist(heldListing, principal);

	  // redirect the user to the listing for the widget
	  return "redirect:/viewMarketListing/" + heldListing.getId();
  }
  
  /**
   * Removes the current item from the User's wishlist
   *
   * @param the market listing id of the current product
   * @return the current viewMarketListing page
   */
  @PostMapping({"/viewMarketListing/delWishlistItem/{marketListingId}"})
  public String delWishlistItem(@PathVariable("marketListingId") long marketListingId, Model model, Principal principal) {
	  // define held listing as the targeted listing bby passing in the market listing ID
	  heldListing = marketListingController.getMarketListing(marketListingId);
	  // call removeFromWishlist in UserController.java and pass in the widget assigned to heldListing
	  userController.removeFromWishlist(heldListing, principal);
	  // redirect the user to the listing
 	  return "redirect:/viewMarketListing/" + heldListing.getId();
  }
  
  /**
   * Gets all of the User's wishlisted items
   *
   * @param the market listing id of the current product
   * @return the current viewMarketListing page
   */
  @RequestMapping({"/viewWishlist"})
  public String getWishlist(Principal principal) {
	  // get the currently logged in user
	  User user = userService.getUserByUsername(principal.getName());
	  
	  user.getWishlistedWidgets();
	  
	  // redirect the user to the watchlist.html page
	  return "watchlist";
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
    marketListingController.editMarketListing(marketListing);
    return "redirect:/viewMarketListing/" + marketListing.getId();
  }

  /**
   * Deletes a market listing
   *
   * @return returns to the index
   */
  @RequestMapping({"/viewMarketListing/deleteListing/{id}"})
  public String deleteListing(@PathVariable long id, Model model, Principal principal) {
    // go to marketListingController and delete the listing (it has the repository there)
    marketListingController.deleteMarketListing(id);
      // redirect - if the user is an admin send to their search page
    User user = userService.getUserByUsername(principal.getName());
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
  

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }
}
