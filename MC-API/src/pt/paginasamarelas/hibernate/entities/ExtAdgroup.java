package pt.paginasamarelas.hibernate.entities;

import java.math.BigDecimal;

public class ExtAdgroup {
	
	private BigDecimal adgroup_id;
	private BigDecimal ca0_nrid;
	private BigDecimal nrid;
	private String adgroup_name;
	private String heading_nrid;
	private Integer template;
	
	
	public BigDecimal getNrid() {
		return nrid;
	}
	public void setNrid(BigDecimal nrid) {
		this.nrid = nrid;
	}	
	
	public BigDecimal getAdgroup_id() {
		return adgroup_id;
	}
	public void setAdgroup_id(BigDecimal adgroup_id) {
		this.adgroup_id = adgroup_id;
	}
	public BigDecimal getCa0_nrid() {
		return ca0_nrid;
	}
	public void setCa0_nrid(BigDecimal ca0_nrid) {
		this.ca0_nrid = ca0_nrid;
	}
	public String getAdgroup_name() {
		return adgroup_name;
	}
	public void setAdgroup_name(String adgroup_name) {
		this.adgroup_name = adgroup_name;
	}
	public String getHeading_nrid() {
		return heading_nrid;
	}
	public void setHeading_nrid(String heading_nrid) {
		this.heading_nrid = heading_nrid;
	}
	public Integer getTemplate() {
		return template;
	}
	public void setTemplate(Integer template) {
		this.template = template;
	}
	

}
