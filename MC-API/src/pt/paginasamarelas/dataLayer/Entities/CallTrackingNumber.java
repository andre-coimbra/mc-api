package pt.paginasamarelas.dataLayer.Entities;

public class CallTrackingNumber {
	private String destinationNumber;
	private boolean preferTollFreeNumber;
	private String nickname;
	
	public String getDestinationNumber() {
		return destinationNumber;
	}
	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
	}
	public boolean isPreferTollFreeNumber() {
		return preferTollFreeNumber;
	}
	public void setPreferTollFreeNumber(boolean preferTollFreeNumber) {
		this.preferTollFreeNumber = preferTollFreeNumber;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
