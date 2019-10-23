package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);
	
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
		@Valid @NotBlank(message = "Email não deve estar em branco.")  
		@Email(message = "Formato de email inválido.") String email){
		log.info("Subscribing {} ...", email);
		Response<?> response = new Response<>();
		Subscription subscription = new Subscription(email);
		this.subscriptionService.saveSubscription(subscription);
		return ResponseEntity.ok(response);	
	}
	
}
