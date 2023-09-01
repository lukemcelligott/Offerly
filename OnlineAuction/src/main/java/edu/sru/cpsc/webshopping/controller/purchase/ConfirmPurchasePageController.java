package edu.sru.cpsc.webshopping.controller.purchase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.rates.RateResponse;
import com.taxjar.model.taxes.TaxResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.controller.billing.PaymentDetailsController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.UserDetailsController;
import edu.sru.cpsc.webshopping.controller.billing.CardTypeController;
import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails_Form;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails_Form;
import edu.sru.cpsc.webshopping.domain.billing.Paypal;
import edu.sru.cpsc.webshopping.domain.billing.Paypal_Form;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Shipping;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.market.ShippingRepository;
import edu.sru.cpsc.webshopping.repository.market.TransactionRepository;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;

/**
 * Manages functionality for the confirmPurchase page This page is used to
 * verify payment details, and to submit the purchase/ create the associated
 * transaction
 */
@Controller
public class ConfirmPurchasePageController {
	private Transaction purchase;
	private MarketListing prevListing;
	private PaymentDetails details;
	private PaymentDetailsRepository payDetRepository;
	private ShippingAddress address;
	private Paypal_Form paypal;
	
	@Lazy
	private PurchaseShippingAddressPageController shippingController;
	// SQL Controllers
	private TransactionController transController;
	private PaymentDetailsController payDetController;
	private MarketListingDomainController marketListingController;
	private UserController userController;
	private CardTypeController cardController;
	private UserDetailsController userDetController;
	private boolean allSelected = false;
	private boolean modifyPayment = false;
	private boolean relogin = true;
	private boolean depositPicked = false;
	private boolean loginEr = false;
	private PaymentDetails validatedDetails;
	private boolean toShipping = false;
	private Taxjar client = new Taxjar("639588118ed6ccfd8af4d3a26ad50970");

	ConfirmPurchasePageController(MarketListingDomainController marketListingController, UserController userController,
			TransactionController transController, UserDetailsController userDetController,
			CardTypeController cardController, PaymentDetailsController payDetController,
			PaymentDetailsRepository payDetRepository, PurchaseShippingAddressPageController shippingController) {
		this.marketListingController = marketListingController;
		this.payDetController = payDetController;
		this.payDetRepository = payDetRepository;
		this.userController = userController;
		this.transController = transController;
		this.cardController = cardController;
		this.userDetController = userDetController;
		this.shippingController = shippingController;
	}
	
	/**
	 * initializes the purchase page by setting the variables to there defaults
	 * used when the page is reopened from anywhere but itself
	 * @param address
	 * @param prevListing
	 * @param purchaseOrder
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/initializePurchasePage")
	public String initializePurchasePage(MarketListing prevListing, Transaction purchaseOrder, Model model) {
		
		System.out.println(address);
		// Setup purchase with total price and profit
		if(purchaseOrder != null)
			this.purchase = purchaseOrder;
		if(prevListing != null)
			this.prevListing = prevListing;
		toShipping = false; //resets all variables to there default
		modifyPayment = false;
		allSelected = false;
		relogin = true;
		loginEr = false;
		
		User user = userController.getCurrently_Logged_In();
		if(user.getDefaultShipping() != null) //when the page is initialized then use the users default if it exists
	    	address = user.getDefaultShipping();
	    else
	    	address = null;
		
		if(this.address != null) //use the taxjar api to get the state and local sales tax information (if there is an address than tax information can be calculated)
		{
			try
			{
				RateResponse res = client.ratesForLocation(this.address.getPostalCode());
				
				purchase.setTotalPriceAfterTaxes(purchase.getTotalPriceBeforeTaxes().add(purchase.getTotalPriceBeforeTaxes().multiply(new BigDecimal(res.rate.getCombinedRate().toString())).setScale(2, RoundingMode.UP)));
				purchase.setSellerProfit(purchase.getTotalPriceAfterTaxes().subtract(purchase.getTotalPriceAfterTaxes().multiply(Transaction.WEBSITE_CUT_PERCENTAGE)));
			}
			catch(TaxjarException e)
			{
				e.printStackTrace();
			}
		}
		
		details = new PaymentDetails();
		paypal = new Paypal_Form();
		if(validatedDetails == null)
			validatedDetails = user.getDefaultPaymentDetails();
		if (this.address == null)
			model.addAttribute("selectedAddress", null);
		else
			model.addAttribute("selectedAddress", this.address);
		model.addAttribute("purchase", purchase);
		model.addAttribute("marketListing", this.prevListing);
		model.addAttribute("widget", this.prevListing.getWidgetSold());
		model.addAttribute("paymentDetails", details);
		model.addAttribute("cardTypes", cardController.getAllCardTypes());
		model.addAttribute("paypal", paypal);
		model.addAttribute("depositPicked", depositPicked);
		model.addAttribute("modifyPayment", modifyPayment);
		model.addAttribute("selectedPayment", validatedDetails);
		model.addAttribute("toShipping", toShipping);
		model.addAttribute("allSelected", allSelected);
		model.addAttribute("relogin", relogin);
		model.addAttribute("loginEr", loginEr);
		model.addAttribute("user", user);
		model.addAttribute("defaultDetails", user.getDefaultPaymentDetails());
		if ((user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty()) || user.getPaymentDetails() == null)
			model.addAttribute("allDetails", null);
		else
			model.addAttribute("allDetails", payDetController.getPaymentDetailsByUser(user));
		model.addAttribute("existingSecurityCode", new String());
		return "confirmPurchase";
	}

	/**
	 * Initializes the confirmPurchase page
	 * @param address
	 * @param prevListing
	 * @param purchaseOrder
	 * @param model
	 * @param persisted
	 * @return
	 */
	@RequestMapping("/confirmPurchase")
	public String openConfirmPurchasePage(ShippingAddress address, MarketListing prevListing, Transaction purchaseOrder,
			Model model) {
		// Setup purchase with total price and profit
		toShipping = false;
		System.out.println(address);
		
		if(address != null && address.getState() != null)
			this.address = address;
		else if(prevListing != null && address == null)
			this.address = null;
		

		
		// Prepare a form for verifying the user's payment details
		details = new PaymentDetails();
		paypal = new Paypal_Form();
		User user = userController.getCurrently_Logged_In();
		if(validatedDetails == null)
			validatedDetails = user.getDefaultPaymentDetails();
		if(user.getDefaultShipping() == null && this.address == null)
			this.address = null;
		if (this.address == null)
			model.addAttribute("selectedAddress", null);
		else
			model.addAttribute("selectedAddress", this.address);
		model.addAttribute("purchase", purchase);
		model.addAttribute("marketListing", this.prevListing);
		model.addAttribute("widget", this.prevListing.getWidgetSold());
		model.addAttribute("paymentDetails", details);
		model.addAttribute("cardTypes", cardController.getAllCardTypes());
		model.addAttribute("paypal", paypal);
		model.addAttribute("depositPicked", depositPicked);
		model.addAttribute("modifyPayment", modifyPayment);
		model.addAttribute("selectedPayment", validatedDetails);
		model.addAttribute("toShipping", toShipping);
		model.addAttribute("allSelected", allSelected);
		model.addAttribute("relogin", relogin);
		model.addAttribute("loginEr", loginEr);
		model.addAttribute("user", user);
		model.addAttribute("defaultDetails", user.getDefaultPaymentDetails());
		if (user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty())
			model.addAttribute("allDetails", null);
		else
			model.addAttribute("allDetails", payDetController.getPaymentDetailsByUser(user));
		model.addAttribute("existingSecurityCode", new String());
		return "confirmPurchase";
	}
	
	
	/**
	 * Confirm the existing card that the user wishes to use by asking for the cards security code and verifying it
	 *  Allow the user to use their currently saved Card details by validating using
	 * the security code If successful, the associated Transaction is saved to the
	 * database, and the number of available items for the MarketListing is
	 * decreased by the number of purchased items The variables are passed by
	 * dependency injection
	 * @param id
	 * @param existingSecurityCode
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/confirmPurchase/existingCard", method = RequestMethod.POST, params = "submit")
	public String submitPurchaseExistingCard(@Validated @ModelAttribute("selected_payment_details") Long id,
			@Validated @ModelAttribute("existingSecurityCode") String existingSecurityCode, BindingResult result,
			Model model) {
		if (this.userController.getCurrently_Logged_In() == null) {
			throw new IllegalStateException("Cannot purchase an item when not logged in.");
		}
		allSelected = false;
		// Test that payment details are valid
		System.out.println(id);
		if (id != null && payDetController.matchExistingCard(existingSecurityCode, payDetRepository.findById(id).get()) || id == userController.getCurrently_Logged_In().getDefaultPaymentDetails().getId()) {
			validatedDetails = payDetRepository.findById(id).get();
			if(this.address != null && validatedDetails != null)
				allSelected = true;
			modifyPayment = false;
			return "redirect:/confirmPurchase";
		}
		
		// Transaction failed - post error
		else {
			if (address == null)
				model.addAttribute("selectedAddress", null);
			else
				model.addAttribute("selectedAddress", address);
			details = new PaymentDetails();
			// Build credit card error message
			User user = userController.getCurrently_Logged_In();
			model.addAttribute("exSecurityCodeErr", "Security code doesn't match current user's saved card");
			model.addAttribute("purchase", purchase);
			if (address == null)
				model.addAttribute("noAddress", "Please enter a shipping address");
			model.addAttribute("marketListing", prevListing);
			model.addAttribute("widget", prevListing.getWidgetSold());
			model.addAttribute("selectedPayment", validatedDetails);
			model.addAttribute("errMessage", "Payment Details Invalid");
			model.addAttribute("paypal", paypal);
			model.addAttribute("paymentDetails", details);
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("user", user);
			model.addAttribute("relogin", relogin);
			model.addAttribute("depositPicked", depositPicked);
			model.addAttribute("loginEr", loginEr);
			model.addAttribute("modifyPayment", modifyPayment);
			model.addAttribute("toShipping", toShipping);
			model.addAttribute("allSelected", allSelected);
			model.addAttribute("defaultDetails", userController.getCurrently_Logged_In().getDefaultPaymentDetails());
			if (user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty())
				model.addAttribute("allDetails", null);
			else
				model.addAttribute("allDetails", payDetController.getPaymentDetailsByUser(user));
			model.addAttribute("existingSecurityCode", new String());
			return "confirmPurchase";
		}
	}


	/**
	 * Attempts to confirm the purchase If successful, the associated Transaction is
	 * saved to the database, and the number of available items for the
	 * MarketListing is decreased by the number of purchased items The variables are
	 * passed by dependency injection
	 * 
	 * @param paymentDetails the PaymentDetails from the form
	 * @param result         BindingResult associated with the form
	 * @param model          the page model
	 * @exception IllegalStateException if the user is not logged in
	 * @return a redirection to index, if purchase is successful, or the same page,
	 *         if verification fails
	 */
	@Transactional
	@RequestMapping(value = "/confirmPurchase/submitPurchase", method = RequestMethod.POST, params = "submit")
	public String submitPurchase(@Validated @ModelAttribute("paymentDetails") PaymentDetails_Form paymentDetails,
			BindingResult result, Model model) {
		PaymentDetails currDetails = new PaymentDetails();
		allSelected = false;
		currDetails.buildFromForm(paymentDetails);
		if (this.userController.getCurrently_Logged_In() == null) {
			throw new IllegalStateException("Cannot purchase an item when not logged in.");
		}
		// Test that payment details are valid
		if (!paymentDetailsInvalid(paymentDetails) && !result.hasErrors()) {
			// add the card to the database if it's new
			if (!payDetController.checkDuplicateCard(currDetails)) {
				payDetController.addPaymentDetails(currDetails);
				System.out.println("option 1");
			} else {
				currDetails = payDetController.getPaymentDetailsByCardNumberAndExpirationDate(currDetails);
				System.out.println(currDetails.getId());
			}
			if (address != null)
				allSelected = true;
			
			User user = userController.getCurrently_Logged_In();
			Set<PaymentDetails> PD = user.getPaymentDetails();
			if(PD == null)
				PD = new HashSet<PaymentDetails>();
			PD.add(currDetails);
			user.setPaymentDetails(PD);
			if(user.getDefaultPaymentDetails() == null)
				user.setDefaultPaymentDetails(currDetails);
			currDetails.setUser(user);
			payDetController.addPaymentDetails(currDetails);
			modifyPayment = false;
			relogin = true;
			validatedDetails = currDetails;
			return "redirect:/confirmPurchase";
		}
		// Transaction failed - post error
		else {
			if (address == null)
				model.addAttribute("selectedAddress", null);
			else
				model.addAttribute("selectedAddress", address);
			details = new PaymentDetails();
			// Build credit card error message
			for (FieldError item : result.getFieldErrors()) {
				model.addAttribute(item.getField() + "Err", item.getDefaultMessage());
			}
			if (paymentDetails.getExpirationDate() != null && paymentDetailsExpired(paymentDetails)) {
				model.addAttribute("cardError", "The Credit Card has expired.");
			}
			if (userDetController.cardFarFuture(paymentDetails) && paymentDetails.getExpirationDate() != "")
				model.addAttribute("cardError", "The expiration date is an impossible number of years in the future");

			User user = userController.getCurrently_Logged_In();
			if (address == null)
				model.addAttribute("noAddress", "Please enter a shipping address");
			model.addAttribute("purchase", purchase);
			model.addAttribute("marketListing", prevListing);
			model.addAttribute("widget", prevListing.getWidgetSold());
			model.addAttribute("paymentDetails", details);
			model.addAttribute("errMessage", "Payment Details Invalid");
			model.addAttribute("paypal", paypal);
			model.addAttribute("modifyPayment", modifyPayment);
			model.addAttribute("selectedPayment", validatedDetails);
			model.addAttribute("toShipping", toShipping);
			model.addAttribute("useThis", true);
			model.addAttribute("depositPicked", depositPicked);
			model.addAttribute("relogin", relogin);
			model.addAttribute("loginEr", loginEr);
			model.addAttribute("allSelected", allSelected);
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("user", user);
			model.addAttribute("defaultDetails", userController.getCurrently_Logged_In().getDefaultPaymentDetails());
			if (user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty())
				model.addAttribute("allDetails", null);
			else
				model.addAttribute("allDetails", payDetController.getPaymentDetailsByUser(user));
			model.addAttribute("existingSecurityCode", new String());
			return "confirmPurchase";
		}
	}

	/**
	 * Cancels the purchase The user is returned to the viewMarketListing page they
	 * attempted to purchase from No changes are made
	 * 
	 * @param paymentDetails passed by the form
	 * @return a redirection to the viewMarketListing page associated with the
	 *         listing they attempted to purchase from
	 */
	@RequestMapping(value = "/confirmPurchase/submitPurchase", method = RequestMethod.POST, params = "cancel")
	public String cancelPurchase(@Validated @ModelAttribute("paymentDetails") PaymentDetails paymentDetails) {
		allSelected = false;
		modifyPayment = false;
		toShipping = false;
		relogin = true;
		loginEr = false;
		return "redirect:/confirmPurchase";
	}

	/**
	 * Attempts to submit the purchase via Paypal
	 * 
	 * @param paypal Validated Paypal form
	 * @param result BindingResult associated with Paypal form
	 * @param model  page model
	 * @return Returns to the index if successful, reloads the page if failed
	 */
	@RequestMapping(value = "/confirmPurchase/submitPurchasePaypal", method = RequestMethod.POST, params = "submit")
	public String submitPurchasePaypal(@Validated @ModelAttribute("paypal") Paypal_Form paypal, BindingResult result,
			Model model) {
		Paypal currPaypal = new Paypal();
		currPaypal.buildFromForm(paypal);
		if (this.userController.getCurrently_Logged_In() == null) {
			throw new IllegalStateException("Cannot purchase an item when not logged in.");
		}
		if (this.userController.verifyPaypalDetails(currPaypal)) {
			// Update market listing to reflect purchase
			marketListingController.marketListingPurchaseUpdate(prevListing, purchase.getQtyBought());
			// Creates an unfinished shipping label, to be filled out later by the seller
			// Preparing the transaction for posting to the database
			Shipping shipping = new Shipping();
			shipping.setTransaction(purchase);
			shipping.setAddress(address);
			purchase.setShippingEntry(shipping);
			transController.addTransaction(purchase);
			return "redirect:/homePage";
		} else {
			if (result.hasErrors()) { // Transaction failed - show errors
				// Build credit card error message
				for (FieldError item : result.getFieldErrors()) {
					model.addAttribute(item.getField() + "Err", item.getDefaultMessage());
				}
			}
			model.addAttribute("purchase", purchase);
			model.addAttribute("marketListing", prevListing);
			model.addAttribute("widget", prevListing.getWidgetSold());
			model.addAttribute("paymentDetails", details);
			model.addAttribute("errMessage", "Paypal Details Invalid");
			model.addAttribute("paypal", paypal);
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("user", userController.getCurrently_Logged_In());
			return "confirmPurchase";
		}
	}

	/**
	 * Attempts to cancel the purchase
	 * 
	 * @param paypal Validated Paypal form
	 * @return the MarketListing that the user attempted to purchase from
	 */
	@RequestMapping(value = "/confirmPurchase/submitPurchasePaypal", method = RequestMethod.POST, params = "cancel")
	public String cancelPurchasePaypal(@Validated @ModelAttribute("paypal") Paypal_Form paypal, BindingResult result,
			Model model) {
		System.out.println("cancel purchase paypal");
		return "redirect:/viewMarketListing/" + prevListing.getId();
	}

	/**
	 * Attempts to cancel the purchase
	 * 
	 * @param paypal Validated Paypal form
	 * @return the MarketListing that the user attempted to purchase from
	 */
	@RequestMapping(value = "/cancel-purchase")
	public String cancelPurchase() {
		System.out.println("cancel purchase paypal");
		return "redirect:/viewMarketListing/" + prevListing.getId();
	}
	
	/**
	 * Allows for the shipping information to be changed
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toShipping")
	public String toShipping(Model model) {
		PaymentDetails details = null;
		allSelected = false;
		toShipping = true;
		depositPicked = false;
		allSelected = false;
		if (userController.getCurrently_Logged_In().getDefaultPaymentDetails() != null)
			details = userController.getCurrently_Logged_In().getDefaultPaymentDetails();
		return this.shippingController.openConfirmShippingPage(true, prevListing, purchase, details, model);
	}
	
	/**
	 * prepares the page for a user modifying their payment details
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/modifyPayment")
	public String modifyPayment(Model model) {
		toShipping = false;
		depositPicked = false;
		allSelected = false;
		modifyPayment = true;
		return this.openConfirmPurchasePage(address, prevListing, purchase, model);
	}
	
	
	/**
	 * attempts to purchase the widget first checking if a shipping address and payment details are given
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/attemptPurchase")
	public String attemptPurchase(Model model) {
		if ((address != null && validatedDetails != null) || (address != null && depositPicked == true)) {
			BigDecimal salesTaxPercentage = this.address.getState().getSalesTaxRate().divide(new BigDecimal(100));
			BigDecimal afterSalesTax = purchase.getTotalPriceBeforeTaxes()
					.add(salesTaxPercentage.multiply(purchase.getTotalPriceBeforeTaxes())).setScale(2, RoundingMode.UP);
			purchase.setTotalPriceAfterTaxes(afterSalesTax);
			BigDecimal finalSellerProfit = afterSalesTax
					.subtract(afterSalesTax.multiply(Transaction.WEBSITE_CUT_PERCENTAGE));
			purchase.setSellerProfit(finalSellerProfit);
			// Update market listing to reflect purchase
			marketListingController.marketListingPurchaseUpdate(prevListing, purchase.getQtyBought());
			// Creates an unfinished shipping label, to be filled out later by the seller
			// Preparing the transaction for posting to the database
			Shipping shipping = new Shipping();
			shipping.setTransaction(purchase);
			shipping.setAddress(address);
			purchase.setShippingEntry(shipping);
			if(depositPicked == false)
				purchase.setPaymentDetails(validatedDetails);
			else
				purchase.setDepositDetails(userController.getCurrently_Logged_In().getDirectDepositDetails());
			transController.addTransaction(purchase);
			return "redirect:/homePage";
		} else {
			model.addAttribute("errMessage", "A Shipping Address and Payment Details Must Be Selected");
			if (address == null)
				model.addAttribute("selectedAddress", null);
			else
				model.addAttribute("selectedAddress", address);
			details = new PaymentDetails();
			// Build credit card error message

			User user = userController.getCurrently_Logged_In();
			if (address == null)
				model.addAttribute("noAddress", "Please enter a shipping address");
			model.addAttribute("purchase", purchase);
			model.addAttribute("marketListing", prevListing);
			model.addAttribute("widget", prevListing.getWidgetSold());
			model.addAttribute("paymentDetails", details);
			model.addAttribute("paypal", paypal);
			model.addAttribute("selectedPayment", validatedDetails);
			model.addAttribute("toShipping", toShipping);
			model.addAttribute("useThis", true);
			model.addAttribute("depositPicked", depositPicked);
			model.addAttribute("allSelected", allSelected);
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("user", user);
			model.addAttribute("relogin", relogin);
			model.addAttribute("loginEr", loginEr);
			model.addAttribute("defaultDetails", userController.getCurrently_Logged_In().getDefaultPaymentDetails());
			if (user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty())
				model.addAttribute("allDetails", null);
			else
				model.addAttribute("allDetails", payDetController.getPaymentDetailsByUser(user));
			model.addAttribute("existingSecurityCode", new String());
			return "confirmPurchase";
		}
	}
	
	/**
	 * Set up the purchase page for using direct deposit to pay
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/useDepositDetails")
	public String payWithDepositDetails(Model model) {
		depositPicked = true;
		modifyPayment = false;
		if(address != null)
			allSelected = true;
		return "redirect:/confirmPurchase";
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * checks the users login info when they add, update, or delete a card
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	
	@PostMapping(value = "/confirmPurchase/submitPurchase", params="loginInfo")
	public String relogin(@RequestParam("usernamePD") String username, @RequestParam("passwordPD") String password, Model model) {
		if(!validateLoginInfo(username, password))
		{
			loginEr = true;
			model.addAttribute("loginError", "Incorrect Username or Password Entered");
			return "redirect:/confirmPurchase";
		}
		relogin = false;
		loginEr = false;
		return "redirect:/confirmPurchase";
	}
	
	/**
	 * validates the users login information
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean validateLoginInfo(String username, String password)
	{
		User user = userController.getCurrently_Logged_In();
		System.out.println(username.equals(user.getUsername()));
		System.out.println(passwordEncoder.matches(password, user.getPassword()));
		if(username.equals(user.getUsername()) && passwordEncoder.matches(password, user.getPassword()))
			return true;
		
		return false;
	}

	/**
	 * Returns true if the PaymentDetails fails constraints that are not represented
	 * by the Spring Validation annotations These constraints are: expiration date
	 * must be before the current date
	 * 
	 * @param form A completed PaymentDetails_Form
	 */
	public boolean paymentDetailsInvalid(PaymentDetails_Form form) {
		return paymentDetailsExpired(form);
	}

	/**
	 * Returns true if the passed PaymentDetails are expired
	 * 
	 * @param form The PaymentDetails_Form to check is expired
	 * @return true if the form is expired, false otherwise, and false if the
	 *         expiration date String is null or empty
	 */
	public boolean paymentDetailsExpired(PaymentDetails_Form form) {
		if (form.getExpirationDate() == null || form.getExpirationDate().length() == 0)
			return false;
		// Check if current date is on or past the expiration date
		LocalDate expirDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy dd");
		try {
			expirDate = LocalDate.parse(form.getExpirationDate()+" 01", formatter);
			return expirDate.compareTo(LocalDate.now()) < 0;
		}
		catch (Exception e) {
			return false;
		}
	}

}
