package blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.models.Post;
import blog.models.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {}
