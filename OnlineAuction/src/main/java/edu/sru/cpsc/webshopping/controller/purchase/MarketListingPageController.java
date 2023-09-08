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
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Blender;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Blender_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Dryer_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Dryers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Microwave;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Microwave_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Refrigerator;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Refrigerator_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Washers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Washers_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Widget_Appliance;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Widget_Appliance_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Widget_Electronics;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Widget_Electronics_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Widget_Vehicles;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Widget_Vehicles_Parts;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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
	/*
	 * if (tempWidget.getCategory().contentEquals("appliance")) { Widget_Appliance
	 * appliance = widgetController.getAppliance(tempWidget.getId());
	 * model.addAttribute("appliance", appliance); if
	 * (tempWidget.getSubCategory().contentEquals("washer")) { Appliance_Washers
	 * washer = widgetController.getWasher(tempWidget.getId());
	 * model.addAttribute("washer", washer); } if
	 * (tempWidget.getSubCategory().contentEquals("dryer")) { Appliance_Dryers dryer
	 * = widgetController.getDryer(tempWidget.getId()); model.addAttribute("dryer",
	 * dryer); } if (tempWidget.getSubCategory().contentEquals("fridge")) {
	 * Appliance_Refrigerator fridge =
	 * widgetController.getRefrigerator(tempWidget.getId());
	 * model.addAttribute("fridge", fridge); } if
	 * (tempWidget.getSubCategory().contentEquals("microwave")) {
	 * Appliance_Microwave microwave =
	 * widgetController.getMicrowave(tempWidget.getId());
	 * model.addAttribute("microwave", microwave); } if
	 * (tempWidget.getSubCategory().contentEquals("blender")) { Appliance_Blender
	 * blender = widgetController.getBlender(tempWidget.getId());
	 * model.addAttribute("blender", blender); } }
	 * 
	 * if (tempWidget.getCategory().contentEquals("appliance_parts")) {
	 * Widget_Appliance_Parts applianceParts =
	 * widgetController.getApplianceParts(tempWidget.getId());
	 * model.addAttribute("appliance_parts", applianceParts); if
	 * (tempWidget.getSubCategory().contentEquals("washer_parts")) {
	 * Appliance_Washers_Parts washerPart =
	 * widgetController.getWasherParts(tempWidget.getId());
	 * model.addAttribute("washer_parts", washerPart); } if
	 * (tempWidget.getSubCategory().contentEquals("dryer_parts")) {
	 * Appliance_Dryer_Parts dryerPart =
	 * widgetController.getDryerParts(tempWidget.getId());
	 * model.addAttribute("dryer_parts", dryerPart); } if
	 * (tempWidget.getSubCategory().contentEquals("refrigerator_parts")) {
	 * Appliance_Refrigerator_Parts refrigeratorPart =
	 * widgetController.getRefrigeratorParts(tempWidget.getId());
	 * model.addAttribute("refrigerator_parts", refrigeratorPart); } if
	 * (tempWidget.getSubCategory().contentEquals("microwave_parts")) {
	 * Appliance_Microwave_Parts microwavePart =
	 * widgetController.getMicrowaveParts(tempWidget.getId());
	 * model.addAttribute("microwave_parts", microwavePart); } if
	 * (tempWidget.getSubCategory().contentEquals("blender_parts")) {
	 * Appliance_Blender_Parts blenderPart =
	 * widgetController.getBlenderParts(tempWidget.getId());
	 * model.addAttribute("blender_parts", blenderPart); } }
	 * 
	 * if (tempWidget.getCategory().contentEquals("electronic")) {
	 * Widget_Electronics electronic =
	 * widgetController.getElectronic(tempWidget.getId());
	 * System.out.println(electronic.getName()); model.addAttribute("electronic",
	 * electronic); if (tempWidget.getSubCategory().contentEquals("videoGame")) {
	 * Electronics_VideoGames videoGame =
	 * widgetController.getVideoGame(tempWidget.getId());
	 * model.addAttribute("videoGame", videoGame); } if
	 * (tempWidget.getSubCategory().contentEquals("computer")) {
	 * Electronics_Computers computer =
	 * widgetController.getComputer(tempWidget.getId());
	 * System.out.println(computer.getName()); model.addAttribute("computer",
	 * computer); } }
	 * 
	 * if (tempWidget.getCategory().contentEquals("electronic_parts")) {
	 * Widget_Electronics_Parts electronicPart =
	 * widgetController.getElectronicParts(tempWidget.getId());
	 * System.out.println(electronicPart.getName());
	 * model.addAttribute("electronic_parts", electronicPart); if
	 * (tempWidget.getSubCategory().contentEquals("videoGame_parts")) {
	 * Electronics_VideoGames_Parts videoGamePart =
	 * widgetController.getVideoGameParts(tempWidget.getId());
	 * System.out.println(videoGamePart.getMaterial() + " I AM REAL");
	 * model.addAttribute("videoGame_parts", videoGamePart); } if
	 * (tempWidget.getSubCategory().contentEquals("computer_parts")) {
	 * Electronics_Computers_Parts computerPart =
	 * widgetController.getComputerParts(tempWidget.getId());
	 * System.out.println(computerPart.getName());
	 * model.addAttribute("computer_parts", computerPart); } }
	 * 
	 * if (tempWidget.getCategory().contentEquals("vehicle")) { Widget_Vehicles
	 * vehicle = widgetController.getVehicle(tempWidget.getId());
	 * model.addAttribute("vehicle", vehicle); if
	 * (tempWidget.getSubCategory().contentEquals("car")) { Vehicle_Car car =
	 * widgetController.getCar(tempWidget.getId()); model.addAttribute("car", car);
	 * } }
	 * 
	 * if (tempWidget.getCategory().contentEquals("vehicle_parts")) {
	 * Widget_Vehicles_Parts vehiclePart =
	 * widgetController.getVehicleParts(tempWidget.getId());
	 * model.addAttribute("vehicle_parts", vehiclePart); if
	 * (tempWidget.getSubCategory().contentEquals("car_parts")) { Vehicle_Car_Parts
	 * carPart = widgetController.getCarParts(tempWidget.getId());
	 * model.addAttribute("car_parts", carPart); } }
	 * 
	 * if (tempWidget.getCategory().contentEquals("lawnCare")) { Widget_LawnCare
	 * lawnCare = widgetController.getLawnCare(tempWidget.getId());
	 * model.addAttribute("lawnCare", lawnCare); if
	 * (tempWidget.getSubCategory().contentEquals("lawnMower")) { LawnCare_LawnMower
	 * mower = widgetController.getMower(tempWidget.getId());
	 * model.addAttribute("mower", mower); } }
	 * 
	 * if (tempWidget.getCategory().contentEquals("lawnCare_parts")) {
	 * Widget_LawnCare_Parts lawnCarePart =
	 * widgetController.getLawnCareParts(tempWidget.getId());
	 * model.addAttribute("lawnCare_parts", lawnCarePart); if
	 * (tempWidget.getSubCategory().contentEquals("mower_parts")) {
	 * LawnCare_LawnMower_Parts mowerPart =
	 * widgetController.getMowerParts(tempWidget.getId());
	 * model.addAttribute("mower_parts", mowerPart); } }
	 */
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

    model.addAttribute("sellerRating", ratingController.determineRating(heldListing.getSeller()));
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
    
    for(int x = 0; x < widgetImages.length; x++)
    	widgetNames[x] = widgetImages[x].getImageName();

    model.addAttribute("page", "");
    model.addAttribute("images", widgetNames);
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
  @PostMapping({"/viewMarketListing/wishlistItem/{marketListingId}"})
  public String wishlistItem(@PathVariable("marketListingId") long marketListingId, Model model) {
	  // define held listing as the targeted listing bby passing in the market listing ID
	  heldListing = marketListingController.getMarketListing(marketListingId);
	  // call addToWishlist in UserController.java and pass in the widget assigned to heldListing
	  userController.addToWishlist(heldListing.getWidgetSold());
	  // redirect the user to the listing for the widget (maybe change this to a 'widget added to watchlist page')
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
