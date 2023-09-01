package edu.sru.cpsc.webshopping.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;


public class AddWidgetControllerTest {

	public static AddWidgetController Widget = new AddWidgetController(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); 

	@BeforeAll
	public static void newWidget() {

		
		Widget.setPage(null);
		Widget.setWidgetStorage(null);
		Widget.addAppliance(null);
		Widget.addCar(null);
		Widget.addComputer(null);
		Widget.addDryer(null);
		Widget.addElectronic(null);
		Widget.addFridge(null);
		Widget.addLawnCare(null);
		Widget.addLawnMower(null);
		Widget.addListing(null, null, null, null, null, null, null);
		Widget.addMicrowave(null);
		Widget.addVehicle(null);
		Widget.addVideoGame(null);
		Widget.addWasher(null);
		Widget.addWidget(null);
		Widget.createAppliance(null, null);
		Widget.createCar(null, null, null);
		Widget.createComputer(null, null, null, null);
		Widget.createDryer(null, null, null);
		Widget.createElectronic(null, null);
		Widget.createFridge(null, null, null);
		Widget.createLawnCare(null, null);
		Widget.createLawnMower(null, null, null);
		Widget.createListing(null);
		Widget.createAppliance(null, null);
		Widget.createVehicle(null, null);
		Widget.createVideoGame(null, null, null);
		Widget.createWasher(null, null, null);
		Widget.createWidget(null, null, null);
	
		
		
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
	public void createAppliance() {
		String actual = null;
		assertEquals(Widget.createAppliance(null, null), actual);
	}
	@Test 
	public void createCar() {
		String actual = null;
		assertEquals(Widget.createCar(null, null, null), actual);
	}
	@Test
	public void createComputer() {
		String actual = null;
		assertEquals(Widget.createComputer(null, actual, null, null), actual);
	}
	@Test
	public void createDryer() {
		String actual = null;
		assertEquals(Widget.createDryer(null, null, null), actual);
	}
	@Test
	public void createElectronic() {
		String actual = null;
		assertEquals(Widget.createElectronic(null, actual), actual);
	}
	@Test
	public void createFridge() {
		String actual = null;
		assertEquals(Widget.createFridge(null, null, null), actual);
	}
	@Test 
	public void createLawnCare() {
		String actual = null;
		assertEquals(Widget.createLawnCare(null, actual), actual);
	}
	@Test
	public void createLawnMower() {
		String actual = null;
		assertEquals(Widget.createLawnMower(null, null, null), actual);
	}
	@Test
	public void createListing() {
		String actual= null;
		assertEquals(Widget.createListing(null), actual);
	}
	@Test
	public void createVehicle() {
		String actual = null;
		assertEquals(Widget.createVehicle(null, actual), actual);
	}
	@Test
	public void createVideoGame() {
		String actual = null;
		assertEquals(Widget.createVideoGame(null, null, null), actual);
	}
	@Test
	public void createWasher() {
		String actual = null;
		assertEquals(Widget.createWasher(null, null, null), actual);
	}
	@Test 
	public void createWidget() {
		String actual = null;
		assertEquals(Widget.createWidget(null, null, null), actual);
	}
	
	
}

