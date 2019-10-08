package com.blog.dtos;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostDTO {

	@NotNull
	private Long id;
	
	@NotNull(message="Title must not be null")
	@Size(min=2, max=50, message="Título do Post deve ter entre 2 e 10 caracteres.")
	private String title;
	
	private Date creationDate;
	private Date lastUpdateDate;
	
	@NotNull(message="Summary must not be null")
	@Size(min=2, max=5000, message="Sumário deve ter entre 2 e 1000 caracteres.")
	private String summary;
	
	@Size(min=2, max=30000, message="Corpo do post deve ter entre 2 e 1000 caracteres.")
	private String body;
	
	@NotNull
	private String userName;
	
	@NotNull(message="Tags must not be null")
	private List<String> tags;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userId) {
		this.userName = userId;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", title=" + title + ", creationDate=" + creationDate + ", lastUpdateDate="
				+ lastUpdateDate + ", summary=" + summary + ", body=" + body + ", userName="
				+ userName + ", tags=" + tags + "]";
	}
	
}
