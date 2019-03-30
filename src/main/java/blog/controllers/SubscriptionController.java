package blog.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.models.Subscription;
import blog.response.Response;
import blog.services.SubscriptionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@GetMapping("/{email}")
	public ResponseEntity<Response<Boolean>> getSearchSummaries(@PathVariable("email") String email){

		Response<Boolean> response = new Response<>();
		
		Subscription subscription = new Subscription(email);
		Optional<Subscription> optSubscription = this.subscriptionService.create(subscription);
		
		if(!optSubscription.isPresent()) {
			response.getErrors().add("It was not possible to create the subscription.");
			response.setData(false);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(true);
		return ResponseEntity.ok(response);
		
	}
	
}
