package com.blog.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.exceptions.InvalidInformationException;
import com.blog.models.Subscription;
import com.blog.repositories.SubscriptionRepository;
import com.blog.services.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Override
	public Optional<Subscription> saveSubscription(Subscription subscription) {
		Optional<Subscription> optSubscription = subscriptionRepository.findByEmail(subscription.getEmail());
		if(!optSubscription.isPresent()) {
			return Optional.of(this.subscriptionRepository.save(subscription));
		}
		log.info("The email {} was already subscribed.", subscription.getEmail());
		throw new InvalidInformationException("Email já foi registrado.");
	}

	@Override
	public List<Subscription> findAllSubscriptions() {
		return subscriptionRepository.findAll();
	}

}
