package edu.sru.cpsc.webshopping.controller.misc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = {CustomErrorControllerSpringTest.class})
@AutoConfigureMockMvc
public class CustomErrorControllerSpringTest {
	
	@MockBean
	private CustomErrorController ErrorController;

	@MockBean
	private MockMvc mvc;
	
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

	
	