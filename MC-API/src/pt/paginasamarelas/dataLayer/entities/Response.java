package pt.paginasamarelas.dataLayer.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Response {
	private String requestUuid;
	private Operations[] operations;
	private Diagnostic[] diagnostics;
	
	public String getRequestUuid() {
		return requestUuid;
	}
	public void setRequestUuid(String requestUuid) {
		this.requestUuid = requestUuid;
	}
	public Operations[] getOperations() {
		return operations;
	}
	public void setOperations(Operations[] operations) {
		this.operations = operations;
	}

	public Diagnostic[] getDiagnostics() {
		return diagnostics;
	}
	public void setDiagnostics(Diagnostic[] diagnostics) {
		this.diagnostics = diagnostics;
	}

}
