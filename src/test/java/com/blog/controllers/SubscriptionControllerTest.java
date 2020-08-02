package com.blog.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.blog.model.Subscription;
import com.blog.model.dto.UserDetailsDTO;
import com.blog.model.enums.ProfileTypeEnum;
import com.blog.services.SubscriptionService;
import com.blog.services.impl.AuthServiceJwtImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SubscriptionControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SubscriptionService subscriptionService;
	
	@MockBean
	private AuthServiceJwtImpl authClient;
		
	private Pageable page = PageRequest.of(0, 1);
	
	@Before
	public void setUp() {
		when(authClient.checkToken(Mockito.anyString()))
			.thenReturn(new UserDetailsDTO("jovanibrasil", ProfileTypeEnum.ROLE_ADMIN));
	}
	
	@Test
	public void testFindAllSubscriptions() throws Exception {
		Subscription subscription0 = new Subscription(1L, "test0@gmail.com", LocalDateTime.now());
		Subscription subscription1 = new Subscription(2L, "test1@gmail.com", LocalDateTime.now());
				
		when(subscriptionService.findAllSubscriptions(any(Pageable.class)))
			.thenReturn(new PageImpl<>(Arrays.asList(subscription0, subscription1)));
		mvc.perform(MockMvcRequestBuilders.get("/subscriptions?page=0&size=1")
			.accept(MediaType.APPLICATION_JSON)
			.header("Authorization", "x.x.x.x"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].id", equalTo(1)))
			.andExpect(jsonPath("$.content[0].email", equalTo("test0@gmail.com")));
	}
	
	@Test
	public void testFindAllSubscriptionsEmptyList() throws Exception {
		when(subscriptionService.findAllSubscriptions(page)).thenReturn(new PageImpl<>(Arrays.asList()));
		mvc.perform(MockMvcRequestBuilders.get("/subscriptions")
			.accept(MediaType.APPLICATION_JSON)
			.header("Authorization", "x.x.x.x"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testSubscribe() throws Exception {
		Subscription subscription = new Subscription(1L, "test0@gmail.com", LocalDateTime.now());
		when(subscriptionService.saveSubscription(Mockito.any())).thenReturn(subscription);
		mvc.perform(MockMvcRequestBuilders.post("/subscriptions/test0@gmail.com")
			.contentType(MediaType.APPLICATION_JSON))			
			.andExpect(status().isCreated());
	}
	
}
