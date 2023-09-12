package edu.sru.cpsc.webshopping.controller;

import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
import edu.sru.cpsc.webshopping.util.PreLoad;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** A class for interacting with MarketListing items from the database */
@RestController
public class MarketListingDomainController {
	private MarketListingRepository marketRepository;
	private WidgetRepository widgetRepository;
	private StatisticsDomainController statControl;
	private WidgetImageController imageController;
	@PersistenceContext private EntityManager entityManager;

	MarketListingDomainController(
			MarketListingRepository marketRepository,
			WidgetRepository widgetRepository,
			StatisticsDomainController statControl,
			WidgetImageController imageController) {
		this.marketRepository = marketRepository;
		this.widgetRepository = widgetRepository;
		this.statControl = statControl;
		this.imageController = imageController;
	}

	/**
	 * Gets the MarketListing with an id matching the passed id
	 *
	 * @param id the ID to search for
	 * @return the MarketListing found, or null if no MarketListing with the passed id is found
	 */
	@RequestMapping("/get-market-listing/{id}")
	@Transactional
	public MarketListing getMarketListing(@PathVariable("id") long id) {
		// MarketListing marketListing = marketRepository.findById(id).orElseThrow(() -> new
		// IllegalArgumentException("Invalid ID provided for retrieving a MarketListing"));
		MarketListing marketListing = entityManager.find(MarketListing.class, id);
		return marketListing;
	}

	@RequestMapping("/get-widget-by-name")
	public Widget getWidget(@PathVariable("name") String name) {
		Widget widget = widgetRepository.findByName(name);
		return widget;
	}

	@RequestMapping("/get-listing-by-widget")
	public MarketListing getListingByWidget(@PathVariable("widget") Widget widget) {
		MarketListing listing = marketRepository.findByWidgetSold(widget);
		return listing;
	}
	

	/**
	 * Gets all MarketListings from the database
	 *
	 * @return an Iterable of all the database's MarketListings
	 */
	@RequestMapping({"/get-all-listings"})
	public Iterable<MarketListing> getAllListings() {
		Iterable<MarketListing> listings = marketRepository.findAll();
		return listings;/*() ->
		not sure what all this was but it doesnt work
		StreamSupport.stream(listings.spliterator(), false)
		.filter(
				marketListing ->
				PreLoad.subCategoryConfiguration().stream()
				.anyMatch(marketListing.getWidgetSold().getSubCategory()::equalsIgnoreCase))
		.iterator();*/
	}

	/**
	 * Updates the MarketListing associated with the passed marketListing to reflect a purchase This
	 * involves reducing the quantity available by the amount purchased
	 *
	 * @param marketListing the MarketListing to update
	 * @param numPurchased the number of items purchased
	 * @exception IllegalStateException is thrown if the MarketListing does not exist in the database,
	 *     or is marked as deleted
	 * @exception IllegalArgumentException is thrown if the number purchased exceeds the quantity
	 *     available
	 */
	@Transactional
	@PostMapping("/market-listing-purchase-update")
	public void marketListingPurchaseUpdate(
			@Validated MarketListing marketListing, int numPurchased) {
		MarketListing currListing = entityManager.find(MarketListing.class, marketListing.getId());
		if (currListing == null) {
			throw new IllegalStateException(
					"The referenced MarketListing you have purchased from does not exist.");
		} else if (currListing.isDeleted()) {
			throw new IllegalStateException(
					"The MarketListing you have purchased from is no longer available for purchase.");
		} else if (numPurchased > currListing.getQtyAvailable()) {
			throw new IllegalArgumentException("The number of items you wish to buy exceeds the stock.");
		}
		float value = currListing.getPricePerItem().floatValue() * numPurchased;
		Statistics recordSaleValue = new Statistics(StatsCategory.SALEVALUE, value);
		Statistics recordSale = new Statistics(StatsCategory.SALE, 1);
		recordSaleValue.setDescription(
				numPurchased
				+ " "
				+ currListing.getWidgetSold().getName()
				+ "(s)"
				+ " were sold for a total of: $"
				+ value);
		recordSale.setDescription(currListing.getWidgetSold().getName() + ": was sold");
		statControl.addStatistics(recordSaleValue);
		statControl.addStatistics(recordSale);
		currListing.setQtyAvailable(currListing.getQtyAvailable() - numPurchased);
		marketRepository.save(currListing);
	}

	/**
	 * Creates a new MarketListing in the database
	 *
	 * @param marketListing the values of the new MarketListing
	 * @param result form verification
	 * @exception IllegalArgumentException is thrown if result is invalid
	 */
	@Transactional
	@PostMapping("/add-market-listing")
	public MarketListing addMarketListing(@Validated MarketListing marketListing) {
		User seller = entityManager.find(User.class, marketListing.getSeller().getId());
		Widget widgetSold = entityManager.find(Widget.class, marketListing.getWidgetSold().getId());
		marketListing.setSeller(seller);
		marketListing.setWidgetSold(widgetSold);
		return marketRepository.save(marketListing);
	}

	@Transactional
	@PostMapping("/add-market-listing-simple")
	public MarketListing addMarketListingSimple(@Validated MarketListing marketListing) {

		return marketRepository.save(marketListing);
	}

	/**
	 * Updates an existing MarketListing to hold the values of the passed MarketListing The updated
	 * MarketListing is the one whose id matches the id of the updatedMarketListing
	 *
	 * @param updatedMarketListing a MarketListing holding the updated values
	 */
	@Transactional
	@PostMapping("/edit-market-listing")
	public MarketListing editMarketListing(@Validated MarketListing updatedMarketListing) {
		Optional<MarketListing> dbListing = marketRepository.findById(updatedMarketListing.getId());
		dbListing.get().setPricePerItem(updatedMarketListing.getPricePerItem());
		dbListing.get().setQtyAvailable(updatedMarketListing.getQtyAvailable());
		dbListing.get().setDeleted(updatedMarketListing.isDeleted());
		// Ensure that we are referring to persistent entities
		dbListing
		.get()
		.setSeller(entityManager.find(User.class, updatedMarketListing.getSeller().getId()));
		dbListing
		.get()
		.setWidgetSold(
				entityManager.find(Widget.class, updatedMarketListing.getWidgetSold().getId()));
		return marketRepository.save(dbListing.get());
	}

	/**
	 * Undos the changes caused by a purchase on the associated MarketListing
	 *
	 * @param trans The purchase to undo
	 * @return true if the operation is successful
	 */
	@Transactional
	@PostMapping("undo-market-listing-purchase")
	public void undoPurchase(@Validated Transaction trans) {
		MarketListing currListing =
				entityManager.find(MarketListing.class, trans.getMarketListing().getId());
		currListing.setQtyAvailable(currListing.getQtyAvailable() + trans.getQtyBought());
		marketRepository.save(currListing);
	}

	@Transactional
	@GetMapping("get-Market-Listing-by-User")
	public MarketListing[] getListingbyUser(@Validated User user) {
		return marketRepository.findBySeller(user).stream()
				.filter(
						marketListing ->
						PreLoad.subCategoryConfiguration().stream()
						.anyMatch(marketListing.getWidgetSold().getCategory().getName()::equalsIgnoreCase))
						.toArray(MarketListing[]::new);
	}

	/**
	 * Deletes a MarketListing from the database
	 *
	 * @param id the id of the MarketListing to delete
	 */
	@PostMapping("/delete-market-listing/{id}")
	public void deleteMarketListing(@PathVariable long id) {
		System.out.println(id);
		for(WidgetImage image : imageController.getwidgetImageByMarketListing(getMarketListing(id)))
		{
			imageController.deleteWidgetImage(image.getId());
		}
		entityManager.detach(getMarketListing(id));
		marketRepository.deleteById(id);
	}

	@Transactional
	@PostMapping("/update-market-listing-auction")
	public MarketListing updateMarketListingAuction(
	        @RequestParam("marketListingId") Long marketListingId, 
	        @RequestParam("startingBid") BigDecimal startingBid, 
	        @RequestParam("endAuctionDate") LocalDateTime endAuctionDate) {   // assuming java.util.Date, adjust as necessary
	
	    // Find the MarketListing entity
	    MarketListing marketListing = entityManager.find(MarketListing.class, marketListingId);
	    if (marketListing == null) {
	        throw new IllegalStateException("No MarketListing found for the provided ID");
	    }
	
	    // Update the auction details
	    Auction auction = marketListing.getAuction();
	    if (auction == null) {
	        auction = new Auction();  // assuming you have a default constructor for Auction
	    }
	    auction.setStartingBid(startingBid);
	    auction.setEndAuctionDate(endAuctionDate);   // Adjust the method names as per your Auction class
	
	    marketListing.setAuction(auction);
	
	    // Save the updated MarketListing
	    return marketRepository.save(marketListing);
		}

	@PostMapping("/updateBid")
	public ResponseEntity<Object> updateBid(@RequestParam BigDecimal bidAmount, @RequestParam Long listingId, Model model, RedirectAttributes redirectAttributes) {
	    MarketListing listing = marketRepository.findById(listingId).orElse(null);
	
	    // Ensure that listing.getAuctionPrice() also returns a BigDecimal
	    if (listing != null && bidAmount.compareTo(listing.getAuctionPrice()) > 0) {
	        listing.setAuctionPrice(bidAmount);
	        marketRepository.save(listing);
	    } 
	    URI redirectUri = URI.create("/viewMarketListing/" + listingId);
	    return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
		}

}
