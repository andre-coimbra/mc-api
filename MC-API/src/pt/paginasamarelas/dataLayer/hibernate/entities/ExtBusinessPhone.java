package pt.paginasamarelas.dataLayer.hibernate.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExtBusinessPhone {
	
	private BigDecimal caNrid;
	private String phoneNumber;
	private Integer template;
	private Timestamp dmod;
		
	public BigDecimal getCaNrid() {
		return caNrid;
	}
	public void setCaNrid(BigDecimal caNrid) {
		this.caNrid = caNrid;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
