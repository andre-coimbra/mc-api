package pt.paginasamarelas.dataLayer.entities;

public class Operations {
	private String operator;
	private Operand operands;
	private String operationUuid;
	private Advertiser result;
	
	
	public enum OperatorEnum
	{
		readAdvertiser,
		createAdvertiser,
		updateAdvertiser,
		deleteAdvertiser
		
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(OperatorEnum operator) {
		this.operator = operator.toString();
	}
	


	public Operand getOperands() {
		return operands;
	}


	public void setOperands(Operand operands) {
		this.operands = operands;
	}


	public String getOperationUuid() {
		return operationUuid;
	}


	public void setOperationUuid(String operationUuid) {
		this.operationUuid = operationUuid;
	}


	public Advertiser getResult() {
		return result;
	}


	public void setResult(Advertiser result) {
		this.result = result;
	}


	

}



