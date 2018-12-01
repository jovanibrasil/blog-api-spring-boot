package blog.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.business.services.NotificationService;
import blog.presentation.views.LogoutForm;


@Controller
public class LogoutController {

	@Autowired
	private NotificationService notifyService;
	
	@RequestMapping("/users/logout")
    public String logout(Model model) {
		notifyService.addInfoMessage("Logout successful");
    	SecurityContextHolder.clearContext();
        return "users/logout";
    }
	
	
    @RequestMapping(value="/users/logout", method = RequestMethod.POST)
    public String logout(LogoutForm logoutForm, BindingResult bindingResult) {
    	notifyService.addInfoMessage("Logout successful");
    	SecurityContextHolder.clearContext();
    	return "redirect:/";
    }

}