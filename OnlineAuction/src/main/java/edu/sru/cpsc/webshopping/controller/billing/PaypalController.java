package edu.sru.cpsc.webshopping.controller.billing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.Paypal;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;
import edu.sru.cpsc.webshopping.repository.billing.PaypalRepository;

/**
 * Controller for handling Paypal in database
 */
@RestController
public class PaypalController {
	@PersistenceContext
	private EntityManager entityManager;
	private PaypalRepository paypalRepository;
	
	PaypalController(PaypalRepository paypalRepository) {
		this.paypalRepository = paypalRepository;
	}
	
	/**
	 * Deletes the passed PaymentDetails from the database
	 * @param details details to delete
	 */
	public void deletePaypalDetails(Paypal details) {
		Paypal dbDetails = entityManager.find(Paypal.class, details.getId());
		if (dbDetails != null)
			paypalRepository.deleteById(dbDetails.getId());
	}
}
