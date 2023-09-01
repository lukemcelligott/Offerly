package edu.sru.cpsc.webshopping.controller;

import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.validation.BindingResult;

import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;

/**
 * jUnit code for testing the MarketListingDomainController class
 */
@SpringBootTest
public class MarketListingDomainControllerTest {
	@Autowired
	private WidgetController widgetController;
	@Autowired 
	private UserController userController;
	@Autowired
	private MarketListingDomainController mlDomainController;
	private MarketListing newListing;
	
	/**
	 * Adds entries to the H2 database for running tests
	 */
	@BeforeEach
	public void TestInitialization() {
		newListing = new MarketListing();
		newListing.setDeleted(false);
		newListing.setPricePerItem(new BigDecimal(50.05));
		newListing.setQtyAvailable(50);
		User user = new User();
		user.setPassword("");
		newListing.setSeller(userController.addUser(user, mock(BindingResult.class)));
		newListing.setWidgetSold(widgetController.addWidget(new Widget(), null));
		newListing = mlDomainController.addMarketListing(newListing);
	}

	/**
	 * Tests the marketListingPurchaseUpdate function when the quantity purchased is valid
	 */
	@Test 
	public void PurchaseQuantityValid() {
		mlDomainController.marketListingPurchaseUpdate(newListing, 20);
		Assertions.assertEquals(50 - 20, mlDomainController.getMarketListing(newListing.getId()).getQtyAvailable());
	}
	
	/**
	 * Tests the marketListingPurchaseUpdate function when the quantity purchased is invalid
	 */
	@Test
	public void PurchaseQuantityInvalid() {
		try {
			mlDomainController.marketListingPurchaseUpdate(newListing, 60);
		}
		catch (IllegalArgumentException e) { return; } // If correct error is thrown, then pass test
		// Incorrect or no error thrown, so test fails
		throw new IllegalArgumentException("Test Failed");
	}
	
	/**
	 * Verifies that marketListingPurchaseUpdate fails when the listing is deleted
	 */
	@Test
	public void PurchaseQuantityListingDeleted() {
		try {
			newListing.setDeleted(true);
			mlDomainController.editMarketListing(newListing);
			mlDomainController.marketListingPurchaseUpdate(newListing, 20);
		}
		catch (IllegalStateException e) {return;}
		throw new IllegalStateException("Test Failed");
	}
}
