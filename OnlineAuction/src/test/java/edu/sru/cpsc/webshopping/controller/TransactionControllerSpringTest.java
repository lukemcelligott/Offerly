package edu.sru.cpsc.webshopping.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class TransactionControllerSpringTest {


	/**
	 * jUnit code for testing the TransactionController
	 */
	@SpringBootTest
	@AutoConfigureMockMvc
	public class TransactionControllerTest {
		
		@BeforeEach
		public void InitializeTest() {
			
		}
		
	}
	
	@Test
	void contextLoads() {
		
	}

	
}
