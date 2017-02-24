package pt.paginasamarelas.dataLayer.hibernate.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExtGeoloc {
	
	private String geoloc_name;
	private String radiusPostalCode;
	private BigDecimal ca0_nrid;
	private BigDecimal radius;
	private BigDecimal heading_nrid;
	private Integer template;
	private Timestamp dmod;
	
	
	
	public BigDecimal getCa0_nrid() {
		return ca0_nrid;
	}
	public void setCa0_nrid(BigDecimal ca0_nrid) {
		this.ca0_nrid = ca0_nrid;
	}
	public String getGeoloc_name() {
		return geoloc_name;
	}
	public void setGeoloc_name(String geolocName) {
		this.geoloc_name = geolocName;
	}
	public String getRadiusPostalCode() {
		return radiusPostalCode;
	}
	public void setRadiusPostalCode(String radiusPostalCode) {
		this.radiusPostalCode = radiusPostalCode;
	}
	public BigDecimal getRadius() {
		return radius;
	}
	public void setRadius(BigDecimal radius) {
		this.radius = radius;
	}
	public BigDecimal getHeading_nrid() {
		return heading_nrid;
	}
	public void setHeading_nrid(BigDecimal headingNrid) {
		this.heading_nrid = headingNrid;
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
