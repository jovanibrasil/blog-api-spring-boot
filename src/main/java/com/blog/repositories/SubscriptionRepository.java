package com.blog.repositories;

import com.blog.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	Optional<Subscription> findByEmail(String email); 
	
}
