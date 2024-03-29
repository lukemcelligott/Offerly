package edu.sru.cpsc.webshopping.usecases;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserLogin() throws Exception {
        String username = "userNama";
        String password = "testPass";

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/homePage")); 
    }
}
