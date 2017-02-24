package pt.paginasamarelas.dataLayer.hibernate.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExtSitelink {
	
	private BigDecimal ca0_nrid;
	private BigDecimal nrid;
	private String sl_name;
	private String requested_url;
	private Integer template;
	private BigDecimal heading_nrid;
	private Timestamp dmod;	
	
	public BigDecimal getCa0_nrid() {
		return ca0_nrid;
	}
	public void setCa0_nrid(BigDecimal ca0_nrid) {
		this.ca0_nrid = ca0_nrid;
	}
	public BigDecimal getNrid() {
		return nrid;
	}
	public void setNrid(BigDecimal nrid) {
		this.nrid = nrid;
	}
	public String getSl_name() {
		return sl_name;
	}
	public void setSl_name(String sl_name) {
		this.sl_name = sl_name;
	}
	public String getRequested_url() {
		return requested_url;
	}
	public void setRequested_url(String requested_url) {
		this.requested_url = requested_url;
	}
	public Integer getTemplate() {
		return template;
	}
	public void setTemplate(Integer template) {
		this.template = template;
	}
	public BigDecimal getHeading_nrid() {
		return heading_nrid;
	}
	public void setHeading_nrid(BigDecimal heading_nrid) {
		this.heading_nrid = heading_nrid;
	}
	public Timestamp getDmod() {
		return dmod;
	}
	public void setDmod(Timestamp dmod) {
		this.dmod = dmod;
	}	
	
}
