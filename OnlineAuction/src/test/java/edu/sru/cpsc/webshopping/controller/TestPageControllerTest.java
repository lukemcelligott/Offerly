package edu.sru.cpsc.webshopping.controller;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sru.cpsc.webshopping.controller.TestPageController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;

public class TestPageControllerTest {
	private Logger log= Logger.getLogger(this.getClass());
	
@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
	
}
@Test
@DisplayName("user Widget Test Page")
public void userWidgetTestPage() {
	try {
		log.info("Starting execution");
		String expectedValue="";
		Model model= null;
		
		UserController userController= null;
		WidgetController widgetController= null;
		
		TestPageController testpagecontroller= new TestPageController(userController, widgetController);
		String actualValue= testpagecontroller.userWidgetTestPage(model);
		
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
@DisplayName("add User")
public void addUser() {
	try {
		log.info("Starting execution");
		String expectedValue="";
		
		Object User = null;
		BindingResult result= null;
		
		UserController userController= null;
		WidgetController widgetController= null;
		
		TestPageController testpagecontroller= new TestPageController(userController, widgetController);
		String actualValue= testpagecontroller.deleteWidget(0);
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
@DisplayName("delete Widget Exception")
public void deleteWidgetException() {
	try {
		log.info("Starting execution");
		String expectedValue="";
		
		Object User = null;
		BindingResult result= null;
		
		UserController userController= null;
		WidgetController widgetController= null;
		
		TestPageController testpagecontroller= new TestPageController(userController, widgetController);
		String actualValue= testpagecontroller.deleteWidget(0);
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
