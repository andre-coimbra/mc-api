package pt.paginasamarelas.Entities;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class AdvertiserID {
	private int ripId;
	private String externalId;
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

}
