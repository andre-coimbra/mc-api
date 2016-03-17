package pt.paginasamarelas.dataLayer.entities;



//@JsonInclude(Include.NON_NULL)
public class OperationsPar {
	private String operationUuid;
	private String operator;
	/**
	 * @return the operationUuid
	 */
	public String getOperationUuid() {
		return operationUuid;
	}
	/**
	 * @param operationUuid the operationUuid to set
	 */
	public void setOperationUuid(String operationUuid) {
		this.operationUuid = operationUuid;
	}
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

}
