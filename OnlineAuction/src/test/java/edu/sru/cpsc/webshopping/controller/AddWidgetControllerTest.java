package edu.sru.cpsc.webshopping.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import edu.sru.cpsc.webshopping.domain.user.User;

import org.junit.Test;

@SpringBootTest
public class AddWidgetControllerTest {

	private static Model mockModel = mock(Model.class);
	private static UserController userController = mock(UserController.class);
	private static User mockUser = mock(User.class);
	private static AddWidgetController widgetController = new AddWidgetController(null, null, null, null, null, null, null, null, null, userController, null, null);
	
	@BeforeAll
	public static void newWidgetController() {
		
		Authentication mockAuth = mock(Authentication.class);
		when(mockAuth.isAuthenticated()).thenReturn(true);
		SecurityContextHolder.getContext().setAuthentication(mockAuth);
		mockUser.setFirstName("test");
		userController.setCurrently_Logged_In(mockUser);
		//when(userController.getCurrently_Logged_In()).thenReturn(mockUser);
		widgetController.setUserController(userController);
}
	@Test
	public void pageTest() {
		String expected = null;
		assertEquals(expected, widgetController.getPage());
	}
	@Test
	public void widgetStorage() {
		String expected = null;
		assertEquals(expected, widgetController.getWidgetStorage());
	}
	@Test
	public void addListing() {
		String expected = "addListing";
		assertEquals(expected, widgetController.addListing(mockModel, null, null, null, null, null, null));
	}
	@Test
	public void addWidget() {
		String expected = "addWidget";
		System.out.println(userController.getCurrently_Logged_In().getFirstName());
		assertEquals(expected, widgetController.addWidget(mockModel));
	}
	@Test
	public void createListing() {
		String expected= "createListing";
		assertEquals(expected, widgetController.createListing(mockModel));
	}
}

