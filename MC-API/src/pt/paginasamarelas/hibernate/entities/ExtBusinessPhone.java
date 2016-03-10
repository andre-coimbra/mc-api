package pt.paginasamarelas.hibernate.entities;

import java.math.BigDecimal;

public class ExtBusinessPhone {
	
	private BigDecimal caNrid;
	private String phoneNumber;
	
	
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

}
