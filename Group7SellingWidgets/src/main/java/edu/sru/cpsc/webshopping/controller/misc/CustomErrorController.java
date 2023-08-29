package edu.sru.cpsc.webshopping.controller.misc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.NestedServletException;

/**
 *  Controller for managing the errorPage
 *  The errorPage is used for presenting a user readable section for errors
 *  that allows the user to recover from the problem
 */
@Controller
public class CustomErrorController implements ErrorController {
	
	CustomErrorController() {
		
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		NestedServletException exception = (NestedServletException)request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		if (exception == null) {
			model.addAttribute("errorMessage", "No exception was thrown.");
			model.addAttribute("statusCode", "N/A");
			model.addAttribute("exceptionFound", false);
		}
		else {
			model.addAttribute("errorMessage", exception.getLocalizedMessage());
			model.addAttribute("statusCode", statusCode.toString());
			model.addAttribute("exceptionFound", true);
		}
		return "error";
	}
	
	@RequestMapping("/errorPage")
	public String openErrorPage(Exception err, Model model) {
		model.addAttribute("errorMessage", err.getMessage());
		model.addAttribute("errorClass", err.getClass().toString());
		return "errorPage";
	}
	
	@RequestMapping("/errorPageTest")
	public String openErrorPageTest(Model model) {
		throw new IllegalArgumentException("Illegal Argument Exception Test");
		//model.addAttribute("errorMessage", "Error found!");
		//return "errorPage";
	}
}
