package edu.sru.cpsc.webshopping.controller;

import static org.junit.Assert.assertEquals;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.widgets.ImagePacket;
import edu.sru.cpsc.webshopping.domain.widgets.Subcategory;
import edu.sru.cpsc.webshopping.repository.widgets.SubcategoryRepository;

public class ImagePacketControllerTest {
	private Logger log= Logger.getLogger(getClass());
	
@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
	
}
@Test
@DisplayName("get Image Packet By Id")

public void getImagePacketById() {

try {
	log.info("Starting Execution");
ImagePacket expectedValue= null;

ImagePacketController imagepacketcontroller= new ImagePacketController(null);
ImagePacket actualValue= imagepacketcontroller.getImagePacketById(null);
	log.info("actualValue");
	System.out.println("actualValue");


}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("get Image Packet By Id Exception")
public void getImagePacketByIdException() {
	try {
		log.info("Starting Execution");
		
		ImagePacketController imagepacketcontroller= new ImagePacketController(null);
		imagepacketcontroller.getImagePacketById(null);
		Assertions.assertTrue(false);
	}
	catch(Exception exception) {
		log.error("Exception");
		exception.printStackTrace();
		Assertions.assertFalse(true);
	}
}
@AfterEach
void tearDown() {
	
}
@AfterAll
static void tearDownAll() {
	
}
}
