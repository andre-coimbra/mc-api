package pt.paginasamarelas.hibernate.entities;

import java.math.BigDecimal;

public class ExtLinkedKeyword {
	
	private BigDecimal nrid;
	private BigDecimal keyword_nrid;
	private BigDecimal adgroup_nrid;
	
	public BigDecimal getNrid() {
		return nrid;
	}
	public void setNrid(BigDecimal nrid) {
		this.nrid = nrid;
	}
	public BigDecimal getKeyword_nrid() {
		return keyword_nrid;
	}
	public void setKeyword_nrid(BigDecimal keyword_nrid) {
		this.keyword_nrid = keyword_nrid;
	}
	public BigDecimal getAdgroup_nrid() {
		return adgroup_nrid;
	}
	public void setAdgroup_nrid(BigDecimal adgroup_nrid) {
		this.adgroup_nrid = adgroup_nrid;
	}
}
