package edu.sru.cpsc.webshopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sru.cpsc.webshopping.domain.user.Applicant;
import edu.sru.cpsc.webshopping.repository.applicant.ApplicantRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
  @Autowired
  private UserService userService;
  
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ApplicantRepository appRepo;

  // Mapping for the /index URL when initiated through Tomcat
  @RequestMapping({"/index"})
  public String showUserList(Model model) {
    return "index";
  }

  @RequestMapping({"/"})
  public String showIndex(Model model) {
    return "index";
  }

  @RequestMapping({"/login"})
  public String showLoginPage() {
    return "login";
  }

  @RequestMapping({"/missionStatement"})
  public String showMission() {
    return "missionStatement";
  }

  @RequestMapping({"/FAQ"})
  public String showFAQ() {
    return "FAQ";
  }

  @RequestMapping({"/application"})
  public String showApplication(Model model) {
    Applicant applicant = new Applicant();

    model.addAttribute("applicant", applicant);
    return "application";
  }

  @PostMapping("/apply")
  public String addApplication(@Valid Applicant applicant, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "application";
    }
    appRepo.save(applicant);
    return "redirect:index";
  }
}
