package blog.presentation.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.business.services.PostService;
import blog.dtos.SummaryDTO;
import blog.presentation.models.Post;
import blog.response.Response;

@CrossOrigin("*")
@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private PostService postService;
	
	@GetMapping("/{query}")
	public ResponseEntity<Response<ArrayList<SummaryDTO>>> getSearchSummaries(@PathVariable("query") String query){

		Response<ArrayList<SummaryDTO>> response = new Response<>();
		Optional<List<Post>> optLatestPosts;
		
		ArrayList<SummaryDTO> summaries = new ArrayList<>();
		
		optLatestPosts = postService.findPosts(2L);
		optLatestPosts.get().forEach(post -> {
			SummaryDTO summaryDTO = new SummaryDTO(post.getPostId(), post.getTitle(), 
					post.getCreationDate(),	post.getLastUpdateDate(), 
					post.getSummary(), post.getAuthor().getUsername(), post.getTags());
			summaries.add(summaryDTO);
		});
		
		response.setData(summaries);
		return ResponseEntity.ok(response);
		
	}
	
	
}
