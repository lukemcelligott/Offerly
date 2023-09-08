package edu.sru.cpsc.webshopping.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.domain.market.MarketListingCSVModel;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.domain.widgets.Widget_General;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Blender;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Blender_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Dryer_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Dryers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Microwave;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Microwave_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Refrigerator;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Refrigerator_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Washers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Washers_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Widget_Appliance;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Widget_Electronics;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Widget_Vehicles;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetGeneralRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceBlenderRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceDryersRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceMicrowaveRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceRefrigeratorRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceWashersRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.WidgetApplianceRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.ElectronicsComputersRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.ElectronicsVideoGamesRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.WidgetElectronicsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.lawncare.LawnCareLawnMowerRepository;
import edu.sru.cpsc.webshopping.repository.widgets.lawncare.WidgetLawnCareRepository;
import edu.sru.cpsc.webshopping.repository.widgets.vehicles.VehicleCarRepository;
import edu.sru.cpsc.webshopping.repository.widgets.vehicles.WidgetVehiclesRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetImageRepository;
import edu.sru.cpsc.webshopping.util.PreLoad;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.WordUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller for creating widgets and listings.
 * @author NICK
 *
 */
@Controller
public class AddWidgetController
{

	WidgetRepository widgetRepository;
	CategoryRepository categoryRepository;
	WidgetImageRepository widgetImageRepository;
	MarketListingRepository marketListingRepos;
	WidgetController widgetController;
	UserController userController;
	MarketListingDomainController marketListingController;
	WidgetImageController widgetImageController;
	UserRepository userRepo;
	Widget widget;
	Widget_Appliance appliances;
	Widget_Electronics electronics;
	Widget_Vehicles vehicle;
	Widget_LawnCare lawnCare;
	Appliance_Dryers dryer;
	Appliance_Washers washer;
	Appliance_Blender blender;
	Appliance_Blender_Parts blenderPart;
	Appliance_Dryer_Parts dryerPart;
	Appliance_Microwave_Parts microwavePart;
	Appliance_Refrigerator_Parts refrigeratorPart;
	Appliance_Washers_Parts washerPart;
	Appliance_Microwave microwave;
	Appliance_Refrigerator refridgerator;
	Electronics_Computers computer;
	Electronics_Computers_Parts computerPart;
	Electronics_VideoGames videoGame;
	Electronics_VideoGames_Parts videoGamePart;
	Vehicle_Car car;
	Vehicle_Car_Parts carPart;
	LawnCare_LawnMower mower;
	LawnCare_LawnMower_Parts mowerPart;
	Widget_General generalWidget;
	Set<WidgetImage> listingImages = new HashSet<>();
	CategoryController categories;
	AttributeController attributes;
	SubcategoryController subcategories;
	FieldsController fields;
	MarketListing marketListing;
	private Widget widgetStorage;
	private Category category;
	private String subcategory;
	private WidgetImage tempImage = new WidgetImage();
	private String page;
	
	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public AddWidgetController(WidgetRepository widgetRepository, CategoryRepository categoryRepository, CategoryController categories,
	 		SubcategoryController subcategories, AttributeController attributes, FieldsController fields, WidgetImageRepository widgetImageRepository, WidgetImageController widgetImageController,
			MarketListingRepository marketListingRepos, WidgetController widgetController, UserController userController, MarketListingDomainController marketListingController, UserRepository userRepo)
	{
		this.categories = categories;
		this.attributes = attributes;
		this.subcategories = subcategories;
		this.fields = fields;
		this.widgetRepository = widgetRepository;
		this.categoryRepository = categoryRepository;
		this.marketListingRepos = marketListingRepos;
		this.widgetController = widgetController;
		this.userController = userController;
		this.marketListingController = marketListingController;
		this.userRepo = userRepo;
		this.widgetImageRepository = widgetImageRepository;
		this.widgetImageController = widgetImageController;
	}

	@RequestMapping("/addWidget")
	public String addWidget(Model model)
	{
		if (userController.getCurrently_Logged_In() == null)
		{
			throw new IllegalStateException("Not logged in.");
		}
		
		setPage("widgets");
		
		model.addAttribute("categories", categories.getAllCategories());
		model.addAttribute("subcategories", subcategories.getAllSubcategories());
		model.addAttribute("user", userController.getCurrently_Logged_In());
		model.addAttribute("page", "addWidget");
		return "addWidget";
	}
	
	@RequestMapping("/createWidget")
	public String createWidget(@RequestParam("category") Long categoryId, Model model)
	{
		Category category = categoryRepository.findById(categoryId)
		        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
		
		this.category = category;
		model.addAttribute("category", category);
		model.addAttribute("attributes", attributes.getAllAttributes());
		model.addAttribute("user", userController.getCurrently_Logged_In());
		
		return "createWidgetTemplate";
	}
	
	
	@RequestMapping("/createWidgetListing") public String createCarParts(Model model, @ModelAttribute Widget widget, BindingResult result) {
		widget.setCategory(category); 
		widgetController.addWidget(widget, result);
		this.widget = widget;
		model.addAttribute("createWidget", true);
		model.addAttribute("Widget", widget);
		model.addAttribute("user", userController.getCurrently_Logged_In()); 
		return "redirect:createListing"; 
	}


	@RequestMapping("addCarParts")
	public String addCarParts(Model model)
	{
		carPart = new Vehicle_Car_Parts();
		model.addAttribute("car_parts", carPart);
		model.addAttribute("fields", fields.getAllFields());
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addCarParts";
	}

	@RequestMapping("/createListing")
	public String createListing(Model model)
	{
		marketListing = new MarketListing();
		model.addAttribute("pricePerItem", marketListing.getPricePerItem());
		model.addAttribute("qtyAvailable", marketListing.getQtyAvailable());
		model.addAttribute("listing", marketListing);
		model.addAttribute("Category", category);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "createListing";
	}
	
	/**
	 * 
	 * @param model
	 * @param coverImage Image to be displayed when browsing
	 * @param files all images associated with the current listing
	 * @param qty
	 * @param attributes
	 * @param marketListing
	 * @param result
	 * @return
	 */

	@RequestMapping("/addListing")
	public String addListing(Model model, @RequestParam("listingCoverImage") MultipartFile coverImage, @RequestParam("imageUpload") MultipartFile[] files, @RequestParam("qtyAvailable") Long qty, RedirectAttributes attributes, @Valid @ModelAttribute MarketListing marketListing, BindingResult result)
	{
		if(getWidgetStorage() != null)
		{
			System.out.println(getWidgetStorage().getId());
			widgetController.addWidgetnobinding(getWidgetStorage());
			getWidgetStorage().setId(getWidgetStorage().getId()+1);
		}
		
		marketListing.setQtyAvailable(qty);
		model.addAttribute("pricePerItem", marketListing);
		model.addAttribute("qtyAvailable", marketListing.getQtyAvailable());
		model.addAttribute("listing", marketListing);
		model.addAttribute("subcategory", subcategory);
		marketListing.setSeller(userController.getCurrently_Logged_In());
		marketListing.setWidgetSold(widget);
		marketListing.setDeleted(false);
		//code basis found at https://attacomsian.com/blog/spring-boot-thymeleaf-file-upload

		marketListing.setCoverImage(marketListing.getSeller().getId() + StringUtils.cleanPath(coverImage.getOriginalFilename()));
		marketListingController.addMarketListing(marketListing);
		tempImage.setImageName(marketListing.getSeller().getId() + StringUtils.cleanPath(coverImage.getOriginalFilename()));
		tempImage.setMarketListing(marketListing);
		widgetImageController.addWidgetImage(tempImage);
		listingImages.add(tempImage);
		
		BigDecimal oneCent = new BigDecimal("0.01");
		
		if(marketListing.getPricePerItem() == null)
		{
			setPage("error2");
			result.addError(new FieldError("pricePerItem", "pricePerItem", "Price per item can't be null"));	    	
		}
		
		// check if the price of the item is at least one cent
		else if(marketListing.getPricePerItem().compareTo(oneCent) < 0)
		{
			setPage("error2");
			result.addError(new FieldError("pricePerItem", "pricePerItem", "Price per item must be greater than 0.01"));
		}

		if(Long.valueOf(marketListing.getQtyAvailable()).compareTo((long) 0) <= 0)
		{
			System.out.println(marketListing.getQtyAvailable());
			System.out.println("help");
			setPage("error3");
			result.addError(new FieldError("pricePerItem", "pricePerItem", "Quantity must be greater than 0"));	    	
		}
		
		if (result.hasErrors())
		{
			System.out.println(marketListing.getQtyAvailable());
			setWidgetStorage(widget);

			widgetController.deleteWidget(getWidgetStorage().getId());
			model.addAttribute("page", getPage());
			model.addAttribute("pricePerItem", marketListing);
			model.addAttribute("qtyAvailable", marketListing.getQtyAvailable());
			model.addAttribute("listing", marketListing);
			model.addAttribute("subcategory", subcategory);
			return "createListing";
		}
		
		if(!(files.length == 0))
		{
			marketListingController.getListingByWidget(widget).setCoverImage(marketListing.getSeller().getId() + StringUtils.cleanPath(coverImage.getOriginalFilename()));
			List<MultipartFile> MPF = Arrays.asList(files).stream().collect(Collectors.toList());
			MPF.forEach(file -> {setListingImage(file, marketListing);});
			marketListing.setImages(listingImages);
		}
		
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "redirect:homePage";
	}
	
	/**
	 * Deletes the added widget from the widgets database and then returns to that widgets add page
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/back-and-delete")
	public String backAndDelete(Model model)
	{
		widgetController.deleteWidget(widget.getId());
		return "redirect:addWidget";
	}
	
	/**
	 * adds to the widgetImage database and sets the foreign key of marketListing
	 * @param file incremented list of images to be associated with the market listing
	 * @param marketListing
	 */
	
	public void setListingImage(MultipartFile file, MarketListing marketListing)
	{
		//WidgetImage newImage = new WidgetImage();
		tempImage = new WidgetImage();
		tempImage.setImageName(marketListing.getSeller().getId() + StringUtils.cleanPath(file.getOriginalFilename()));
		tempImage.setMarketListing(marketListing);
		widgetImageController.addWidgetImage(tempImage);
		listingImages.add(tempImage);
		
		try
		{
			String fileLocation = new File("src/main/resources/static/listingImages").getAbsolutePath() + "/" + tempImage.getImageName();
			String fileLocationTemp = new ClassPathResource("static/listingImages").getFile().getAbsolutePath() + "/" + tempImage.getImageName();

			FileOutputStream output = new FileOutputStream(fileLocation);
			output.write(file.getBytes());
			output.close();

			output = new FileOutputStream(fileLocationTemp);
			output.write(file.getBytes());
			output.close();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("upload failed");
		}
		
		tempImage = new WidgetImage();
	}
	
	/*
	 *  Editing code above to remove hard code
	 */
	
	@RequestMapping("/addAppliance")
	public String addAppliance(Model model)
	{
		Set<String> subCategories = new HashSet<>();
		for (Pair<String, String> pair : PreLoad.categoryConfiguration()) {
			if (pair.getLeft().equalsIgnoreCase("appliance")) {
				String right = pair.getRight();
				subCategories.add(right);
			}
		}
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addAppliance";
	}

	@RequestMapping("/addApplianceParts")
	public String addApplianceParts(Model model)
	{
		Set<String> subCategories = new HashSet<>();
		for (Pair<String, String> pair : PreLoad.categoryConfiguration()) {
			if (pair.getLeft().equalsIgnoreCase("appliance_parts")) {
				String right = pair.getRight();
				subCategories.add(right);
			}
		}
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addApplianceParts";
	}

	@RequestMapping("/addElectronic")
	public String addElectronic(Model model)
	{
		Set<String> subCategories = new HashSet<>();
		for (Pair<String, String> pair : PreLoad.categoryConfiguration()) {
			if (pair.getLeft().equalsIgnoreCase("electronics")) {
				String right = pair.getRight();
				subCategories.add(right);
			}
		}
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addElectronic";
	}

	@RequestMapping("/addElectronicParts")
	public String addElectronicParts(Model model) {
		Set<String> subCategories = new HashSet<>();
		for (Pair<String, String> pair : PreLoad.categoryConfiguration()) {
			if (pair.getLeft().equalsIgnoreCase("electronic_parts")) {
				String right = pair.getRight();
				subCategories.add(right);
			}
		}
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addElectronicParts";
	}

	@RequestMapping("/addLawnCare")
	public String addLawnCare(Model model)
	{
		Set<String> subCategories = new HashSet<>();
		for (Pair<String, String> pair : PreLoad.categoryConfiguration()) {
			if (pair.getLeft().equalsIgnoreCase("lawnCare")) {
				String right = pair.getRight();
				subCategories.add(right);
			}
		}
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addLawnCare";
	}

	@RequestMapping("/addLawnCareParts")
	public String addLawnCareParts(Model model) {
		Set<String> subCategories = new HashSet<>();
		for (Pair<String, String> pair : PreLoad.categoryConfiguration()) {
			if (pair.getLeft().equalsIgnoreCase("lawnCare_parts")) {
				String right = pair.getRight();
				subCategories.add(right);
			}
		}
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addLawnCareParts";
	}

	@RequestMapping("/addVehicle")
	public String addVehicle(Model model)
	{
		Set<String> subCategories = new HashSet<>();
		for (Pair<String, String> pair : PreLoad.categoryConfiguration()) {
			if (pair.getLeft().equalsIgnoreCase("vehicle")) {
				String right = pair.getRight();
				subCategories.add(right);
			}
		}
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addVehicle";
	}

	@RequestMapping("/addVehicleParts")
	public String addVehicleParts(Model model) {
		Set<String> subCategories = new HashSet<>();
		for (Pair<String, String> pair : PreLoad.categoryConfiguration()) {
			if (pair.getLeft().equalsIgnoreCase("vehicle_parts")) {
				String right = pair.getRight();
				subCategories.add(right);
			}
		}
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addVehicleParts";
	}

	@RequestMapping("/createAppliance")
	public String createAppliance(Model model, @RequestParam("subcategory") String subcategory)
	{
		this.subcategory = subcategory;
		String url = "";
		if(subcategory.contentEquals("dryer"))
		{
			url = "redirect:addDryer";
		}
		else if (subcategory.contentEquals("microwave"))
		{
			url = "redirect:addMicrowave";
		}
		else if (subcategory.contentEquals("refridgerator"))
		{
			url = "redirect:addFridge";
		}
		else if (subcategory.contentEquals("washer"))
		{
			url = "redirect:addWasher";
		}
		else if(subcategory.contentEquals("blender"))
		{
			url ="redirect:addBlender";
		}
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return url;
	}

	@RequestMapping("/createApplianceParts")
	public String createApplianceParts(Model model, @RequestParam("subcategory") String subcategory)
	{
		this.subcategory = subcategory;
		String url = "";
		if(subcategory.contentEquals("dryer_parts"))
		{
			url = "redirect:addDryerParts";
		}
		else if (subcategory.contentEquals("microwave_parts"))
		{
			url = "redirect:addMicrowaveParts";
		}
		else if (subcategory.contentEquals("refrigerator_parts"))
		{
			url = "redirect:addRefrigeratorParts";
		}
		else if (subcategory.contentEquals("washer_parts"))
		{
			url = "redirect:addWasherParts";
		}
		else if(subcategory.contentEquals("blender_parts"))
		{
			url ="redirect:addBlenderParts";
		}
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return url;
	}

	@RequestMapping("/createElectronic")
	public String createElectronic(Model model, @RequestParam("subcategory") String subcategory)
	{
		this.subcategory = subcategory;
		String url = "";

		if (subcategory.contentEquals("computer"))
		{
			url = "redirect:addComputer";
		}
		else if (subcategory.contentEquals("videoGame"))
		{
			url = "redirect:addVideoGame";
		}
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return url;
	}

	@RequestMapping("/createElectronicParts")
	public String createElectronicParts(Model model, @RequestParam("subcategory") String subcategory)
	{
		this.subcategory = subcategory;
		String url = "";

		if (subcategory.contentEquals("computer_parts"))
		{
			url = "redirect:addComputerParts";
		}
		else if (subcategory.contentEquals("videoGame_parts"))
		{
			url = "redirect:addVideoGameParts";
		}
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return url;
	}

	@RequestMapping("/createLawnCare")
	public String createLawnCare(Model model, @RequestParam("subcategory") String subcategory)
	{
		this.subcategory = subcategory;
		String url = "";
		if (subcategory.contentEquals("lawnMower"))
		{
			url = "redirect:addLawnMower";
		}
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return url;
	}

	@RequestMapping("/createLawnCareParts")
	public String createLawnCareParts(Model model, @RequestParam("subcategory") String subcategory)
	{
		this.subcategory = subcategory;
		String url = "";
		if (subcategory.contentEquals("mower_parts"))
		{
			url = "redirect:addLawnMowerParts";
		}
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return url;
	}

	@RequestMapping("/createVehicle")
	public String createVehicle(Model model, @RequestParam("subcategory") String subcategory)
	{
		this.subcategory = subcategory;
		String url = "";
		if(subcategory.contentEquals("car"))
		{

			url = "redirect:addCar";
		}
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return url;
	}

	@RequestMapping("/addDryer")
	public String addDryer(Model model)
	{
		dryer = new Appliance_Dryers();
		model.addAttribute("dryer", dryer);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addDryer";
	}

	@RequestMapping("/addGeneral")
	public String addGeneral(Model model)
	{
		generalWidget = new Widget_General();
		model.addAttribute("generalWidget", generalWidget);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addGeneral";
	}

	@RequestMapping("addMicrowave")
	public String addMicrowave(Model model)
	{
		microwave = new Appliance_Microwave();
		model.addAttribute("microwave", microwave);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addMicrowave";
	}

	@RequestMapping("addFridge")
	public String addFridge(Model model)
	{
		refridgerator = new Appliance_Refrigerator();
		model.addAttribute("refridgerator", refridgerator);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addFridge";
	}

	@RequestMapping("addWasher")
	public String addWasher(Model model)
	{
		washer = new Appliance_Washers();
		model.addAttribute("washer", washer);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addWasher";
	}

	@RequestMapping("addBlender")
	public String addBlender(Model model)
	{
		blender = new Appliance_Blender();
		model.addAttribute("blender", blender);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addBlender";
	}

	@RequestMapping("addBlenderParts")
	public String addBlenderParts(Model model) {
		blenderPart = new Appliance_Blender_Parts();
		model.addAttribute("blender_parts", blenderPart);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addBlenderParts";
	}

	@RequestMapping("addDryerParts")
	public String addDryerParts(Model model) {
		dryerPart = new Appliance_Dryer_Parts();
		model.addAttribute("dryer_parts", dryerPart);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addDryerParts";
	}

	@RequestMapping("addMicrowaveParts")
	public String addMicrowaveParts(Model model) {
		microwavePart = new Appliance_Microwave_Parts();
		model.addAttribute("microwave_parts", microwavePart);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addMicrowaveParts";
	}

	@RequestMapping("addRefrigeratorParts")
	public String addRefrigeratorParts(Model model) {
		refrigeratorPart = new Appliance_Refrigerator_Parts();
		model.addAttribute("refrigerator_parts", refrigeratorPart);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addRefrigeratorParts";
	}

	@RequestMapping("addWasherParts")
	public String addWasherParts(Model model) {
		washerPart = new Appliance_Washers_Parts();
		model.addAttribute("washer_parts", washerPart);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addWasherParts";
	}

	@RequestMapping("addComputer")
	public String addComputer(Model model)
	{
		computer = new Electronics_Computers();
		model.addAttribute("computer", computer);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addComputer";
	}

	@RequestMapping("addComputerParts")
	public String addComputerParts(Model model)
	{
		computerPart = new Electronics_Computers_Parts();
		model.addAttribute("computer_parts", computerPart);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addComputerParts";
	}

	@RequestMapping("addVideoGame")
	public String addVideoGame(Model model)
	{
		videoGame = new Electronics_VideoGames();
		model.addAttribute("videoGame", videoGame);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addVideoGame";
	}

	@RequestMapping("addVideoGameParts")
	public String addVideoGameParts(Model model)
	{
		videoGamePart = new Electronics_VideoGames_Parts();
		model.addAttribute("videoGame_parts", videoGamePart);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addVideoGameParts";
	}

	@RequestMapping("addLawnMower")
	public String addLawnMower(Model model)
	{
		mower = new LawnCare_LawnMower();
		model.addAttribute("mower", mower);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addLawnMower";
	}

	@RequestMapping("addLawnMowerParts")
	public String addLawnMowerParts(Model model)
	{
		mowerPart = new LawnCare_LawnMower_Parts();
		model.addAttribute("mower_parts", mowerPart);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addLawnMowerParts";
	}

	@RequestMapping("addCar")
	public String addCar(Model model)
	{
		car = new Vehicle_Car();
		model.addAttribute("car", car);
		model.addAttribute("user", userController.getCurrently_Logged_In());
		return "addCar";
	}

	@PostMapping({"/uploadComputerDataFile"})
	public String uploadComputerDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "computer");
		return "redirect:addComputer";
	}

	@PostMapping({"/uploadVideoGameDataFile"})
	public String uploadVideoGameDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "videoGame");
		return "redirect:addVideoGame";
	}

	@PostMapping({"/uploadDryerDataFile"})
	public String uploadDryerDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "dryer");
		return "redirect:addDryer";
	}

	@PostMapping({"/uploadBlenderDataFile"})
	public String uploadBlenderDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "blender");
		return "redirect:homePage";
	}

	@PostMapping({"/uploadMicrowaveDataFile"})
	public String uploadMicrowaveDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "microwave");
		return "redirect:addMicrowave";
	}

	@PostMapping({"/uploadRefrigeratorDataFile"})
	public String uploadRefrigeratorDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "refrigerator");
		return "redirect:addFridge";
	}

	@PostMapping({"/uploadWasherDataFile"})
	public String uploadWasherDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "washer");
		return "redirect:addWasher";
	}

	@PostMapping({"/uploadCarDataFile"})
	public String uploadCarDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "car");
		return "redirect:addCar";
	}

	@PostMapping({"/uploadLawnMowerDataFile"})
	public String uploadLawnMowerDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "lawnMower");
		return "redirect:addLawnMower";
	}

	@PostMapping({"/uploadMarketListingDataFile"})
	public String uploadMarketListingDataFile(
			@RequestParam("file") MultipartFile file) throws IOException {
		User user = userController.getCurrently_Logged_In();
		this.saveMarketListingFromCSV(file, user);
		return "redirect:homePage";
	}

	private void saveMarketListingFromCSV(final MultipartFile file, final User user) throws IOException {

		InputStreamReader streamReader =
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
		CsvToBean<MarketListingCSVModel> listingsCsvBean = new CsvToBeanBuilder<MarketListingCSVModel>(streamReader)
				.withType(MarketListingCSVModel.class).withIgnoreLeadingWhiteSpace(true).build();
		List<MarketListingCSVModel> marketListingCSVModels = listingsCsvBean.parse();

		marketListingCSVModels.forEach(marketListingCSVModel -> {
			MarketListing newMarketListing = new MarketListing();
			newMarketListing.setPricePerItem(marketListingCSVModel.getPricePerItem());
			newMarketListing.setQtyAvailable(marketListingCSVModel.getQtyAvailable());
			Set<WidgetImage> WI = new HashSet<WidgetImage>();
			WidgetImage image = new WidgetImage();
			image.setImageName("TempImage");
			WI.add(image);
			newMarketListing.setCoverImage("tempCoverImage");
			newMarketListing.setImages(WI);
			newMarketListing.setSeller(user);
			Optional<Widget> widgetSoldOptional = widgetRepository.findById(marketListingCSVModel.getWidgetId());
			if (widgetSoldOptional.isPresent()) {
				Widget widgetSold = widgetSoldOptional.get();
				newMarketListing.setWidgetSold(widgetSold);
			}
			newMarketListing.setDeleted(false);
			marketListingController.addMarketListing(newMarketListing);
		});

	}

	private void saveWidgetFromCSV(final MultipartFile file, final String subcategory) throws IOException {
		InputStreamReader streamReader =
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
		CsvToBeanBuilder<Widget> widgetsCsvBeanBuilder = new CsvToBeanBuilder<>(streamReader);
		switch (subcategory) {
		case "computer" -> widgetsCsvBeanBuilder.withType(Electronics_Computers.class);
		case "videoGame" -> widgetsCsvBeanBuilder.withType(Electronics_VideoGames.class);
		case "dryer" -> widgetsCsvBeanBuilder.withType(Appliance_Dryers.class);
		case "blender" -> widgetsCsvBeanBuilder.withType(Appliance_Blender.class);
		case "microwave" -> widgetsCsvBeanBuilder.withType(Appliance_Microwave.class);
		case "refrigerator" -> widgetsCsvBeanBuilder.withType(Appliance_Refrigerator.class);
		case "washer" -> widgetsCsvBeanBuilder.withType(Appliance_Washers.class);
		case "car" -> widgetsCsvBeanBuilder.withType(Vehicle_Car.class);
		case "lawnMower" -> widgetsCsvBeanBuilder.withType(LawnCare_LawnMower.class);
		}
		CsvToBean<Widget> widgetsCsvBean =
				widgetsCsvBeanBuilder.withIgnoreLeadingWhiteSpace(true).build();
		List<Widget> widgets = widgetsCsvBean.parse();

		widgets.parallelStream().forEach(widgetRepository::save);
	}

	/*
	 * @RequestMapping("createDryer") public String createDryer(Model
	 * model, @ModelAttribute Appliance_Dryers dryer, BindingResult result) {
	 * model.addAttribute("name", dryer); model.addAttribute("description", dryer);
	 * dryer.setCategory(category); dryer.setSubCategory(subcategory);
	 * model.addAttribute("length", dryer); model.addAttribute("width", dryer);
	 * model.addAttribute("height", dryer); model.addAttribute("color", dryer);
	 * model.addAttribute("itemCondition", dryer); model.addAttribute("model",
	 * dryer); model.addAttribute("brand", dryer); model.addAttribute("material",
	 * dryer); widgetController.addDryer(dryer, result); this.dryer = dryer; widget
	 * = dryer; model.addAttribute("createDryer", true); model.addAttribute("user",
	 * userController.getCurrently_Logged_In()); return "redirect:createListing"; }
	 * 
	 * @RequestMapping("createMicrowave") public String createMicrowave(Model
	 * model, @ModelAttribute Appliance_Microwave microwave, BindingResult result) {
	 * model.addAttribute("name", microwave); model.addAttribute("description",
	 * microwave); microwave.setCategory(category);
	 * microwave.setSubCategory(subcategory); model.addAttribute("length",
	 * microwave); model.addAttribute("width", microwave);
	 * model.addAttribute("height", microwave); model.addAttribute("color",
	 * microwave); model.addAttribute("itemCondition", microwave);
	 * model.addAttribute("model", microwave); model.addAttribute("brand",
	 * microwave); model.addAttribute("material", microwave);
	 * widgetController.addMicrowave(microwave, result); this.microwave = microwave;
	 * widget = microwave; model.addAttribute("createMicrowave", true);
	 * model.addAttribute("user", userController.getCurrently_Logged_In()); return
	 * "redirect:createListing"; }
	 * 
	 * @RequestMapping("createFridge") public String createFridge(Model
	 * model, @ModelAttribute Appliance_Refrigerator fridge, BindingResult result) {
	 * model.addAttribute("name", fridge); model.addAttribute("description",
	 * fridge); fridge.setCategory(category); fridge.setSubCategory(subcategory);
	 * model.addAttribute("length", fridge); model.addAttribute("width", fridge);
	 * model.addAttribute("height", fridge); model.addAttribute("color", fridge);
	 * model.addAttribute("itemCondition", fridge); model.addAttribute("model",
	 * fridge); model.addAttribute("brand", fridge); model.addAttribute("material",
	 * fridge); widgetController.addRefrigerator(fridge, result); this.refridgerator
	 * = fridge; widget = fridge; model.addAttribute("createFridge", true);
	 * model.addAttribute("user", userController.getCurrently_Logged_In()); return
	 * "redirect:createListing"; }
	 * 
	 * @RequestMapping("createWasher") public String createWasher(Model
	 * model, @ModelAttribute Appliance_Washers washer, BindingResult result) {
	 * model.addAttribute("name", washer); model.addAttribute("description",
	 * washer); washer.setCategory(category); washer.setSubCategory(subcategory);
	 * model.addAttribute("length", washer); model.addAttribute("width", washer);
	 * model.addAttribute("height", washer); model.addAttribute("color", washer);
	 * model.addAttribute("itemCondition", washer); model.addAttribute("model",
	 * washer); model.addAttribute("brand", washer); model.addAttribute("material",
	 * washer); widgetController.addWasher(washer, result); this.washer = washer;
	 * widget = washer; model.addAttribute("createWasher", true);
	 * model.addAttribute("user", userController.getCurrently_Logged_In()); return
	 * "redirect:createListing"; }
	 * 
	 * @RequestMapping("createBlender") public String createBlender(Model
	 * model, @ModelAttribute Appliance_Blender blender, BindingResult result) {
	 * model.addAttribute("name", blender); model.addAttribute("description",
	 * blender); blender.setCategory(category); blender.setSubCategory(subcategory);
	 * model.addAttribute("length", blender); model.addAttribute("width", blender);
	 * model.addAttribute("height", blender); model.addAttribute("color", blender);
	 * model.addAttribute("itemCondition", blender); model.addAttribute("model",
	 * blender); model.addAttribute("brand", blender);
	 * model.addAttribute("material", blender); widgetController.addBlender(blender,
	 * result); this.blender = blender; widget = blender;
	 * model.addAttribute("createBlender", true); model.addAttribute("user",
	 * userController.getCurrently_Logged_In()); return "redirect:createListing"; }
	 * 
	 * @RequestMapping("createBlenderParts") public String createBlenderParts(Model
	 * model, @ModelAttribute Appliance_Blender_Parts blenderPart, BindingResult
	 * result) { model.addAttribute("name", blenderPart);
	 * model.addAttribute("description", blenderPart);
	 * blenderPart.setCategory(category); blenderPart.setSubCategory(subcategory);
	 * model.addAttribute("length", blenderPart); model.addAttribute("width",
	 * blenderPart); model.addAttribute("height", blenderPart);
	 * model.addAttribute("color", blenderPart); model.addAttribute("itemCondition",
	 * blenderPart); model.addAttribute("model", blenderPart);
	 * model.addAttribute("brand", blenderPart); model.addAttribute("material",
	 * blenderPart); widgetController.addBlenderParts(blenderPart, result);
	 * this.blenderPart = blenderPart; widget = blenderPart;
	 * model.addAttribute("createBlenderPart", true); model.addAttribute("user",
	 * userController.getCurrently_Logged_In()); return "redirect:createListing"; }
	 * 
	 * @RequestMapping("createDryerParts") public String createDryerParts(Model
	 * model, @ModelAttribute Appliance_Dryer_Parts dryerPart, BindingResult result)
	 * { model.addAttribute("name", dryerPart); model.addAttribute("description",
	 * dryerPart); dryerPart.setCategory(category);
	 * dryerPart.setSubCategory(subcategory); model.addAttribute("length",
	 * dryerPart); model.addAttribute("width", dryerPart);
	 * model.addAttribute("height", dryerPart); model.addAttribute("color",
	 * dryerPart); model.addAttribute("itemCondition", dryerPart);
	 * model.addAttribute("model", dryerPart); model.addAttribute("brand",
	 * dryerPart); model.addAttribute("material", dryerPart);
	 * widgetController.addDryerParts(dryerPart, result); this.dryerPart =
	 * dryerPart; widget = dryerPart; model.addAttribute("createDryerPart", true);
	 * model.addAttribute("user", userController.getCurrently_Logged_In()); return
	 * "redirect:createListing"; }
	 * 
	 * @RequestMapping("createMicrowaveParts") public String
	 * createMicrowaveParts(Model model, @ModelAttribute Appliance_Microwave_Parts
	 * microwavePart, BindingResult result) { model.addAttribute("name",
	 * microwavePart); model.addAttribute("description", microwavePart);
	 * microwavePart.setCategory(category);
	 * microwavePart.setSubCategory(subcategory); model.addAttribute("length",
	 * microwavePart); model.addAttribute("width", microwavePart);
	 * model.addAttribute("height", microwavePart); model.addAttribute("color",
	 * microwavePart); model.addAttribute("itemCondition", microwavePart);
	 * model.addAttribute("model", microwavePart); model.addAttribute("brand",
	 * microwavePart); model.addAttribute("material", microwavePart);
	 * widgetController.addMicrowaveParts(microwavePart, result); this.microwavePart
	 * = microwavePart; widget = microwavePart;
	 * model.addAttribute("createMicrowavePart", true); model.addAttribute("user",
	 * userController.getCurrently_Logged_In()); return "redirect:createListing"; }
	 * 
	 * @RequestMapping("createRefrigeratorParts") public String
	 * createRefrigeratorParts(Model model, @ModelAttribute
	 * Appliance_Refrigerator_Parts refrigeratorPart, BindingResult result) {
	 * model.addAttribute("name", refrigeratorPart);
	 * model.addAttribute("description", refrigeratorPart);
	 * refrigeratorPart.setCategory(category);
	 * refrigeratorPart.setSubCategory(subcategory); model.addAttribute("length",
	 * refrigeratorPart); model.addAttribute("width", refrigeratorPart);
	 * model.addAttribute("height", refrigeratorPart); model.addAttribute("color",
	 * refrigeratorPart); model.addAttribute("itemCondition", refrigeratorPart);
	 * model.addAttribute("model", refrigeratorPart); model.addAttribute("brand",
	 * refrigeratorPart); model.addAttribute("material", refrigeratorPart);
	 * widgetController.addRefrigeratorParts(refrigeratorPart, result);
	 * this.refrigeratorPart = refrigeratorPart; widget = refrigeratorPart;
	 * model.addAttribute("createRefrigeratorPart", true);
	 * model.addAttribute("user", userController.getCurrently_Logged_In()); return
	 * "redirect:createListing"; }
	 * 
	 * @RequestMapping("createWasherParts") public String createWasherParts(Model
	 * model, @ModelAttribute Appliance_Washers_Parts washersPart, BindingResult
	 * result) { model.addAttribute("name", washersPart);
	 * model.addAttribute("description", washersPart);
	 * washersPart.setCategory(category); washersPart.setSubCategory(subcategory);
	 * model.addAttribute("length", washersPart); model.addAttribute("width",
	 * washersPart); model.addAttribute("height", washersPart);
	 * model.addAttribute("color", washersPart); model.addAttribute("itemCondition",
	 * washersPart); model.addAttribute("model", washersPart);
	 * model.addAttribute("brand", washersPart); model.addAttribute("material",
	 * washersPart); widgetController.addWasherParts(washersPart, result);
	 * this.washerPart = washersPart; widget = washersPart;
	 * model.addAttribute("createRefrigeratorPart", true);
	 * model.addAttribute("user", userController.getCurrently_Logged_In()); return
	 * "redirect:createListing"; }
	 * 
	 * @RequestMapping("createComputer") public String createComputer(Model
	 * model, @RequestParam("storageSize") String storageSize, @ModelAttribute
	 * Electronics_Computers computer, BindingResult result) {
	 * model.addAttribute("name", computer); model.addAttribute("description",
	 * computer); computer.setCategory(category);
	 * computer.setSubCategory(subcategory); model.addAttribute("officeUse",
	 * computer); model.addAttribute("entertainmentUse", computer);
	 * model.addAttribute("memory", computer); model.addAttribute("storage",
	 * computer); computer.setStorage(computer.getStorage() + storageSize);
	 * model.addAttribute("processor", computer); model.addAttribute("gpu",
	 * computer); widgetController.addComputer(computer, result); this.computer =
	 * computer; widget = computer; model.addAttribute("createComputer", true);
	 * model.addAttribute("user", userController.getCurrently_Logged_In()); return
	 * "redirect:createListing"; }
	 * 
	 * @RequestMapping("createComputerParts") public String
	 * createComputerParts(Model model, @ModelAttribute Electronics_Computers_Parts
	 * computerPart, BindingResult result) { model.addAttribute("name",
	 * computerPart); model.addAttribute("description", computerPart);
	 * computerPart.setCategory(category); computerPart.setSubCategory(subcategory);
	 * model.addAttribute("length", computerPart); model.addAttribute("width",
	 * computerPart); model.addAttribute("height", computerPart);
	 * model.addAttribute("color", computerPart);
	 * model.addAttribute("itemCondition", computerPart);
	 * model.addAttribute("model", computerPart); model.addAttribute("brand",
	 * computerPart); model.addAttribute("material", computerPart);
	 * widgetController.addComputerParts(computerPart, result); this.computerPart =
	 * computerPart; widget = computerPart; model.addAttribute("createComputerPart",
	 * true); model.addAttribute("user", userController.getCurrently_Logged_In());
	 * return "redirect:createListing"; }
	 * 
	 * @RequestMapping("createVideoGame") public String createVideoGame(Model
	 * model, @ModelAttribute Electronics_VideoGames videoGame, BindingResult
	 * result) { model.addAttribute("name", videoGame);
	 * model.addAttribute("description", videoGame);
	 * videoGame.setCategory(category); videoGame.setSubCategory(subcategory);
	 * model.addAttribute("officeUse", videoGame);
	 * model.addAttribute("entertainmentUse", videoGame);
	 * model.addAttribute("developer", videoGame); model.addAttribute("title",
	 * videoGame); model.addAttribute("console", videoGame);
	 * model.addAttribute("itemCondition", videoGame);
	 * widgetController.addVideoGame(videoGame, result); this.videoGame = videoGame;
	 * widget = videoGame; model.addAttribute("createVideoGame", true);
	 * model.addAttribute("user", userController.getCurrently_Logged_In()); return
	 * "redirect:createListing"; }
	 * 
	 * @RequestMapping("createVideoGameParts") public String
	 * createVideoGameParts(Model model, @ModelAttribute
	 * Electronics_VideoGames_Parts videoGamePart, BindingResult result) {
	 * model.addAttribute("name", videoGamePart); model.addAttribute("description",
	 * videoGamePart); videoGamePart.setCategory(category);
	 * videoGamePart.setSubCategory(subcategory); model.addAttribute("length",
	 * videoGamePart); model.addAttribute("width", videoGamePart);
	 * model.addAttribute("height", videoGamePart); model.addAttribute("color",
	 * videoGamePart); model.addAttribute("itemCondition", videoGamePart);
	 * model.addAttribute("model", videoGamePart); model.addAttribute("brand",
	 * videoGamePart); model.addAttribute("material", videoGamePart);
	 * widgetController.addVideoGameParts(videoGamePart, result); this.videoGamePart
	 * = videoGamePart; widget = videoGamePart;
	 * model.addAttribute("createVideoGamePart", true); model.addAttribute("user",
	 * userController.getCurrently_Logged_In()); return "redirect:createListing"; }
	 * 
	 * @RequestMapping("createLawnMower") public String createLawnMower(Model
	 * model, @ModelAttribute LawnCare_LawnMower mower, BindingResult result) {
	 * model.addAttribute("name", mower); model.addAttribute("description", mower);
	 * mower.setCategory(category); mower.setSubCategory(subcategory);
	 * model.addAttribute("yardSize", mower); model.addAttribute("toolType", mower);
	 * model.addAttribute("brand", mower); model.addAttribute("powerSource", mower);
	 * model.addAttribute("bladeWidth", mower); widgetController.addLawnMower(mower,
	 * result); this.mower = mower; widget = mower;
	 * model.addAttribute("createLawnMower", true); model.addAttribute("user",
	 * userController.getCurrently_Logged_In()); return "redirect:createListing"; }
	 * 
	 * @RequestMapping("createLawnMowerParts") public String
	 * createLawnMowerParts(Model model, @ModelAttribute LawnCare_LawnMower_Parts
	 * mowerPart, BindingResult result) { model.addAttribute("name", mowerPart);
	 * model.addAttribute("description", mowerPart);
	 * mowerPart.setCategory(category); mowerPart.setSubCategory(subcategory);
	 * model.addAttribute("length", mowerPart); model.addAttribute("width",
	 * mowerPart); model.addAttribute("height", mowerPart);
	 * model.addAttribute("color", mowerPart); model.addAttribute("itemCondition",
	 * mowerPart); model.addAttribute("model", mowerPart);
	 * model.addAttribute("brand", mowerPart); model.addAttribute("material",
	 * mowerPart); widgetController.addLawnMowerParts(mowerPart, result);
	 * this.mowerPart = mowerPart; widget = mowerPart;
	 * model.addAttribute("createLawnMowerPart", true); model.addAttribute("user",
	 * userController.getCurrently_Logged_In()); return "redirect:createListing"; }
	 * 
	 * @RequestMapping("createCar") public String createCar(Model
	 * model, @ModelAttribute Vehicle_Car car, BindingResult result) {
	 * model.addAttribute("name", car); model.addAttribute("description", car);
	 * car.setCategory(category); car.setSubCategory(subcategory);
	 * model.addAttribute("terrain", car); model.addAttribute("roadSafe", car);
	 * model.addAttribute("itemCondition", car); model.addAttribute("wheelDrive",
	 * car); model.addAttribute("transmisionType", car); model.addAttribute("model",
	 * car); model.addAttribute("make", car); model.addAttribute("year", car);
	 * widgetController.addCar(car, result); this.car = car; widget = car;
	 * 
	 * model.addAttribute("createCar", true); model.addAttribute("user",
	 * userController.getCurrently_Logged_In()); return "redirect:createListing"; }
	 */
	
	public Widget getWidgetStorage()
	{
		return widgetStorage;
	}

	public void setWidgetStorage(Widget widgetStorage)
	{
		this.widgetStorage = widgetStorage;
	}
}




