package com.blog.services;

import com.blog.models.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
	Optional<Subscription> saveSubscription(Subscription subscription);
	List<Subscription> findAllSubscriptions();
}
