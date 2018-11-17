package blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		// The user service is going to contain the code that check if the user is present in the db ot not
		// The authenticationProvider manages the service 
		authenticationProvider.setUserDetailsService(userDetailsService); 
		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		// authenticationProvider is managed by AuthenticationManagerBuilder
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// cross-site request forgery
		http.csrf()
			.disable()
			.exceptionHandling()
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/users/login"))
			.and()
			.authorizeRequests()
			.and()
			.authorizeRequests()
			//.antMatchers("/", "/users/logout", "/users/login", "/posts/view/**")
			.antMatchers("/", "/users/login", "/users/signup", "/users/logout")
			.permitAll()
			.antMatchers("/users/*", "/posts/view/**", "/posts/management/**")
			.fullyAuthenticated();
//			.and()
//			.logout()
//			.logoutUrl("/users/logout")
//			.invalidateHttpSession(true)
//			.deleteCookies("JSESSIONID");
	}
	
	@Bean // Insert DaoAuthenticationProvider in the context
	public DaoAuthenticationProvider createDaoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
}
