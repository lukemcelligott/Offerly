package edu.sru.cpsc.webshopping.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.repository.ticket.TicketRepository;


/*
 * Tests that the overall ticketing service is functioning
 * and that tickets can be found
 */

@SpringBootTest(classes = {TicketServiceSpringTest.class})
public class TicketServiceSpringTest {
	
	@MockBean
	private TicketRepository ticketRepository;
	
	/*
	 * Tests to ensure all the tickets are received
	 */
	@Test
	void getAllTicketsTest() {
		TicketService ticket = new TicketService(ticketRepository);
		ticket.findById(null);
		ticket.save(null);
		ticket.getAllTickets();
		//ticket.getTicketById(null);

	}

}
