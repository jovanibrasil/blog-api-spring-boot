package com.blog.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.exceptions.InvalidInformationException;
import com.blog.models.Subscription;
import com.blog.repositories.SubscriptionRepository;
import com.blog.services.SubscriptionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SubscriptionServiceTest {

	@MockBean
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private SubscriptionService subscriptionService;

	@Before
	public void setUp() {}
	
	@After
	public void tearDown() {}
	
	@Test
	public void testSaveValidSubscription() {
		Subscription subscription = new Subscription("test@gmail.com");
		BDDMockito.given(subscriptionRepository.findByEmail("test@gmail.com")).willReturn(Optional.empty());
		BDDMockito.given(subscriptionRepository.save(subscription)).willReturn(subscription);
		Optional<Subscription> optSubscription = this.subscriptionService
				.saveSubscription(subscription);
		assertTrue(optSubscription.isPresent());
	}
	
	@Test(expected = InvalidInformationException.class)
	public void testSaveSubscriptionEmailAlreadyExist() {
		BDDMockito.given(subscriptionRepository.findByEmail("test@gmail.com"))
			.willReturn(Optional.of(new Subscription("test@gmail.com")));
		this.subscriptionService.saveSubscription(new Subscription("test@gmail.com"));
	}
	
	@Test
	public void testFindSubscriptions() {
		BDDMockito.given(this.subscriptionRepository.findAll())
			.willReturn(Arrays.asList(new Subscription("test0@gmail.com"), new Subscription("test1@gmail.com")));
		List<Subscription> subscriptions = this.subscriptionService.findAllSubscriptions();
		assertEquals(2, subscriptions.size());
	}
	
	@Test
	public void testFindSubscriptionsEmptyList() {
		BDDMockito.given(this.subscriptionRepository.findAll()).willReturn(Arrays.asList());
		List<Subscription> subscriptions = this.subscriptionService.findAllSubscriptions();
		assertTrue(subscriptions.isEmpty());
	}
	
}
