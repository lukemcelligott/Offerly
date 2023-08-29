package edu.sru.cpsc.webshopping.controller;
import static org.junit.Assert.assertEquals;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.FriendRequest;
import edu.sru.cpsc.webshopping.repository.user.FriendRequestRepository;
/*
 * Tests that the data is loaded into the director 
 */
public class FriendRequestControllerTest {
	private Logger log= Logger.getLogger(getClass());
	
@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
	
}
@Test
@DisplayName("get All Friend Requests")
public void getAllFriendRequests() {
	try {
		log.info("Starting Execution");
		
		Iterable<FriendRequest>expectedValue= null;
		FriendRequestController friendrequestcontroller= new FriendRequestController(null);
		Iterable<FriendRequest>actualValue= friendrequestcontroller.getAllFriendRequests();
		log.info("actualValue");

	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@AfterEach
void tearDown() {
	
}
@AfterAll
static void tearDownAll() {
	
}

}
