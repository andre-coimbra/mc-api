package pt.paginasamarelas.dataLayer.entities;


public class Advertiser {
	
	private AdvertiserID advertiserId;
	private String name;
	private String userStatus;
	private String googleProvisioningId;
	private Contact contact;
	private BusinessAddress businessAddress;
	private String businessPhone;
	private TargetDemographics targetDemographics;
	private boolean useReverseProxy;
	private boolean includeGoogleAnalyticsTags;
	private CallTracking callTracking;
	private String[] searchEngines;
	private String[] devices;
	private String notes;
	private String campaignManagerEmail;
	private ManagementHierarchyRef managementHierarchyRef;
	private CustomAttributes[] customAttributes;
	private Budget[] budgets;
	private Location[] locations;
	
	
	public AdvertiserID getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(AdvertiserID advertiserId) {
		this.advertiserId = advertiserId;
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
	public String getGoogleProvisioningId() {
		return googleProvisioningId;
	}
	public void setGoogleProvisioningId(String googleProvisioningId) {
		this.googleProvisioningId = googleProvisioningId;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public BusinessAddress getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(BusinessAddress businessAddress) {
		this.businessAddress = businessAddress;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public TargetDemographics getTargetDemographics() {
		return targetDemographics;
	}
	public void setTargetDemographics(TargetDemographics targetDemographics) {
		this.targetDemographics = targetDemographics;
	}
	public boolean isUseReverseProxy() {
		return useReverseProxy;
	}
	public void setUseReverseProxy(boolean useReverseProxy) {
		this.useReverseProxy = useReverseProxy;
	}
	public boolean isIncludeGoogleAnalyticsTags() {
		return includeGoogleAnalyticsTags;
	}
	public void setIncludeGoogleAnalyticsTags(boolean includeGoogleAnalyticsTags) {
		this.includeGoogleAnalyticsTags = includeGoogleAnalyticsTags;
	}
	public CallTracking getCallTracking() {
		return callTracking;
	}
	public void setCallTracking(CallTracking callTracking) {
		this.callTracking = callTracking;
	}

	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCampaignManagerEmail() {
		return campaignManagerEmail;
	}
	public void setCampaignManagerEmail(String campaignManagerEmail) {
		this.campaignManagerEmail = campaignManagerEmail;
	}
	public ManagementHierarchyRef getManagementHierarchyRef() {
		return managementHierarchyRef;
	}
	public void setManagementHierarchyRef(
			ManagementHierarchyRef managementHierarchyRef) {
		this.managementHierarchyRef = managementHierarchyRef;
	}
	public String[] getSearchEngines() {
		return searchEngines;
	}
	public void setSearchEngines(String[] searchEngines) {
		this.searchEngines = searchEngines;
	}
	public String[] getDevices() {
		return devices;
	}
	public void setDevices(String[] devices) {
		this.devices = devices;
	}
	public CustomAttributes[] getCustomAttributes() {
		return customAttributes;
	}
	public void setCustomAttributes(CustomAttributes[] customAttributes) {
		this.customAttributes = customAttributes;
	}
	
	public Budget[] getBudgets() {
		return budgets;
	}
	public void setBudgets(Budget[] budgets) {
		this.budgets = budgets;
	}
	public Location[] getLocations() {
		return locations;
	}
	public void setLocations(Location[] locations) {
		this.locations = locations;
	}
	
	
	
	
	
	
	
	
}
