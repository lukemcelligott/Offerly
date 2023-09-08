package edu.sru.cpsc.webshopping.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;


public class AddWidgetControllerTest {

	public static AddWidgetController Widget = new AddWidgetController(null, null, null, null, null, null, null, null, null, null, null, null, null); 

	@BeforeAll
	public static void newWidget() {
		Widget.setPage(null);
		Widget.setWidgetStorage(null);
		Widget.addListing(null, null, null, null, null, null, null);
		Widget.addWidget(null);
		Widget.createAppliance(null, null);
		Widget.createElectronic(null, null);
		Widget.createLawnCare(null, null);
		Widget.createListing(null);
		Widget.createAppliance(null, null);
		Widget.createVehicle(null, null);
		
}
	@Test
	public void pageTest() {
		String actual = null;
		assertEquals(Widget.getPage(), actual);
		
	}
	@Test
	public void widgetStorage() {
		String actual = null;
		assertEquals(Widget.getWidgetStorage(), actual);
	}
	@Test
	public void addAppliance() {
		String actual = null;
		assertEquals(Widget.addAppliance(null), actual);
	}
	@Test
	public void addCar() {
		String actual = null;
		assertEquals(Widget.addCar(null), actual);
	}
	@Test
	public void addDryer() {
		String actual = null;
		assertEquals(Widget.addDryer(null), actual);
	}
	@Test
	public void addElectronic() {
		String actual = null;
		assertEquals(Widget.addElectronic(null), actual);
	}
	@Test
	public void addFridge() {
		String actual = null;
		assertEquals(Widget.addFridge(null), actual);
	}
	@Test
	public void addLawn() {
		String actual = null;
		assertEquals(Widget.addLawnCare(null), actual);
		
	}
	@Test
	public void addListing() {
		String actual = null;
		assertEquals(Widget.addListing(null, null, null, null, null, null, null), actual);
	}
	@Test
	public void addMicrowave() {
		String actual = null;
		assertEquals(Widget.addMicrowave(null), actual);
	}
	@Test
	public void addVehicle() {
		String actual = null;
		assertEquals(Widget.addVehicle(null), actual);
	}
	@Test 
	public void addWasher() {
		String actual = null;
		assertEquals(Widget.addWasher(null), actual);
	}
	@Test
	public void addWidget() {
		String actual = null;
		assertEquals(Widget.addWidget(null), actual);
	}
	@Test
	public void createListing() {
		String actual= null;
		assertEquals(Widget.createListing(null), actual);
	}
}

