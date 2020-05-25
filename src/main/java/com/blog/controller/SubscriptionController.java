package com.blog.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.model.Subscription;
import com.blog.services.SubscriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

	private final SubscriptionService subscriptionService;

	@GetMapping
	public ResponseEntity<Page<Subscription>> findAllSubscriptions(Pageable pageable){
		log.info("Finding all subscriptions ...");
		Page<Subscription> subscriptions = subscriptionService.findAllSubscriptions(pageable);
		return ResponseEntity.ok(subscriptions);
	}
	
	@PostMapping("/{email}")
	public ResponseEntity<?> subscribe(@PathVariable("email") 
		@Valid @NotBlank(message = "{error.user.email.notblank}")
		@Email(message = "{error.user.email.format}") String email, UriComponentsBuilder uriBuilder){
		log.info("Subscribing {} ...", email);
	 	Subscription subscription = subscriptionService.saveSubscription(email);
		URI uri = uriBuilder.path("/subscriptions/{id}")
				.buildAndExpand(subscription.getId())
				.toUri();
		return ResponseEntity.created(uri).build();	
	}
	
}
