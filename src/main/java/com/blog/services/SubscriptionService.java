package com.blog.services;

import com.blog.models.Subscription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionService {
	Subscription saveSubscription(String email);
	Page<Subscription> findAllSubscriptions(Pageable page);
}
