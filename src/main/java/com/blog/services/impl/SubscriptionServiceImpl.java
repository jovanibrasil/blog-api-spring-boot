package com.blog.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.exceptions.InvalidInformationException;
import com.blog.models.Subscription;
import com.blog.repositories.SubscriptionRepository;
import com.blog.services.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Override
	public Optional<Subscription> saveSubscription(Subscription subscription) {
		Optional<Subscription> optSubscription = subscriptionRepository.findByEmail(subscription.getEmail());
		if(!optSubscription.isPresent()) {
			return Optional.of(this.subscriptionRepository.save(subscription));
		}
		throw new InvalidInformationException("The email was already subscribed.");
	}

	@Override
	public List<Subscription> findAllSubscriptions() {
		return subscriptionRepository.findAll();
	}

}
