package com.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.controllers.PostsController;
import com.blog.integrations.AuthClient;

/**
 * 
 * Validated the received requests.
 * 
 * @author jovani
 *
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
	private static final String AUTH_HEADER = "Authorization";
	
	@Autowired
	private AuthClient authClient;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader(AUTH_HEADER);
		if(token != null) {
			try {
				// Verify token with the authentication service
				TempUser tempUser = authClient.checkToken(token);
				if(tempUser != null) {
					UserDetails userDetails = new UserDetailsImpl(tempUser.getName(), tempUser.getRole()); 
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);		
				}else {
					log.info("User not found.");
					response.sendError(401);
				}	
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(401);
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
}
