package pt.paginasamarelas.dataLayer.entities;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Operand {
	private Advertiser advertiser;
	private AdvertiserID advertiserId;
	private GeographicTarget geographicTarget;
	private boolean includeAdcopy;
	private boolean includeKeyphrases;
	
	public Advertiser getAdvertiser() {
		return advertiser;
	}
	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}
	public AdvertiserID getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(AdvertiserID advertiserId) {
		this.advertiserId = advertiserId;
	}
	public GeographicTarget getGeographicTarget() {
		return geographicTarget;
	}
	public void setGeographicTarget(GeographicTarget geographicTarget) {
		this.geographicTarget = geographicTarget;
	}
	public boolean getIncludeAdcopy() {
		return includeAdcopy;
	}
	public void setIncludeAdcopy(boolean pIncludeAdcopy) {
		this.includeAdcopy = pIncludeAdcopy;
	}
	public boolean getIncludeKeyphrases() {
		return includeKeyphrases;
	}
	public void setIncludeKeyphrases(boolean pIncludeKeyphrases) {
		this.includeKeyphrases = pIncludeKeyphrases;
	}
}
