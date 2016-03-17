package pt.paginasamarelas.dataLayer.entities;

public class AdSchedulePeriodType {
	
	private String typeEnum;
	private int beginHour;
	private int endHour;
	
	
	public enum typeEnum
	{
		ALL_DAYS,
		MONDAY_TO_SATURDAY,
		MONDAY_TO_FRIDAY,
		SATURDAY_AND_SUNDAY,
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY;
	}


	public String getTypeEnum() {
		return typeEnum;
	}


	public void setTypeEnum(String typeEnum) {
		this.typeEnum = typeEnum;
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

}


