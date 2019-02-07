package blog.dtos;

public class UserDTO {
	
	// basic user data
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    // network references
    private String gitHubUserName;
    private String linkedInUserName;
    private String googleScholarLink;
    private String lattesLink;
    private Long id;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGitHubUserName() {
		return gitHubUserName;
	}
	public void setGitHubUserName(String gitHubUserName) {
		this.gitHubUserName = gitHubUserName;
	}
	public String getLinkedInUserName() {
		return linkedInUserName;
	}
	public void setLinkedInUserName(String linkedInUserName) {
		this.linkedInUserName = linkedInUserName;
	}
	public String getGoogleScholarLink() {
		return googleScholarLink;
	}
	public void setGoogleScholarLink(String googleScholarLink) {
		this.googleScholarLink = googleScholarLink;
	}
	public String getLattesLink() {
		return lattesLink;
	}
	public void setLattesLink(String lattesLink) {
		this.lattesLink = lattesLink;
	}
    
}
