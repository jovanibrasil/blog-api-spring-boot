package com.blog.repositories;

import com.blog.models.Subscription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SubscriptionRepositoryTest {

	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Test
	public void testSubscribe() {
		Subscription subscription = new Subscription("test@gmail.com");
		subscription = subscriptionRepository.save(subscription);
		assertNotNull(subscription.getId());
	}
	
	@Test
	public void testFindSubscription() {
		Subscription subscription = new Subscription("test@gmail.com");
		subscription = subscriptionRepository.save(subscription);
		Optional<Subscription> savedSubscription = subscriptionRepository.findById(subscription.getId());
		assertTrue(savedSubscription.isPresent());
		assertEquals("test@gmail.com", savedSubscription.get().getEmail());
		assertEquals(subscription.getId(), savedSubscription.get().getId());
	}
	
	@Test
	public void testFindSubscriptionByEmail() {
		Subscription subscription = new Subscription("test@gmail.com");
		subscription = subscriptionRepository.save(subscription);
		Optional<Subscription> savedSubscription = subscriptionRepository.findByEmail(subscription.getEmail());
		assertTrue(savedSubscription.isPresent());
		assertEquals("test@gmail.com", savedSubscription.get().getEmail());
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testSubscribeEmailAlreadyExist() {
		Subscription subscription1 = new Subscription("test@gmail.com");
		subscription1 = subscriptionRepository.save(subscription1);
		Subscription subscription2 = new Subscription("test@gmail.com");
		subscription2 = subscriptionRepository.save(subscription2);
		assertNull(subscription2.getId());
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testSubscribeInvalidEmail() {
		Subscription subscription1 = new Subscription(null);
		subscriptionRepository.save(subscription1);
	}
	
	@Test
	public void testSubscriptionDate() {
		Subscription subscription1 = new Subscription("test@gmail.com");
		subscriptionRepository.save(subscription1);
		Long subscriptionTime = Math.abs(subscription1.getSubscriptionDate()
				.atZone(ZoneId.systemDefault())
				.toInstant()
				.toEpochMilli());
		assertTrue((Calendar.getInstance().getTime().getTime() - subscriptionTime) > 0);
	}
	
}
