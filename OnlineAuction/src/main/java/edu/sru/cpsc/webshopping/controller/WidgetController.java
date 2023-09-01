package edu.sru.cpsc.webshopping.controller;

import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
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
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Widget_Appliance_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Widget_Electronics;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Widget_Electronics_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Widget_Vehicles;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Widget_Vehicles_Parts;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceBlenderPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceBlenderRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceDryerPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceDryersRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceMicrowavePartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceMicrowaveRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceRefrigeratorPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceRefrigeratorRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceWashersPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.ApplianceWashersRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.WidgetAppliancePartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.appliances.WidgetApplianceRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.ElectronicsComputersPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.ElectronicsComputersRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.ElectronicsVideoGamesPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.ElectronicsVideoGamesRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.WidgetElectronicsPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.electronics.WidgetElectronicsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.lawncare.LawnCareLawnMowerPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.lawncare.LawnCareLawnMowerRepository;
import edu.sru.cpsc.webshopping.repository.widgets.lawncare.WidgetLawnCarePartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.lawncare.WidgetLawnCareRepository;
import edu.sru.cpsc.webshopping.repository.widgets.vehicles.VehicleCarPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.vehicles.VehicleCarRepository;
import edu.sru.cpsc.webshopping.repository.widgets.vehicles.WidgetVehiclesPartsRepository;
import edu.sru.cpsc.webshopping.repository.widgets.vehicles.WidgetVehiclesRepository;
import edu.sru.cpsc.webshopping.util.PreLoad;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for interacting with the Widgets database table */
@RestController
@RequiredArgsConstructor
public class WidgetController {
  private final WidgetRepository<Widget> widgetRepository;
  private final ApplianceDryersRepository<Appliance_Dryers> dryerRepository;
  private final ApplianceMicrowaveRepository<Appliance_Microwave> microwaveRepository;
  private final ApplianceRefrigeratorRepository<Appliance_Refrigerator> refrigeratorRepository;
  private final ApplianceWashersRepository<Appliance_Washers> washerRepository;
  private final ApplianceBlenderRepository<Appliance_Blender> blenderRepository;
  private final ApplianceBlenderPartsRepository<Appliance_Blender_Parts> blenderPartsRepository;
  private final ApplianceDryerPartsRepository<Appliance_Dryer_Parts> dryerPartsRepository;
  private final ApplianceMicrowavePartsRepository<Appliance_Microwave_Parts>
      microwavePartsRepository;
  private final ApplianceRefrigeratorPartsRepository<Appliance_Refrigerator_Parts>
      refrigeratorPartsRepository;
  private final ApplianceWashersPartsRepository<Appliance_Washers_Parts> washersPartsRepository;
  private final ElectronicsComputersRepository<Electronics_Computers> computerRepository;
  private final ElectronicsComputersPartsRepository<Electronics_Computers_Parts>
      computersPartsRepository;
  private final ElectronicsVideoGamesRepository<Electronics_VideoGames> videoGameRepository;
  private final ElectronicsVideoGamesPartsRepository<Electronics_VideoGames_Parts>
      videoGamesPartsRepository;
  private final LawnCareLawnMowerRepository<LawnCare_LawnMower> mowerRepository;
  private final LawnCareLawnMowerPartsRepository<LawnCare_LawnMower_Parts> mowerPartsRepository;
  private final VehicleCarRepository<Vehicle_Car> carRepository;
  private final VehicleCarPartsRepository<Vehicle_Car_Parts> carPartsRepository;
  private final WidgetApplianceRepository<Widget_Appliance> applianceRepository;
  private final WidgetAppliancePartsRepository<Widget_Appliance_Parts> appliancePartsRepository;
  private final WidgetElectronicsRepository<Widget_Electronics> electronicRepository;
  private final WidgetElectronicsPartsRepository<Widget_Electronics_Parts>
      electronicPartsRepository;
  private final WidgetLawnCareRepository<Widget_LawnCare> lawnCareRepository;
  private final WidgetLawnCarePartsRepository<Widget_LawnCare_Parts> lawnCarePartsRepository;
  private final WidgetVehiclesRepository<Widget_Vehicles> vehicleRepository;
  private final WidgetVehiclesPartsRepository<Widget_Vehicles_Parts> vehiclePartsRepository;
  private final StatisticsDomainController statControl;

  // Widget CRUD functions
  @RequestMapping("add-widget")
  public Widget addWidget(@Validated Widget widget, BindingResult result) {
    Statistics stats = new Statistics(Category.WIDGETCREATED, 1);
    stats.setDescription("Widget: " + widget.getName() + " was created");
    statControl.addStatistics(stats);
    return widgetRepository.save(widget);
  }

  @RequestMapping("add-widget-simple")
  public Widget addWidgetnobinding(@Validated Widget widget) {

    return widgetRepository.save(widget);
  }

  @RequestMapping({"get-widget"})
  public Widget getWidget(@PathVariable("id") long id) {
    Widget widget =
        widgetRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return widget;
  }

  @RequestMapping({"/get-all-widgets"})
  public Iterable<Widget> getAllWidgets() {
    Iterable<Widget> widgets = widgetRepository.findAll();
    return () ->
        StreamSupport.stream(widgets.spliterator(), false)
            .filter(
                widget ->
                    PreLoad.subCategoryConfiguration().stream()
                        .anyMatch(widget.getSubCategory()::equalsIgnoreCase))
            .iterator();
  }

  @PostMapping({"update-widget/{id}"})
  public void updateWidget(
      @Validated Widget widget, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    widgetRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    widgetRepository.save(widget);
  }

  @GetMapping({"delete-widget/{id}"})
  public void deleteWidget(@PathVariable("id") long id) {
    Widget widget = widgetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    widgetRepository.delete(widget);
  }

  @GetMapping({"add-dryer"})
  public void addDryer(@Validated Appliance_Dryers dryer, BindingResult result) {
    if (result.hasErrors())
    {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    
    dryerRepository.save(dryer);
  }

  @RequestMapping({"get-dryer"})
  public Appliance_Dryers getDryer(@PathVariable("id") long id) {
    Appliance_Dryers dryer =
        dryerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return dryer;
  }

  @RequestMapping({"get-dryer-parts"})
  public Appliance_Dryer_Parts getDryerParts(@PathVariable("id") long id) {
    Appliance_Dryer_Parts dryerPart =
        dryerPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return dryerPart;
  }

  @RequestMapping({"/get-all-dryers"})
  public Iterable<Appliance_Dryers> getAllDryers() {
    Iterable<Appliance_Dryers> dryers = dryerRepository.findAll();
    return dryers;
  }

  @PostMapping({"update-dryer/{id}"})
  public void updateDryer(
      @Validated Appliance_Dryers dryer, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    dryerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    dryerRepository.save(dryer);
  }

  @GetMapping({"delete-dryer/{id}"})
  public void deleteDryer(@PathVariable("id") long id) {
    Appliance_Dryers dryer =
        dryerRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    dryerRepository.delete(dryer);
  }
  
  @GetMapping({"back-delete-dryer/{id}"})
  public String backDeleteDryer(@PathVariable("id") long id) {
	  Appliance_Dryers dryer =
		        dryerRepository
		            .findById(id)
		            .orElseThrow(
		                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
		    dryerRepository.delete(dryer);
		    Widget widget =
		            widgetRepository
		                .findById(id)
		                .orElseThrow(
		                    () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
		        widgetRepository.delete(widget);
		    return "addDryer";
  }
  
  @GetMapping({"back-delete-car-parts/{id}"})
  public String backDeleteCarParts(@PathVariable("id") long id) {
	  Vehicle_Car_Parts carParts =
		        carPartsRepository
		            .findById(id)
		            .orElseThrow(
		                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
		    carPartsRepository.delete(carParts);
		    Widget widget =
		            widgetRepository
		                .findById(id)
		                .orElseThrow(
		                    () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
		        widgetRepository.delete(widget);
		    return "/addCarParts";
  }

  @GetMapping({"add-microwave"})
  public void addMicrowave(@Validated Appliance_Microwave microwave, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    microwaveRepository.save(microwave);
  }

  @RequestMapping({"get-microwave"})
  public Appliance_Microwave getMicrowave(@PathVariable("id") long id) {
    Appliance_Microwave microwave =
        microwaveRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return microwave;
  }

  @RequestMapping({"get-microwave-parts"})
  public Appliance_Microwave_Parts getMicrowaveParts(@PathVariable("id") long id) {
    Appliance_Microwave_Parts microwavePart =
        microwavePartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return microwavePart;
  }

  @RequestMapping({"/get-all-microwaves"})
  public Iterable<Appliance_Microwave> getAllMicrowaves() {
    Iterable<Appliance_Microwave> microwaves = microwaveRepository.findAll();
    return microwaves;
  }

  @PostMapping({"update-microwave/{id}"})
  public void updateMicrowave(
      @Validated Appliance_Microwave microwave, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    microwaveRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    microwaveRepository.save(microwave);
  }

  @GetMapping({"delete-microwave/{id}"})
  public void deleteMicrowave(@PathVariable("id") long id) {
    Appliance_Microwave microwave =
        microwaveRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    microwaveRepository.delete(microwave);
  }

  @GetMapping({"add-refrigerator"})
  public void addRefrigerator(@Validated Appliance_Refrigerator fridge, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    refrigeratorRepository.save(fridge);
  }

  @RequestMapping({"get-refrigerator"})
  public Appliance_Refrigerator getRefrigerator(@PathVariable("id") long id) {
    Appliance_Refrigerator refrigerator =
        refrigeratorRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return refrigerator;
  }

  @RequestMapping({"get-refrigerator-parts"})
  public Appliance_Refrigerator_Parts getRefrigeratorParts(@PathVariable("id") long id) {
    Appliance_Refrigerator_Parts refrigeratorPart =
        refrigeratorPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return refrigeratorPart;
  }

  @RequestMapping({"/get-all-refrigerators"})
  public Iterable<Appliance_Refrigerator> getAllRefrigerators() {
    Iterable<Appliance_Refrigerator> refrigerators = refrigeratorRepository.findAll();
    return refrigerators;
  }

  @PostMapping({"update-refrigerator/{id}"})
  public void updateRefrigerator(
      @Validated Appliance_Refrigerator refrigerator,
      BindingResult result,
      @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    refrigeratorRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    refrigeratorRepository.save(refrigerator);
  }

  @GetMapping({"delete-refrigerator/{id}"})
  public void deleteRefrigerator(@PathVariable("id") long id) {
    Appliance_Refrigerator refrigerator =
        refrigeratorRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    refrigeratorRepository.delete(refrigerator);
  }

  @GetMapping({"add-washer"})
  public void addWasher(@Validated Appliance_Washers washer, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    washerRepository.save(washer);
  }

  @RequestMapping({"get-washer"})
  public Appliance_Washers getWasher(@PathVariable("id") long id) {
    Appliance_Washers washer =
        washerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return washer;
  }

  @RequestMapping({"get-washer-parts"})
  public Appliance_Washers_Parts getWasherParts(@PathVariable("id") long id) {
    Appliance_Washers_Parts washerPart =
        washersPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return washerPart;
  }

  @RequestMapping({"/get-all-washers"})
  public Iterable<Appliance_Washers> getAllWashers() {
    Iterable<Appliance_Washers> washers = washerRepository.findAll();
    return washers;
  }

  @PostMapping({"update-washer/{id}"})
  public void updateWasher(
      @Validated Appliance_Washers washer, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    washerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    washerRepository.save(washer);
  }

  @GetMapping({"delete-washer/{id}"})
  public void deleteWasher(@PathVariable("id") long id) {
    Appliance_Washers washer =
        washerRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    washerRepository.delete(washer);
  }

  @GetMapping({"add-blender"})
  public void addBlender(@Validated Appliance_Blender blender, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    blenderRepository.save(blender);
  }

  @GetMapping({"add-blender-parts"})
  public void addBlenderParts(
      @Validated Appliance_Blender_Parts blenderPart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    blenderPartsRepository.save(blenderPart);
  }

  @GetMapping({"add-dryer-parts"})
  public void addDryerParts(@Validated Appliance_Dryer_Parts dryerPart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    dryerPartsRepository.save(dryerPart);
  }

  @GetMapping({"add-microwave-parts"})
  public void addMicrowaveParts(
      @Validated Appliance_Microwave_Parts microwavePart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    microwavePartsRepository.save(microwavePart);
  }

  @GetMapping({"add-refrigerator-parts"})
  public void addRefrigeratorParts(
      @Validated Appliance_Refrigerator_Parts refrigeratorPart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    refrigeratorPartsRepository.save(refrigeratorPart);
  }

  @GetMapping({"add-washer-parts"})
  public void addWasherParts(@Validated Appliance_Washers_Parts washerPart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    washersPartsRepository.save(washerPart);
  }

  @RequestMapping({"get-blender"})
  public Appliance_Blender getBlender(@PathVariable("id") long id) {
    Appliance_Blender blender =
        blenderRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return blender;
  }

  @RequestMapping({"get-blender-parts"})
  public Appliance_Blender_Parts getBlenderParts(@PathVariable("id") long id) {
    Appliance_Blender_Parts blenderPart =
        blenderPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return blenderPart;
  }

  @RequestMapping({"/get-all-blenders"})
  public Iterable<Appliance_Blender> getAllBlenders() {
    Iterable<Appliance_Blender> blender = blenderRepository.findAll();
    return blender;
  }
  
  @RequestMapping({"/get-all-blender-parts"})
  public Iterable<Appliance_Blender_Parts> getAllBlenderParts() {
    Iterable<Appliance_Blender_Parts> blenderParts = blenderPartsRepository.findAll();
    return blenderParts;
  }
  
  @RequestMapping({"/get-all-washer-parts"})
  public Iterable<Appliance_Washers_Parts> getAllWasherParts() {
    Iterable<Appliance_Washers_Parts> washerParts = washersPartsRepository.findAll();
    return washerParts;
  }
  
  @RequestMapping({"/get-all-dryer-parts"})
  public Iterable<Appliance_Dryer_Parts> getAllDryerParts() {
    Iterable<Appliance_Dryer_Parts> dryerParts = dryerPartsRepository.findAll();
    return dryerParts;
  }
  
  @RequestMapping({"/get-all-microwave-parts"})
  public Iterable<Appliance_Microwave_Parts> getAllMicrowaveParts() {
    Iterable<Appliance_Microwave_Parts> microwaveParts = microwavePartsRepository.findAll();
    return microwaveParts;
  }
  
  @RequestMapping({"/get-all-refrigerator-parts"})
  public Iterable<Appliance_Refrigerator_Parts> getAllRefrigeratorParts() {
    Iterable<Appliance_Refrigerator_Parts> refrigeratorParts = refrigeratorPartsRepository.findAll();
    return refrigeratorParts;
  }

  @PostMapping({"update-blender/{id}"})
  public void updateBlender(
      @Validated Appliance_Blender blender, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    blenderRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    blenderRepository.save(blender);
  }

  @GetMapping({"delete-blender/{id}"})
  public void deleteBlender(@PathVariable("id") long id) {
    Appliance_Blender blender =
        blenderRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    blenderRepository.delete(blender);
  }

  @GetMapping({"add-comptuer"})
  public void addComputer(@Validated Electronics_Computers computer, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    computerRepository.save(computer);
  }

  @GetMapping({"add-comptuer-parts"})
  public void addComputerParts(
      @Validated Electronics_Computers_Parts computerPart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    computersPartsRepository.save(computerPart);
  }

  @RequestMapping({"get-computer"})
  public Electronics_Computers getComputer(@PathVariable("id") long id) {
    Electronics_Computers computer =
        computerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return computer;
  }

  @RequestMapping({"get-computer-parts"})
  public Electronics_Computers_Parts getComputerParts(@PathVariable("id") long id) {
    Electronics_Computers_Parts computerPart =
        computersPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return computerPart;
  }

  @RequestMapping({"/get-all-computers"})
  public Iterable<Electronics_Computers> getAllComputers() {
    Iterable<Electronics_Computers> computers = computerRepository.findAll();
    return computers;
  }
  
  @RequestMapping({"/get-all-computer-parts"})
  public Iterable<Electronics_Computers_Parts> getAllComputerParts() {
    Iterable<Electronics_Computers_Parts> computerParts = computersPartsRepository.findAll();
    return computerParts;
  }

  @PostMapping({"update-computer/{id}"})
  public void updateComputer(
      @Validated Electronics_Computers computer,
      BindingResult result,
      @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    computerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    computerRepository.save(computer);
  }

  @GetMapping({"delete-computer/{id}"})
  public void deleteComputer(@PathVariable("id") long id) {
    Electronics_Computers computer =
        computerRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    computerRepository.delete(computer);
  }

  @GetMapping({"add-videoGame"})
  public void addVideoGame(@Validated Electronics_VideoGames videoGame, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    videoGameRepository.save(videoGame);
  }

  @GetMapping({"add-videoGame-parts"})
  public void addVideoGameParts(
      @Validated Electronics_VideoGames_Parts videoGamePart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    videoGamesPartsRepository.save(videoGamePart);
  }

  @RequestMapping({"get-videoGame"})
  public Electronics_VideoGames getVideoGame(@PathVariable("id") long id) {
    Electronics_VideoGames videoGame =
        videoGameRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return videoGame;
  }

  @RequestMapping({"get-videoGame-Part"})
  public Electronics_VideoGames_Parts getVideoGameParts(@PathVariable("id") long id) {
    Electronics_VideoGames_Parts videoGamePart =
        videoGamesPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return videoGamePart;
  }

  @RequestMapping({"/get-all-videoGames"})
  public Iterable<Electronics_VideoGames> getAllVideoGames() {
    Iterable<Electronics_VideoGames> videoGames = videoGameRepository.findAll();
    return videoGames;
  }
  
  @RequestMapping({"/get-all-videoGames-accessories"})
  public Iterable<Electronics_VideoGames_Parts> getAllVideoGameAccessories() {
    Iterable<Electronics_VideoGames_Parts> videoGameAccessories = videoGamesPartsRepository.findAll();
    return videoGameAccessories;
  }

  @PostMapping({"update-videoGame/{id}"})
  public void updateVideoGame(
      @Validated Electronics_VideoGames videoGame,
      BindingResult result,
      @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    videoGameRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    videoGameRepository.save(videoGame);
  }

  @GetMapping({"delete-videoGame/{id}"})
  public void deleteVideoGame(@PathVariable("id") long id) {
    Electronics_VideoGames videoGame =
        videoGameRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    videoGameRepository.delete(videoGame);
  }

  @GetMapping({"add-mower"})
  public void addLawnMower(@Validated LawnCare_LawnMower mower, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    mowerRepository.save(mower);
  }

  @GetMapping({"add-mower-parts"})
  public void addLawnMowerParts(
      @Validated LawnCare_LawnMower_Parts mowerPart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    mowerPartsRepository.save(mowerPart);
  }

  @RequestMapping({"get-mower"})
  public LawnCare_LawnMower getMower(@PathVariable("id") long id) {
    LawnCare_LawnMower mower =
        mowerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return mower;
  }

  @RequestMapping({"get-mower-parts"})
  public LawnCare_LawnMower_Parts getMowerParts(@PathVariable("id") long id) {
    LawnCare_LawnMower_Parts mowerPart =
        mowerPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return mowerPart;
  }

  @RequestMapping({"/get-all-mowers"})
  public Iterable<LawnCare_LawnMower> getAllMowers() {
    Iterable<LawnCare_LawnMower> mowers = mowerRepository.findAll();
    return mowers;
  }

  @PostMapping({"update-mower/{id}"})
  public void updateMower(
      @Validated LawnCare_LawnMower mower, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    mowerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    mowerRepository.save(mower);
  }

  @GetMapping({"delete-mower/{id}"})
  public void deleteMower(@PathVariable("id") long id) {
    LawnCare_LawnMower mower =
        mowerRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    mowerRepository.delete(mower);
  }

  @GetMapping({"add-car"})
  public void addCar(@Validated Vehicle_Car car, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    carRepository.save(car);
  }

  @GetMapping({"add-car-parts"})
  public void addCarParts(@Validated Vehicle_Car_Parts carPart, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    carPartsRepository.save(carPart);
  }
  
  @GetMapping({"delete-carPart/{id}"})
  public void deleteCarPart(@PathVariable("id") long id) {
	  Vehicle_Car_Parts carPart =
        carPartsRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    carPartsRepository.delete(carPart);
  }

  @RequestMapping({"get-car"})
  public Vehicle_Car getCar(@PathVariable("id") long id) {
    Vehicle_Car car =
        carRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return car;
  }

  @RequestMapping({"get-car-parts"})
  public Vehicle_Car_Parts getCarParts(@PathVariable("id") long id) {
    Vehicle_Car_Parts carPart =
        carPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return carPart;
  }

  @RequestMapping({"/get-all-car"})
  public Iterable<Vehicle_Car> getAllCars() {
    Iterable<Vehicle_Car> car = carRepository.findAll();
    return car;
  }
  
  @RequestMapping({"/get-all-car-parts"})
  public Iterable<Vehicle_Car_Parts> getAllCarParts() {
    Iterable<Vehicle_Car_Parts> carParts = carPartsRepository.findAll();
    return carParts;
  }

  @PostMapping({"update-car/{id}"})
  public void updateCar(
      @Validated Vehicle_Car car, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    carRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    carRepository.save(car);
  }

  @GetMapping({"delete-car/{id}"})
  public void deleteCar(@PathVariable("id") long id) {
    Vehicle_Car car =
        carRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    carRepository.delete(car);
  }

  @GetMapping({"add-appliance"})
  public void addAppliance(@Validated Widget_Appliance appliance, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    applianceRepository.save(appliance);
  }

  @RequestMapping({"get-appliance"})
  public Widget_Appliance getAppliance(@PathVariable("id") long id) {
    Widget_Appliance appliance =
        applianceRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return appliance;
  }

  @RequestMapping({"get-appliance-parts"})
  public Widget_Appliance_Parts getApplianceParts(@PathVariable("id") long id) {
    Widget_Appliance_Parts appliancePart =
        appliancePartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return appliancePart;
  }

  @RequestMapping({"/get-all-appliances"})
  public Iterable<Widget_Appliance> getAllAppliances() {
    Iterable<Widget_Appliance> appliances = applianceRepository.findAll();
    return appliances;
  }

  @PostMapping({"update-appliance/{id}"})
  public void updateAppliance(
      @Validated Widget_Appliance appliance, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    applianceRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    applianceRepository.save(appliance);
  }

  @GetMapping({"delete-appliance/{id}"})
  public void deleteAppliance(@PathVariable("id") long id) {
    Widget_Appliance appliance =
        applianceRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    applianceRepository.delete(appliance);
  }

  @GetMapping({"add-electronic"})
  public void addElectronic(@Validated Widget_Electronics electronic, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    electronicRepository.save(electronic);
  }

  @RequestMapping({"get-electronic"})
  public Widget_Electronics getElectronic(@PathVariable("id") long id) {
    Widget_Electronics electronic =
        electronicRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return electronic;
  }

  @RequestMapping({"get-electronic-parts"})
  public Widget_Electronics_Parts getElectronicParts(@PathVariable("id") long id) {
    Widget_Electronics_Parts electronicPart =
        electronicPartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return electronicPart;
  }

  @RequestMapping({"/get-all-electronics"})
  public Iterable<Widget_Electronics> getAllElectronics() {
    Iterable<Widget_Electronics> electronics = electronicRepository.findAll();
    return electronics;
  }

  @PostMapping({"update-electronic/{id}"})
  public void updateElectronic(
      @Validated Widget_Electronics electronic, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    electronicRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    electronicRepository.save(electronic);
  }

  @GetMapping({"delete-electronic/{id}"})
  public void deleteElectronic(@PathVariable("id") long id) {
    Widget_Electronics electronic =
        electronicRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    electronicRepository.delete(electronic);
  }

  @GetMapping({"add-lawnCare"})
  public void addLawnCare(@Validated Widget_LawnCare lawnCare, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    lawnCareRepository.save(lawnCare);
  }

  @RequestMapping({"get-lawnCare"})
  public Widget_LawnCare getLawnCare(@PathVariable("id") long id) {
    Widget_LawnCare lawnCare =
        lawnCareRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return lawnCare;
  }

  @RequestMapping({"get-lawnCare-parts"})
  public Widget_LawnCare_Parts getLawnCareParts(@PathVariable("id") long id) {
    Widget_LawnCare_Parts lawnCarePart =
        lawnCarePartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return lawnCarePart;
  }
  
  @RequestMapping({"/get-all-mower-parts"})
  public Iterable<LawnCare_LawnMower_Parts> getAllMowerParts() {
    Iterable<LawnCare_LawnMower_Parts> mowerParts = mowerPartsRepository.findAll();
    return mowerParts;
  }

  @RequestMapping({"/get-all-lawnCares"})
  public Iterable<Widget_LawnCare> getAllLawnCares() {
    Iterable<Widget_LawnCare> lawnCares = lawnCareRepository.findAll();
    return lawnCares;
  }

  @PostMapping({"update-lawnCare/{id}"})
  public void updateLawnCare(
      @Validated Widget_LawnCare lawnCare, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    lawnCareRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    lawnCareRepository.save(lawnCare);
  }

  @GetMapping({"delete-lawnCare/{id}"})
  public void deleteLawnCare(@PathVariable("id") long id) {
    Widget_LawnCare lawnCare =
        lawnCareRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    lawnCareRepository.delete(lawnCare);
  }

  @GetMapping({"add-vehicle"})
  public void addVehicle(@Validated Widget_Vehicles vehicle, BindingResult result) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("Error adding new widget in WidgetController.addWidget");
    }
    vehicleRepository.save(vehicle);
  }

  @RequestMapping({"get-vehicle"})
  public Widget_Vehicles getVehicle(@PathVariable("id") long id) {
    Widget_Vehicles vehicle =
        vehicleRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return vehicle;
  }

  @RequestMapping({"get-vehicle-parts"})
  public Widget_Vehicles_Parts getVehicleParts(@PathVariable("id") long id) {
    Widget_Vehicles_Parts vehiclePart =
        vehiclePartsRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID specified to get-widget"));
    return vehiclePart;
  }

  @RequestMapping({"/get-all-vehicles"})
  public Iterable<Widget_Vehicles> getAllVehicles() {
    Iterable<Widget_Vehicles> vehicles = vehicleRepository.findAll();
    return vehicles;
  }

  @PostMapping({"update-vehicle/{id}"})
  public void updateVehicle(
      @Validated Widget_Vehicles vehicle, BindingResult result, @PathVariable("id") long id) {
    if (result.hasErrors()) {
      throw new IllegalArgumentException("BindResult error in WidgetController.updateWidget");
    }
    vehicleRepository
        .findById(id)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Invalid ID provided for updating widget in WidgetController.updateWidget"));
    vehicleRepository.save(vehicle);
  }

  @GetMapping({"delete-vehicle/{id}"})
  public void deleteVehicle(@PathVariable("id") long id) {
    Widget_Vehicles vehicle =
        vehicleRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid ID provided for deleting widget."));
    vehicleRepository.delete(vehicle);
  }
}
