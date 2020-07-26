package com.blog.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtAuthenticationProvider authenticationProvider;
	
	public SecurityConfig(JwtAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}
	
	/**
	 * Filter used when the application intercepts a requests.
	 */
	public JwtAuthenticationFilter authenticationTokenFilterBean() {
		return new JwtAuthenticationFilter();
	}
	
	/**
	 * Configure request authorization
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/search","/posts/top", "/posts/summaries", 
					"/posts", "/posts/byuser/*").permitAll()
			.antMatchers(HttpMethod.POST, "/posts").hasRole("ADMIN")
			.antMatchers(HttpMethod.PATCH, "/posts/*/likes").permitAll()
			.antMatchers(HttpMethod.DELETE, "/posts/*").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/posts").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/subscriptions", "/feedbacks").hasRole("ADMIN")
			.antMatchers("/users").hasRole("SERVICE")
			.and()
			.addFilterAfter(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class) // set token verification filter
			.csrf().disable() // disable CSRF (cross-site request forgery) 
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // set session police stateless (no states)
		
	}
	
	/**
	 * Authentication configuration. Setup the custom authentication provider that works
	 * with JWT authentication using a remote service.
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
			
}

