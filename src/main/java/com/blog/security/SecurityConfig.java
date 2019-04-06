package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
			
	/*
	 * Filter used when the application intercepts a requests.
	 */
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}
	
	/**
	 * Configure request authorization
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
				
		http
			.csrf().disable() // disable csrf (cross-site request forgery) 
			.cors()
			.and()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler) // set authentication error
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // set session police stateless
			.and()
			.authorizeRequests()
			.antMatchers("/users/login", "/users/signup", "/users/logout", "/search", "/subscription",
					 "/posts/top/**", "/posts/summary/**", "/posts/post/**").permitAll()
			.antMatchers("/posts/delete/*", "/posts/create", "/posts/update", "/posts/list/**").hasRole("ADMIN");
		
			//http.addFilterBefore(new LoginFilter("/users/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class);
			http.addFilterBefore(authenticationTokenFilterBean(),  BasicAuthenticationFilter.class); // set filter
					        
			http.headers().cacheControl();
		
	}
	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	/**
	 * The password encoder is used to hash the passwords.  in this case a BCryptPasswordEncoder.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

