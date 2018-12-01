package blog.presentation.views;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditPostForm {

	@NotNull
	@Size(min=2, max=10, message="Título do Post deve ter entre 2 e 10 caracteres.")
	private String title;
	@NotNull
	@Size(min=2, max=1000, message="Corpo do post deve ter entre 2 e 1000 caracteres.")
	private String body;
	
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
