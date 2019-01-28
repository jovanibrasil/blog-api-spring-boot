package blog.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import blog.presentation.models.User;

public class PostDTO {

	@NotNull
	private Long postId;
	@NotNull
	@Size(min=2, max=10, message="Título do Post deve ter entre 2 e 10 caracteres.")
	private String title;
	@NotNull
	@Size(min=2, max=1000, message="Corpo do post deve ter entre 2 e 1000 caracteres.")
	private String body;
	@NotNull
	private Long userId;
	private Date lastUpdateDate;
	
	public PostDTO() {}
	
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
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

	@Override
	public String toString() {
		return "PostDTO [postId=" + postId + ", title=" + title + ", body=" + body + ", UserId=" + userId
				+ ", lastUpdateDate=" + lastUpdateDate + "]";
	}
	
}
