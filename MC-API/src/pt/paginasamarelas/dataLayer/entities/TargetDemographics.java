package pt.paginasamarelas.dataLayer.entities;

public class TargetDemographics {
	private int minAge;
	private int maxAge;
	private String gender;
	
	public enum Gender
	{
		male,female;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public String getGender() {
		return gender;
	}

	/*public void setGender(String gender) {
		this.gender = gender;
	}*/
	
	public void setGender(Gender gender) {
		this.gender = gender.toString();
	}
}
