package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.models.Post;
import com.blog.models.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	Optional<Subscription> findByEmail(String email); 
	
}
