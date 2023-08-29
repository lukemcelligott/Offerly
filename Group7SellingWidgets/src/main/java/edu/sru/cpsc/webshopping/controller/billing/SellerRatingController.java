package edu.sru.cpsc.webshopping.controller.billing;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Controller;

import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.SellerRatingRepository;

/**
 * Controller for determining a User's seller rating
 */
@Controller
public class SellerRatingController {
	private SellerRatingRepository ratingRepository;
	private TransactionController transController;
	// Late if hasn't shipped within a business week
	public long LATE_SHIPPING_NUMBER_DAYS = 5;
	
	SellerRatingController(SellerRatingRepository ratingRepository, TransactionController transController) {
		this.ratingRepository = ratingRepository;
		this.transController = transController;
	}
	
	public SellerRating determineRating(User seller) {
		Iterable<SellerRating> ratings = ratingRepository.findAll();
		float percentQuickShipping = determinePercentQuickShipping(seller);
		SellerRating rating = new SellerRating();
		// Find associated rating
		for (SellerRating possibleRating : ratings) {
			//System.out.println(possibleRating.getMinPercent());
			//System.out.println(possibleRating.getMaxPercent());
			//System.out.println(possibleRating.getRatingName());
			if (percentQuickShipping >= possibleRating.getMinPercent() && percentQuickShipping <= possibleRating.getMaxPercent()) 
				rating.setRatingName(possibleRating.getRatingName());
				rating.setMinPercent(possibleRating.getMinPercent());
				rating.setMaxPercent(possibleRating.getMaxPercent());
		}
		// No rating found - so no sold items. Return a special SellerRating.
		if (rating.getRatingName() == null)
			rating.setRatingName("N/A");
		
		return rating;
	}
	
	/**
	 * 
	 * @param seller
	 * @return a float between 0.0 and 1.0 for the percentage, or a float < 0.0 if no items sold
	 */
	private float determinePercentQuickShipping(User seller) {
		Iterable<Transaction> soldTransactions = transController.getUserSoldItems(seller);
		if (soldTransactions.spliterator().getExactSizeIfKnown() == 0) 
			return Float.NEGATIVE_INFINITY;
		long numberLate = 0;
		long totalItems = soldTransactions.spliterator().getExactSizeIfKnown();
		for (Transaction trans : soldTransactions) {
			LocalDate transDate = Date.valueOf(trans.getPurchaseDate().toString()).toLocalDate();
			// Case for an item that has not been shipped yet
			if (trans.getShippingEntry().getShippingDate() == null) {
				if (ChronoUnit.DAYS.between(transDate, LocalDate.now()) >= LATE_SHIPPING_NUMBER_DAYS) {
					numberLate++;
					//System.out.println("incr");
				}
				//System.out.println("days between not shipped item: " + ChronoUnit.DAYS.between(transDate, LocalDate.now()));
			}
			// Case for an item that has shipped, and possibly arrived
			else {
				LocalDate shippedDate = Date.valueOf(trans.getShippingEntry().getShippingDate().toString()).toLocalDate();
				if (ChronoUnit.DAYS.between(transDate, shippedDate) >= LATE_SHIPPING_NUMBER_DAYS) {
					numberLate++;
					//System.out.println("incr");
				}
				//System.out.println("days between shipped item: " + ChronoUnit.DAYS.between(transDate, shippedDate));
			}
		}
		//System.out.println(numberLate);
		//System.out.println(totalItems);
		//System.out.println((1.0f - (float)numberLate) / (float)(totalItems));
		return 1.0f - ((float)numberLate / (float)(totalItems));
	}
}
