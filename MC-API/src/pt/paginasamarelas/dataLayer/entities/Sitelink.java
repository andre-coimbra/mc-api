package pt.paginasamarelas.dataLayer.entities;

public class Sitelink {
	
	private String name;
	private String requested_url;
	private String userStatus;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequestedUrl() {
		return requested_url;
	}
	public void setRequestedUrl(String requestedUrl) {
		this.requested_url = requestedUrl;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}
