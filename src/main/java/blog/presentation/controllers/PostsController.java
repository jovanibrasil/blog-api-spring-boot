package blog.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.business.services.CustomUserDetails;
import blog.business.services.NotificationService;
import blog.business.services.PostService;
import blog.presentation.models.Post;
import blog.presentation.models.User;
import blog.presentation.views.EditPostForm;

@Controller
@RequestMapping("/posts")
public class PostsController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private NotificationService notificationService;
	
	//@ResponseBody
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		Post post = postService.findById(id);
		if(post == null) {
			notificationService.addErrorMessafe("Post não encontrado #"+id);
			return "redirect:/";
		}
		model.addAttribute("post", post);
		return "posts/view";
	}
	
	@RequestMapping("/create")
	public String createPage(EditPostForm editPostForm) {
		return "posts/edit";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(EditPostForm editPostForm, BindingResult bindingResult) {
		// se deu erro, mantém onde está
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()) {
			CustomUserDetails customUser = (CustomUserDetails)auth.getPrincipal();
			System.out.println(customUser.getUserId());
			User author = new User();
			author.setUserId(customUser.getUserId());
			
			System.out.println("O id do autor é"+author.getId());
			
			Post post = new Post();
			post.setTitle(editPostForm.getTitle());
			post.setBody(editPostForm.getBody());
			post.setAuthor(author);
			postService.create(post);
			return "posts/edit"; // TODO se sucesso, retorna para lista de posts do user
		}else {
			return "users/login";
		}
	}
	
	
	
}
