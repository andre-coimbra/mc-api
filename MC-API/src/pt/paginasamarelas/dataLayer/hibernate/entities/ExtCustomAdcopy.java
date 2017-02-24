package pt.paginasamarelas.dataLayer.hibernate.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExtCustomAdcopy {
	
	private BigDecimal nrid;
	private BigDecimal ca0_nrid;
	private BigDecimal heading_nrid;
	private String title;
	private String line1;
	private String line2;
	private String title1;
	private String title2;
	private String description;
	private Integer disapproved;	
	private Integer template;	
	private Timestamp dmod;
	
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
	public BigDecimal getHeading_nrid() {
		return heading_nrid;
	}
	public void setHeading_nrid(BigDecimal heading_nrid) {
		this.heading_nrid = heading_nrid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title = title2;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
