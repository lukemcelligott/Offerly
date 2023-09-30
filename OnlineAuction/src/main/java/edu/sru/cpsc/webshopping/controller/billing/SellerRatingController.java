package edu.sru.cpsc.webshopping.controller.billing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.repository.user.SellerRatingRepository;
import edu.sru.cpsc.webshopping.service.UserService;

/**
 * Controller for determining a User's seller rating
 */
@Controller
public class SellerRatingController {
	private final SellerRatingRepository ratingRepository;
	private final TransactionController transController;
	private final UserService userService;

	// Late if hasn't shipped within a business week
	public long LATE_SHIPPING_NUMBER_DAYS = 5;
	
	SellerRatingController(SellerRatingRepository ratingRepository, TransactionController transController, UserService userService) {
		this.ratingRepository = ratingRepository;
		this.transController = transController;
		this.userService = userService;
	}
	
	@PostMapping("/rate/{userId}")
    public void rateUser(@PathVariable Long userId, float rating) {
		// rate user
		userService.rateUser(userId, rating);
		System.out.println("User " + userId + " rated " + rating);
    }
}
