package pt.paginasamarelas.dataLayer.entities;

public class AdSchedulePeriod {

	private AdSchedulePeriodType type;
	private int beginHour;
	private int endHour;
	private float bidAdjustmentFactor;
	
	
	public AdSchedulePeriodType getType() {
		return type;
	}
	public void setType(AdSchedulePeriodType type) {
		this.type = type;
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
