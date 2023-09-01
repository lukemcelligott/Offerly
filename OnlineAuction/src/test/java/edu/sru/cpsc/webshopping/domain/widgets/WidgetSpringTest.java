package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.sru.cpsc.webshopping.domain.user.DomainUserSpringTest;

@SpringBootTest(classes = {WidgetSpringTest.class})
public class WidgetSpringTest {

	/*
	 * Tests that basic user information is stored
	 */
	@Test
	void widgetTest() {
		Widget widget = new Widget();
		widget.setCategory("category");
		widget.setDescription("description");
		widget.setId(21);
		widget.setName("Heidi");
		widget.setSubCategory("subcategory");
		
		assertEquals("category", widget.getCategory());
		assertEquals("description", widget.getDescription());
		assertEquals(21, widget.getId());
		assertEquals("Heidi", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		
	}
	


}
