package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.billing.DomainBillingSpringTest;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.user.Statistics.Category;
import edu.sru.cpsc.webshopping.util.enums.MessageType;

@SpringBootTest(classes = {DomainUserSpringTest.class})
/*
 * Tests everything dealing with what the user interacts with 
 */
public class DomainUserSpringTest {
	
	/*
	 * Tests that a new applicant is loaded and verified 
	 */
	@Test
	void applicantUserTest() {
		
		Applicant applicant = new Applicant();
		applicant.setFirstName("Addie");
		applicant.setLastName("Tellier");
		applicant.setEmail("ant1017@sru.edu");
		applicant.setPhoneNumber("724-123-4567");
		applicant.setRoleAppliedfor("Role");
		applicant.setAnswer1("Dog");
		applicant.setAnswer2("Cat");
		applicant.setAnswer3("Yes");
		applicant.setAnswer4("No");
		applicant.setId(34);
		applicant.setApplicationDate("Oct 6");
		applicant.setReviewed(true);
		
		assertEquals("Addie", applicant.getFirstName());
		assertEquals("Tellier", applicant.getLastName());
		assertEquals("ant1017@sru.edu", applicant.getEmail());
		assertEquals("724-123-4567", applicant.getPhoneNumber());
		assertEquals("Role", applicant.getRoleAppliedfor());
		assertEquals("Dog", applicant.getAnswer1());
		assertEquals("Cat", applicant.getAnswer2());
		assertEquals("Yes", applicant.getAnswer3());
		assertEquals("No", applicant.getAnswer4());
		assertEquals(34, applicant.getId());
		assertEquals("Oct 6", applicant.getApplicationDate());
		assertEquals(true, applicant.getReviewed());
	}
	
	/*
	 * From the previous group
	 * Tests the statistics and creats a slight log
	 */
	@Test
	void createStatisticsTest() {
		Statistics stats = new Statistics(Category.SALE, 100);
		System.out.println(stats.getDate());

		
		LocalDateTime date1 = LocalDateTime.of(2015, Month.FEBRUARY,20,06,20);
		LocalDateTime date2 = LocalDateTime.of(2015, Month.FEBRUARY,25,06,20);
		do {
			date1 = date1.plusDays(1);
			int day = date1.getDayOfMonth();
			System.out.println(day);
		}while(date1.getDayOfMonth() != date2.getDayOfMonth());
		
		LocalDateTime date3 = LocalDateTime.of(2015, Month.FEBRUARY,20,06,20);
		LocalDateTime date4 = LocalDateTime.of(2015, Month.MARCH,25,06,20);
		do {
			date3 = date3.plusDays(1);
			int day = date3.getDayOfMonth();
			System.out.println(day);
			System.out.println("date 3 Month value: " + date3.getMonthValue());
			System.out.println("date 4 Month value: " + date4.getMonthValue());
			
		}while(date3.getMonthValue() != date4.getMonthValue() || date3.getDayOfMonth() != date4.getDayOfMonth());
		date3 = LocalDateTime.of(2015, Month.FEBRUARY,20,06,20);
		date4 = LocalDateTime.of(2015, Month.FEBRUARY,25,06,20);
		do {
			int daytemp = date3.getDayOfMonth();
			date3 = date3.plusHours(1);
			int day = date3.getDayOfMonth();
			if(daytemp != day) {
				System.out.println("now it is day: " + day);
			}
			int hour = date3.getHour();
			System.out.println("hour of day: " + hour);
			
		}while(date3.getMonthValue() != date4.getMonthValue() || date3.getDayOfMonth() != date4.getDayOfMonth());
	}
	
	/*
	 * Tests the messaging system
	 */
	@Test
	void messageTest(){
		Message message = new Message();
		User mySpam = new User();
		User myTrash = new User();
		User offender = new User();
		User oldOwner = new User();
		User oldReceiver = new User();
		User owner = new User();
		User receiver = new User();
		User spamOwner = new User();
		User spamOwnerReceiver = new User();
		User trashOwner = new User();
		User trashOwnerReceiver = new User();
		LocalDateTime toTrashOwner = LocalDateTime.now();
		LocalDateTime toTrashReceiver = LocalDateTime.now();
		LocalDateTime expireOwner = LocalDateTime.now();
		LocalDateTime expireReceiver = LocalDateTime.now();
		
		message.setMsgDate("Oct. 6");
		message.setContent("Hello");
		message.setDateSentToTrashOwner(toTrashOwner);
		message.setDateSentToTrashReceiver(toTrashReceiver);
		message.setExpireDateOwner(expireOwner);
		message.setExpireDateReceiver(expireReceiver);
		message.setId(0);
		message.setMessageType(MessageType.SPAM);
		message.setMySpam(mySpam);
		message.setMyTrash(myTrash);
		message.setOffender("offender");
		message.setOldOwner(oldOwner);
		message.setOldReceiver(oldReceiver);
		message.setOwner(owner);
		message.setReceiver(receiver);
		message.setReceiverName("receiverName");
		message.setReviewerName("reviewerName");
		message.setSender("sender");
		message.setSpamOwner(spamOwner);
		message.setSpamOwnerReceiver(spamOwnerReceiver);
		message.setSpamReporter("report");
		message.setSubject("subject");
		message.setTrashOwner(trashOwner);
		message.setTrashOwnerReceiver(trashOwnerReceiver);
		message.setUserFeedback("feedback");
			
		assertEquals("Oct. 6", message.getMsgDate());
		assertEquals("Hello", message.getContent());
		assertEquals(toTrashOwner, message.getDateSentToTrashOwner());
		assertEquals(toTrashReceiver, message.getDateSentToTrashReceiver());
		assertEquals(expireOwner, message.getExpireDateOwner());
		assertEquals(expireReceiver, message.getExpireDateReceiver());
		assertEquals(0, message.getId());
		assertEquals(MessageType.SPAM, message.getMessageType());
		assertEquals(mySpam, message.getMySpam());
		assertEquals(myTrash, message.getMyTrash());
		assertEquals("offender", message.getOffender());
		assertEquals(oldOwner, message.getOldOwner());
		assertEquals(oldReceiver, message.getOldReceiver());
		assertEquals(owner, message.getOwner());
		assertEquals(receiver, message.getReceiver());
		assertEquals("receiverName", message.getReceiverName());
		assertEquals("reviewerName", message.getReviewerName());
		assertEquals("sender", message.getSender());
		assertEquals(spamOwner, message.getSpamOwner());
		assertEquals(spamOwnerReceiver, message.getSpamOwnerReceiver());
		assertEquals("report", message.getSpamReporter());
		assertEquals("subject", message.getSubject());
		assertEquals(trashOwner, message.getTrashOwner());
		assertEquals(trashOwnerReceiver, message.getTrashOwnerReceiver());
		assertEquals("feedback", message.getUserFeedback());
	}
	
	
	/*
	 * Tests that the rating is executed correctly
	 */
	@Test
	void sellerRatingTest() {
		SellerRating sellerRating = new SellerRating();
		sellerRating.setId(69);
		sellerRating.setRatingName("Luke");
		sellerRating.setMinPercent(0);
		sellerRating.setMaxPercent(100);
		
		assertEquals(69, sellerRating.getId());
		assertEquals("Luke", sellerRating.getRatingName());
		assertEquals(0, sellerRating.getMinPercent());
		assertEquals(100, sellerRating.getMaxPercent());
	}
	
	/*
	 * Tests that the statistics loads correctly and are verified
	 */
		
	@Test
	void statisticsTest() {
		Statistics statistics = new Statistics();
		statistics.setId(56);
		statistics.setValue(2);
		statistics.setDescription("night");
		
		assertEquals(56, statistics.getId());
		assertEquals(2, statistics.getValue());
		assertEquals("night", statistics.getDescription());
		statistics.getDate();
		statistics.getHour();
		
	}
	
	
	/*
	 * Tests that the user is assigned a role
	 */
	@Test
	void userRoleTest() {
		UserRole userRole = new UserRole();
		userRole.setId(17);
		
		assertEquals(17, userRole.getId());
		userRole.getAuthority();
		
	}
	
	/*
	 * Tests that a user is added and created into the user profile
	 */
	@Test
	void userTest() {
		User user = new User();
		PaymentDetails details = new PaymentDetails();
		details.setLast4Digits("1234");
		user.setFirstName("Heidi");
		user.setLastName("Bednarz");
		user.setUsername("HeidiB");
		user.setDisplayName("Heidi");
		user.setPassword("1234");
		user.setPasswordconf("1234");
		user.setEmail("heb1010@sru.edu");
		user.setEmailVerification("heb1010@sru.edu");
		user.setPhoneNumber("724-123-4567");
		user.setCountryCode("1");
		user.setRole("USER");
		user.setUserDescription("short");
		user.setUserImage("image");
		user.setId(24);
		user.setCreationDate("Oct 5");
		user.setUserSecret1("What is your birthday");
		user.setUserSecret2("Where did you grow up");
		user.setUserSecret3("What's your mom's maiden name");
		user.setSecret1("September 2");
		user.setSecret2("Cranberry");
		user.setSecret3("Smith");
		user.setCaptcha("abc");
		user.setHiddenCaptcha("abc");
		user.setRealCaptcha("abc");
		user.setDefaultPaymentDetails(details);
		user.setMarketListings(null);
		user.setInbox(null);
		user.setEnabled(false);
		user.setWishlistedWidgets(null);
		user.setPaymentDetails(null);
		user.setPaypal(null);
		user.setDirectDepositDetails(null);
		
		assertEquals("Heidi", user.getFirstName());
		assertEquals("Bednarz", user.getLastName());
		assertEquals("HeidiB", user.getUsername());
		assertEquals("Heidi", user.getDisplayName());
		assertEquals("1234", user.getPassword());
		assertEquals("1234", user.getPasswordconf());
		assertEquals("heb1010@sru.edu", user.getEmail());
		assertEquals("heb1010@sru.edu", user.getEmailVerification());
		assertEquals("724-123-4567", user.getPhoneNumber());
		assertEquals("1", user.getCountryCode());
		assertEquals("USER", user.getRole());
		assertEquals("short", user.getUserDescription());
		assertEquals("image", user.getUserImage());
		assertEquals(24, user.getId());
		assertEquals("Oct 5", user.getCreationDate());
		assertEquals("What is your birthday", user.getUserSecret1());
		assertEquals("Where did you grow up", user.getUserSecret2());
		assertEquals("What's your mom's maiden name", user.getUserSecret3());
		assertEquals("September 2", user.getSecret1());
		assertEquals("Cranberry", user.getSecret2());
		assertEquals("Smith", user.getSecret3());
		assertEquals("abc", user.getCaptcha());
		assertEquals("abc", user.getHiddenCaptcha());
		assertEquals("abc", user.getRealCaptcha());
		assertEquals("1234", user.getDefaultPaymentDetails().getLast4Digits());
		assertEquals(null, user.getMarketListings());
		assertEquals(null, user.getInbox());
		assertEquals(false, user.getEnabled());
		assertEquals(null, user.getWishlistedWidgets());
		assertEquals(null, user.getPaymentDetails());
		assertEquals(null, user.getPaypal());
		assertEquals(null, user.getDirectDepositDetails());
		
		user.isAccountNonExpired();
		user.isAccountNonLocked();
		user.isEnabled();
		user.isCredentialsNonExpired();
	}

	}


