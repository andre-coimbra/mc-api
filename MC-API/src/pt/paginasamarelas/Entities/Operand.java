package pt.paginasamarelas.Entities;

public class Operand {
	private Advertiser advertiser;
	private AdvertiserID advertiserId;
	
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
}
