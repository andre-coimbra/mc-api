package pt.paginasamarelas.dataLayer.Entities;



public class Location {
	
	private LocationId locationId;
	private String name;
	private String userStatus;
	private String url;
	private String requestedDisplayUrl;
	private String[] languages;
	private float googleMobileBidFactor;
	private CategoryRef[] categoryRefs;
	private GeographicTarget geographicTarget;
	private GeoKeywordTarget geoKeywordTarget;
	private Sitelink[] sitelinks;
	private AdSchedule adSchedule;
	private boolean useProfilePage;
	private LandingPageProfile landingPageProfile;
	
	
	public LocationId getLocationId() {
		return locationId;
	}
	public void setLocationId(LocationId locationId) {
		this.locationId = locationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRequestedDisplayUrl() {
		return requestedDisplayUrl;
	}
	public void setRequestedDisplayUrl(String requestedDisplayUrl) {
		this.requestedDisplayUrl = requestedDisplayUrl;
	}
	public String[] getLanguages() {
		return languages;
	}
	public void setLanguages(String[] languages) {
		this.languages = languages;
	}
	public float getGoogleMobileBidFactor() {
		return googleMobileBidFactor;
	}
	public void setGoogleMobileBidFactor(float googleMobileBidFactor) {
		this.googleMobileBidFactor = googleMobileBidFactor;
	}
	public CategoryRef[] getCategoryRefs() {
		return categoryRefs;
	}
	public void setCategoryRefs(CategoryRef[] categoryRefs) {
		this.categoryRefs = categoryRefs;
	}
	public GeographicTarget getGeographicTarget() {
		return geographicTarget;
	}
	public void setGeographicTarget(GeographicTarget geographicTarget) {
		this.geographicTarget = geographicTarget;
	}
	public GeoKeywordTarget getGeoKeywordTarget() {
		return geoKeywordTarget;
	}
	public void setGeoKeywordTarget(GeoKeywordTarget geoKeywordTarget) {
		this.geoKeywordTarget = geoKeywordTarget;
	}

	public AdSchedule getAdSchedule() {
		return adSchedule;
	}
	public void setAdSchedule(AdSchedule adSchedule) {
		this.adSchedule = adSchedule;
	}
	public boolean isUseProfilePage() {
		return useProfilePage;
	}
	public void setUseProfilePage(boolean useProfilePage) {
		this.useProfilePage = useProfilePage;
	}
	public LandingPageProfile getLandingPageProfile() {
		return landingPageProfile;
	}
	public void setLandingPageProfile(LandingPageProfile landingPageProfile) {
		this.landingPageProfile = landingPageProfile;
	}
	public Sitelink[] getSitelinks() {
		return sitelinks;
	}
	public void setSitelinks(Sitelink[] sitelinks) {
		this.sitelinks = sitelinks;
	}
	
}
