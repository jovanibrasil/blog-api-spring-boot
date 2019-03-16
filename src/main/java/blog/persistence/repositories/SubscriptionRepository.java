package blog.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.presentation.models.Post;
import blog.presentation.models.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {}
