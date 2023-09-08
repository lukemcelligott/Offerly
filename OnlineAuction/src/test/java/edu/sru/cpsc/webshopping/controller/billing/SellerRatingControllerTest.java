package edu.sru.cpsc.webshopping.controller.billing;
import static org.junit.Assert.assertEquals;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.User;

public class SellerRatingControllerTest {
	private Logger log= Logger.getLogger(getClass());
	
@BeforeAll 
static void initAll() {
	
}
@BeforeEach
void init() {
	
}
@Test
@DisplayName("delete Rating")
public void determineRating() {
	try {
		log.info("Starting Execution");
		SellerRating expectedValue= null;
		User seller= null;
		
		SellerRatingController sellerratingcontroller= new SellerRatingController(null, null);
		SellerRating actualValue= sellerratingcontroller.determineRating(seller);
		log.info("actualValue");
		System.out.println("actualValue");
		assertEquals(null, null);
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("determine Percent Quick Shipping")
public void determinePercentQuickShipping() {
	try {
		log.info("exception");
		float expectedValue=0;
		User seller= null;
		
		SellerRatingController sellerratingcontroller= new SellerRatingController(null, null);
		
		SellerRating actualValue= sellerratingcontroller.determineRating(seller);
		}
		finally {
		log.info("expectedValue");
		System.out.println("expectedValue");
		assertEquals(null, null);
		
	}
}

@AfterEach
void tearDown() {
	
}
@AfterAll
static void tearDownAll() {
	
}

}
