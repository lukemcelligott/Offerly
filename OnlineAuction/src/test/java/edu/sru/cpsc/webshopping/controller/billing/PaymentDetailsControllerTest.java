package edu.sru.cpsc.webshopping.controller.billing;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import static org.hamcrest.Matchers.containsString;
import org.jboss.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;

@SpringBootTest(classes = {PaymentDetailsController.class})
public class PaymentDetailsControllerTest {
	
	@MockBean
	private PaymentDetailsRepository paymentDetailsRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private EntityManager entityManager;
	
	@MockBean
	private EntityManagerFactory EMF;
	
	@Autowired
	PaymentDetailsController controller;
	
	/*@Before
	public void setUp()
	{
		details.setCardholderName("tyler");
		details.setId(0L);
		when(paymentDetailsRepository.findById(0L).orElseThrow(() -> new IllegalArgumentException("Invalid ID passed to find a user"))).thenReturn(details);
		when(passwordEncoder.encode(details.getCardholderName())).thenReturn(details.getCardholderName());
		when(passwordEncoder.encode(details.getCardNumber())).thenReturn(details.getCardNumber());
		when((passwordEncoder.encode(details.getExpirationDate()))).thenReturn(details.getExpirationDate());
		when(passwordEncoder.encode(details.getPostalCode())).thenReturn(details.getPostalCode());
		when(passwordEncoder.encode(details.getSecurityCode())).thenReturn(details.getSecurityCode());
	}*/
	
	/*@Test
	public void contextLoads() throws Exception
	{
		mvc.perform(get("/get-all-payment-details"))
		.andExpect(status().isOk());
	}*/

@Test
public void getPaymentDetails() throws Exception
{
	PaymentDetails testDetails = new PaymentDetails();
	testDetails.setCardholderName("tyler");
	testDetails.setId(0L);
	System.out.println(testDetails.getId());
	when(paymentDetailsRepository.findById(testDetails.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid ID passed to find a user"))).thenReturn(testDetails);
	assertThat(controller.getPaymentDetail(0L, null)).isEqualTo(testDetails);
}

/*@Test
@DisplayName("delete Payment Details")
public void deletePaymentDetails() {
	try {
		log.info("Starting Execution");
		EntityManager entityManager = Mockito.mock(EntityManager.class);
		when(entityManager.find(PaymentDetails.class, details.getId())).thenReturn(details);
		
		paymentDetailsController.deletePaymentDetails(details);
		Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertTrue(false);
	}
}*/


}
