package pt.paginasamarelas.dataLayer.entities;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)

public class CategoryRef {
	private String externalId;
	private String name;
// FM 13.02.2017
	private String userStatus;
	private CustomAdCopy[] adcopies;
	private CustomKeyphrase[] keyphrases;
	private MediaObject[] images;
	private CustomAdCopy[] customAdcopies;
	private CustomKeyphrase[] customKeyphrases;	
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
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
	public void setUserStatus(String pUserStatus) {
		this.userStatus = pUserStatus;
	}	
	public CustomKeyphrase[] getKeyphrases() {
		return keyphrases;
	}
	public void setKeyphrases(CustomKeyphrase[] keyphrases) {
		this.keyphrases = keyphrases;
	}
	public MediaObject[] getImages() {
		return images;
	}
	public void setImages(MediaObject[] images) {
		this.images = images;
	}
	public CustomAdCopy[] getAdcopies() {
		return adcopies;
	}
	public void setAdcopies(CustomAdCopy[] adcopies) {
		this.adcopies = adcopies;
	}
	public CustomAdCopy[] getCustomAdcopies() {
		return customAdcopies;
	}
	public void setCustomAdcopies(CustomAdCopy[] customAdcopies) {
		this.customAdcopies = customAdcopies;
	}
	public CustomKeyphrase[] getCustomKeyphrases() {
		return customKeyphrases;
	}
	public void setCustomKeyphrases(CustomKeyphrase[] customKeyphrases) {
		this.customKeyphrases = customKeyphrases;
	}
	
}
