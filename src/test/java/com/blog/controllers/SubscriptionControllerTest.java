package com.blog.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.blog.models.Subscription;
import com.blog.services.SubscriptionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SubscriptionControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SubscriptionService subscriptionService;
	
	@Test
	public void testFindAllSubscriptions() throws Exception {
		Subscription subscription0 = new Subscription(1L, "test0@gmail.com", LocalDateTime.now());
		Subscription subscription1 = new Subscription(2L, "test1@gmail.com", LocalDateTime.now());
				
		BDDMockito.given(this.subscriptionService.findAllSubscriptions())
			.willReturn(Arrays.asList(subscription0, subscription1));
		mvc.perform(MockMvcRequestBuilders.get("/subscriptions")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.errors").isEmpty())
			.andExpect(jsonPath("$.data").isNotEmpty())
			.andExpect(jsonPath("$.data[0].id", equalTo(1)))
			.andExpect(jsonPath("$.data[0].email", equalTo("test0@gmail.com")));
	}
	
	@Test
	public void testFindAllSubscriptionsEmptyList() throws Exception {
		BDDMockito.given(this.subscriptionService.findAllSubscriptions()).willReturn(Arrays.asList());
		mvc.perform(MockMvcRequestBuilders.get("/subscriptions")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.errors").isEmpty())
			.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testSubscribe() throws Exception {
		Subscription subscription = new Subscription(1L, "test0@gmail.com", LocalDateTime.now());
		BDDMockito.given(this.subscriptionService.saveSubscription(Mockito.any()))
			.willReturn(Optional.of(subscription));
		mvc.perform(MockMvcRequestBuilders.post("/subscriptions/test0@gmail.com")
				.contentType(MediaType.APPLICATION_JSON))			
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
//	@Test
//	public void testSubscribeNullEmail() throws Exception {
//		mvc.perform(MockMvcRequestBuilders.post("/subscriptions/null")
//				.contentType(MediaType.APPLICATION_JSON))			
//				.andExpect(status().isBadRequest())
//				.andExpect(jsonPath("$.data").isEmpty())
//				.andExpect(jsonPath("$.errors").isNotEmpty());
//	}
//	
//	@Test
//	public void testSubscribeInvalidEmail() throws Exception {
//		mvc.perform(MockMvcRequestBuilders.post("/subscriptions/testgmailcom")
//				.contentType(MediaType.APPLICATION_JSON))			
//				.andExpect(status().isBadRequest())
//				.andExpect(jsonPath("$.data").isEmpty())
//				.andExpect(jsonPath("$.errors").isNotEmpty());
//	}
	
}
