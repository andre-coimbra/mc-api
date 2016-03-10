package pt.paginasamarelas.hibernate.entities;

import java.math.BigDecimal;

public class ExtLinkedAdcopy {
	
	private BigDecimal nrid;
	private BigDecimal ca0_nrid;
	private BigDecimal adcopy_nrid;
	private BigDecimal adgroup_nrid;
	
	
	public BigDecimal getNrid() {
		return nrid;
	}
	public void setNrid(BigDecimal nrid) {
		this.nrid = nrid;
	}
	public BigDecimal getCa0_nrid() {
		return ca0_nrid;
	}
	public void setCa0_nrid(BigDecimal ca0_nrid) {
		this.ca0_nrid = ca0_nrid;
	}
	public BigDecimal getAdcopy_nrid() {
		return adcopy_nrid;
	}
	public void setAdcopy_nrid(BigDecimal adcopy_nrid) {
		this.adcopy_nrid = adcopy_nrid;
	}
	public BigDecimal getAdgroup_nrid() {
		return adgroup_nrid;
	}
	public void setAdgroup_nrid(BigDecimal adgroup_nrid) {
		this.adgroup_nrid = adgroup_nrid;
	}

}
