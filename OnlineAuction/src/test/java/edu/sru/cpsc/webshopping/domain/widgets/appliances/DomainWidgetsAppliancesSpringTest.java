package edu.sru.cpsc.webshopping.domain.widgets.appliances;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.user.DomainUserSpringTest;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;

@SpringBootTest(classes = {DomainWidgetsAppliancesSpringTest.class})
public class DomainWidgetsAppliancesSpringTest {

	/*
	 * Tests if the blender attributes are added
	 */
	@Test
	void appliance_BlenderTest() {
		Widget_Appliance blender = new Widget_Appliance();
		blender.setCategory("blender");
		blender.setColor("purple");
		blender.setCondition("good");
		blender.setDescription("loud");
		blender.setHeight(2);
		blender.setId(11);
		blender.setItemCondition("good");
		blender.setLength(1);
		blender.setName("Bob");
		blender.setSubCategory("subcategory");
		blender.setWidth(1);
		
		assertEquals("blender", blender.getCategory());
		assertEquals("purple", blender.getColor());
		assertEquals("good", blender.getCondition());
		assertEquals("loud", blender.getDescription());
		assertEquals(2, blender.getHeight());
		assertEquals(11, blender.getId());
		assertEquals("good", blender.getItemCondition());
		assertEquals(1, blender.getLength());
		assertEquals("Bob", blender.getName());
		assertEquals("subcategory", blender.getSubCategory());
		assertEquals(1, blender.getWidth());
	}
	
	/*
	 * Tests if the dryer attributes are added
	 */
	@Test
	void appliance_DryersTest() {
		Widget_Appliance dryer = new Widget_Appliance();
		dryer.setCategory("dryer");
		dryer.setColor("white");
		dryer.setCondition("good");
		dryer.setDescription("loud");
		dryer.setHeight(2);
		dryer.setId(11);
		dryer.setItemCondition("good");
		dryer.setLength(1);
		dryer.setName("Bob");
		dryer.setSubCategory("subcategory");
		dryer.setWidth(1);
		
		assertEquals("dryer", dryer.getCategory());
		assertEquals("white", dryer.getColor());
		assertEquals("good", dryer.getCondition());
		assertEquals("loud", dryer.getDescription());
		assertEquals(2, dryer.getHeight());
		assertEquals(11, dryer.getId());
		assertEquals("good", dryer.getItemCondition());
		assertEquals(1, dryer.getLength());
		assertEquals("Bob", dryer.getName());
		assertEquals("subcategory", dryer.getSubCategory());
		assertEquals(1, dryer.getWidth());
	}
	
	/*
	 * Tests if the microware attributes are added
	 */
	@Test
	void appliance_MicrowaveTest() {
		Widget_Appliance microwave = new Widget_Appliance();
		microwave.setCategory("microwave");
		microwave.setColor("black");
		microwave.setCondition("good");
		microwave.setDescription("loud");
		microwave.setHeight(2);
		microwave.setId(11);
		microwave.setItemCondition("good");
		microwave.setLength(1);
		microwave.setName("Bob");
		microwave.setSubCategory("subcategory");
		microwave.setWidth(1);
		
		assertEquals("microwave", microwave.getCategory());
		assertEquals("black", microwave.getColor());
		assertEquals("good", microwave.getCondition());
		assertEquals("loud", microwave.getDescription());
		assertEquals(2, microwave.getHeight());
		assertEquals(11, microwave.getId());
		assertEquals("good", microwave.getItemCondition());
		assertEquals(1, microwave.getLength());
		assertEquals("Bob", microwave.getName());
		assertEquals("subcategory", microwave.getSubCategory());
		assertEquals(1, microwave.getWidth());
	}
	
	/*
	 * Tests if the refrigerator attributes are added
	 */
	@Test
	void appliance_RefigeratorTest() {
		Widget_Appliance refrigerator = new Widget_Appliance();
		refrigerator.setCategory("refrigerator");
		refrigerator.setColor("black");
		refrigerator.setCondition("good");
		refrigerator.setDescription("loud");
		refrigerator.setHeight(2);
		refrigerator.setId(11);
		refrigerator.setItemCondition("good");
		refrigerator.setLength(1);
		refrigerator.setName("Bob");
		refrigerator.setSubCategory("subcategory");
		refrigerator.setWidth(1);
		
		assertEquals("refrigerator", refrigerator.getCategory());
		assertEquals("black", refrigerator.getColor());
		assertEquals("good", refrigerator.getCondition());
		assertEquals("loud", refrigerator.getDescription());
		assertEquals(2, refrigerator.getHeight());
		assertEquals(11, refrigerator.getId());
		assertEquals("good", refrigerator.getItemCondition());
		assertEquals(1, refrigerator.getLength());
		assertEquals("Bob", refrigerator.getName());
		assertEquals("subcategory", refrigerator.getSubCategory());
		assertEquals(1, refrigerator.getWidth());
	}
	
	/*
	 * Tests if the washers attributes are added
	 */
	@Test
	void appliance_WashersTest() {
		Widget_Appliance washers = new Widget_Appliance();
		washers.setCategory("washers");
		washers.setColor("blue");
		washers.setCondition("good");
		washers.setDescription("loud");
		washers.setHeight(2);
		washers.setId(11);
		washers.setItemCondition("good");
		washers.setLength(1);
		washers.setName("Bob");
		washers.setSubCategory("subcategory");
		washers.setWidth(1);
		
		assertEquals("washers", washers.getCategory());
		assertEquals("blue", washers.getColor());
		assertEquals("good", washers.getCondition());
		assertEquals("loud", washers.getDescription());
		assertEquals(2, washers.getHeight());
		assertEquals(11, washers.getId());
		assertEquals("good", washers.getItemCondition());
		assertEquals(1, washers.getLength());
		assertEquals("Bob", washers.getName());
		assertEquals("subcategory", washers.getSubCategory());
		assertEquals(1, washers.getWidth());
	}
	
	/*
	 * Tests if the appliance attributes are added
	 */
	@Test
	void widget_ApplianceTest() {
		Widget widget = new Widget();
		widget.setCategory("category");
		widget.setDescription("description");
		widget.setId(1);
		widget.setName("Alexis");
		widget.setSubCategory("subcategory");
		
		assertEquals("category", widget.getCategory());
		assertEquals("description", widget.getDescription());
		assertEquals(1, widget.getId());
		assertEquals("Alexis", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
	}
	
	/*
	 * Tests if the appliance attributes are added
	 */
	@Test
	void widget_Appliance_PartsTest() {
		Widget_Appliance_Parts widget = new Widget_Appliance_Parts();
		widget.setCategory("category");
		widget.setDescription("description");
		widget.setId(1);
		widget.setName("Alexis");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setColor("blue");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		
		assertEquals("category", widget.getCategory());
		assertEquals("description", widget.getDescription());
		assertEquals(1, widget.getId());
		assertEquals("Alexis", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("blue", widget.getColor());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
	}

	/*
	 * Tests if the appliance attributes are added
	 */
	@Test
	void Appliance_Blender_PartsTest() {
		Appliance_Blender_Parts widget = new Appliance_Blender_Parts();
		widget.setCategory("category");
		widget.setDescription("description");
		widget.setId(1);
		widget.setName("Alexis");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setColor("blue");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		widget.setMaterial("mat");
		widget.setCondition("con");
		widget.setWarranty("war");
		
		assertEquals("category", widget.getCategory());
		assertEquals("description", widget.getDescription());
		assertEquals(1, widget.getId());
		assertEquals("Alexis", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("blue", widget.getColor());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
		assertEquals("mat", widget.getMaterial());
		assertEquals("con", widget.getCondition());
		assertEquals("war", widget.getWarranty());
	}
	
	/*
	 * Tests if the appliance attributes are added
	 */
	@Test
	void Appliance_Dryer_PartsTest() {
		Appliance_Dryer_Parts widget = new Appliance_Dryer_Parts();
		widget.setCategory("category");
		widget.setDescription("description");
		widget.setId(1);
		widget.setName("Alexis");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setColor("blue");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		widget.setMaterial("mat");
		widget.setCondition("con");
		widget.setWarranty("war");
		
		assertEquals("category", widget.getCategory());
		assertEquals("description", widget.getDescription());
		assertEquals(1, widget.getId());
		assertEquals("Alexis", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("blue", widget.getColor());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
		assertEquals("mat", widget.getMaterial());
		assertEquals("con", widget.getCondition());
		assertEquals("war", widget.getWarranty());
	}
	
	/*
	 * Tests if the appliance attributes are added
	 */
	@Test
	void Appliance_Microwave_PartsTest() {
		Appliance_Microwave_Parts widget = new Appliance_Microwave_Parts();
		widget.setCategory("category");
		widget.setDescription("description");
		widget.setId(1);
		widget.setName("Alexis");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setColor("blue");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		widget.setMaterial("mat");
		widget.setCondition("con");
		widget.setWarranty("war");
		
		assertEquals("category", widget.getCategory());
		assertEquals("description", widget.getDescription());
		assertEquals(1, widget.getId());
		assertEquals("Alexis", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("blue", widget.getColor());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
		assertEquals("mat", widget.getMaterial());
		assertEquals("con", widget.getCondition());
		assertEquals("war", widget.getWarranty());
	}
	
	/*
	 * Tests if the appliance attributes are added
	 */
	@Test
	void Appliance_Refrigerator_PartsTest() {
		Appliance_Refrigerator_Parts widget = new Appliance_Refrigerator_Parts();
		widget.setCategory("category");
		widget.setDescription("description");
		widget.setId(1);
		widget.setName("Alexis");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setColor("blue");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		widget.setMaterial("mat");
		widget.setCondition("con");
		widget.setWarranty("war");
		
		assertEquals("category", widget.getCategory());
		assertEquals("description", widget.getDescription());
		assertEquals(1, widget.getId());
		assertEquals("Alexis", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("blue", widget.getColor());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
		assertEquals("mat", widget.getMaterial());
		assertEquals("con", widget.getCondition());
		assertEquals("war", widget.getWarranty());
	}
	
	/*
	 * Tests if the appliance attributes are added
	 */
	@Test
	void Appliance_Washer_PartsTest() {
		Appliance_Washers_Parts widget = new Appliance_Washers_Parts();
		widget.setCategory("category");
		widget.setDescription("description");
		widget.setId(1);
		widget.setName("Alexis");
		widget.setSubCategory("subcategory");
		widget.setBrand("brand");
		widget.setColor("blue");
		widget.setMadeIn("China");
		widget.setModel("model");
		widget.setPartName("part");
		widget.setMaterial("mat");
		widget.setCondition("con");
		widget.setWarranty("war");
		
		assertEquals("category", widget.getCategory());
		assertEquals("description", widget.getDescription());
		assertEquals(1, widget.getId());
		assertEquals("Alexis", widget.getName());
		assertEquals("subcategory", widget.getSubCategory());
		assertEquals("brand", widget.getBrand());
		assertEquals("blue", widget.getColor());
		assertEquals("China", widget.getMadeIn());
		assertEquals("model", widget.getModel());
		assertEquals("part", widget.getPartName());
		assertEquals("mat", widget.getMaterial());
		assertEquals("con", widget.getCondition());
		assertEquals("war", widget.getWarranty());
	}
}
