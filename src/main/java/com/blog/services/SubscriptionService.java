package com.blog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blog.model.Subscription;

public interface SubscriptionService {
	Subscription saveSubscription(String email);
	Page<Subscription> findAllSubscriptions(Pageable page);
}
