package pt.paginasamarelas.dataLayer.hibernate.entities;


import java.math.BigDecimal;
import java.sql.Timestamp;



public class ca0 {

	
	private String externalId;
	private BigDecimal nrid;
	private Timestamp startDate;
	private Timestamp endDate;
	private String networks;
	private BigDecimal targetRetailSpend;
	private BigDecimal targetMediaSpend;
	private BigDecimal targetClicks;
	private BigDecimal targetImpressions;
	private String status;
	private BigDecimal num8;
	private String var2;
	private String var3;
	private String var6;
	private String var9;
	private String var23;
/* FM 01.08.2016 - Added */	
	private Timestamp intfDate;
	private Timestamp dmod;	
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getNum8() {
		return num8;
	}
	public void setNum8(BigDecimal pNum8) {
		this.num8 = pNum8;
	}
	public String getVar2() {
		return var2;
	}
	public void setVar2(String pVar2) {
		this.var2 = pVar2;
	}
	public String getVar3() {
		return var3;
	}
	public void setVar3(String pVar3) {
		this.var3 = pVar3;
	}
	public String getVar6() {
		return var6;
	}
	public void setVar6(String pVar6) {
		this.var6 = pVar6;
	}
	public String getVar9() {
		return var9;
	}
	public void setVar9(String pVar9) {
		this.var9 = pVar9;
	}
	public String getVar23() {
		return var23;
	}
	public void setVar23(String pVar23) {
		this.var23 = pVar23;
	}
	public Timestamp getIntfDate() {
		return intfDate;
	}
	public void setIntfDate(Timestamp intfDate) {
		this.intfDate = intfDate;
	}
	public Timestamp getDmod() {
		return dmod;
	}
	public void setDmod(Timestamp dmod) {
		this.dmod = dmod;
	}

}
