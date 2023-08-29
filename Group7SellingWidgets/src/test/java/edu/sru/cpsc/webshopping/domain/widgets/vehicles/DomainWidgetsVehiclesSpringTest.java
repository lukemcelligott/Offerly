package edu.sru.cpsc.webshopping.domain.widgets.vehicles;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.widgets.appliances.DomainWidgetsAppliancesSpringTest;

/*
 * Tests the entire vehicle package. It ensure that the car 
 * file inherits the Widget file. The tests ensure that both are
 * working and correspond to each other. 
 * 
 * Gets confused between Condition and ItemCondition
 */

@SpringBootTest(classes = {DomainWidgetsVehiclesSpringTest.class})
public class DomainWidgetsVehiclesSpringTest {

	/*
	 * Tests that the attributes are added correctly for cars
	 */
	@Test
	void vehicle_CarTest() {
		Vehicle_Car car = new Vehicle_Car();
		car.setCategory("vehicle");
		car.setCondition("good");
		car.setDescription("red");
		car.setId(45);
		//car.setItemCondition("mint");
		car.setMake("Kia");
		car.setModel("Soul");
		car.setName("Heidi");
		car.setRoadSafe("yes");
		car.setSubCategory("car");
		car.setTerrain("rough");
		car.setTransmissionType("Automatic");
		car.setWheelDrive("yes");
		car.setYear(2022);
		
		assertEquals("vehicle", car.getCategory());
		assertEquals("good", car.getCondition());
		assertEquals("red", car.getDescription());
		assertEquals(45, car.getId());
		//assertEquals("mint", car.getItemCondition());
		assertEquals("Kia", car.getMake());
		assertEquals("Soul", car.getModel());
		assertEquals("Heidi", car.getName());
		assertEquals("yes", car.getRoadSafe());
		assertEquals("car", car.getSubCategory());
		assertEquals("rough", car.getTerrain());
		assertEquals("Automatic", car.getTransmissionType());
		assertEquals("yes", car.getWheelDrive());
		assertEquals(2022, car.getYear());
	}
	
	/*
	 * Tests that the attributes are added correctly for the widget
	 */
	@Test
	void widget_VehiclesTest() {
		Widget_Vehicles widget = new Widget_Vehicles();
		widget.setCategory("widget");
		widget.setCondition("fair");
		widget.setDescription("pink");
		widget.setId(12);
		//widget.setItemCondition("poor");
		widget.setName("Beth");
		widget.setRoadSafe("no");
		widget.setSubCategory("vehicle");
		widget.setTerrain("bumpy");
		
		assertEquals("widget", widget.getCategory());
		assertEquals("fair", widget.getCondition());
		assertEquals("pink", widget.getDescription());
		assertEquals(12, widget.getId());
		//assertEquals("poor", widget.getItemCondition());
		assertEquals("Beth", widget.getName());
		assertEquals("no", widget.getRoadSafe());
		assertEquals("vehicle", widget.getSubCategory());
		assertEquals("bumpy", widget.getTerrain());
	}

	/*
	 * Tests that the attributes are added correctly for the vehicle_parts
	 */
	@Test
	void widget_Vehicles_Parts() {
		Widget_Vehicles_Parts parts = new Widget_Vehicles_Parts();
		
		parts.setBrand("Ford");
		parts.setCategory("cat");
		parts.setColor("brown");
		parts.setDescription("desc");
		parts.setId(0);
		parts.setMadeIn("America");
		parts.setModel("model");
		parts.setName("engine");
		parts.setPartName("eugine");
		parts.setSubCategory("parts");
		
		assertEquals("cat", parts.getCategory());
		assertEquals("Ford", parts.getBrand());
		assertEquals("brown", parts.getColor());
		assertEquals("desc", parts.getDescription());
		assertEquals(0, parts.getId());
		assertEquals("America", parts.getMadeIn());
		assertEquals("model", parts.getModel());
		assertEquals("engine", parts.getName());
		assertEquals("eugine", parts.getPartName());
		assertEquals("parts", parts.getSubCategory());
	}
	
	/*
	 * Tests that the attributes are added correctly for the car_parts
	 */
	@Test
	void vehicle_Car_Parts() {
		Vehicle_Car_Parts parts = new Vehicle_Car_Parts();
		
		parts.setCondition("bad");
		parts.setMaterial("sheet metal");
		parts.setWarranty("none");
		
		assertEquals("bad", parts.getCondition());
		assertEquals("sheet metal", parts.getMaterial());
		assertEquals("none", parts.getWarranty());
	}
}
