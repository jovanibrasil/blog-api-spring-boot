package blog.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.business.services.CustomUserDetails;
import blog.business.services.PostService;
import blog.presentation.models.Post;

@Controller
public class PostManagementController {

	@Autowired
	private PostService postService;
	
	@RequestMapping(value="/posts/management")
	public String management(Model model){
		try {		
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
				CustomUserDetails customerDatails = (CustomUserDetails)auth.getPrincipal();
				List<Post> posts = postService.findPostsByUserId(customerDatails.getUserId());
				model.addAttribute("userPosts", posts);
				model.addAttribute("lblHelloUserName", auth.getName());
				return "/posts/management";
			}else {
				return "redirect:/users/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
	
}
