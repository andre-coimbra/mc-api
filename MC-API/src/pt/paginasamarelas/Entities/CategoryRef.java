package pt.paginasamarelas.Entities;



public class CategoryRef {
	private String externalId;
	private String name;
	private CustomAdCopy[] adcopies;
	private CustomKeyphrase[] keyphrases;
	private MediaObject images;
	
	
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
	public CustomKeyphrase[] getKeyphrases() {
		return keyphrases;
	}
	public void setKeyphrases(CustomKeyphrase[] keyphrases) {
		this.keyphrases = keyphrases;
	}
	public MediaObject getImages() {
		return images;
	}
	public void setImages(MediaObject images) {
		this.images = images;
	}
	public CustomAdCopy[] getAdcopies() {
		return adcopies;
	}
	public void setAdcopies(CustomAdCopy[] adcopies) {
		this.adcopies = adcopies;
	}
	
}
