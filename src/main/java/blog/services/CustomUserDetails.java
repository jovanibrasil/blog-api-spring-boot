package blog.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

	private static final long serialVersionUID = 1L;

	private String userName;
	
	public CustomUserDetails(String  userName, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userName = userName;
	}

	public String getUserId() {
		return userName;
	}

	public void setUserId(String userName) {
		this.userName = userName;
	}	
	
}
