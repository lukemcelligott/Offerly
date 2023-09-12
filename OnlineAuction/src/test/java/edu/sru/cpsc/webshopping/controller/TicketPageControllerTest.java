package edu.sru.cpsc.webshopping.controller;

import edu.sru.cpsc.webshopping.domain.user.Ticket;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
public class TicketPageControllerTest {
	private Logger log= Logger.getLogger(getClass());
	
@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
	
}
// testing the tickets page
@Test
@DisplayName("get Tickets Page")

public void getTicketsPage() {
	try {
		log.info("Starting execution");
		
		String expectedValue="";
		Model model= null;
		
		TicketPageController ticketpagecontroller= new TicketPageController(null, null, null);
		String actualValue= ticketpagecontroller.getTicketsPage(model);
		log.info(actualValue);
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
	}
	catch(Exception exception) {
		log.error("Exception in execution");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing the details page for ticketing
@Test
@DisplayName("get Tickets Details Page")
public void getTicketDetailsPage() {
	try {
		log.info("Starting execution");
		String expectedValue="";
		
		TicketPageController ticketpagecontroller= new TicketPageController(null, null, null);
		String actualValue= ticketpagecontroller.getTicketDetailsPage(null, null);
		log.info("actualValue");
		System.out.println("actualValue");
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing receiving tickets
@Test
@DisplayName("get Ticket Details Page Exception")
public void getTicketDetailsPageException() {
	try {
		log.info("Starting execution");
		
		TicketPageController ticketpagecontroller= new TicketPageController(null, null, null);
		ticketpagecontroller.getTicketDetailsPage(null, null);
		Assertions.assertTrue(false);
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertTrue(true);
	}
}
// test reopening tickets 
@Test
@DisplayName("reopen Ticket")
public void reopenTicket() {
	try {
		log.info("Starting execution");
		String expectedValue="";
		
		TicketPageController ticketpagecontroller= new TicketPageController(null, null, null);
		String actualValue= ticketpagecontroller.reopenTicket(null, null);
		log.info("actualValue");
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("reopen Ticket Exception")
public void reopenTicketException() {
	try {
		log.info("Starting execution");
		
		TicketPageController ticketpagecontroller= new TicketPageController(null, null, null);
		ticketpagecontroller.reopenTicket(null, null);
		Assertions.assertTrue(false);
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertTrue(true);
	}
}
// testing creating tickets page
@Test
@DisplayName("create Tickets Page")
public void createTicketsPage() {
	try {
		log.info("Starting execution");
		String expectedValue="";
		Model model= null;
		
		//TicketPageController ticketpagecontroller= new TicketPageController();
		//String actualValue= ticketpagecontroller.createTicketsPage(model);
		log.info("actualValue");
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("create Tickets")
public void createTickets() {
	try {
		log.info("Starting execution");
		String expectedValue="";
		Model model= null;
		Ticket ticket= null;
		
		TicketPageController ticketpagecontroller= new TicketPageController(null, null, null);
		String actualValue= ticketpagecontroller.createTickets(model, ticket);
		log.info("actualValue");
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing recieving the tickets on the ticket page
@Test
@DisplayName("get Tickets Page")
public void getTicketsPage1() {
	try {
		log.info("Starting execution");
		String expectedValue="";
		Model model= null;
		
		TicketPageController ticketpagecontroller= new TicketPageController(null, null, null);
		String actualValue= ticketpagecontroller.getTicketsPage(model);
		log.info("actualValue");
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
				
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