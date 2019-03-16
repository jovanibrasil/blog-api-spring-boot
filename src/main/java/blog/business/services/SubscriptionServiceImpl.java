package blog.business.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.persistence.repositories.SubscriptionRepository;
import blog.presentation.models.Subscription;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Override
	public Optional<Subscription> create(Subscription subscription) {
		return Optional.of(this.subscriptionRepository.save(subscription));
	}

}
