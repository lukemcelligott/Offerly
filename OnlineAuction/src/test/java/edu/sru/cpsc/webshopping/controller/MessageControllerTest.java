package edu.sru.cpsc.webshopping.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import javax.transaction.Transaction;
import org.junit.Test;
import org.h2.engine.Database;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.MessageDomainController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.controller.purchase.MarketListingPageController;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.Applicant;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.message.MessageRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {
	

	@Autowired
	private UserController userController = mock(UserController.class);
	@Autowired
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Mock
	private Model model;
	@Mock
	Database database;
	@Mock
	private BindingResult result;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private MessageDomainController msgcontrol;
	@Autowired
	private MessageRepository msgrepo;
	
	  @BeforeEach
	  public void setUp() {
			this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	
			// Initializes User
			User user = new User();
			user.setUsername("timMock");
			user.setPassword("");
			user.setEmail("tmd1021@sru.edu");
			userController.addUser(user, result);
			userController.setCurrently_Logged_In(user);


	  }
	  @AfterEach
	  public void breakDown() {
		  
		  userController.deleteUser(userController.getCurrently_Logged_In().getId(), model);
	  }
	  
	@Test
	void messagePageStatusTests() throws Exception {
		
		mvc
	    .perform(MockMvcRequestBuilders.get("/messages"))
	    .andExpect(MockMvcResultMatchers.status().isOk());

		mvc
	    .perform(MockMvcRequestBuilders.get("/outbox"))
	    .andExpect(MockMvcResultMatchers.status().isOk());
		
		mvc
	    .perform(MockMvcRequestBuilders.get("/trashbox"))
	    .andExpect(MockMvcResultMatchers.status().isOk());
		
		mvc
	    .perform(MockMvcRequestBuilders.get("/spambox"))
	    .andExpect(MockMvcResultMatchers.status().isOk());
				
		mvc
	    .perform(MockMvcRequestBuilders.get("/closeMessage"))
	    .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
					
	}
	@Test
	public void messageIntegrationTestSendToTrashandRecover() throws Exception {

		User user = userController.getCurrently_Logged_In();
		System.out.println(userController.getCurrently_Logged_In().getUsername());
		MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/sendmail")
				.secure( true ) 
				.param("recipient", "timMock")
				.param("message", "Hello World")
				.param("subject", "Test Subject")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		Message mailbox[] = msgcontrol.getUserInbox(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World");
		
		
		String json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res2 = mvc.perform(MockMvcRequestBuilders.post("/trash")
				.secure( true ) 
				.param("id",json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserTrash(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World");
		
		json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res3 = mvc.perform(MockMvcRequestBuilders.post("/recoverFromTrash")
				.secure( true ) 
				.param("id",json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserInbox(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World");
		msgrepo.deleteAll();
	}
	@Test
	public void messageIntegrationTestSendToTrashandDelete() throws Exception {

		User user = userController.getCurrently_Logged_In();
		System.out.println(userController.getCurrently_Logged_In().getUsername());
		MvcResult res4 = mvc.perform(MockMvcRequestBuilders.post("/sendmail")
				.secure( true ) 
				.param("recipient", "timMock")
				.param("message", "Hello World2")
				.param("subject", "Test Subject2")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		Message mailbox[] = msgcontrol.getUserInbox(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World2");
		
		
		String json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res5 = mvc.perform(MockMvcRequestBuilders.post("/trash")
				.secure( true ) 
				.param("id",json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserTrash(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World2");
		
		json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res6 = mvc.perform(MockMvcRequestBuilders.post("/trashDelete")
				.secure( true ) 
				.param("id",json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserTrash(user);
		assertEquals(mailbox.length, 0);
		
	}
	@Test
	public void messageIntegrationTestSendToSpamandRecover() throws Exception {

		User user = userController.getCurrently_Logged_In();
		System.out.println(userController.getCurrently_Logged_In().getUsername());
		MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/sendmail")
				.secure( true ) 
				.param("recipient", "timMock")
				.param("message", "Hello World")
				.param("subject", "Test Subject")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		Message mailbox[] = msgcontrol.getUserInbox(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World");
		
		
		String json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res2 = mvc.perform(MockMvcRequestBuilders.post("/spam")
				.secure( true ) 
				.param("id",json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserSpam(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World");
		
		json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res3 = mvc.perform(MockMvcRequestBuilders.post("/recoverFromSpam")
				.secure( true ) 
				.param("id",json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserInbox(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World");
		msgrepo.deleteAll();
	}
	@Test
	public void messageIntegrationTestSendToSpamandDelete() throws Exception {

		User user = userController.getCurrently_Logged_In();
		System.out.println(userController.getCurrently_Logged_In().getUsername());
		MvcResult res4 = mvc.perform(MockMvcRequestBuilders.post("/sendmail")
				.secure( true ) 
				.param("recipient", "timMock")
				.param("message", "Hello World2")
				.param("subject", "Test Subject2")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		Message mailbox[] = msgcontrol.getUserInbox(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World2");
		
		
		String json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res5 = mvc.perform(MockMvcRequestBuilders.post("/spam")
				.secure( true ) 
				.param("id",json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserSpam(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World2");
		
		json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res6 = mvc.perform(MockMvcRequestBuilders.post("/spamDelete")
				.secure( true ) 
				.param("id",json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserSpam(user);
		assertEquals(mailbox.length, 0);
		
	}
/*	@Test
	public void messageIntegrationTestSendMessageandOpen()throws Exception {

		User user = userController.getCurrently_Logged_In();
		System.out.println(userController.getCurrently_Logged_In().getUsername());
		MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/sendmail")
				.secure( true ) 
				.param("recipient", "timMock")
				.param("message", "Hello World")
				.param("subject", "Test Subject")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		Message mailbox[] = msgcontrol.getUserInbox(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World");
		
		
		String json = mapper.writeValueAsString(mailbox[0].getId());
		MvcResult res2 = mvc.perform(MockMvcRequestBuilders.post("/openMessage/" + mailbox[0].getId())
				.secure( true ) 
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("messages"))
					.andReturn();
		mailbox = msgcontrol.getUserTrash(user);
		System.out.println(mailbox[0].getContent());
		assertEquals(mailbox[0].getContent(), "Hello World");
		
				mvc
	    .perform(MockMvcRequestBuilders.get("/replyButton"))
	    .andExpect(MockMvcResultMatchers.status().isOk());
		msgrepo.deleteAll();
	}*/
	

}
