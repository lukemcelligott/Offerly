package edu.sru.cpsc.webshopping.controller.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.StateDetails;
import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.DomainWidgetsLawncareSpringTest;
import edu.sru.cpsc.webshopping.repository.billing.CardTypeRepository;
import edu.sru.cpsc.webshopping.repository.billing.DirectDepositDetailsRepository;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;
import edu.sru.cpsc.webshopping.repository.billing.PaypalRepository;
import edu.sru.cpsc.webshopping.repository.billing.StateDetailsRepository;
import edu.sru.cpsc.webshopping.repository.user.SellerRatingRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import javax.persistence.Id;

@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration(classes = ControllerBillingSpringTest.class, loader = AnnotationConfigContextLoader.class)

public class ControllerBillingSpringTest {
	@Autowired
	private CardTypeRepository cardRepository;
	
	@Autowired
	private DirectDepositDetailsRepository directDepositDetailsRepository;
	
	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;
	private EntityManager entityManager;
	
	@Autowired
	private PaypalRepository paypalRepository;
	
	@Autowired
	private SellerRatingRepository ratingRepository;
	private TransactionController transController;
	
	@Autowired
	private StateDetailsRepository stateDetailsRepository;
	@Autowired
	private MockMvc mvc;
	

	@Test
	/*
	 * Tests to see if the cardTypes repository is empty 
	 */
	 
	void cardTypeTest() throws Exception {
		
		if(cardRepository.findAll() == null) {
			System.out.println("true");
		}
			
		}
	
	@Test
	/*
	 * Tests to see if the directDeposit repository is empty 
	 */
	void directDepositTest() {
		if(directDepositDetailsRepository == null) {
			System.out.println("true");
			
		}
	}
	
	@Test
	/*
	 * Tests to see if the paymentDetails repository is empty 
	 */
	void paymentDetailsTest() {
		if(paymentDetailsRepository == null) {
			System.out.println("true");
		}
	}
	

	@Test
	/*
	 * Tests to see if the paypal repository deletes/is empty
	 */
	void paypalTest() {
		if(paypalRepository == null) {
			System.out.println("true");
		}
	}
	
	@Test
	/*
	 * Tests to see if the paypal repository has matching information
	 */
	void sellerRatingTest() {
		SellerRating rating = new SellerRating();
		rating.setMaxPercent(100);
		rating.setMinPercent(5);
		rating.setRatingName("Rate");
		
		assertEquals(100, rating.getMaxPercent());
		assertEquals(5, rating.getMinPercent());
		assertEquals("Rate", rating.getRatingName());	
	}
	
	@Test
	/*
	 * Tests to see if the paypal repository has information stored in DB
	 */
	void stateDetailsTest() {
		
		if(stateDetailsRepository != null) {
			System.out.print(true);
			
		}
	}
	
	
}
