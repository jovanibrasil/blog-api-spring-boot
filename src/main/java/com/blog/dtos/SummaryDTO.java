package com.blog.dtos;

import java.util.Date;
import java.util.List;

public class SummaryDTO {

	private Long id;
	private String title;
	private Date creationDate;
	private Date lastUpdateDate;
	private String summary;
	private String userName;
	private List<String> tags;
	
	public SummaryDTO() {}
	
	public SummaryDTO(Long id, String title, Date creationDate, Date lastUpdateDate, 
			String summary, String userName, List<String> tags) {
		super();
		this.id = id;
		this.title = title;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.summary = summary;
		this.userName = userName;
		this.tags = tags;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
