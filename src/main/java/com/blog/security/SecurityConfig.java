package com.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blog.services.impl.JwtAuthenticationProvider;

//@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	
	public SecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider) {
		this.jwtAuthenticationProvider = jwtAuthenticationProvider;
	}
	
	/**
	 * Filter used when the application intercepts a requests.
	 */
	public JwtAuthenticationFilter authenticationTokenFilterBean() {
		return new JwtAuthenticationFilter(jwtAuthenticationProvider);
	}
	
	/**
	 * Configure request authorization
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
				
		http.authorizeRequests()
			.antMatchers("/search", "/subscriptions", "/posts/top", "/posts/summaries", 
					"/posts/*", "/feedbacks", "/posts/byuser/*").permitAll()
			.antMatchers(HttpMethod.DELETE, "/posts/*").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/posts").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/posts").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/subscriptions").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/feedbacks").hasRole("ADMIN")
			.antMatchers("/users").hasRole("SERVICE")
			.and()
			.csrf().disable() // disable CSRF (cross-site request forgery) 
			.httpBasic().disable()		
			.formLogin().disable()
			.addFilterAfter(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class) // set token verification filter
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // set session police stateless
		
	}
	
}

