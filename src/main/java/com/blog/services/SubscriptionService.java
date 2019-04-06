package com.blog.services;

import java.util.Optional;

import com.blog.models.Subscription;

public interface SubscriptionService {
	Optional<Subscription> create(Subscription subscription);
}
