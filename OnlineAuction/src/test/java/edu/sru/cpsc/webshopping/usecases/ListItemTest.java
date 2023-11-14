package edu.sru.cpsc.webshopping.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import edu.sru.cpsc.webshopping.controller.AddWidgetController;
import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.WidgetService;

@SpringBootTest(classes = {ListItemTest.class})
@AutoConfigureMockMvc
public class ListItemTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Mock
    private WidgetService widgetService;

    @InjectMocks
    private AddWidgetController addWidgetController;


    @BeforeEach
    public void setup() {
        //mockMvc = MockMvcBuilders.standaloneSetup(addWidgetController).build();
        when(userService.getUserByUsername(anyString())).thenReturn(new User());
    }

    /*
     * Mock the following:
     * user
     * widget
     * images
     * 
     * verify the following:
     * listing exists
     */

    @Test
    public void testAddListing() throws Exception {
        // Arrange
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testUser");
        User mockUser = new User(); // Adjust with actual user details
        when(userService.getUserByUsername("testUser")).thenReturn(mockUser);
        String stateId = "1"; 

        // Prepare your test data here...
        MarketListing marketListing = new MarketListing(); // Populate with test data

        MockMultipartFile coverImage = new MockMultipartFile("listingCoverImage", "filename.txt", "text/plain", "some xml".getBytes());
        byte[] files = new byte[1];

        // Act & Assert
        mockMvc.perform(multipart("/addListing")
            .file(coverImage)
            .file("imageUpload", files)
            .param("stateId", stateId)
            .flashAttr("marketListing", marketListing)
            .principal(mockPrincipal))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("homePage"));

        // Verify interactions with mocked services
        verify(userService).getUserByUsername("testUser");
        verify(widgetService).addWidget(any(Widget.class));
        // Add more verifications as needed
    }
}
