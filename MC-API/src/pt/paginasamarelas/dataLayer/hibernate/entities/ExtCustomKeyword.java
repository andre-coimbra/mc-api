package pt.paginasamarelas.dataLayer.hibernate.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExtCustomKeyword {
	
	private BigDecimal nrid;
	private BigDecimal ca0_nrid;
	private BigDecimal heading_nrid;
	private int id;
	private String keyword;
	private Integer disapproved;	
	private Integer template;	
	private Timestamp dmod;
	
	public BigDecimal getNrid() {
		return nrid;
	}
	public void setNrid(BigDecimal nrid) {
		this.nrid = nrid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getCa0_nrid() {
		return ca0_nrid;
	}
	public void setCa0_nrid(BigDecimal ca0_nrid) {
		this.ca0_nrid = ca0_nrid;
	}
	public BigDecimal getHeading_nrid() {
		return heading_nrid;
	}
	public void setHeading_nrid(BigDecimal heading_nrid) {
		this.heading_nrid = heading_nrid;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getDisapproved() {
		return disapproved;
	}
	public void setDisapproved(Integer disapproved) {
		this.disapproved = disapproved;
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
