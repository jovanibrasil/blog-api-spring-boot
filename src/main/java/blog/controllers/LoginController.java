package blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.forms.LoginForm;
import blog.services.NotificationService;
import blog.services.UserService;

import javax.validation.Valid;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notifyService;

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;
    
    @RequestMapping("/users/login")
    public String login(LoginForm loginForm) {
        return "users/login";
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String loginPage(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
             notifyService.addErrorMessafe("Please fill the form correctly!");
             return "users/login";
        }

        try {
        	Authentication auth = new UsernamePasswordAuthenticationToken(
		    		loginForm.getUserName(), loginForm.getPassword());
		    Authentication authenticated = daoAuthenticationProvider.authenticate(auth);
		    SecurityContextHolder.getContext().setAuthentication(authenticated);
		      
		    if (!authenticated.isAuthenticated()) {
		         notifyService.addErrorMessafe("Invalid login!");
		         return "users/login";
		    }

	        notifyService.addInfoMessage("Login successful");
	        
	        // TODO Redirect to last visited page
	        
	        return "redirect:/";
		    
		} catch (Exception e) {
			e.printStackTrace();
	         notifyService.addErrorMessafe("Invalid login!");
	         return "users/login";
	    
		}
        
    }
}