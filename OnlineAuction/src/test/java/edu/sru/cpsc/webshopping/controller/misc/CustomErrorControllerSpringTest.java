package edu.sru.cpsc.webshopping.controller.misc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomErrorControllerSpringTest {
	
	private CustomErrorController ErrorController;
	private MockMvc mvc;

	@Autowired
	public CustomErrorControllerSpringTest(CustomErrorController errorController, MockMvc mvc) {
		this.ErrorController = errorController;
		this.mvc = mvc;
	}
	
	/*
	 * ensures that the website can handle an error
	 */
	@Test
	@WithMockUser(username = "user", roles = {"USER"})
    public void handleErrorTest() throws Exception {
        mvc.perform(get("/error"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("No exception was thrown.")));
    }
	
}

	
	