package blog.dtos;

import java.util.Date;

public class SummaryDTO {

	private Long id;
	private String title;
	private Date date;
	private String summary;
	private String userName;
	
	public SummaryDTO(Long id, String title, Date date, String summary, String userName) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.summary = summary;
		this.userName = userName;
	}
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public Date getDate() {
		return date;
	}
	public String getSummary() {
		return summary;
	}
	public String getUserName() {
		return userName;
	}
	
}
