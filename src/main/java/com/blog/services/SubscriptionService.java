package com.blog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blog.model.Subscription;
import com.blog.model.dto.SubscriptionForm;

public interface SubscriptionService {
	Subscription saveSubscription(SubscriptionForm subscriptionDTO);
	Page<Subscription> findAllSubscriptions(Pageable page);
}
