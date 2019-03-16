package blog.dtos;

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
	public String getTitle() {
		return title;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	
	public List<String> getTags() {
		return tags;
	}

	public String getSummary() {
		return summary;
	}
	public String getUserName() {
		return userName;
	}
	
}
