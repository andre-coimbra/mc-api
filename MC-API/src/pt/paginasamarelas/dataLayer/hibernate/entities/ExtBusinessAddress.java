package pt.paginasamarelas.dataLayer.hibernate.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExtBusinessAddress {
	
	private BigDecimal caNrid;
	private String line1;
	private String line2;
	private String postalCode;
	private String city;
	private String region;
//	private String countryCode;
	private Integer template;
	private Timestamp dmod;
	
	public BigDecimal getCaNrid() {
		return caNrid;
	}
	public void setCaNrid(BigDecimal caNrid) {
		this.caNrid = caNrid;
	}
	
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
/*	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
*/	

}
