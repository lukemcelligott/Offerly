package edu.sru.cpsc.webshopping.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.controller.AddWidgetController.WidgetForm;
import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetAttribute;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;
import edu.sru.cpsc.webshopping.service.AttributeService;
import edu.sru.cpsc.webshopping.service.CategoryService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.WidgetService;
import edu.sru.cpsc.webshopping.util.enums.AttributeDataType;

@SpringBootTest
public class AddWidgetControllerTest {

	@Mock
    private Category category;

	@Mock
    private CategoryController categoryController;

    @Mock
    private UserController userController;

    @Mock
    private AttributeService attributeService;

    @Mock
    private WidgetService widgetService;

    @Mock
    private UserService userService;

	@Mock
    private CategoryRepository categoryRepository;

	@Mock
    private CategoryService categoryService;

	@Mock
    private MarketListing marketListing;

	@Mock
    private MarketListingDomainController marketListingController;

	@Mock
    private WidgetImageController widgetImageController;

	@Mock
    private Auction auction;

	@Mock
    private Widget widget;

	@Mock
    private RedirectAttributes attributes;

	@Mock
    private BindingResult result;

	@Mock
    private List<MultipartFile> files;

	@Mock
    private MultipartFile file;

	@Mock
    private WidgetImage tempImage;

	@Mock
    private MultipartFile coverImage;

	@Mock
    private User seller;

	@Mock
    private WidgetController widgetController;

    @Mock
    private Model model;

    @InjectMocks
    private AddWidgetController addWidgetController;

    @BeforeEach
    public void setUp() {
         MockitoAnnotations.openMocks(this);
    }


    @Test
	// This test should add a widget
    public void testAddWidget() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
        User user = new User();
        user.setUsername("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);
        when(categoryController.getAllCategories()).thenReturn(new ArrayList<Category>());

        String result = addWidgetController.addWidget(model, principal);

        assertEquals("addWidget", result);
        assertEquals("widgets", addWidgetController.getPage());
    }

    @Test
	// This test is expects an IllegalStateException to be thrown
    public void testAddWidgetNotLoggedIn() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(null);
        User user = new User();
        user.setUsername("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);

        assertThrows(IllegalStateException.class, () -> {
            addWidgetController.addWidget(model, principal);
        });
    }

	@Test
	public void testCreateWidget() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryService.getTopRecommendedAttributes(category, 0)).thenReturn(new ArrayList<Attribute>());
        WidgetForm widgetForm = addWidgetController.new WidgetForm();
        when(model.addAttribute("category", category)).thenReturn(model);
        when(model.addAttribute("entries", widgetForm.getEntries())).thenReturn(model);
        
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
        User user = new User();
        user.setUsername("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);

		// Act
        String result = addWidgetController.createWidget(categoryId, model, principal);

        // Assert
        assertEquals("createWidgetTemplate", result);
    }

	@Test
	// This test should create a widget
    public void testCreateWidgetListing() {
        // Arrange
        WidgetForm widgetForm = addWidgetController.new WidgetForm();
        widgetForm.setName("Test Widget");
        widgetForm.setDescription("Test Description");
        WidgetForm.AttributeFormEntry entry = new WidgetForm.AttributeFormEntry();
        Attribute attribute = new Attribute();
        attribute.setAttributeKey("Test Attribute");
        attribute.setDataType(AttributeDataType.STRING);
        entry.setAttribute(attribute);
        WidgetAttribute widgetAttribute = new WidgetAttribute();
        widgetAttribute.setValue("Test Value");
        entry.setWidgetAttribute(widgetAttribute);
        List<WidgetForm.AttributeFormEntry> entries = new ArrayList<>();
        entries.add(entry);
        widgetForm.setEntries(entries);
        when(category.getId()).thenReturn(1L);
        
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
        User user = new User();
        user.setUsername("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);

        // Act
        String result = addWidgetController.createWidgetListing(model, widgetForm, null, principal);

        // Assert
        assertEquals("redirect:createListing", result);
    }

	@Test
	// This test should create a listing
    public void testCreateListing() {
        // Arrange
        MarketListing marketListing = new MarketListing();
        Category category = new Category();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
        User user = new User();
        user.setUsername("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);
        when(model.addAttribute("listing", marketListing)).thenReturn(model);
        when(model.addAttribute("Category", category)).thenReturn(model);

        // Act
        String result = addWidgetController.createListing(model, principal);

        // Assert
        assertEquals("createListing", result);
    }

	/* @Test
	public void testAddListing() {
		// Arrange
		List<WidgetImage> listingImages = new ArrayList<>();
		WidgetImage tempImage = new WidgetImage();

		when(marketListing.getAuction()).thenReturn(auction);
		when(auction.getStartingBid()).thenReturn(BigDecimal.ONE);
		when(marketListing.getPricePerItem()).thenReturn(BigDecimal.ONE);
		when(marketListing.getAuction().getCurrentBid()).thenReturn(BigDecimal.ONE);
		when(marketListing.getQtyAvailable()).thenReturn(1L);
		when(userController.getCurrently_Logged_In()).thenReturn(new User());
		when(marketListing.getSeller()).thenReturn(seller);
		when(seller.getId()).thenReturn(1L);
		when(marketListing.getSeller().getId()).thenReturn(1L);
		when(coverImage.getOriginalFilename()).thenReturn("filename");
		when(StringUtils.cleanPath(coverImage.getOriginalFilename())).thenReturn("filename");
		when(marketListingController.getListingByWidget(widget)).thenReturn(marketListing);
		when(result.hasErrors()).thenReturn(false);
		when(widgetController.getWidget(widget.getId())).thenReturn(widget);

		List<MultipartFile> fileList = new ArrayList<>();
		fileList.add(coverImage);
		MultipartFile[] tempFileArray = fileList.isEmpty() ? null : fileList.toArray(new MultipartFile[0]);

		// Act
		String result = addWidgetController.addListing(model, coverImage, tempFileArray, attributes, marketListing, this.result);

		// Assert
		assertEquals("redirect:homePage", result);
	} */
	
}

