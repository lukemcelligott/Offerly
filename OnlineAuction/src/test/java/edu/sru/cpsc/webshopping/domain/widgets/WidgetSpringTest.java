package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {WidgetSpringTest.class})
public class WidgetSpringTest {

	/*
	 * Tests that basic user information is stored
	 */
	@Test
	void widgetTest() {
		Widget widget = new Widget();
		Category category = new Category("category");
		widget.setCategory(category);
		widget.setDescription("description");
		widget.setId(21);
		widget.setName("Heidi");
		
		assertEquals("category", widget.getCategory().getName());
		assertEquals("description", widget.getDescription());
		assertEquals(21, widget.getId());
		assertEquals("Heidi", widget.getName());
		
	}
}
