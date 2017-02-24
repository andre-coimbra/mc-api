package pt.paginasamarelas.dataLayer.entities;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)

public class AdSchedulePeriod {

// FM 03.12.2016	private AdSchedulePeriodType type;
	private String type;
	private int beginHour;
	private int endHour;
	private float bidAdjustmentFactor;
	
	
	public String getType() {
		return type;
	}
//	public void setType(AdSchedulePeriodType pType) {
	public void setType(String pType) {
		this.type = pType;
	}
	public int getBeginHour() {
		return beginHour;
	}
	public void setBeginHour(int beginHour) {
		this.beginHour = beginHour;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public float getBidAdjustmentFactor() {
		return bidAdjustmentFactor;
	}
	public void setBidAdjustmentFactor(float bidAdjustmentFactor) {
		this.bidAdjustmentFactor = bidAdjustmentFactor;
	}
	
	
}
