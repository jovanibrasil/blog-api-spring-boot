package blog.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FeedbackDTO {
	
	@NotNull
	@Size(min=2, max=15, message="Título do feedback deve ter entre 2 e 15 caracteres.")
	private String title;
	@NotNull
	@Size(min=2, max=1000, message="Conteudo do feedback deve ter entre 2 e 1000 caracteres.")
	private String content;
	
	public FeedbackDTO() {}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}
