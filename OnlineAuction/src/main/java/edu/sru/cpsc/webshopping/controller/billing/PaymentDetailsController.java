package edu.sru.cpsc.webshopping.controller.billing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;

/**
 * Controller for handling PaymentDetails in database
 */
@RestController
public class PaymentDetailsController {
	@PersistenceContext
	private EntityManager entityManager;
	private PaymentDetailsRepository paymentDetailsRepository;
	
	public PaymentDetailsController(PaymentDetailsRepository paymentDetailsRepository) {
		this.paymentDetailsRepository = paymentDetailsRepository;
	}
	
	/**
	 * Deletes the passed PaymentDetails from the database
	 * @param details details to delete
	 */
	public void deletePaymentDetails(PaymentDetails details) {
		System.out.println("entered pdcont");
		PaymentDetails dbDetails = entityManager.find(PaymentDetails.class, details.getId());
		if (dbDetails != null)
		{
			System.out.println(dbDetails.getId());
			paymentDetailsRepository.deleteById(dbDetails.getId());
		}
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void addPaymentDetails(@Validated PaymentDetails details) {
		System.out.println("add payment details database function called");
		
		// Encode fields
		details.setCardholderName(passwordEncoder.encode(details.getCardholderName()));
		details.setCardNumber(passwordEncoder.encode(details.getCardNumber()));
		details.setLast4Digits(details.getLast4Digits());
		details.setCardType(details.getCardType());
		details.setExpirationDate(passwordEncoder.encode(details.getExpirationDate()));
		details.setPostalCode(passwordEncoder.encode(details.getPostalCode()));
		details.setSecurityCode(passwordEncoder.encode(details.getSecurityCode()));
		// No assigned details - add to user
		paymentDetailsRepository.save(details);
		// entityManager.persist(details);
		
	}
	
	@RequestMapping("/get-all-payment-details") 
	public Iterable<PaymentDetails> getAllPaymentDetails() {
		Iterable<PaymentDetails> paymentDetails = paymentDetailsRepository.findAll();
		return paymentDetails;
	}
	
	@RequestMapping("/get-payment-details-by-user") 
	public PaymentDetails[] getPaymentDetailsByUser(@PathVariable("user") User user) {
		if(user.getPaymentDetails() == null || user.getPaymentDetails().isEmpty())
			return null;
		return paymentDetailsRepository.findAllByUser(user).toArray(PaymentDetails[]::new);
	}
	
	/**
	 * checks to see if the paymentDetailsRepository already has the passed PaymentDetails
	 * @param details
	 * @return
	 */
	@RequestMapping("/check-for-same")
	public boolean checkDuplicateCard(PaymentDetails details){
		int check = 1;
		for(PaymentDetails PD : paymentDetailsRepository.findAll())
		{
			if( passwordEncoder.matches(details.getCardholderName(), PD.getCardholderName())
			&& passwordEncoder.matches(details.getCardNumber(), PD.getCardNumber())
			&& passwordEncoder.matches(details.getExpirationDate(), PD.getExpirationDate())
			&& passwordEncoder.matches(details.getPostalCode(), PD.getPostalCode())
			&& passwordEncoder.matches(details.getSecurityCode(), PD.getSecurityCode()))
				check = 0;
		}
		if(check == 1)
			return false;
		else
			return true;
	}
	
	@RequestMapping({"/get-payment-detail/{id}"}) 
	public PaymentDetails getPaymentDetail(@PathVariable("id") long id, Model model) {
		PaymentDetails paymentDetails = paymentDetailsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID passed to find a user"));
		return paymentDetails;
	}
	
	/**
	 * finds if there are payment details in the paymentDetailsRepository that match the passed PaymentDetails 
	 * @param details
	 * @return
	 */
	public PaymentDetails getPaymentDetailsByCardNumberAndExpirationDate(PaymentDetails details) {
		for(PaymentDetails PD : paymentDetailsRepository.findAll())
		{
			if( passwordEncoder.matches(details.getCardholderName(), PD.getCardholderName())
			&& passwordEncoder.matches(details.getCardNumber(), PD.getCardNumber())
			&& passwordEncoder.matches(details.getExpirationDate(), PD.getExpirationDate())
			&& passwordEncoder.matches(details.getPostalCode(), PD.getPostalCode())
			&& passwordEncoder.matches(details.getSecurityCode(), PD.getSecurityCode()))
				return PD;
		}
		return details;
	}
	
	/**
	 * Saves or updates the current PaymentDetails of the user
	 * PaymentDetails is encoded using BCryptPasswordEncoder and then stored in the database
	 * @param details new PaymentDetails to save
	 * @exception IllegalStateException is thrown if the user is not logged in
	 */
	@PostMapping("/update-payment-details") 
	@Transactional
	public void updatePaymentDetails(@Validated PaymentDetails details, @Validated PaymentDetails currDetails) {
		System.out.println("update payment details database function called");
		currDetails = paymentDetailsRepository.findById(currDetails.getId()).get();
		// Encode fields
		details.setId(currDetails.getId());
		details.setCardholderName(passwordEncoder.encode(details.getCardholderName()));
		details.setCardNumber(passwordEncoder.encode(details.getCardNumber()));
		details.setLast4Digits(details.getLast4Digits());
		details.setCardType(details.getCardType());
		details.setExpirationDate(passwordEncoder.encode(details.getExpirationDate()));
		details.setPostalCode(passwordEncoder.encode(details.getPostalCode()));
		details.setSecurityCode(passwordEncoder.encode(details.getSecurityCode()));
		// No assigned details - add to user
		PaymentDetails curr = entityManager.find(PaymentDetails.class, currDetails.getId());
		curr.transferFields(details);
		paymentDetailsRepository.save(curr);
	}
	
	public boolean matchExistingCard(String secCode, PaymentDetails details)
	{
		if(passwordEncoder.matches(secCode, details.getSecurityCode()))
		{
			return true;
		}
		else
			return false;
	}
}
