package blog.business.services;

import java.util.Optional;

import blog.presentation.models.Subscription;

public interface SubscriptionService {
	Optional<Subscription> create(Subscription subscription);
}
