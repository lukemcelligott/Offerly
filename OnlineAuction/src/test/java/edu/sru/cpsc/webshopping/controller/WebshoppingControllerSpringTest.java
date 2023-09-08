package edu.sru.cpsc.webshopping.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Blender;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Dryers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Microwave;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Refrigerator;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Washers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Widget_Appliance;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Widget_Electronics;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Widget_Vehicles;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
/*
 * Tests all of the files that are under the webshopping controller
 * package. They ensure that everything fits together and loads 
 * correctly. This section is what most the project is comprised of. 
 */

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebshoppingControllerSpringTest.class, loader = AnnotationConfigContextLoader.class)
public class WebshoppingControllerSpringTest {
	
	@Autowired
	@MockBean
	private WidgetController widgetController;
	@Autowired 
	private UserController userController;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	WidgetRepository widgetRepository;
	@Autowired
	MarketListingRepository marketListingRepos;
	@Autowired
	MarketListingDomainController marketListingController;
	@Autowired
	UserRepository userRepo;
	@Autowired
	Widget widget;
	@Autowired
	Widget_Appliance appliances;
	@Autowired
	Widget_Electronics electronics;
	@Autowired
	Widget_Vehicles vehicle;
	@Autowired
	Widget_LawnCare lawnCare;
	@Autowired
	Appliance_Dryers dryer;
	@Autowired
	Appliance_Washers washer;
	@Autowired
	Appliance_Blender blender;
	@Autowired
	Appliance_Microwave microwave;
	@Autowired
	Appliance_Refrigerator refridgerator;
	@Autowired
	Electronics_Computers computer;
	@Autowired
	Electronics_VideoGames videoGame;
	@Autowired
	Vehicle_Car car;
	@Autowired
	LawnCare_LawnMower mower;
	@Autowired
	MarketListing marketListing;
	
	/*
	 * Tests that the pages load correctly 
	*/

	@Test
	void addWidgetControllerTest() throws Exception {
		User user  = new User();
		mvc.perform(post("/addWidget").content(objectMapper.writeValueAsString(userController)));
		assertThat(userController.getCurrently_Logged_In()).isEqualTo("/addWidget");
	}
	
}
