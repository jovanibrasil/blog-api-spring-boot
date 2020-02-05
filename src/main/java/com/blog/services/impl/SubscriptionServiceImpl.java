package com.blog.services.impl;

import com.blog.exceptions.InvalidInformationException;
import com.blog.models.Subscription;
import com.blog.repositories.SubscriptionRepository;
import com.blog.services.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

	private SubscriptionRepository subscriptionRepository;

	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	@Override
	public Optional<Subscription> saveSubscription(String email) {
		Subscription subscription = new Subscription(email);
		Optional<Subscription> optSubscription = subscriptionRepository.findByEmail(subscription.getEmail());
		if(!optSubscription.isPresent()) {
			return Optional.of(this.subscriptionRepository.save(subscription));
		}
		log.info("The email {} was already subscribed.", subscription.getEmail());
		throw new InvalidInformationException("Email already registered.");
	}

	@Override
	public List<Subscription> findAllSubscriptions() {
		return subscriptionRepository.findAll();
	}

}
