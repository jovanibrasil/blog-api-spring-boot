package blog.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.models.Post;
import blog.services.PostService;

@Controller
public class HomeController {
	
	@Autowired // It automatically injects the PostService implementation 
	private PostService postService;
	
	@RequestMapping("/") // trata requisições http get para a URL /
	public String index(Model model) { // adjusts the view model
		
		List<Post> latestPosts = postService.findLatest(5);
		model.addAttribute("latest5Posts", latestPosts);
		
		latestPosts = latestPosts.stream().limit(3).collect(Collectors.toList());
		model.addAttribute("latest3Posts", latestPosts);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
			model.addAttribute("lblHelloUserName", auth.getName());
		}
		
		return "index";
	}
	
}
