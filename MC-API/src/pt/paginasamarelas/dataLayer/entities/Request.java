package pt.paginasamarelas.dataLayer.entities;


public class Request {
	private Authentication authentication;
	private Operations[] operations;
	
	public Request(){}
	
	
	/*public Request(Authentication authentication,List<Operations> operations) throws IOException
	{
		this.authentication = authentication;
		this.operations = operations;
		
	}
	
	public Request(Authentication authentication,Operations operation) throws IOException
	{
		this.authentication = authentication;
		this.operations.add(operation);
	}*/
	
	
	public Authentication getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	public Operations[] getOperations() {
		return operations;
	}
	public void setOperations(Operations[] operations) {
		this.operations = operations;
	}
	
}
