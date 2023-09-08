package edu.sru.cpsc.webshopping.controller.billing;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.SellerRatingRepository;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SellerRatingControllerTest {

    @Mock
    private SellerRatingRepository ratingRepository;

    @Mock
    private TransactionController transactionController;

    @InjectMocks
    private SellerRatingController sellerRatingController;
	
	@BeforeEach
	public void init() {
		
	}
	@Test
	public void testDetermineRating() {
		// Given
		User seller = new User(); // Assuming User has a default constructor
		SellerRating rating1 = new SellerRating();
		rating1.setMinPercent(0);
		rating1.setMaxPercent(0.5f);
		rating1.setRatingName("Low");

		SellerRating rating2 = new SellerRating();
		rating2.setMinPercent(0.5f);
		rating2.setMaxPercent(1.0f);
		rating2.setRatingName("High");

		when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));
		when(transactionController.getUserSoldItems(any())).thenReturn(Arrays.asList(new Transaction())); // Mocking one transaction for simplicity

		// When
		SellerRating result = sellerRatingController.determineRating(seller);

		// Then
		assertNotNull(result);
		// Add more assertions based on expected behavior
	}
}
