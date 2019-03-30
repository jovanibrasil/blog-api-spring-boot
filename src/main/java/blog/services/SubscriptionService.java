package blog.services;

import java.util.Optional;

import blog.models.Subscription;

public interface SubscriptionService {
	Optional<Subscription> create(Subscription subscription);
}
