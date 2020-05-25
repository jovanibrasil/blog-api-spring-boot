package com.blog.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * Validated the received requests.
 * 
 * @author Jovani Brasil
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private static final String AUTH_HEADER = "Authorization";
		
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader(AUTH_HEADER);
		if(token != null) {
			try {
				JwtAuthentication auth = new JwtAuthentication(token); 
				SecurityContextHolder.getContext().setAuthentication(auth);	
			} catch (Exception e) {
				log.error("User not found. Error: {}", e.getMessage());
				response.sendError(401);
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
}
