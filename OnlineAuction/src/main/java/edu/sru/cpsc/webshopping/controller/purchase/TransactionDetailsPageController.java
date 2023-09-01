package edu.sru.cpsc.webshopping.controller.purchase;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sru.cpsc.webshopping.controller.ShippingDomainController;
import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.domain.market.Shipping;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Dryers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Microwave;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Refrigerator;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Washers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Widget_Appliance;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Widget_Electronics;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Widget_Vehicles;

/**
 * Manages the transactionDetails page
 * This is used for viewing information about a particular transaction, and for allowing shipping information to be added by
 * the seller
 */
@Controller
public class TransactionDetailsPageController {
	private UserController userController;
	private WidgetController widgetController;
	private TransactionController transController;
	private ShippingDomainController shippingController;
	private Shipping origEntry;
	private Transaction trans;
	
	TransactionDetailsPageController(UserController userController, TransactionController transController,
			ShippingDomainController shippingController, WidgetController widgetController) {
		this.userController = userController;
		this.transController = transController;
		this.shippingController = shippingController;
		this.widgetController = widgetController;
	}
	
	/**
	 * Initializes the viewTransaction page
	 * @param transId the Id of the Transaction to view
	 * @param model the View model, added by dependency injection
	 * @return the transactionDetails page string
	 */
	@RequestMapping({"/viewTransactionDetails/{transId}"})
	public String purchaseDetails(@PathVariable("transId") long transId, Model model) {
		trans = transController.getTransaction(transId);
		// Check that user is valid
		if (userController.getCurrently_Logged_In() == null ||
			(userController.getCurrently_Logged_In().getId() != trans.getSeller().getId() &&
			userController.getCurrently_Logged_In().getId() != trans.getBuyer().getId()))
			throw new IllegalStateException("Invalid user.");
		origEntry = shippingController.getShippingEntry(trans.getShippingEntry().getId());
		reloadModel(model);
		return "transactionDetails";
	}
	
	private void reloadModel(Model model) {
		Widget tempWidget = trans.getMarketListing().getWidgetSold();
		if(tempWidget.getCategory().contentEquals("appliance"))
		{
			Widget_Appliance appliance = widgetController.getAppliance(tempWidget.getId());
			model.addAttribute("appliance", appliance);
			if(tempWidget.getSubCategory().contentEquals("washer"))
			{
				Appliance_Washers washer = widgetController.getWasher(tempWidget.getId());
				model.addAttribute("washer", washer);
			}
			if(tempWidget.getSubCategory().contentEquals("dryer"))
			{
				Appliance_Dryers dryer = widgetController.getDryer(tempWidget.getId());
				model.addAttribute("dryer", dryer);
			}
			if(tempWidget.getSubCategory().contentEquals("fridge"))
			{
				Appliance_Refrigerator fridge = widgetController.getRefrigerator(tempWidget.getId());
				model.addAttribute("fridge", fridge);
			}
			if(tempWidget.getSubCategory().contentEquals("microwave"))
			{
				Appliance_Microwave microwave = widgetController.getMicrowave(tempWidget.getId());
				model.addAttribute("microwave", microwave);
			}
		}
		if(tempWidget.getCategory().contentEquals("electronic"))
		{
			Widget_Electronics electronic = widgetController.getElectronic(tempWidget.getId());
			System.out.println(electronic.getName());
			model.addAttribute("electronic", electronic);
			if(tempWidget.getSubCategory().contentEquals("videoGame"))
			{
				Electronics_VideoGames videoGame = widgetController.getVideoGame(tempWidget.getId());
				model.addAttribute("videoGame", videoGame);
			}
			if(tempWidget.getSubCategory().contentEquals("computer"))
			{
				Electronics_Computers computer = widgetController.getComputer(tempWidget.getId());
				System.out.println(computer.getName());
				model.addAttribute("computer", computer);
			}
		}
		if(tempWidget.getCategory().contentEquals("vehicle"))
		{
			Widget_Vehicles vehicle = widgetController.getVehicle(tempWidget.getId());
			model.addAttribute("vehicle", vehicle);
			if(tempWidget.getSubCategory().contentEquals("car"))
			{
				Vehicle_Car car = widgetController.getCar(tempWidget.getId());
				model.addAttribute("car", car);
			}
		}
		if(tempWidget.getCategory().contentEquals("lawnCare"))
		{
			Widget_LawnCare lawnCare = widgetController.getLawnCare(tempWidget.getId());
			model.addAttribute("lawnCare", lawnCare);
			if(tempWidget.getSubCategory().contentEquals("lawnMower"))
			{
				LawnCare_LawnMower mower = widgetController.getMower(tempWidget.getId());
				model.addAttribute("mower", mower);
			}
		}
		model.addAttribute("widget", tempWidget);
		model.addAttribute("trans", trans);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		model.addAttribute("shipping", new Shipping());
		model.addAttribute("currentDate", LocalDate.now());
		model.addAttribute("canDelete", canDeleteTransaction());
	}
	
	/**
	 * Returns to homePage
	 * @return redirection string to homepPage
	 */
	@RequestMapping({"viewTransactionDetails/returnToHome"})
	public String returnToHome() {
		return "redirect:/homePage";
	}
	
	/**
	 * Submits updates to shipping information
	 * @return redirection string to homePage
	 */
	@RequestMapping({"/viewTransactionDetails/submitShippingUpdate"})
	public String submitShippingUpdate(@Validated @ModelAttribute("shipping") Shipping form, BindingResult result, Model model) {
		// Form error or server-side validation error found
		if (result.hasErrors() || ShippingConstraintsInvalid(form)) {
			System.out.println(form.getCarrier());
			System.out.println(form.getShippingDate());
			System.out.println(form.getArrivalDate());
			model.addAttribute("trans", trans);
			model.addAttribute("user", userController.getCurrently_Logged_In());
			model.addAttribute("shipping", new Shipping());
			// Produce error messages
			System.out.println(form.getCarrier().length());
			if (result.hasErrors()  || ShippingConstraintsInvalid(form)) {
				model.addAttribute("errMessage", "Form must be fully filled out before submission.");
				if (form.getCarrier() == null || form.getCarrier().length() == 0) 
					model.addAttribute("carrierErr", "Carrier field cannot be empty.");
				if (form.getShippingDate() == null)
					model.addAttribute("shippingDateErr", "Shipping Date cannot be empty.");
				if (form.getArrivalDate() == null)
					model.addAttribute("arrivalDateErr", "Arrival Date cannot be empty.");
				model.addAttribute("canDelete", canDeleteTransaction());
			}
			else if (form.getArrivalDate().compareTo(form.getShippingDate()) < 0) {
				model.addAttribute("errMessage", "Arrival Date must be after the Shipping Date.");
			}
			reloadModel(model);
			return "transactionDetails";
		}
		// Add items to form that the seller cannot modify
		form.setId(origEntry.getId());
		form.setAddress(origEntry.getAddress());
		shippingController.editShippingEntry(form);
		return "redirect:/homePage";
	}
	
	/**
	 * Tests if a form is invalid, using constraints that cannot be performed using Validation annotations
	 * @param form The Shipping form to evaluate
	 * @return true if the form is invalid, false if it is valid
	 */
	public boolean ShippingConstraintsInvalid(Shipping form) {

		return (form.getArrivalDate().compareTo(form.getShippingDate()) < 0) //arrival must be after shipping date
				 || form.getArrivalDate() == null || form.getShippingDate() == null //needs an arrival and shipping date
				 || form.getCarrier() == null || form.getCarrier().length() == 0; //must have a carrier
	}
	
	/**
	 * Tests if the loaded Transaction can be cancelled
	 * @return true if the loaded Transaction can be cancelled, false otherwise
	 */
	public boolean canDeleteTransaction() {
		if (trans.getShippingEntry().hasShipped())
			return false;
		return true;
	}
	
	/**
	 * Attempts to delete the associated Transaction
	 * @return returns true if the Transaction is deleted, false otherwise
	 */
	@RequestMapping({"/viewTransactionDetails/deleteTransaction"})
	public String deleteTransaction(Model model) {
		if (canDeleteTransaction()) {
			boolean cancelSuccess = transController.cancelTransaction(trans);
			if (cancelSuccess)
				return "redirect:/homePage";
		}
		// Failed, so we reload the page
		model.addAttribute("errMessage", "Failed to cancel transaction.");
		model.addAttribute("trans", trans);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		model.addAttribute("shipping", new Shipping());
		model.addAttribute("currentDate", LocalDate.now());
		model.addAttribute("canDelete", canDeleteTransaction());
		reloadModel(model);
		return "transactionDetails";
	}
}
