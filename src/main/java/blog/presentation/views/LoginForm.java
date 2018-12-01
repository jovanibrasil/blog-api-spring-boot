package blog.presentation.views;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
	
	@Size(min=2, max=30, message="Nome de usuário deve ter entre 2 e 30 caracteres.")
	private String userName;
	
	@NotNull
	@Size(min=1, max=50)
	private String password;

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
