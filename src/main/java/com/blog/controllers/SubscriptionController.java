package com.blog.controllers;

import com.blog.models.Subscription;
import com.blog.response.Response;
import com.blog.services.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subscriptions")
@Slf4j
public class SubscriptionController {

	private SubscriptionService subscriptionService;

	public SubscriptionController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@GetMapping
	public ResponseEntity<Response<?>> findAllSubscriptions(){
		log.info("Finding all subscriptions ...");
		Response<List<Subscription>> response = new Response<>();
		List<Subscription> subscriptions = this.subscriptionService.findAllSubscriptions();
		response.setData(subscriptions);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{email}")
	public ResponseEntity<Response<?>> subscribe(@PathVariable("email") 
		@Valid @NotBlank(message = "{error.user.email.notblank}")
		@Email(message = "{error.user.email.format}") String email){
		log.info("Subscribing {} ...", email);
		Response<?> response = new Response<>();
		this.subscriptionService.saveSubscription(email);
		return ResponseEntity.ok(response);	
	}
	
}
