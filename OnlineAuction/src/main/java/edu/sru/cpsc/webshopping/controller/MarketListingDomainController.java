package edu.sru.cpsc.webshopping.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.Principal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.AutoBid;
import edu.sru.cpsc.webshopping.domain.market.Bid;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.market.AutoBidRepository;
import edu.sru.cpsc.webshopping.repository.market.BidRepository;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
import edu.sru.cpsc.webshopping.service.AuctionService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.WatchlistService;

/** A class for interacting with MarketListing items from the database */
@RestController
public class MarketListingDomainController {
	private User currentlyLoggedIn;
	private MarketListingRepository marketRepository;
	@Autowired
	private WidgetRepository widgetRepository;
	private StatisticsDomainController statControl;
	private WidgetImageController imageController;
	private UserController userController;
	private WatchlistService watchlistService;
	private AutoBidRepository autoBidRepository;
	@Autowired
	private AuctionService auctionService;
	@Autowired
	private UserService userService;
	@Autowired
	private BidRepository bidRepository;
	@PersistenceContext private EntityManager entityManager;
	
	MarketListingDomainController(
			MarketListingRepository marketRepository,
			StatisticsDomainController statControl,
			WidgetImageController imageController,
			WatchlistService watchlistService
			//UserController userController
			) {
			this.marketRepository = marketRepository;
			this.widgetRepository = widgetRepository;
			this.statControl = statControl;
			this.imageController = imageController;
			this.watchlistService = watchlistService;
			//this.userController = userController;
		}


	/**
	 * Gets the MarketListing with an id matching the passed id
	 *
	 * @param id the ID to search for product
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

	@RequestMapping("/get-widget-by-name/{name}")
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
		Optional<MarketListing> dbListingOpt = marketRepository.findById(updatedMarketListing.getId());
		
		if (!dbListingOpt.isPresent()) {
	        throw new IllegalArgumentException("MarketListing with ID " + updatedMarketListing.getId() + " not found.");
	    }
		
		MarketListing dbListing = dbListingOpt.get();
		dbListing.setPricePerItem(updatedMarketListing.getPricePerItem());
		dbListing.setQtyAvailable(updatedMarketListing.getQtyAvailable());
		dbListing.setDeleted(updatedMarketListing.isDeleted());
		return marketRepository.save(dbListing);
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
		MarketListing currListing = entityManager.find(MarketListing.class, trans.getMarketListing().getId());
		currListing.setQtyAvailable(currListing.getQtyAvailable() + trans.getQtyBought());
		marketRepository.save(currListing);
	}

	@Transactional
	@GetMapping("get-Market-Listing-by-User")
	public MarketListing[] getListingbyUser(@Validated User user) {
		return marketRepository.findBySeller(user).stream()
				.filter(marketListing -> !marketListing.isDeleted())
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
	public ResponseEntity<Object> updateBid(@RequestParam BigDecimal bidAmount, @RequestParam Long listingId, @RequestParam Long bidderId, Model model, RedirectAttributes redirectAttributes, Principal principal) {
		User bidder = userService.getUserById(bidderId);
		User user = userService.getUserByUsername(principal.getName());
		
		MarketListing listing = marketRepository.findById(listingId).orElse(null);
		Auction auction = listing.getAuction();
		BigDecimal currentBidPrice = auction.getCurrentBid();
		
		// Ensure that listing.getAuctionPrice() also returns a BigDecimal. Bid amount should be less than 20
		if (listing != null && bidAmount.compareTo(new BigDecimal(20)) <= 0 && listing.getAuction().getCurrentBid().add(bidAmount).compareTo(listing.getAuction().getStartingBid()) >= 0){
			BigDecimal newBid = currentBidPrice.add(bidAmount);
			auctionService.bid(auction, bidder, bidAmount);
			listing.getAuction().setCurrentBid(newBid);
			marketRepository.save(listing);
			
			watchlistService.watchlistAdd(listing, user); // add listing to bidder's watchlist
			userService.addUser(user); // save user to the user repository      
		}
		
		// check for any autobids
		List<AutoBid> autobids = auctionService.getAutoBidsForListing(auction);
		if (autobids != null) {
			for (AutoBid bidEntry : autobids) {
				BigDecimal userMaxPrice = bidEntry.getMaxBid(); // the maximum price the bidder is willing to spend				
				Auction autoAuction = listing.getAuction(); // local auction variable
				BigDecimal autoCurrentPrice = autoAuction.getCurrentBid(); // local current price of auction
				BigDecimal increment = calculateIncrement(bidEntry.getMaxBid()); // auto bid increment bid
				BigDecimal potentialBid = autoCurrentPrice.add(increment); // the potential auto bid value
				
				// if the user is willing to spend more than the current bid price and the potential bid does not put them over their maximum spending limit
				if (userMaxPrice.compareTo(currentBidPrice) > 0 && userMaxPrice.compareTo(potentialBid) >= 0) {
					auctionService.bid(auction, bidEntry.getBidder(), increment); // bid on the item
					autoAuction.setCurrentBid(potentialBid); // update the current bid price
					marketRepository.save(listing); // save the listing with the new bid price
					System.out.println("found autobidders");
				}
			}
		}
		
		// redirect
		URI redirectUri = URI.create("/viewMarketListing/" + listingId);
		return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
	}
	
	/*
	 * Method for setting up automatic bidding
	 * Source for incrementing: https://www.ebay.com/help/buying/bidding/automatic-bidding?id=4014#section1
	 * 
	 * @param   the maximum value the user would be willing to spend on the product
	 * @param   the listing ID of the product
	 * @param   the id of the user that is placing the bid
	 * @param   model
	 * @param   redirect attributes
	 * @param   principal
	 * @return  redirect to the market listing page
	 */
	@PostMapping("/autoBid")
	public ResponseEntity<Object> autoBid(@RequestParam BigDecimal maxBid, @RequestParam Long listingId, @RequestParam Long bidderId, Model model, RedirectAttributes redirectAttributes, Principal principal) {
//		User bidder = userService.getUserById(bidderId); // get bidder
//		User user = userService.getUserByUsername(principal.getName()); // get user
//		
//		MarketListing listing = marketRepository.findById(listingId).orElse(null); // get listing
//		Auction auction = listing.getAuction(); // get auction
//		
//		BigDecimal currentBidPrice = auction.getCurrentBid(); // up to date bid price		
//		BigDecimal increment = calculateIncrement(maxBid); // set up increments for auto bidding
//		
//		System.out.println("increment is currently:" + increment);
//		if (listing != null && maxBid.compareTo(currentBidPrice) > 0){
//			auctionService.autoBid(auction, user, maxBid); // update autobid table with user and max amount for according auction
//			BigDecimal newBid = currentBidPrice.add(increment); // set current bid price
//			auctionService.bid(auction, bidder, increment); // actually bid on the item
//			listing.getAuction().setCurrentBid(newBid); // update listing
//			marketRepository.save(listing); //save listing
//			
//			watchlistService.watchlistAdd(listing, user); // add listing to bidder's watchlist
//			userService.addUser(user); // save user to the user repository
//		}
		
		// check for any autobids
//		List<AutoBid> autobids = auctionService.getAutoBidsForListing(auction);
//		if (autobids != null) {
//			for (AutoBid bidEntry : autobids) {
//				BigDecimal userMaxPrice = bidEntry.getMaxBid(); // the maximum price the bidder is willing to spend				
//				Auction autoAuction = listing.getAuction(); // local auction variable
//				BigDecimal autoCurrentPrice = autoAuction.getCurrentBid(); // local current price of auction
//				BigDecimal autoIncrement = calculateIncrement(bidEntry.getMaxBid()); // auto bid increment bid
//				BigDecimal potentialBid = autoCurrentPrice.add(autoIncrement); // the potential auto bid value
//				
//				// if the user is willing to spend more than the current bid price and the potential bid does not put them over their maximum spending limit
//				if (userMaxPrice.compareTo(currentBidPrice) > 0 && userMaxPrice.compareTo(potentialBid) >= 0) {
//					auctionService.bid(auction, bidEntry.getBidder(), increment); // bid on the item
//					autoAuction.setCurrentBid(potentialBid); // update the current bid price
//					marketRepository.save(listing); // save the listing with the new bid price
//					System.out.println("found autobidders");
//				}
//			}
//		}
		
		User bidder = userService.getUserById(bidderId); // get bidder
	    User user = userService.getUserByUsername(principal.getName()); // get user

	    MarketListing listing = marketRepository.findById(listingId).orElse(null); // get listing
	    Auction auction = listing.getAuction(); // get auction

	    BigDecimal currentBidPrice = auction.getCurrentBid(); // up to date bid price

	    if (listing != null && maxBid.compareTo(currentBidPrice) > 0) {
	        BigDecimal increment = calculateIncrement(maxBid);

	        // Iteratively check for autobids competition
	        boolean autobidPlaced;
	        do {
	            autobidPlaced = false; // Flag to track if any autobid was placed in this iteration
	            List<AutoBid> autobids = auctionService.getAutoBidsForListing(auction);
	            if (autobids != null) {
	                for (AutoBid bidEntry : autobids) {
	                    BigDecimal userMaxPrice = bidEntry.getMaxBid(); // the maximum price the bidder is willing to spend
	                    Auction autoAuction = listing.getAuction(); // local auction variable
	                    BigDecimal autoCurrentPrice = autoAuction.getCurrentBid(); // local current price of the auction
	                    BigDecimal autoIncrement = calculateIncrement(userMaxPrice); // auto bid increment
	                    BigDecimal potentialBid = autoCurrentPrice.add(autoIncrement); // the potential auto bid value

	                    // If the user is willing to spend more than the current bid price and the potential bid does not put them over their maximum spending limit
	                    if (userMaxPrice.compareTo(autoCurrentPrice) > 0 && userMaxPrice.compareTo(potentialBid) >= 0) {
	                        auctionService.bid(auction, bidEntry.getBidder(), autoIncrement); // bid on the item
	                        autoAuction.setCurrentBid(potentialBid); // update the current bid price
	                        marketRepository.save(listing); // save the listing with the new bid price
	                        autobidPlaced = true; // A bid was placed in this iteration
	                        System.out.println("Found autobidders competing.");
	                    }
	                }
	            }
	        } while (autobidPlaced); // Continue until no more autobids can be placed in this iteration

	        // Place the initial autobid
	        auctionService.autoBid(auction, user, maxBid); // update autobid table with user and max amount for the auction
	        BigDecimal newBid = currentBidPrice.add(increment); // set current bid price
	        auctionService.bid(auction, bidder, increment); // place the initial autobid
	        listing.getAuction().setCurrentBid(newBid); // update listing
	        marketRepository.save(listing); // save listing

	        watchlistService.watchlistAdd(listing, user); // add listing to the bidder's watchlist
	        userService.addUser(user); // save the user to the user repository
	    }
		
		// redirect
		URI redirectUri = URI.create("/viewMarketListing/" + listingId);
		return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
	}
	
	/*
	 * Method for calculating auto bid increments
	 * @param   the current bid price of the item
	 * @return  the bid increment
	 */
	private BigDecimal calculateIncrement(BigDecimal currentBidPrice) {
		if (currentBidPrice.compareTo(new BigDecimal("0.01")) >= 0 && currentBidPrice.compareTo(new BigDecimal("0.99")) <= 0) {
	        return new BigDecimal("0.05");
	    } else if (currentBidPrice.compareTo(new BigDecimal("1.00")) >= 0 && currentBidPrice.compareTo(new BigDecimal("4.99")) <= 0) {
	        return new BigDecimal("0.25");
	    } else if (currentBidPrice.compareTo(new BigDecimal("5.00")) >= 0 && currentBidPrice.compareTo(new BigDecimal("24.99")) <= 0) {
	        return new BigDecimal("0.50");
	    } else if (currentBidPrice.compareTo(new BigDecimal("25.00")) >= 0 && currentBidPrice.compareTo(new BigDecimal("99.99")) <= 0) {
	        return new BigDecimal("1.00");
	    } else if (currentBidPrice.compareTo(new BigDecimal("100.00")) >= 0 && currentBidPrice.compareTo(new BigDecimal("249.99")) <= 0) {
	        return new BigDecimal("2.50");
	    } else if (currentBidPrice.compareTo(new BigDecimal("250.00")) >= 0 && currentBidPrice.compareTo(new BigDecimal("499.99")) <= 0) {
	        return new BigDecimal("5.00");
	    } else if (currentBidPrice.compareTo(new BigDecimal("500.00")) >= 0 && currentBidPrice.compareTo(new BigDecimal("999.99")) <= 0) {
	        return new BigDecimal("10.00");
	    } else if (currentBidPrice.compareTo(new BigDecimal("1000.00")) >= 0 && currentBidPrice.compareTo(new BigDecimal("2499.99")) <= 0) {
	        return new BigDecimal("25.00");
	    } else if (currentBidPrice.compareTo(new BigDecimal("2500.00")) >= 0 && currentBidPrice.compareTo(new BigDecimal("4999.99")) <= 0) {
	        return new BigDecimal("50.00");
	    } else {
	        return new BigDecimal("100.00");
	    }
	}
	
	@GetMapping("/uniqueBiddersCount/{id}")
	public ResponseEntity<String> getUniqueBiddersCount(@PathVariable Long id) {
	    MarketListing marketListing = marketRepository.findById(id).orElse(null);
	    if (marketListing == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Listing not found");
	    }
	    int uniqueBidderCount = auctionService.countUniqueBiddersForListing(marketListing);
	    return ResponseEntity.ok(String.valueOf(uniqueBidderCount));
	}
	
	 @GetMapping
	    public String getAllAuctions(Model model) {
	        List<Auction> auctions = auctionService.getAllAuctions();
	        model.addAttribute("auctions", auctions);
	        return "auctions";
	    }
	 
	 @GetMapping("/totalBidsCount/{id}")
	 public ResponseEntity<String> getTotalBidsCount(@PathVariable Long id) {
	     MarketListing marketListing = marketRepository.findById(id).orElse(null);
	     if (marketListing == null) {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Listing not found");
	     }
	     int totalBidsCount = auctionService.getTotalBidsForListing(marketListing);
	     return ResponseEntity.ok(String.valueOf(totalBidsCount));
	 }
	 
     @GetMapping("/isHighestBidder/{marketListingId}")
     @ResponseBody
     public Map<String, Boolean> isHighestBidder(@PathVariable Long marketListingId, Principal principal) {
         MarketListing marketListing = marketRepository.findById(marketListingId).orElse(null);

         if (marketListing == null) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Market listing not found");
         }

         Auction auction = marketListing.getAuction();
         Bid mostRecentBid = auctionService.findHighestBidForAuction(auction);

         Map<String, Boolean> response = new HashMap<>();
         if (mostRecentBid != null && mostRecentBid.getBidder().getUsername().equals(principal.getName())) {
             response.put("isHighestBidder", true);
             System.out.println("Lake");
         } else {
             response.put("isHighestBidder", false);
             System.out.println("Dog");
         } 
         System.out.println("Maple");
         return response;
     }
     
     @GetMapping("/showUniqueBidders/{id}")
     public ResponseEntity<List<String>> getUniqueBidders(@PathVariable Long id) {
    	 System.out.println("Ocean Man");
         MarketListing marketListing = marketRepository.findById(id).orElse(null);
         if (marketListing == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
         }
         Set<User> uniqueBidders = auctionService.findUniqueBiddersForListing(marketListing);
         List<String> bidderNames = uniqueBidders.stream().map(User::getUsername).collect(Collectors.toList());
         return ResponseEntity.ok(bidderNames);
     }
     
     @GetMapping("/bidsForListing/{id}")
     public ResponseEntity<List<BidDTO>> getBidsForListing(@PathVariable Long id) {
         try {
             System.out.println("Ocean Darcy");
             MarketListing marketListing = marketRepository.findById(id).orElse(null);
             if (marketListing == null) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
             }
             List<Bid> bids = bidRepository.findByAuction(marketListing.getAuction());
             
             List<BidDTO> bidDTOs = bids.stream()
                                     .map(bid -> new BidDTO(bid.getBidder().getUsername(), bid.getBidAmount()))
                                     .collect(Collectors.toList());
             
             System.out.println("Number of bids retrieved: " + bids.size());
             for (Bid bid : bids) {
                 System.out.println("Bidder: " + bid.getBidder().getUsername() + ", Amount: " + bid.getBidAmount());
             }
             
             return ResponseEntity.ok(bidDTOs);
         
         } catch (Exception e) {
             e.printStackTrace(); // log the error
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
         }
     }
     
     static class BidDTO {
    	    private String username;
    	    private BigDecimal bidAmount;

    	    public BidDTO(String username, BigDecimal bigDecimal) {
    	        this.username = username;
    	        this.bidAmount = bigDecimal;
    	    }

    	    public String getUsername() {
    	        return username;
    	    }

    	    public void setUsername(String username) {
    	        this.username = username;
    	    }

    	    public BigDecimal getBidAmount() {
    	        return bidAmount;
    	    }

    	    public void setBidAmount(BigDecimal bidAmount) {
    	        this.bidAmount = bidAmount;
    	    }
    	}
}


