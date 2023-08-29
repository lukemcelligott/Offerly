package edu.sru.cpsc.webshopping.controller.misc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.domain.user.User;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {CustomErrorControllerSpringTest.class})
@AutoConfigureMockMvc
public class CustomErrorControllerSpringTest {
	
	@Autowired
	 CustomErrorController ErrorController;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	/*
	 * ensures that the website can handle an error
	 */
	@Test
	void handleErrorTest() throws Exception {
		Error error = new Error("/error");
		mvc.perform(post("/error").content(objectMapper.writeValueAsString(error)));
		assertThat(error.getMessage()).isEqualTo("/error");
		
		Error error1 = new Error("/errorPage");
		mvc.perform(post("/errorPage").content(objectMapper.writeValueAsString(error1)));
		assertThat(error1.getMessage()).isEqualTo("/errorPage");
		
		Error error2 = new Error("/errorPageTest");
		mvc.perform(post("/errorPageTest").content(objectMapper.writeValueAsString(error2)));
		assertThat(error2.getMessage()).isEqualTo("/errorPageTest");
			
		}
	
	
	
	
	}

	
	