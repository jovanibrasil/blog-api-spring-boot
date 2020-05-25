package com.blog.services.impl;

import com.blog.exception.InvalidInformationException;
import com.blog.model.Subscription;
import com.blog.repositories.SubscriptionRepository;
import com.blog.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository subscriptionRepository;

	@Override
	public Subscription saveSubscription(String email) {
		Subscription subscription = new Subscription(email);
		Optional<Subscription> optSubscription = subscriptionRepository.findByEmail(subscription.getEmail());
		if(!optSubscription.isPresent()) {
			return this.subscriptionRepository.save(subscription);
		}
		log.info("The email {} was already subscribed.", subscription.getEmail());
		throw new InvalidInformationException("Email already registered.");
	}

	@Override
	public Page<Subscription> findAllSubscriptions(Pageable page) {
		return subscriptionRepository.findAll(page);
	}

}
