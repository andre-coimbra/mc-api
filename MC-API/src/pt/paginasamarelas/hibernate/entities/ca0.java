package pt.paginasamarelas.hibernate.entities;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


public class ca0 {

	
	private String externalId;
	private BigDecimal nrid;
	private String name;
	private Timestamp startDate;
	private Timestamp endDate;
	private String networks;
	private BigDecimal targetRetailSpend;
	private BigDecimal targetMediaSpend;
	private BigDecimal targetClicks;
	private BigDecimal targetImpressions;
	
	
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	public BigDecimal getNrid() {
		return nrid;
	}
	public void setNrid(BigDecimal nrid) {
		this.nrid = nrid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getNetworks() {
		return networks;
	}
	public void setNetworks(String networks) {
		this.networks = networks;
	}
	public BigDecimal getTargetRetailSpend() {
		return targetRetailSpend;
	}
	public void setTargetRetailSpend(BigDecimal targetRetailSpend) {
		this.targetRetailSpend = targetRetailSpend;
	}
	public BigDecimal getTargetMediaSpend() {
		return targetMediaSpend;
	}
	public void setTargetMediaSpend(BigDecimal targetMediaSpend) {
		this.targetMediaSpend = targetMediaSpend;
	}
	public BigDecimal getTargetClicks() {
		return targetClicks;
	}
	public void setTargetClicks(BigDecimal targetClicks) {
		this.targetClicks = targetClicks;
	}
	public BigDecimal getTargetImpressions() {
		return targetImpressions;
	}
	public void setTargetImpressions(BigDecimal targetImpressions) {
		this.targetImpressions = targetImpressions;
	}

}
