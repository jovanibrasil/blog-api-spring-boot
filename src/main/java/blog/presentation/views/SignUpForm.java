package blog.presentation.views;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpForm {
	
	@Size(min=2, max=30, message="Nome de usuário deve ter entre 2 e 20 caracteres.")
	private String userName;
	
	@Size(min=2, max=30, message="Nome de usuário deve ter entre 2 e 40 caracteres.")
	private String fullUserName;
	
	public String getFullUserName() {
		return fullUserName;
	}

	public void setFullUserName(String fullUserName) {
		this.fullUserName = fullUserName;
	}

	@NotNull
	@Size(min=1, max=50)
	private String password;

	@NotNull
	@Size(min=1, max=50)
	private String passwordConfirmation;
	
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

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
