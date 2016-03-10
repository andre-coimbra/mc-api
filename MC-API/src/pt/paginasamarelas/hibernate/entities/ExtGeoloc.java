package pt.paginasamarelas.hibernate.entities;

import java.math.BigDecimal;

public class ExtGeoloc {
	
	private String radiusPostalCode;
	private BigDecimal ca0_nrid;
	private BigDecimal radius;
	private String heading_nrid;
	private Integer template;
	
	
	
	public BigDecimal getCa0_nrid() {
		return ca0_nrid;
	}
	public void setCa0_nrid(BigDecimal ca0_nrid) {
		this.ca0_nrid = ca0_nrid;
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
