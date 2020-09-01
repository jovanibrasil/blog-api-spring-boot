package com.blog.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.model.Subscription;
import com.blog.model.dto.SubscriptionForm;
import com.blog.services.SubscriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

	private final SubscriptionService subscriptionService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<Subscription> findAllSubscriptions(Pageable pageable){
		log.info("Finding all subscriptions ...");
		return subscriptionService.findAllSubscriptions(pageable);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public ResponseEntity<?> subscribe(@Valid @RequestBody SubscriptionForm subscriptionDTO, UriComponentsBuilder uriBuilder){
		log.info("Subscribing {} ...", subscriptionDTO.getEmail());
	 	Subscription subscription = subscriptionService.saveSubscription(subscriptionDTO);
		URI uri = uriBuilder.path("/subscriptions/{id}")
				.buildAndExpand(subscription.getId())
				.toUri();
		return ResponseEntity.created(uri).build();	
	}
	
}
