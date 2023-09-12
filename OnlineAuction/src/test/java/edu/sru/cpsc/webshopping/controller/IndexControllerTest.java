package edu.sru.cpsc.webshopping.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.domain.user.Applicant;

@SpringBootTest
public class IndexControllerTest {
	
	  @Autowired
	  private WebApplicationContext webApplicationContext;
	  private MockMvc mvc;
	  
		@Autowired
		private ObjectMapper mapper;
		
		@Mock
		private Model model;
		@Mock
		private BindingResult result;

	  @BeforeEach
	  public void setUp() {
	    this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	  }
	
	@Test
	// Tests that invalid indexes are rejected
	public void indexStatusTests() throws Exception {
		
		mvc
	    .perform(MockMvcRequestBuilders.get("/index"))
	    .andExpect(MockMvcResultMatchers.status().isOk());

		mvc
	    .perform(MockMvcRequestBuilders.get("/"))
	    .andExpect(MockMvcResultMatchers.status().isOk());
		
		mvc
	    .perform(MockMvcRequestBuilders.get("/login"))
	    .andExpect(MockMvcResultMatchers.status().isOk());
		
		mvc
	    .perform(MockMvcRequestBuilders.get("/missionStatement"))
	    .andExpect(MockMvcResultMatchers.status().isOk());
		
		mvc
	    .perform(MockMvcRequestBuilders.get("/FAQ"))
	    .andExpect(MockMvcResultMatchers.status().isOk());
		
		mvc
	    .perform(MockMvcRequestBuilders.get("/application"))
	    .andExpect(MockMvcResultMatchers.status().isOk());



					
	}
	@Test
	//  Tests that invalid applications are rejected
	public void applicationPostTest() throws Exception {

		Applicant newApplicant = new Applicant();

		String json = mapper.writeValueAsString(newApplicant);
		MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/apply")
				.secure( true ) 
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
					.andExpect(MockMvcResultMatchers.view().name("redirect:index"))
					.andReturn();
		
	}

}
