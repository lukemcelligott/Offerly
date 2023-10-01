package edu.sru.cpsc.webshopping.service;

import java.security.Principal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

@Service
public class WatchlistService {
	UserController userController;
	UserService userService;
	UserRepository userRepository;
	@PersistenceContext
	private EntityManager entityManager;
	
	WatchlistService(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}
	
	/*
	 *  Adds an item to the user's watchlist
	 *  @param marketListing   the listing that the user wants to add to their watchlist
	 *  @param user   the currently logged in user
	 */
	public void watchlistAdd(@Validated MarketListing marketListing, User user) {
		MarketListing addedWidget = entityManager.find(MarketListing.class, marketListing.getId());
		
		// check if the widget is null
		if (addedWidget == null) {
			throw new IllegalArgumentException("Widget pass to addToWishlist not found in database.");
		}
		
		user.getWishlistedWidgets().add(addedWidget);
		
	}
	
	/*
	 * Removes an item from the user's watchlist
	 * @param marketListing   the listing that the user wants to remove from their watchlist
	 * @param user   the currently logged in user
	 */
	public void watchlistRemove(@Validated MarketListing marketListing, User user) {
		MarketListing delWidget = entityManager.find(MarketListing.class, marketListing.getId());
		
		// check if the widget is null
		if (delWidget == null) {
			throw new IllegalArgumentException("Widget pass to removeFromWishlist not found in database.");
		}
		
		user.getWishlistedWidgets().remove(delWidget);
	}
	
	/*
	 * Checks how many users have the market listing saved in their watchlist
	 * @param marketListing   the listing to be checked
	 */
	public Long countUsersWithMarketListingInWatchlist(Long marketListingId) {
		
		Long count = userRepository.countUsersWithMarketListingInWatchlist(marketListingId);
		
		return count;
	}
	
}