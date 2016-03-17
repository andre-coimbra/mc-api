package pt.paginasamarelas.dataLayer.entities;

public class AdSchedule {
	
	private String timeZoneId;
	private AdSchedulePeriod periods;
	
	
	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	public AdSchedulePeriod getPeriods() {
		return periods;
	}
	public void setPeriods(AdSchedulePeriod periods) {
		this.periods = periods;
	}

}
