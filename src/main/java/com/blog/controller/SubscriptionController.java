package com.blog.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.model.Subscription;
import com.blog.services.SubscriptionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

	private final SubscriptionService subscriptionService;

	@ApiOperation(value = "Busca por todas as inscrições no blog.")
	@ApiResponses({@ApiResponse(code = 200, message = "Resultado da busca.", response = Subscription.class, responseContainer = "List")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<Subscription> findAllSubscriptions(Pageable pageable){
		log.info("Finding all subscriptions ...");
		return subscriptionService.findAllSubscriptions(pageable);
	}
	
	@ApiOperation("Cria inscrição de um usuário.")
	@ApiResponses({@ApiResponse(code = 200, message = "Inscrição criada com sucesso.", response = Void.class)})
	@ResponseStatus(HttpStatus.OK)
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
