package edu.sru.cpsc.webshopping.domain.widgets.electronics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.DomainWidgetsAppliancesSpringTest;

@SpringBootTest(classes = {DomainWidgetsElectronicsSpringTest.class})
public class DomainWidgetsElectronicsSpringTest {
	
	/*
	 * Tests that the computer attributes are valid
	 */
	@Test
	void electronics_ComputersTest() {
		Widget_Electronics computers = new Widget_Electronics();
		computers.setCategory("computer");
		computers.setDescription("pink");
		computers.setEntertainmentUse("YouTube");
		computers.setId(22);
		computers.setName("Karen");
		computers.setOfficeUse("Excel");
		computers.setSubCategory("machine");
		
		assertEquals("computer", computers.getCategory());
		assertEquals("pink", computers.getDescription());
		assertEquals("YouTube", computers.getEntertainmentUse());
		assertEquals(22, computers.getId());
		assertEquals("Karen", computers.getName());
		assertEquals("Excel", computers.getOfficeUse());
		assertEquals("machine", computers.getSubCategory());
	}
	
	/*
	 * Tests that the video game attributes are valid
	 */
	@Test
	void electronics_VideoGamesTest() {
		Widget_Electronics games = new Widget_Electronics();
		games.setCategory("video games");
		games.setDescription("GTA");
		games.setEntertainmentUse("YouTube");
		games.setId(22);
		games.setName("Karen");
		games.setOfficeUse("Excel");
		games.setSubCategory("machine");
		
		assertEquals("video games", games.getCategory());
		assertEquals("GTA", games.getDescription());
		assertEquals("YouTube", games.getEntertainmentUse());
		assertEquals(22, games.getId());
		assertEquals("Karen", games.getName());
		assertEquals("Excel", games.getOfficeUse());
		assertEquals("machine", games.getSubCategory());
	}
	
	/*
	 * Tests that the electronics attributes are valid
	 */
	@Test
	void widget_ElectronicsTest() {
		Widget widget = new Widget();
		widget.setCategory("electronics");
		widget.setDescription("shiny");
		widget.setId(3);
		widget.setName("Emilie");
		widget.setSubCategory("subcategory");
		
		assertEquals("electronics", widget.getCategory());
		assertEquals("shiny", widget.getDescription());
		assertEquals(3, widget.getId());
		assertEquals("Emilie", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
	}

	/*
	 * Tests that the electronics attributes are valid
	 */
	@Test
	void widget_Electronics_PartsTest() {
		Widget_Electronics_Parts widget = new Widget_Electronics_Parts();
		widget.setCategory("electronics");
		widget.setDescription("shiny");
		widget.setId(3);
		widget.setName("Emilie");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		
		assertEquals("electronics", widget.getCategory());
		assertEquals("shiny", widget.getDescription());
		assertEquals(3, widget.getId());
		assertEquals("Emilie", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
	}
	
	/*
	 * Tests that the electronics attributes are valid
	 */
	@Test
	void Electronics_Computers_PartsTest() {
		Electronics_Computers_Parts widget = new Electronics_Computers_Parts();
		widget.setCategory("electronics");
		widget.setDescription("shiny");
		widget.setId(3);
		widget.setName("Emilie");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		widget.setMaterial("mat");
		widget.setCondition("con");
		widget.setWarranty("war");
		
		assertEquals("electronics", widget.getCategory());
		assertEquals("shiny", widget.getDescription());
		assertEquals(3, widget.getId());
		assertEquals("Emilie", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
		assertEquals("mat", widget.getMaterial());
		assertEquals("con", widget.getCondition());
		assertEquals("war", widget.getWarranty());
	}
	
	/*
	 * Tests that the electronics attributes are valid
	 */
	@Test
	void Electronics_VideoGames_PartsTest() {
		Electronics_VideoGames_Parts widget = new Electronics_VideoGames_Parts();
		widget.setCategory("electronics");
		widget.setDescription("shiny");
		widget.setId(3);
		widget.setName("Emilie");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		widget.setMaterial("mat");
		widget.setCondition("con");
		widget.setWarranty("war");
		
		assertEquals("electronics", widget.getCategory());
		assertEquals("shiny", widget.getDescription());
		assertEquals(3, widget.getId());
		assertEquals("Emilie", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
		assertEquals("mat", widget.getMaterial());
		assertEquals("con", widget.getCondition());
		assertEquals("war", widget.getWarranty());
	}
}
