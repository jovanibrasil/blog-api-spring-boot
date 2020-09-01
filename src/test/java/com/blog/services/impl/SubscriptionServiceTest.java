package com.blog.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.exception.InvalidInformationException;
import com.blog.model.Subscription;
import com.blog.model.dto.SubscriptionForm;
import com.blog.repositories.SubscriptionRepository;
import com.blog.services.SubscriptionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SubscriptionServiceTest {

	@Autowired
	private SubscriptionService subscriptionService;
	
	@MockBean
	private SubscriptionRepository subscriptionRepository;
	
	private Pageable page = PageRequest.of(0, 1);

	@Before
	public void setUp() {}
	
	@After
	public void tearDown() {}
	
	@Test
	public void testSaveValidSubscription() {
		Subscription subscription = new Subscription("test@gmail.com");
		BDDMockito.given(subscriptionRepository.findByEmail("test@gmail.com")).willReturn(Optional.empty());
		BDDMockito.given(subscriptionRepository.save(any())).willReturn(subscription);
		subscription = subscriptionService
				.saveSubscription(new SubscriptionForm("test@gmail.com"));
		assertNotNull(subscription);
	}
	
	@Test(expected = InvalidInformationException.class)
	public void testSaveSubscriptionEmailAlreadyExist() {
		BDDMockito.given(subscriptionRepository.findByEmail("test@gmail.com"))
			.willReturn(Optional.of(new Subscription("test@gmail.com")));
		subscriptionService.saveSubscription(new SubscriptionForm("test@gmail.com"));
	}
	
	@Test
	public void testFindSubscriptions() {
		BDDMockito.given(subscriptionRepository.findAll(any(Pageable.class)))
			.willReturn(new PageImpl<Subscription>(
					Arrays.asList(new Subscription("test0@gmail.com"), 
							new Subscription("test1@gmail.com"))));
		Page<Subscription> subscriptions = subscriptionService.findAllSubscriptions(page);
		assertEquals(2, subscriptions.getContent().size());
	}
	
	@Test
	public void testFindSubscriptionsEmptyList() {
		BDDMockito.given(subscriptionRepository.findAll(any(Pageable.class)))
			.willReturn(new PageImpl<>(Arrays.asList()));
		Page<Subscription> subscriptions = subscriptionService.findAllSubscriptions(page);
		assertTrue(subscriptions.isEmpty());
	}
	
}
