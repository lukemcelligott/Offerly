package edu.sru.cpsc.webshopping.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.Applicant;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.Category;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetsInfo;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Blender;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Dryers;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Microwave;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Refrigerator;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Washers;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_Computers;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.Electronics_VideoGames;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car;
import edu.sru.cpsc.webshopping.repository.applicant.ApplicantRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetsInfoRepository;
import edu.sru.cpsc.webshopping.service.TicketService;
import edu.sru.cpsc.webshopping.util.PreLoad;
import edu.sru.cpsc.webshopping.util.constants.TimeConstants;
import edu.sru.cpsc.webshopping.util.enums.MessageType;
import edu.sru.cpsc.webshopping.util.enums.Role;
import edu.sru.cpsc.webshopping.util.enums.TicketState;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
  private final UserController userController;
  private final ApplicantDomainController appControl;
  private final MarketListingDomainController market;
  private final MessageDomainController msgcontrol;
  private final WidgetController widgetController;
  private final WidgetsInfoRepository widgetsInfoRepository;
  private final ApplicantRepository appRepo;
  private final EmailController email;
  private final StatisticsDomainController statControl;
  private final TicketService ticketService;
  private final UserRepository userRepository;
  private final EmailController emailController;
  private final WidgetRepository<Widget> widgetRepository;
  private final CategoryController categories;
  private final SubcategoryController subcategories;
  private String page;
  private String mailboxPage;
  private String page2;
  private String page3;
  private String masterPage;
  private String pageSuccess;
  private User[] myUserSearch;
  private List<MarketListing> searchedUserListings = new ArrayList<>();
  private List<Widget> searchedUserWidgets = new ArrayList<>();
  private List<User> searchedUserSellers = new ArrayList<>();
  private List<User> allUsers = new ArrayList<>();
  private List<Widget> allWidgets = new ArrayList<>();
  private List<User> allSellers = new ArrayList<>();
  private List<MarketListing> allMarketListings = new ArrayList<>();
  private List<Applicant> allApplicants = new ArrayList<>();
  private List<Applicant> reviewedApplicants = new ArrayList<>();
  private List<Applicant> newApplicants = new ArrayList<>();
  private List<Message> allMessages = new ArrayList<>();
  private int applicantPage;
  private User searchedUser;
  private Widget searchedWidget;
  private MarketListing searchedMarket;
  private User searchedSeller;
  private boolean singleWidgetCheck = false;
  public String search;
  List<String> roleList;

  @ModelAttribute
  public void preLoad(Model model) {
    roleList = Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toList());
  }

  List<Category> CategoryList;

  @ModelAttribute
  public void preLoad2(Model model) {
    CategoryList = new ArrayList<>();

    CategoryList.add(Category.ACCOUNTCREATION);
    CategoryList.add(Category.ACCOUNTDELETED);
    CategoryList.add(Category.SALE);
    CategoryList.add(Category.SALEVALUE);
    CategoryList.add(Category.USERLOGIN);
    CategoryList.add(Category.MESSAGESENT);
    CategoryList.add(Category.WIDGETCREATED);
  }

  List<String> dateList;

  @ModelAttribute
  public void preLoad3(Model model) {
    dateList = new ArrayList<>();

    dateList.add("Today");
    dateList.add("Week to date");
    dateList.add("Month to date");
    dateList.add("Year to date");
    dateList.add("Yesterday");
    dateList.add("Previous Week");
    dateList.add("Previous Month");
    dateList.add("Previous Year");
  }

  @RequestMapping({"/statButton"})
  public String statButton(Model model) {
    //
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_SALES")) {
      setPage("statistics");
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    User useradd = new User();
    model.addAttribute("dateList", dateList);
    model.addAttribute("catList", CategoryList);
    model.addAttribute("roleList", roleList);
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("page2", getPage2());
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    return "employee";
  }

  @RequestMapping({"/lookupStatistics"})
  public String StatisticsButton(
      @RequestParam String date, @RequestParam Category category, Model model) {
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_SALES")) {
      setPage2("stats");
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    /*SALEVALUE,
    SALE,
    ACCOUNTCREATION,
    USERLOGIN,
    ACCOUNTDELETED,
    MESSAGESENT,
    WIDGETCREATED;*/
    LocalDateTime date1 = LocalDateTime.now();
    // System.out.println(date);
    LocalDateTime date2 = LocalDateTime.now();
    // System.out.println(category);
    if (date.equals("Today")) {
      date2 = date1;
    }
    if (date.equals("Week to date")) {
      date2 = date1;
      DayOfWeek today = date1.getDayOfWeek();
      while (today != DayOfWeek.SUNDAY) {
        date1 = date1.minusDays(1);
        today = date1.getDayOfWeek();
      }
    }
    if (date.equals("Month to date")) {
      date2 = date1;
      while (date1.getDayOfMonth() != 1) {
        date1 = date1.minusDays(1);
      }
    }
    if (date.equals("Year to date")) {
      date2 = date1;
      while (date1.getDayOfYear() != 1) {
        date1 = date1.minusDays(1);
      }
    }
    if (date.equals("Yesterday")) {

      date1 = date1.minusDays(1);
      date2 = date1;
    }
    if (date.equals("Previous Week")) {

      DayOfWeek today = date1.getDayOfWeek();
      while (today != DayOfWeek.SUNDAY) {
        date1 = date1.minusDays(1);
        today = date1.getDayOfWeek();
      }
      date2 = date1.minusDays(1);
      date1 = date2.minusDays(6);
    }
    if (date.equals("Previous Month")) {
      date1 = date1.minusMonths(1);
      while (date1.getDayOfMonth() != 1) {
        date1 = date1.minusDays(1);
      }
      date2 = date1;
      while (true) {
        date2 = date2.plusDays(1);
        if (date2.getMonthValue() != date1.getMonthValue()) {
          date2 = date2.minusDays(1);
          break;
        }
      }
    }
    if (date.equals("Previous Year")) {
      date1 = date1.minusYears(1);
      while (date1.getDayOfYear() != 1) {
        date1 = date1.minusDays(1);
      }
      date2 = date1;
      while (true) {
        date2 = date2.plusDays(1);
        if (date2.getYear() != date1.getYear()) {
          date2 = date2.minusDays(1);
          break;
        }
      }
    }

    // Statistics[] stats = statControl.getStatisticsByHour(date1, date2, Category.ACCOUNTCREATION);
    // Statistics[] stats = statControl.getStatsByCategory(Category.ACCOUNTCREATION);
    Statistics[] stats = statControl.getStatsByCategory(category);

    List<Statistics> resultStats = new ArrayList<>();

    for (LocalDateTime day = date1;
        day.getDayOfYear() <= date2.getDayOfYear();
        day = day.plusDays(1)) {
      /*
      System.out.println(day.getDayOfYear());
      System.out.println(date2.getDayOfYear());
      System.out.println(day.getYear());
      System.out.println(date2.getYear());*/
      for (int i = 0; i < stats.length; i++) {
        if (stats[i].getDate().getYear() == day.getYear()
            && stats[i].getDate().getDayOfYear() == day.getDayOfYear()) {
          resultStats.add(stats[i]);
        }
      }
      if (date.equals("Previous Year") && day.getDayOfYear() == date2.getDayOfYear()) {
        break;
      }
    }

    Statistics[] statsValues = resultStats.toArray(new Statistics[resultStats.size()]);
    float total = 0;
    for (int i = 0; i < statsValues.length; i++) {
      total += statsValues[i].getValue();
    }
    model.addAttribute("total", total);
    model.addAttribute("date", date);
    model.addAttribute("cat", category);
    model.addAttribute("dateList", dateList);
    model.addAttribute("catList", CategoryList);
    model.addAttribute("allStats", resultStats);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("page2", getPage2());
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    return "employee";
  }

  @RequestMapping({"/employee"})
  public String showEmployeePage(Model model) {
    String[] allusernames = new String[getAllUsers().size()];
    setMasterPage("base");
    if (getMasterPage().equals("base")) {
      setPage("begin");

      model.addAttribute("page", page);
    }
    model.addAttribute("masterPage", getMasterPage());
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);

    User useradd = new User();
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().contains("ROLE_USER")) {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    model.addAttribute("user", user);
    model.addAttribute("useradd", useradd);
    model.addAttribute("roleList", roleList);
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("masterPage", getMasterPage());
    return "employee";
  }

  @RequestMapping({"/backButton"})
  public String backButton(Model model) {
    String[] allusernames = new String[getAllUsers().size()];

    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());

    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);

    User user = userController.getCurrently_Logged_In();
    if (user.getRole().contains("ROLE_USER")) {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    model.addAttribute("widgetNames", allWidgetNames);
    model.addAttribute("dateList", dateList);
    model.addAttribute("catList", CategoryList);
    model.addAttribute("allMarketListings", getSearchedUserListings());
    model.addAttribute("allWidgets", getSearchedUserWidgets());
    model.addAttribute("allSellers", getSearchedUserSellers());
    model.addAttribute("myusers", getMyUserSearch());
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("user", user);
    model.addAttribute("page2", getPage2());
    model.addAttribute("page", getPage());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("masterPage", getMasterPage());
    return "employee";
  }

  @PostMapping({"/createEmployee"})
  public String createEmp(
      @Valid User useradd, BindingResult result, @RequestParam String role, Model model) {
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user2 = userController.getCurrently_Logged_In();

    if (user2.getRole().equals("ROLE_ADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user2);
      model.addAttribute("page", page);
      return "employee";
    }
    if (userController.getUserByUsername(useradd.getUsername()) != null) {
      result.addError(new FieldError("username", "username", "Username is already taken."));
    }
    if (userController.getUserByEmail(useradd.getEmail()) != null) {
      result.addError(
          new FieldError("email", "email", "An account is already associated with this email."));
    }

    if (result.hasErrors()) {
      setPage2("createFail");
      List<String> errorString = new ArrayList<>();
      result.getAllErrors().forEach(u -> errorString.add(u.getDefaultMessage() + " "));
      model.addAttribute("createStringError", errorString);
      model.addAttribute("user", user2);
      model.addAttribute("page", getPage());
      model.addAttribute("page2", getPage2());
      model.addAttribute("useradd", useradd);
      model.addAttribute("roleList", roleList);
      return "employee";
    }
    useradd.setEnabled(true);
    userController.addUser(useradd, result);

    setPage2("create");
    model.addAttribute("user", user2);
    model.addAttribute("page2", getPage2());
    model.addAttribute("page", getPage());
    model.addAttribute("roleList", roleList);
    model.addAttribute("useradd", new User());
    return "employee";
  }

  @RequestMapping({"/createButton"})
  public String createButton(Model model) {
    setPage("create");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (!user.getRole().equals("ROLE_ADMIN")) {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    User useradd = new User();
    model.addAttribute("roleList", roleList);
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    return "employee";
  }

  @RequestMapping({"/cancelButton"})
  public String cancelButton(Model model) {
    setPage("null");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();

    User useradd = new User();
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    return "employee";
  }

  @RequestMapping({"/searchButton"})
  public String searchButton(Model model) {

    setPage("search");
    setMasterPage("query");

    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    getAllUsers().clear();
    Iterable<User> allUsersIterator = userController.getAllUsers();

    allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }
    User useradd = new User();
    setMyUserSearch(getAllUsers().toArray(new User[getAllUsers().size()]));
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    return "employee";
  }

  @RequestMapping({"/searchWidgetButton"})
  public String searchWidgetButton(Model model) {

    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    getAllMarketListings().clear();
    getAllWidgets().clear();
    getAllSellers().clear();
    Iterable<MarketListing> allMarketListingsIterator = market.getAllListings();

    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllMarketListings().add(u));
    allMarketListingsIterator
        .iterator()
        .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllSellers().add(u.getSeller()));

    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }
    setSearchedUserListings(getAllMarketListings());
    setSearchedUserWidgets(getAllWidgets());
    setSearchedUserSellers(getAllSellers());
    model.addAttribute("widgetNames", allWidgetNames);
    setPage("searchWidgetSellerMarketListing");
    model.addAttribute("allMarketListings", getAllMarketListings());
    model.addAttribute("allWidgets", getAllWidgets());
    model.addAttribute("allSellers", getAllSellers());
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "employee";
  }

  @GetMapping({"/widgetsInfo"})
  public String widgetsInfo(Model model) {

    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      model.addAttribute("page", getPage());
      model.addAttribute("user", user);
      return "employee";
    }
    setPage("widgetsInfo");
    model.addAttribute("page", getPage());
    model.addAttribute("user", user);
    model.addAttribute("categories", categories.getAllCategories());
    return "employee";
  }

  @PostMapping({"/widgetsInfoCategory"})
  public String widgetsInfoCategory(@RequestParam("category") String category, Model model) {
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      model.addAttribute("page", getPage());
      model.addAttribute("user", user);
      return "employee";
    }
    
    model.addAttribute("categories", categories.getAllCategories());
    model.addAttribute("subcategories", subcategories.getAllSubcategories());
    setPage("widgetsInfoCategory");
    setPage2(category);
    model.addAttribute("page", getPage());
    model.addAttribute("page2", getPage2());
    model.addAttribute("user", user);
    return "employee";
  }

  @PostMapping({"/widgetsInfoSubCategory"})
  public String widgetsInfoSubCategory(@RequestParam("subCategory") String subCategory, Model model) {  
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      model.addAttribute("page", getPage());
      model.addAttribute("user", user);
      return "employee";
    }
    
    model.addAttribute("subcategories", subcategories.getAllSubcategories());
    setPage("widgetsInfoSubCategory");
    setPage2(subCategory);
    model.addAttribute("page", getPage());
    model.addAttribute("page2", getPage2());
    model.addAttribute("user", user);
    model.addAttribute("subCategory", subCategory);

    List<WidgetsInfo> widgetsInfos =
        widgetsInfoRepository.findAllBySubCategoryEqualsIgnoreCase(subCategory);
    model.addAttribute("widgetsInfos", widgetsInfos);

    return "employee";
  }

  @PostMapping({"/updateWidgetsInfo"})
  public String updateWidgetsInfo(Model model, @RequestBody List<WidgetsInfo> widgetsInfos) {
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      model.addAttribute("page", getPage());
      model.addAttribute("user", user);
      return "employee";
    }
    setPage("widgetsInfoSubCategory");
    for (WidgetsInfo widgetsInfo : widgetsInfos) {
      System.out.println(widgetsInfo.getAttribute() + " - " + widgetsInfo.isVisible());
    }
    model.addAttribute("page", getPage());
    model.addAttribute("page2", getPage2());
    model.addAttribute("user", user);

    model.addAttribute("widgetsInfos", Collections.emptyList());

    return "employee";
  }

  // https://attacomsian.com/blog/export-download-data-csv-file-spring-boot
  @GetMapping({"/downloadDataFile"})
  public void downloadDataFile(HttpServletResponse response, @RequestParam String subCategory)
      throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

    List<Widget> widgets = widgetRepository.findBySubCategory(subCategory);

    switch (subCategory) {
      case "car_parts":
        widgets =
            widgets.stream().filter(Vehicle_Car.class::isInstance).collect(Collectors.toList());
        break;
    }

    String filename = String.format("%s.csv", subCategory);

    response.setContentType("text/csv");
    response.setHeader(
        HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

    StatefulBeanToCsv<Widget> writer =
        new StatefulBeanToCsvBuilder<Widget>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();

    writer.write(widgets);
  }

  @RequestMapping({"/BrowseWidgetsButton"})
  public String browseWidgetsButton(Model model) {

    User user = userController.getCurrently_Logged_In();
    model.addAttribute("user", user);

    setPage("searchWidgetSellerMarketListing");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    getAllMarketListings().clear();
    getAllWidgets().clear();
    getAllSellers().clear();
    getAllUsers().clear();

    Iterable<User> allUsersIterator = userController.getAllUsers();
    allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));

    Iterable<MarketListing> allMarketListingsIterator = market.getAllListings();

    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllMarketListings().add(u));
    allMarketListingsIterator
        .iterator()
        .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllSellers().add(u.getSeller()));

    model.addAttribute("users", getAllUsers());
    model.addAttribute("allMarketListings", getAllMarketListings());
    model.addAttribute("allWidgets", getAllWidgets());
    model.addAttribute("allSellers", getAllSellers());
    model.addAttribute("page", getPage());
    model.addAttribute("user", user);
    return "browseWidgets";
  }

  @RequestMapping({"/searchMessageButton"})
  public String searchMessageButton(Model model) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    setPage("searchMessage");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    getAllUsers().clear();
    Iterable<User> allUsersIterator = userController.getAllUsers();

    allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));

    User useradd = new User();
    model.addAttribute("useradd", useradd);
    model.addAttribute("roleList", roleList);
    model.addAttribute("user", user);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("page", page);
    return "employee";
  }

  @GetMapping({"/viewSpecificUserInbox/{id}"})
  public String viewUserInbox(@PathVariable("id") int id, Model model) {
    User user = userController.getCurrently_Logged_In();
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    User user2 = userController.getUser(id, model);
    setSearchedUser(user2);
    Message[] inbox = msgcontrol.getUserInbox(user2);

    setMailboxPage("inboxPage");

    User useradd = new User();

    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("roleList", roleList);
    model.addAttribute("inbox", inbox);
    model.addAttribute("user", user);
    model.addAttribute("useradd", useradd);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("page", getPage());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("myusers", getMyUserSearch());

    return "employee";
  }

  @GetMapping({"/viewSpecificUserOutbox/{id}"})
  public String viewUserOutbox(@PathVariable("id") int id, Model model) {
    User user = userController.getCurrently_Logged_In();
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    User user2 = userController.getUser(id, model);
    setSearchedUser(user2);
    Message[] outbox = msgcontrol.getUserOutbox(user2);
    setMailboxPage("outboxPage");
    User useradd = new User();
    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("roleList", roleList);
    model.addAttribute("outbox", outbox);
    model.addAttribute("useradd", useradd);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    model.addAttribute("myusers", getMyUserSearch());
    return "employee";
  }

  @GetMapping({"/viewSpecificUserTrash/{id}"})
  public String viewUserTrash(@PathVariable("id") int id, Model model) {
    User user = userController.getCurrently_Logged_In();
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    User user2 = userController.getUser(id, model);
    setSearchedUser(user2);
    Message[] trash = (msgcontrol.getUserTrash(user2));
    setMailboxPage("trashPage");
    User useradd = new User();
    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("roleList", roleList);
    model.addAttribute("trash", trash);
    model.addAttribute("useradd", useradd);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    model.addAttribute("myusers", getMyUserSearch());
    return "employee";
  }

  @RequestMapping({"/searchApplicationsButton"})
  public String searchApplicationsButton(@RequestParam("id") int id, Model model) {
    setPage("applications");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_HIRINGMANAGER")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    getAllApplicants().clear();
    getReviewedApplicants().clear();
    getNewApplicants().clear();
    Iterable<Applicant> applicantIterator = appRepo.findAll();

    applicantIterator.iterator().forEachRemaining(u -> getAllApplicants().add(u));

    Applicant[] allApplications =
        getAllApplicants().toArray(new Applicant[getAllApplicants().size()]);
    for (int i = 0; i < allApplications.length; i++) {
      if (allApplications[i].getReviewed() != null) {
        if (allApplications[i].getReviewed() == true) {
          getReviewedApplicants().add(allApplications[i]);
        } else {
          getNewApplicants().add(allApplications[i]);
        }
      } else {
        getNewApplicants().add(allApplications[i]);
      }
    }
    if (id == 1) {
      model.addAttribute("applicants", getNewApplicants());
      setApplicantPage(id);
    } else {
      model.addAttribute("applicants", getReviewedApplicants());
      setApplicantPage(id);
    }
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    return "employee";
  }

  @GetMapping({"/searchTickets"})
  public String searchTickets(Model model) {
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    Iterable<Ticket> tickets = ticketService.getAllTickets();
    setPage("ticket");
    model.addAttribute("tickets", tickets);

    List<User> users =
        StreamSupport.stream(userController.getAllUsers().spliterator(), true)
            .filter(user1 -> !user1.getRole().equals(Role.ROLE_USER.name()))
            .collect(Collectors.toList());
    model.addAttribute("users", users);

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "employee";
  }

  @GetMapping({"/searchTickets/{id}"})
  public String searchTickets(@PathVariable("id") Long id, Model model) {
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    Ticket ticket = ticketService.getTicketById(id);
    setPage("ticketdetail");
    model.addAttribute("ticket", ticket);
    model.addAttribute("message", new Message());

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "employee";
  }

  @PostMapping("/assignTicket/{id}")
  public String getTicketsPage(
      @PathVariable Long id, @RequestParam(value = "username") String userName, Model model) {
    User user = userController.getCurrently_Logged_In();
    model.addAttribute("user", user);

    User user1 = userRepository.findByUsername(userName);
    Ticket ticket = ticketService.getTicketById(id);
    ticket.setAssignedTo(user1);
    ticket.setState(TicketState.UNANSWERED);
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    ticket.setAssignedAt(ticket.getUpdatedAt());
    ticketService.save(ticket);

    emailController.updateTicketStatus(user, ticket, "assign");

    Iterable<Ticket> tickets = ticketService.getAllTickets();
    setPage("ticket");
    model.addAttribute("tickets", tickets);
    model.addAttribute("userName", "");

    List<User> users =
        StreamSupport.stream(userController.getAllUsers().spliterator(), true)
            .filter(user2 -> !user2.getRole().equals(Role.ROLE_USER.name()))
            .collect(Collectors.toList());
    model.addAttribute("users", users);

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "redirect:/searchTickets";
  }

  @PostMapping("/reply/{id}")
  public String getTicketsPage(
      @PathVariable Long id, @ModelAttribute Message message, Model model) {
    User user = userController.getCurrently_Logged_In();
    model.addAttribute("user", user);

    Ticket ticket = ticketService.findById(id).get();
    if (ticket.getAssignedTo() != null) {
      ticket.setState(TicketState.ANSWERED);
    } else {
      ticket.setState(TicketState.UNASSIGNED);
    }
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    message.setMessageType(MessageType.TICKET);
    message.setSender(user.getUsername());
    message.setMsgDate();
    ticket.addMessage(message);
    ticketService.save(ticket);

    emailController.updateTicketStatus(user, ticket, "reply");

    model.addAttribute("ticket", ticket);
    model.addAttribute("message", new Message());

    setPage("ticketdetail");

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());

    return "redirect:/searchTickets/" + id;
  }

  @PostMapping("/resolveTicket/{id}")
  public String resolveTicket(@PathVariable Long id, Model model) {
    User user = userController.getCurrently_Logged_In();
    model.addAttribute("user", user);

    Ticket ticket = ticketService.getTicketById(id);
    ticket.setState(TicketState.RESOLVED);
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    ticket.setResolvedAt(ticket.getUpdatedAt());
    ticketService.save(ticket);

    emailController.updateTicketStatus(user, ticket, "resolve");

    Iterable<Ticket> tickets = ticketService.getAllTickets();
    setPage("ticket");
    model.addAttribute("tickets", tickets);
    model.addAttribute("userName", "");

    List<User> users =
        StreamSupport.stream(userController.getAllUsers().spliterator(), true)
            .filter(anotherUser -> !anotherUser.getRole().equals(Role.ROLE_USER.name()))
            .collect(Collectors.toList());
    model.addAttribute("users", users);

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "redirect:/searchTickets";
  }

  @GetMapping({"/editUserButton/{id}"})
  public String editUserButton(@PathVariable("id") int id, Model model) {
    User user = userController.getCurrently_Logged_In();
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    setPage2("user");
    User user2 = userController.getUser(id, model);
    setSearchedUser(user2);
    User useradd = new User();

    model.addAttribute("users", getAllUsers());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("roleList", roleList);
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("page2", page2);
    model.addAttribute("myusers", getMyUserSearch());
    return "employee";
  }

  @RequestMapping({"/editUser"})
  public String editUser(
      @RequestParam("id") Long id,
      @RequestParam("userName") String userName,
      @RequestParam("password") String password,
      @RequestParam("passwordConf") String passwordConf,
      @RequestParam("email") String email,
      @RequestParam("fName") String fName,
      @RequestParam("lName") String lName,
      @RequestParam("phoneNumber") String phoneNumber,
      @RequestParam("cc") String cc,
      @RequestParam("role") String role,
      @RequestParam("emailVerification") String emailVerification,
      @RequestParam("dispName") String dispName,
      @RequestParam("userDesc") String userDesc,
      @RequestParam("creationDate") String creationDate,
      Model model) {
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    User editUser = userController.getUser(id, model);

    editUser.setUsername(userName);
    editUser.setPassword(password);
    editUser.setPasswordconf(passwordConf);
    editUser.setEmail(email);
    editUser.setFirstName(fName);
    editUser.setLastName(lName);
    editUser.setPhoneNumber(phoneNumber);
    editUser.setCountryCode(cc);
    editUser.setRole(role);
    editUser.setEmailVerification(emailVerification);
    editUser.setDisplayName(dispName);
    editUser.setUserDescription(userDesc);
    editUser.setCreationDate(creationDate);
    List<String> errorString = new ArrayList<>();
    if (userName.length() < 6 || userName.length() > 30) {
      errorString.add(" Enter username between 6-30 characters ");
    }
    if (fName.length() < 1 || fName.length() > 200) {
      errorString.add(" Enter first name between 1-200 characters ");
    }
    if (lName.length() < 6 || lName.length() > 30) {
      errorString.add(" Enter last name between 1-200 characters ");
    }
    if (email.length() < 6 || email.length() > 10) {
      errorString.add(" Enter valid email address between 6-100 characters ");
    }
    if (phoneNumber.length() < 1 || phoneNumber.length() > 20) {
      errorString.add(" Enter valid phone-number ");
    }
    if (passwordConf.length() < 6 || passwordConf.length() > 200) {
      errorString.add(" Enter a password confirmation between 6-200 characters ");
    }
    try {
      userController.editUser(editUser);
      setPage3("editSuccess");
    } catch (Exception e) {

      model.addAttribute("error", errorString);
      setPage3("editFail");
    } finally {

      getAllUsers().clear();
      Iterable<User> allUsersIterator = userController.getAllUsers();

      allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));
      User useradd = new User();
      setSearchedUser(editUser);
      model.addAttribute("users", getAllUsers());
      model.addAttribute("searchedUser", getSearchedUser());
      model.addAttribute("roleList", roleList);
      model.addAttribute("useradd", useradd);
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      model.addAttribute("page2", page2);
      model.addAttribute("page3", getPage3());
      model.addAttribute("myusers", getMyUserSearch());
    }
    return "employee";
  }

  @RequestMapping({"/editWidgetHere"})
  public String editWidget(
      @RequestParam("widgetId") String widgetId,
      @RequestParam("widgetName") String widgetName,
      @RequestParam("widgetDesc") String widgetDesc,
      @RequestParam("widgetCat") String widgetCat,
      @RequestParam("sellerName") String sellerName,
      @RequestParam("marketId") String marketId,
      @RequestParam("marketQty") String marketQty,
      @RequestParam("marketPrice") String marketPrice,
      Model model) {
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    User user2 = userController.getCurrently_Logged_In();
    if (user2.getRole().equals("ROLE_ADMIN")
        || user2.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user2.getRole().equals("ROLE_TECHNICALSERVICE")) {
      System.out.println("here2");
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user2);
      model.addAttribute("page", page);
      return "employee";
    }

    try {

      setPage3("editWidgetSuccess");

      String[] widgetData = widgetId.split(",");
      Widget[] editWidgets = new Widget[widgetData.length];
      String[] widgetNameData = new String[widgetData.length];
      String[] widgetDescriptionData = new String[widgetData.length];
      String[] widgetCategoryData = new String[widgetData.length];
      widgetNameData = widgetName.split(",");
      widgetDescriptionData = widgetDesc.split(",");
      widgetCategoryData = widgetCat.split(",");

      String[] marketData = marketId.split(",");
      System.out.println(marketData.length);
      MarketListing[] editMarkets = new MarketListing[marketData.length];
      String[] sellerNameData = new String[marketData.length];
      String[] marketQtyData = new String[marketData.length];
      String[] marketPriceData = new String[marketData.length];
      sellerNameData = sellerName.split(",");
      marketQtyData = marketQty.split(",");
      marketPriceData = marketPrice.split(",");
      System.out.println(marketPriceData.length);
      getSearchedUserSellers().clear();
      getSearchedUserWidgets().clear();
      getSearchedUserListings().clear();
      for (int i = 0; i < widgetData.length; i++) {
        editWidgets[i] = widgetController.getWidget(Long.parseLong(widgetData[i], 10));
        editWidgets[i].setName(widgetNameData[i]);
        editWidgets[i].setDescription(widgetDescriptionData[i]);
        editWidgets[i].setCategory(widgetCategoryData[i]);
        widgetController.addWidgetnobinding(editWidgets[i]);
        getSearchedUserWidgets().add(editWidgets[i]);
      }
      for (int i = 0; i < marketData.length; i++) {
        editMarkets[i] = market.getMarketListing(Long.parseLong(marketData[i], 10));
        User user = userController.getUserByUsername(sellerNameData[i]);
        editMarkets[i].setSeller(user);
        editMarkets[i].setQtyAvailable(Long.parseLong(marketQtyData[i], 10));
        // editMarkets[i].setPricePerItem(new BigDecimal(marketPriceData[i]));
        if (!(marketPriceData[i].isBlank())) {
          editMarkets[i].setPricePerItem(
              NumberUtils.parseNumber(marketPriceData[i], BigDecimal.class));
          System.out.println(NumberUtils.parseNumber(marketPriceData[i], BigDecimal.class));
        }

        market.addMarketListingSimple(editMarkets[i]);
        getSearchedUserSellers().add(user);
        getSearchedUserListings().add(editMarkets[i]);
      }

    } catch (Exception e) {

      model.addAttribute("error", e.getMessage());
      setPage3("editWidgetFail");
    } finally {

      getAllUsers().clear();
      Iterable<User> allUsersIterator = userController.getAllUsers();

      allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));

      getAllMarketListings().clear();
      getAllWidgets().clear();
      getAllSellers().clear();
      Iterable<MarketListing> allMarketListingsIterator = market.getAllListings();

      allMarketListingsIterator.iterator().forEachRemaining(u -> getAllMarketListings().add(u));
      allMarketListingsIterator
          .iterator()
          .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
      allMarketListingsIterator
          .iterator()
          .forEachRemaining(u -> getAllSellers().add(u.getSeller()));

      if (!(isSingleWidgetCheck())) {
        model.addAttribute("sellers", getSearchedUserSellers());
        model.addAttribute("searchedWidgets", getSearchedUserWidgets());
        model.addAttribute("searchedMarkets", getSearchedUserListings());
      } else {

        model.addAttribute("sellers", getSearchedSeller());
        model.addAttribute("searchedWidgets", getSearchedWidget());
        model.addAttribute("searchedMarkets", getSearchedMarket());
      }

      setMailboxPage("searchMarketListing");
      model.addAttribute("mailpage", getMailboxPage());
      model.addAttribute("allMarketListings", getAllMarketListings());
      model.addAttribute("allWidgets", getAllWidgets());
      model.addAttribute("allSellers", getAllSellers());
      model.addAttribute("roleList", roleList);
      model.addAttribute("users", getAllUsers());
      model.addAttribute("user", user2);
      model.addAttribute("page", getPage());
      model.addAttribute("page3", getPage3());
      model.addAttribute("searchedUser", getSearchedUser());
      model.addAttribute("roleList", roleList);

      model.addAttribute("myusers", getMyUserSearch());
    }
    return "employee";
  }

  @RequestMapping({"/escalateApplication"})
  public String escalateApplication(
      @RequestParam("id") int id, @RequestParam("id2") int id2, Model model) {
    setPage("applications");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userController.getCurrently_Logged_In();
    if (user.getRole().equals("ROLE_HIRINGMANAGER")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    Boolean accept = false;
    if (id2 == 1) {
      accept = true;
    }
    Applicant application = appControl.getApplicant(id);
    if (accept) {
      // email.applicationAccepted("tmd1021@sru.edu", application.getFirstName(),
      // application.getLastName()
      // , application.getPhoneNumber(), application.getRoleAppliedfor());
      email.applicationAccepted(
          application.getEmail(),
          application.getFirstName(),
          application.getLastName(),
          application.getPhoneNumber(),
          application.getRoleAppliedfor());

    } else {
      // email.applicationRejected("tmd1021@sru.edu", application.getFirstName(),
      // application.getLastName());
      email.applicationRejected(
          application.getEmail(), application.getFirstName(), application.getLastName());
    }
    setPageSuccess("reviewedApplication");
    application.setReviewed(true);
    appControl.addApplicantSimple(application);
    getAllApplicants().clear();
    getReviewedApplicants().clear();
    getNewApplicants().clear();
    Iterable<Applicant> applicantIterator = appRepo.findAll();

    applicantIterator.iterator().forEachRemaining(u -> getAllApplicants().add(u));

    Applicant[] allApplications =
        getAllApplicants().toArray(new Applicant[getAllApplicants().size()]);
    for (int i = 0; i < allApplications.length; i++) {
      if (allApplications[i].getReviewed() != null) {
        if (allApplications[i].getReviewed() == true) {
          getReviewedApplicants().add(allApplications[i]);
        } else {
          getNewApplicants().add(allApplications[i]);
        }
      } else {
        getNewApplicants().add(allApplications[i]);
      }
    }
    if (getApplicantPage() == 1) {
      model.addAttribute("applicants", getNewApplicants());
    } else {
      model.addAttribute("applicants", getReviewedApplicants());
    }
    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    model.addAttribute("pageSuccess", getPageSuccess());
    return "employee";
  }

  @RequestMapping({"/searchUser"})
  public String searchuser(@RequestParam("userName") String userName, @RequestParam("filterUser") String filterUser, Model model) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    User user = userController.getCurrently_Logged_In();
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    int count = 0;
    if(filterUser.equals("name")) {
	    for (int i = 0; i < getAllUsers().size(); i++) {
	      if (getAllUsers().get(i).getUsername().contains(userName)) {
	        count++;
	      }
	    }
    }else if(filterUser.equals("email")) {
    	for (int i = 0; i < getAllUsers().size(); i++) {
  	      if (getAllUsers().get(i).getEmail().contains(userName)) {
  	        count++;
  	      }
    	}
    }else if (count == 0) {
      setPage("userSearchfailed");
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      model.addAttribute("roleList", roleList);
      model.addAttribute("searchedUser", getSearchedUser());

      return "employee";
    }
    User[] searchedUsers = new User[count];
    setMyUserSearch(searchedUsers);
    count = 0;
    if(filterUser.equals("name")) {
	    for (int i = 0; i < getAllUsers().size(); i++) {
	      if (getAllUsers().get(i).getUsername().contains(userName)) {
	        searchedUsers[count] = getAllUsers().get(i);
	        count++;
	      }
	    }
    }else if(filterUser.equals("email")) {
    	for (int i = 0; i < getAllUsers().size(); i++) {
  	      if (getAllUsers().get(i).getEmail().contains(userName)) {
  	        searchedUsers[count] = getAllUsers().get(i);
  	        count++;
  	      }
  	    }
    }
    count = 0;
    setMyUserSearch(searchedUsers);
    model.addAttribute("user", user);
    setPage("userResult");
    User useradd = new User();
    model.addAttribute("useradd", useradd);
    model.addAttribute("page", page);
    model.addAttribute("roleList", roleList);
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("myusers", getMyUserSearch());

    return "employee";
  }

  @RequestMapping({"/searchUser/{id}"})
  public String searchUserById(@PathVariable("id") int id, Model model) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    User user = userController.getCurrently_Logged_In();
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    setSearchedUser(userController.getUser(id, model));
    model.addAttribute("user", user);
    setPage("singleUserResult");
    User useradd = new User();
    model.addAttribute("users", getAllUsers());
    model.addAttribute("useradd", useradd);
    model.addAttribute("page", page);
    model.addAttribute("roleList", roleList);
    model.addAttribute("searchedUser", getSearchedUser());

    return "employee";
  }

  @GetMapping({"/viewListing/{id}"})
  public String searchMarketListingButton(@PathVariable("id") int id, Model model) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    User user = userController.getCurrently_Logged_In();
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    setMailboxPage("searchMarketListing");
    User user2 = userController.getUser(id, model);
    MarketListing[] listings = market.getListingbyUser(user2);
    Widget[] widgets = new Widget[listings.length];
    User[] sellers = new User[listings.length];
    getSearchedUserListings().clear();
    getSearchedUserWidgets().clear();
    getSearchedUserSellers().clear();
    for (int i = 0; i < listings.length; i++) {
      widgets[i] = listings[i].getWidgetSold();
      sellers[i] = listings[i].getSeller();
      getSearchedUserListings().add(listings[i]);
      getSearchedUserWidgets().add(widgets[i]);
      getSearchedUserSellers().add(sellers[i]);
    }
    User useradd = new User();
    setSingleWidgetCheck(false);
    model.addAttribute("listings", listings);
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("sellers", getSearchedUserSellers());
    model.addAttribute("searchedWidgets", getSearchedUserWidgets());
    model.addAttribute("searchedMarkets", getSearchedUserListings());
    model.addAttribute("myusers", getMyUserSearch());
    return "employee";
  }

  @GetMapping({"/viewOneListing/{id}"})
  public String searchListing(@PathVariable("id") int id, Model model) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    User user = userController.getCurrently_Logged_In();
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    model.addAttribute("user", user);
    User useradd = new User();
    model.addAttribute("useradd", useradd);
    model.addAttribute("page", page);
    model.addAttribute("roleList", roleList);
    if (widgetController.getWidget(id) == null) {
      setPage("listingSearchError");
      return "employee";
    }
    setSingleWidgetCheck(true);
    System.out.println(isSingleWidgetCheck());
    setSearchedWidget(widgetController.getWidget(id));
    setSearchedMarket(market.getListingByWidget(getSearchedWidget()));
    setSearchedSeller(searchedMarket.getSeller());
    setSearchedUser(getSearchedSeller());
    model.addAttribute("user", user);
    // setPage("listingResult");
    setMailboxPage("searchMarketListing");
    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("allMarketListings", getAllMarketListings());
    model.addAttribute("allWidgets", getAllWidgets());
    model.addAttribute("allSellers", getAllSellers());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("sellers", getSearchedSeller());
    model.addAttribute("searchedWidgets", getSearchedWidget());
    model.addAttribute("searchedMarkets", getSearchedMarket());
    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("myusers", getMyUserSearch());
    model.addAttribute("page", page);

    return "employee";
  }

  @RequestMapping({"/viewWidgetListing"})
  public String searchOneListing(@RequestParam("name") String name, @RequestParam("filterSearch") String filter, Model model) {
    getAllMarketListings().clear();
    getAllWidgets().clear();
    getAllSellers().clear();
    Iterable<MarketListing> allMarketListingsIterator = market.getAllListings();

    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllMarketListings().add(u));
    allMarketListingsIterator
        .iterator()
        .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllSellers().add(u.getSeller()));
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }
    
    String[] allWidgetDesc = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetDesc.length; i++) {
      allWidgetDesc[i] = getAllWidgets().get(i).getDescription();
    }
    
    String[] allWidgetCate = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetCate.length; i++) {
      allWidgetCate[i] = getAllWidgets().get(i).getCategory();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    model.addAttribute("widgetDesc", allWidgetDesc);
    model.addAttribute("widgetCate", allWidgetCate);
    User user = userController.getCurrently_Logged_In();
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("roleList", roleList);
    
    
    int count = 0;
    if(filter.equals("name")) {
	    for (int i = 0; i < getAllWidgets().size(); i++) {
	      if (getAllWidgets().get(i).getName().contains(name)) {
	        count++;
	        System.out.println("count first" + count);
	      }
	    }
    }else if(filter.equals("description")) {
    	for (int i = 0; i < getAllWidgets().size(); i++) {
  	      if (getAllWidgets().get(i).getDescription().contains(name)) {
  	        count++;
  	        System.out.println("count first" + count);
  	      }
  	    }
    }else if(filter.equals("category")) {
    	for (int i = 0; i < getAllWidgets().size(); i++) {
  	      if (getAllWidgets().get(i).getCategory().contains(name)) {
  	        count++;
  	        System.out.println("count first" + count);
  	      }
  	    }
    }else if(count == 0){
      System.out.println("count 2nd" + count + filter + name);
      setPage2("widgetSearchFailed");
      model.addAttribute("user", user);
      model.addAttribute("page2", page2);
      model.addAttribute("roleList", roleList);
      model.addAttribute("searchedUser", getSearchedUser());

      return "employee";
    }
    Widget[] searchedWidgets = getAllWidgets().toArray(new Widget[getAllWidgets().size()]);
    User[] seller = new User[count];
    MarketListing[] listings = new MarketListing[count];
    count = 0;
    getSearchedUserListings().clear();
    getSearchedUserWidgets().clear();
    getSearchedUserSellers().clear();
    
    if(filter.equals("name")) {
	    int j = 0;
	    for (int i = 0; i < searchedWidgets.length; i++) {
	      if (searchedWidgets[i].getName().contains(name)) {
	        System.out.println("count" + count);
	
	        getSearchedUserListings().add(market.getListingByWidget(searchedWidgets[i]));
	        getSearchedUserWidgets().add(searchedWidgets[i]);
	        getSearchedUserSellers().add(getSearchedUserListings().get(j).getSeller());
	        j++;
	        count++;
	      }
	    }
    }else if(filter.equals("description")) {
    	int j = 0;
	    for (int i = 0; i < searchedWidgets.length; i++) {
	      if (searchedWidgets[i].getDescription().contains(name)) {
	        System.out.println("count" + count);
	
	        getSearchedUserListings().add(market.getListingByWidget(searchedWidgets[i]));
	        getSearchedUserWidgets().add(searchedWidgets[i]);
	        getSearchedUserSellers().add(getSearchedUserListings().get(j).getSeller());
	        j++;
	        count++;
	      }
	    }
    }else if(filter.equals("category")) {
    	int j = 0;
	    for (int i = 0; i < searchedWidgets.length; i++) {
	      if (searchedWidgets[i].getCategory().contains(name)) {
	        System.out.println("count" + count);
	
	        getSearchedUserListings().add(market.getListingByWidget(searchedWidgets[i]));
	        getSearchedUserWidgets().add(searchedWidgets[i]);
	        getSearchedUserSellers().add(getSearchedUserListings().get(j).getSeller());
	        j++;
	        count++;
	      }
	    }
    }
    count = 0;
    setPage2("widget");
    setSingleWidgetCheck(false);
    setPage("searchWidgetSellerMarketListing");
    model.addAttribute("allMarketListings", getSearchedUserListings());
    model.addAttribute("allWidgets", getSearchedUserWidgets());
    model.addAttribute("allSellers", getSearchedUserSellers());
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("user", user);
    model.addAttribute("page2", getPage2());
    model.addAttribute("page", getPage());

    return "employee";
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getPage2() {
    return page2;
  }

  public void setPage2(String page2) {
    this.page2 = page2;
  }

  public User getSearchedUser() {
    return searchedUser;
  }

  public void setSearchedUser(User searchedUser) {
    this.searchedUser = searchedUser;
  }

  public String getMailboxPage() {
    return mailboxPage;
  }

  public void setMailboxPage(String mailboxPage) {
    this.mailboxPage = mailboxPage;
  }

  public List<User> getAllUsers() {
    return allUsers;
  }

  public void setAllUsers(List<User> allUsers) {
    this.allUsers = allUsers;
  }

  public List<Applicant> getAllApplicants() {
    return allApplicants;
  }

  public void setAllApplicants(List<Applicant> allApplicants) {
    this.allApplicants = allApplicants;
  }

  public User[] getMyUserSearch() {
    return myUserSearch;
  }

  public void setMyUserSearch(User[] myUserSearch) {
    this.myUserSearch = myUserSearch;
  }

  public List<Widget> getAllWidgets() {
    return allWidgets;
  }

  public void setAllWidgets(List<Widget> allWidgets) {
    this.allWidgets = allWidgets;
  }

  public List<User> getAllSellers() {
    return allSellers;
  }

  public void setAllSellers(List<User> allSellers) {
    this.allSellers = allSellers;
  }

  public List<MarketListing> getAllMarketListings() {
    return allMarketListings;
  }

  public void setAllMarketListings(List<MarketListing> allMarketListings) {
    this.allMarketListings = allMarketListings;
  }

  public List<Message> getAllMessages() {
    return allMessages;
  }

  public void setAllMessages(List<Message> allMessages) {
    this.allMessages = allMessages;
  }

  public List<MarketListing> getSearchedUserListings() {
    return searchedUserListings;
  }

  public List<Widget> getSearchedUserWidgets() {
    return searchedUserWidgets;
  }

  public List<User> getSearchedUserSellers() {
    return searchedUserSellers;
  }

  public void setSearchedUserListings(List<MarketListing> searchedUserListings) {
    this.searchedUserListings = searchedUserListings;
  }

  public void setSearchedUserWidgets(List<Widget> searchedUserWidgets) {
    this.searchedUserWidgets = searchedUserWidgets;
  }

  public void setSearchedUserSellers(List<User> searchedUserSellers) {
    this.searchedUserSellers = searchedUserSellers;
  }

  public List<Applicant> getReviewedApplicants() {
    return reviewedApplicants;
  }

  public List<Applicant> getNewApplicants() {
    return newApplicants;
  }

  public void setReviewedApplicants(List<Applicant> reviewedApplicants) {
    this.reviewedApplicants = reviewedApplicants;
  }

  public void setNewApplicants(List<Applicant> newApplicants) {
    this.newApplicants = newApplicants;
  }

  public int getApplicantPage() {
    return applicantPage;
  }

  public void setApplicantPage(int applicantPage) {
    this.applicantPage = applicantPage;
  }

  public String getPageSuccess() {
    return pageSuccess;
  }

  public void setPageSuccess(String pageSuccess) {
    this.pageSuccess = pageSuccess;
  }

  public String getMasterPage() {
    return masterPage;
  }

  public void setMasterPage(String masterPage) {
    this.masterPage = masterPage;
  }

  public Widget getSearchedWidget() {
    return searchedWidget;
  }

  public MarketListing getSearchedMarket() {
    return searchedMarket;
  }

  public User getSearchedSeller() {
    return searchedSeller;
  }

  public void setSearchedWidget(Widget searchedWidget) {
    this.searchedWidget = searchedWidget;
  }

  public void setSearchedMarket(MarketListing searchedMarket) {
    this.searchedMarket = searchedMarket;
  }

  public void setSearchedSeller(User searchedSeller) {
    this.searchedSeller = searchedSeller;
  }

  public boolean isSingleWidgetCheck() {
    return singleWidgetCheck;
  }

  public void setSingleWidgetCheck(boolean singleWidgetCheck) {
    this.singleWidgetCheck = singleWidgetCheck;
  }

  public String getPage3() {
    return page3;
  }

  public void setPage3(String page3) {
    this.page3 = page3;
  }
}
