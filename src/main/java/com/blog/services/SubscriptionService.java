package com.blog.services;

import java.util.List;
import java.util.Optional;

import com.blog.models.Subscription;

public interface SubscriptionService {
	Optional<Subscription> saveSubscription(Subscription subscription);
	List<Subscription> findAllSubscriptions();
}
