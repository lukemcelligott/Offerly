package edu.sru.cpsc.webshopping.domain.widgets.lawncare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(classes = DomainWidgetsLawncareSpringTest.class, loader = AnnotationConfigContextLoader.class)

/*
 * Tests the entire Lawncare package. It ensure that the LawnMower 
 * file inherits the Widget file. The tests ensure that both are
 * working and correspond to each other. 
 */

public class DomainWidgetsLawncareSpringTest {
	
	/*
	 * Tests that the attributes are loaded 
	 */
	@Test
	void lawnCare_LawnMowerTest() {
		LawnCare_LawnMower lawnmower = new LawnCare_LawnMower();
		lawnmower.setCategory("LawnCare");
		lawnmower.setDescription("brown");
		lawnmower.setId(36);
		lawnmower.setName("Bill");
		lawnmower.setSubCategory("garden");
		lawnmower.setToolType("lawn");
		lawnmower.setYardSize("big");
		lawnmower.setBladeWidth("4");
		lawnmower.setBrand("Deer");
		lawnmower.setPowerSource("engine");
		
		assertEquals("LawnCare", lawnmower.getCategory());
		assertEquals("brown", lawnmower.getDescription());
		assertEquals(36, lawnmower.getId());
		assertEquals("Bill", lawnmower.getName());
		assertEquals("garden", lawnmower.getSubCategory());
		assertEquals("lawn", lawnmower.getToolType());
		assertEquals("big", lawnmower.getYardSize());
		assertEquals("4", lawnmower.getBladeWidth());
		assertEquals("Deer", lawnmower.getBrand());
		assertEquals("engine", lawnmower.getPowerSource());
	}
	
	/*
	 * Tests that the widget attributes are loaded 
	 */
	@Test
	void widget_LawnCareTest() {
		Widget_LawnCare widget = new Widget_LawnCare();
		widget.setCategory("lawncare");
		widget.setDescription("black");
		widget.setId(12);
		widget.setName("Steve");
		widget.setSubCategory("garden");
		widget.setToolType("tool");
		widget.setYardSize("small");
		
		assertEquals("lawncare", widget.getCategory());
		assertEquals("black", widget.getDescription());
		assertEquals(12, widget.getId());
		assertEquals("Steve", widget.getName());
		assertEquals("garden", widget.getSubCategory());
		assertEquals("tool", widget.getToolType());
		assertEquals("small", widget.getYardSize());
	}
	
	/*
	 * Tests that the widget attributes are loaded 
	 */
	@Test
	void widget_LawnCare_PartsTest() {
		Widget_LawnCare_Parts widget = new Widget_LawnCare_Parts();
		widget.setCategory("lawncare");
		widget.setDescription("black");
		widget.setId(12);
		widget.setName("Steve");
		widget.setSubCategory("garden");
		widget.setBrand("B&D");
		widget.setColor("black");
		widget.setPartName("part");
		widget.setModel("model");
		widget.setMadeIn("China");
		
		assertEquals("lawncare", widget.getCategory());
		assertEquals("black", widget.getDescription());
		assertEquals(12, widget.getId());
		assertEquals("Steve", widget.getName());
		assertEquals("garden", widget.getSubCategory());
		assertEquals("B&D", widget.getBrand());
		assertEquals("black", widget.getColor());
		assertEquals("part", widget.getPartName());
		assertEquals("model", widget.getModel());
		assertEquals("China", widget.getMadeIn());
	}
	
	/*
	 * Tests that the widget attributes are loaded 
	 */
	@Test
	void LawnCare_LawnMower_Parts() {
		LawnCare_LawnMower_Parts widget = new LawnCare_LawnMower_Parts();
		widget.setCategory("lawncare");
		widget.setDescription("black");
		widget.setId(12);
		widget.setName("Steve");
		widget.setSubCategory("garden");
		widget.setBrand("B&D");
		widget.setColor("black");
		widget.setPartName("part");
		widget.setModel("model");
		widget.setMadeIn("China");
		widget.setMaterial("metal");
		widget.setCondition("bad");
		widget.setWarranty("war");
		
		assertEquals("lawncare", widget.getCategory());
		assertEquals("black", widget.getDescription());
		assertEquals(12, widget.getId());
		assertEquals("Steve", widget.getName());
		assertEquals("garden", widget.getSubCategory());
		assertEquals("B&D", widget.getBrand());
		assertEquals("black", widget.getColor());
		assertEquals("part", widget.getPartName());
		assertEquals("model", widget.getModel());
		assertEquals("China", widget.getMadeIn());
		assertEquals("metal", widget.getMaterial());
		assertEquals("bad", widget.getCondition());
		assertEquals("war", widget.getWarranty());
	}
}
