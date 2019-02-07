package blog.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostDTO {

	@NotNull
	private Long id;
	
	@NotNull(message="Title must not be null")
	@Size(min=2, max=10, message="Título do Post deve ter entre 2 e 10 caracteres.")
	private String title;
	
	private Date lastUpdateDate;
	
	@NotNull(message="Summary must not be null")
	@Size(min=2, max=1000, message="Sumário deve ter entre 2 e 1000 caracteres.")
	private String summary;
	
	@Size(min=2, max=1000, message="Corpo do post deve ter entre 2 e 1000 caracteres.")
	private String body;
	
	@NotNull(message="UserId must not be null")
	private Long userId;
	
	public PostDTO() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "PostDTO [postId=" + id + ", title=" + title + ", body=" + body + ", UserId=" + userId
				+ ", lastUpdateDate=" + lastUpdateDate + "]";
	}
	
}
