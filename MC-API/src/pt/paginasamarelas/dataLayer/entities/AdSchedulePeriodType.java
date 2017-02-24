package pt.paginasamarelas.dataLayer.entities;

public class AdSchedulePeriodType {
	
	private String typeEnum;	
	
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

}


