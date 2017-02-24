package pt.paginasamarelas.dataLayer.hibernate.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExtAdvert {
	
	private BigDecimal advert_id;
	private BigDecimal ca0_nrid;
	private String advertLanguage;
	private String campaignUrl;
	private String displayUrl;
	private String heading_nrid;
	private String documentId;
	private Integer template;
	private Timestamp dmod;
	
	
	public BigDecimal getAdvert_id() {
		return advert_id;
	}
	public void setAdvert_id(BigDecimal advert_id) {
		this.advert_id = advert_id;
	}
	public BigDecimal getCa0_nrid() {
		return ca0_nrid;
	}
	public void setCa0_nrid(BigDecimal ca0_nrid) {
		this.ca0_nrid = ca0_nrid;
	}
	public String getAdvertLanguage() {
		return advertLanguage;
	}
	public void setAdvertLanguage(String advertLanguage) {
		this.advertLanguage = advertLanguage;
	}
	public String getCampaignUrl() {
		return campaignUrl;
	}
	public void setCampaignUrl(String campaignUrl) {
		this.campaignUrl = campaignUrl;
	}
	public String getDisplayUrl() {
		return displayUrl;
	}
	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}
	public String getHeading_nrid() {
		return heading_nrid;
	}
	public void setHeading_nrid(String heading_nrid) {
		this.heading_nrid = heading_nrid;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public Integer getTemplate() {
		return template;
	}
	public void setTemplate(Integer template) {
		this.template = template;
	}
	public Timestamp getDmod() {
		return dmod;
	}
	public void setDmod(Timestamp dmod) {
		this.dmod = dmod;
	}	
	

}
