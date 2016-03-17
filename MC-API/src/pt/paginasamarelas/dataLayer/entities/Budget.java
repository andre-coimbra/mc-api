package pt.paginasamarelas.dataLayer.entities;

public class Budget {
	
	private int contractBeginDate;
	private int contractEndDate;
	private String externalId;
	private float targetRetailSpend;
	private float mediaFraction;
	private float targetMediaSpend;
	private int targetClicks;
	private int targetImpressions;
	private boolean suppressRolloverAndExtension;
	
	
	public int getContractBeginDate() {
		return contractBeginDate;
	}
	public void setContractBeginDate(int contractBeginDate) {
		this.contractBeginDate = contractBeginDate;
	}
	public int getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(int contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public float getTargetRetailSpend() {
		return targetRetailSpend;
	}
	public void setTargetRetailSpend(float targetRetailSpend) {
		this.targetRetailSpend = targetRetailSpend;
	}
	public float getMediaFraction() {
		return mediaFraction;
	}
	public void setMediaFraction(float mediaFraction) {
		this.mediaFraction = mediaFraction;
	}
	public float getTargetMediaSpend() {
		return targetMediaSpend;
	}
	public void setTargetMediaSpend(float targetMediaSpend) {
		this.targetMediaSpend = targetMediaSpend;
	}
	public int getTargetClicks() {
		return targetClicks;
	}
	public void setTargetClicks(int targetClicks) {
		this.targetClicks = targetClicks;
	}
	public int getTargetImpressions() {
		return targetImpressions;
	}
	public void setTargetImpressions(int targetImpressions) {
		this.targetImpressions = targetImpressions;
	}
	public boolean isSuppressRolloverAndExtension() {
		return suppressRolloverAndExtension;
	}
	public void setSuppressRolloverAndExtension(boolean suppressRolloverAndExtension) {
		this.suppressRolloverAndExtension = suppressRolloverAndExtension;
	}

}
