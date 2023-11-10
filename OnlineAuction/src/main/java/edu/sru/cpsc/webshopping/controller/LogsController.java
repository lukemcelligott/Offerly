package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.stats.StatsRepository;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;

@Controller
public class LogsController {	
	@Autowired
	private UserService userService;
	@Autowired private StatsRepository statsRepository;
	
	
	@GetMapping({"/viewLogs"})
	public String viewLogs (Model model, Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		
		List<Statistics> statList = getLogs();

		model.addAttribute("user", user);
		model.addAttribute("page", "logs");
		model.addAttribute("statList", statList);
		
		return "logs";
	}
	
	public List<Statistics> getLogs() {
		Iterable<Statistics> statsIterable = statsRepository.findAll();
        List<Statistics> statsList = new ArrayList<>();
        
        for (Statistics stat : statsIterable) {
            statsList.add(stat);
        }
		
		return statsList;
	}

}
