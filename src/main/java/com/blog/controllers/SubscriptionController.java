package com.blog.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.models.Subscription;
import com.blog.response.Response;
import com.blog.services.SubscriptionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@GetMapping("/{email}")
	public ResponseEntity<Response<Boolean>> getSearchSummaries(@PathVariable("email") String email){

		Response<Boolean> response = new Response<>();
		
		Subscription subscription = new Subscription(email);
		Optional<Subscription> optSubscription = this.subscriptionService.create(subscription);
		
		if(!optSubscription.isPresent()) {
			response.addError("It was not possible to create the subscription.");
			response.setData(false);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(true);
		return ResponseEntity.ok(response);
		
	}
	
}
