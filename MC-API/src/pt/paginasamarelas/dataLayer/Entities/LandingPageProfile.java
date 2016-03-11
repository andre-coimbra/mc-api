package pt.paginasamarelas.dataLayer.Entities;

public class LandingPageProfile {
	
	private String shortName;
	private String phoneNumber;
	private String faxNumber;
	private String email;
	private Address address;
	//special structure (not sure)
	private String mapLatitudeLongitude;
	
	private String uniqueSellingPropositions;
	private String businessInfo;
	private String tagline;
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getMapLatitudeLongitude() {
		return mapLatitudeLongitude;
	}
	public void setMapLatitudeLongitude(String mapLatitudeLongitude) {
		this.mapLatitudeLongitude = mapLatitudeLongitude;
	}
	public String getUniqueSellingPropositions() {
		return uniqueSellingPropositions;
	}
	public void setUniqueSellingPropositions(String uniqueSellingPropositions) {
		this.uniqueSellingPropositions = uniqueSellingPropositions;
	}
	public String getBusinessInfo() {
		return businessInfo;
	}
	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}
	public String getTagline() {
		return tagline;
	}
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	
	
}
