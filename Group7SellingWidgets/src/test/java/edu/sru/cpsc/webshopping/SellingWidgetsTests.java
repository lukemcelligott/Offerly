package edu.sru.cpsc.webshopping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.controller.misc.CustomErrorControllerSpringTest;

@SpringBootTest(classes = {SellingWidgetsTests.class})
@AutoConfigureMockMvc

public class SellingWidgetsTests
{

	@Test
	void loadTest() {
			assertThat(SpringApplication.run(SellingWidgets.class)).isNotExactlyInstanceOf(getClass());
	}
}