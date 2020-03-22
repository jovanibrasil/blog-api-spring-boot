package com.blog.controllers;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.models.Subscription;
import com.blog.response.Response;
import com.blog.services.SubscriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subscriptions")
@Slf4j
@RequiredArgsConstructor
public class SubscriptionController {

	private final SubscriptionService subscriptionService;

	@GetMapping
	public ResponseEntity<Response<Page<Subscription>>> findAllSubscriptions(Pageable pageable){
		log.info("Finding all subscriptions ...");
		Page<Subscription> subscriptions = subscriptionService.findAllSubscriptions(pageable);
		return ResponseEntity.ok(new Response<Page<Subscription>>(subscriptions));
	}
	
	@PostMapping("/{email}")
	public ResponseEntity<Response<?>> subscribe(@PathVariable("email") 
		@Valid @NotBlank(message = "{error.user.email.notblank}")
		@Email(message = "{error.user.email.format}") String email, UriComponentsBuilder uriBuilder){
		log.info("Subscribing {} ...", email);
	 	Subscription subscription = subscriptionService.saveSubscription(email);
		URI uri = uriBuilder.path("/subscriptions/{id}")
				.buildAndExpand(subscription.getId())
				.toUri();
		return ResponseEntity.created(uri).body(new Response<>());	
	}
	
}
