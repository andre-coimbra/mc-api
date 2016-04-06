package pt.paginasamarelas.dataLayer.entities;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class AdvertiserID {
	private int ripId;
	private String externalId;
	private Diagnostic[] diagnostics;
	
	/**
	 * @return the ripId
	 */
	
	public int getRipId() {
		return ripId;
	}
	/**
	 * @param ripId the ripId to set
	 */
	public void setRipId(int ripId) {
		this.ripId = ripId;
	}
	/**
	 * @return the externalId
	 */
	public String getExternalId() {
		return externalId;
	}
	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Diagnostic[] getDiagnostics() {
		return diagnostics;
	}
	public void setDiagnostics(Diagnostic[] diagnostics) {
		this.diagnostics = diagnostics;
	}

}
