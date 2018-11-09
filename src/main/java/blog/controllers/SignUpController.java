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
import blog.forms.SignUpForm;
import blog.services.NotificationService;
import blog.services.UserService;

import javax.validation.Valid;


@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notifyService;

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;
    
    @RequestMapping("/users/signup")
    public String signup(SignUpForm signupForm) {
        return "users/signup";
    }

    @RequestMapping(value = "/users/signup", method = RequestMethod.POST)
    public String loginPage(@Valid SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
             notifyService.addErrorMessafe("Please fill the form correctly!");
             return "users/signup";
        }

        //signUpForm.getUserName(), signUpForm.getPassword()
          
        if (!signUpForm.getPassword().equals(signUpForm.getPasswordConfirmation())) {
             notifyService.addErrorMessafe("Registro inválido!");
             return "users/signup";
        }

        userService.saveUser(signUpForm.getUserName(), signUpForm.getFullUserName(), signUpForm.getPassword());
        
        notifyService.addInfoMessage("Registrado com sucesso");
        return "redirect:/";
    }
}