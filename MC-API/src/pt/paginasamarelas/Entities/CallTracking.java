package pt.paginasamarelas.Entities;




public class CallTracking {
	
	
	//@JsonProperty("callTrackingNumbers")
	private  CallTrackingNumber[] callTrackingNumbers;
	private boolean recordCalls;
	private AdditionalPhoneNumberReplacement[] additionalPhoneNumberReplacements;
	
	
	
	public boolean isRecordCalls() {
		return recordCalls;
	}
	public void setRecordCalls(boolean recordCalls) {
		this.recordCalls = recordCalls;
	}
	public CallTrackingNumber[] getCallTrackingNumbers() {
		return callTrackingNumbers;
	}
	public void setCallTrackingNumbers(CallTrackingNumber[] callTrackingNumbers) {
		this.callTrackingNumbers = callTrackingNumbers;
	}
	public AdditionalPhoneNumberReplacement[] getAdditionalPhoneNumberReplacements() {
		return additionalPhoneNumberReplacements;
	}
	public void setAdditionalPhoneNumberReplacements(
			AdditionalPhoneNumberReplacement[] additionalPhoneNumberReplacements) {
		this.additionalPhoneNumberReplacements = additionalPhoneNumberReplacements;
	}
	
	
	
	

}
