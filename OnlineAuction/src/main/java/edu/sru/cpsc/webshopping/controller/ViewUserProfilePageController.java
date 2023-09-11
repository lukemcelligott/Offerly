package edu.sru.cpsc.webshopping.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.Vector;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sru.cpsc.webshopping.controller.billing.SellerRatingController;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.User;

/**
 * Page for viewing the details of other users
 */
@Controller
public class ViewUserProfilePageController {
	// Database controllers
	private UserController userController;
	private MarketListingDomainController listingController;
	private SellerRatingController ratingController;
	private MessageDomainController messageController;
	private EmailController emailController;
	// Page data
	private User selectedUser;
	private MarketListing[] soldItems;
	private Vector<Vector<MarketListing>> itemsEachPage;
	private SellerRating rating;
	private int pageNumber;
	private boolean messagePaneOpen;
	// Configuration constant
	private final int NUM_LISTINGS_PER_PAGE = 4;
	
	ViewUserProfilePageController(UserController userController, MarketListingDomainController listingController,
			SellerRatingController ratingController, MessageDomainController messageController, EmailController emailController) {
		this.userController = userController;
		this.listingController = listingController;
		this.ratingController = ratingController;
		this.messageController = messageController;
		this.emailController = emailController;
	}
	
	/**
	 * Loads model data based on the current state of the controller
	 * @param model the page model
	 */
	private void reloadPageModel(Model model) {
		model.addAttribute("selectedUser", selectedUser);
		model.addAttribute("currUser", userController.getCurrently_Logged_In());
		model.addAttribute("currPageItems", itemsEachPage.get(pageNumber - 1));
		model.addAttribute("selectedPageNum", pageNumber);
		model.addAttribute("soldItems", soldItems);
		model.addAttribute("numPages", itemsEachPage.size());
		model.addAttribute("rating", rating);
		model.addAttribute("messagePaneOpen", messagePaneOpen);
	}
	
	/**
	 * Opens a user's user profile
	 * @param userId the id of the user to load
	 * @param model the page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/{userId}")
	public String openUserProfile(@PathVariable("userId") long userId, Model model) {
		User user = userController.getCurrently_Logged_In();
		model.addAttribute("user", user);
		this.pageNumber = 1;
		this.selectedUser = userController.getUser(userId, null);
		this.soldItems = listingController.getListingbyUser(selectedUser);
		this.soldItems = Arrays.stream(this.soldItems).filter(item -> !item.isDeleted()).toArray(MarketListing[]::new);
		this.rating = selectedUser.getSellerRating();
		this.messagePaneOpen = false;
		model.addAttribute("selectedUser", selectedUser);
		itemsEachPage = new Vector<Vector<MarketListing>>();
		if (soldItems.length == 0) {
			// If no sold items, add a blank page
			itemsEachPage.add(new Vector<MarketListing>());
		}
		else {
			int currItemOnPage = 0;
			for (MarketListing listing : soldItems) {
				// If sold items, add them to each page
				if (currItemOnPage == 0) 
					itemsEachPage.add(new Vector<MarketListing>());
				itemsEachPage.lastElement().add(listing);
				currItemOnPage = (currItemOnPage + 1) % NUM_LISTINGS_PER_PAGE;
			}
		}
		reloadPageModel(model);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "viewUserProfile";
	}
	
	/**
	 * Changes the current page of displayed market listings
	 * @param pageNumber a page number - indexed starting from 1
	 * @param model the page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/changePage/{pageNumber}")
	public String changePageNumber(@PathVariable("pageNumber") int pageNumber, Model model) {
		// pageNumber is not read from page as zero indexed for user readability reasons
		User user = userController.getCurrently_Logged_In();
		model.addAttribute("user", user);
		if (pageNumber <= 0 || pageNumber > itemsEachPage.size()) {
			throw new IllegalArgumentException("Invalid page number for viewUserProfilePage");
		}
		// Load updated model
		this.pageNumber = pageNumber;
		reloadPageModel(model);
		return "viewUserProfile";
	}
	
	/**
	 * Opens a MarketListing
	 * @param listingId the id of the listing to open
	 * @param model the page model
	 * @return /viewMarketListing/{listingId}
	 */
	@RequestMapping("/viewUserProfile/openListing/{listingId}")
	public String openListing(@PathVariable("listingId") long listingId, Model model) {
		User user = userController.getCurrently_Logged_In();
		model.addAttribute("user", user);
		return "redirect:/viewMarketListing/" + listingId;
	}
	
	/**
	 * Opens the pane for sending messages
	 * @param model the page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/openMessagePane")
	public String openMessagePane(Model model) {
		User user = userController.getCurrently_Logged_In();
		model.addAttribute("user", user);
		System.out.println("open message pane");
		this.messagePaneOpen = true;
		reloadPageModel(model);
		return "viewUserProfile";
	}
	
	/**
	 * Closes the pane for sending messages
	 * @param model the page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/closeMessagePane")
	public String closeMessagePane(Model model) {
		User user = userController.getCurrently_Logged_In();
		model.addAttribute("user", user);
		this.messagePaneOpen = false;
		reloadPageModel(model);
		return "viewUserProfile";
	}
	
	/**
	 * Sends a message to the the owner of the profile page
	 * @param content The content of the message
	 * @param subject The subject line
	 * @param model The page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/sendMessage")
	public String sendMessage(@RequestParam("message") String content, @RequestParam("subject") String subject, Model model)
	{
		User user = userController.getCurrently_Logged_In();
		model.addAttribute("user", user);
		if (content.isBlank() || subject.isBlank()) { 
			reloadPageModel(model);
		}
		else {
			Message message = new Message();
			message.setOwner(userController.getCurrently_Logged_In());
			message.setSender(userController.getCurrently_Logged_In().getUsername());
			message.setContent(content);
			message.setSubject(subject);
			message.setMsgDate();
			message.setReceiverName(selectedUser.getUsername());
			message.setReceiver(selectedUser);
			this.messagePaneOpen = false;
			messageController.addMessage(message);
			emailController.messageEmail(selectedUser, userController.getCurrently_Logged_In(), message);
			reloadPageModel(model);
		}
		return "viewUserProfile";
	}
	
}
