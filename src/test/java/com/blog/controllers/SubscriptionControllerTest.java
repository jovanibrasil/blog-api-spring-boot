package com.blog.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	private Pageable page = PageRequest.of(0, 1);
	
	@Test
	public void testFindAllSubscriptions() throws Exception {
		Subscription subscription0 = new Subscription(1L, "test0@gmail.com", LocalDateTime.now());
		Subscription subscription1 = new Subscription(2L, "test1@gmail.com", LocalDateTime.now());
				
		BDDMockito.given(subscriptionService.findAllSubscriptions(any(Pageable.class)))
			.willReturn(new PageImpl<>(Arrays.asList(subscription0, subscription1)));
		mvc.perform(MockMvcRequestBuilders.get("/subscriptions?page=0&size=1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].id", equalTo(1)))
			.andExpect(jsonPath("$.content[0].email", equalTo("test0@gmail.com")));
	}
	
	@Test
	public void testFindAllSubscriptionsEmptyList() throws Exception {
		BDDMockito.given(this.subscriptionService.findAllSubscriptions(page))
			.willReturn(new PageImpl<>(Arrays.asList()));
		mvc.perform(MockMvcRequestBuilders.get("/subscriptions")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testSubscribe() throws Exception {
		Subscription subscription = new Subscription(1L, "test0@gmail.com", LocalDateTime.now());
		BDDMockito.given(this.subscriptionService.saveSubscription(Mockito.any()))
			.willReturn(subscription);
		mvc.perform(MockMvcRequestBuilders.post("/subscriptions/test0@gmail.com")
				.contentType(MediaType.APPLICATION_JSON))			
				.andExpect(status().isCreated());
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
