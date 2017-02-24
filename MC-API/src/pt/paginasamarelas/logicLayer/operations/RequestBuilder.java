package pt.paginasamarelas.logicLayer.operations;

import java.io.IOException;

import pt.paginasamarelas.dataLayer.entities.Advertiser;
import pt.paginasamarelas.dataLayer.entities.AdvertiserID;
import pt.paginasamarelas.dataLayer.entities.Authentication;
import pt.paginasamarelas.dataLayer.entities.Operand;
import pt.paginasamarelas.dataLayer.entities.Operations;
import pt.paginasamarelas.dataLayer.entities.Request;
import pt.paginasamarelas.dataLayer.entities.Operations.OperatorEnum;
import pt.paginasamarelas.dataLayer.entities.GeographicTarget;

public class RequestBuilder {
	
	public Request createRequest(Advertiser advertiser) throws IOException {
		Authentication authentication = new Authentication();
		PropertiesReader props = new PropertiesReader();
	
		
		authentication.setClientProgramNickname(props.getUser());
		authentication.setPassword(props.getPassword());
		
		
		Operand operands = new Operand();
		operands.setAdvertiser(advertiser);
		
		Operations operation = new Operations();
		operation.setOperator(OperatorEnum.createAdvertiser);
		
		operation.setOperands(operands);
		
		Operations[] operations = {operation};
		
		Request request = new Request();
		request.setAuthentication(authentication);
		request.setOperations(operations);
		
		return request;
	}

	public Request updateRequest(Advertiser modifiedAdvertiser) throws IOException {
		Authentication authentication = new Authentication();
		PropertiesReader props = new PropertiesReader();
	
		
		authentication.setClientProgramNickname(props.getUser());
		authentication.setPassword(props.getPassword());
		
		
		Operand operands = new Operand();
		operands.setAdvertiser(modifiedAdvertiser);
		
		Operations operation = new Operations();
		operation.setOperator(OperatorEnum.updateAdvertiser);
		
		operation.setOperands(operands);
		
		Operations[] operations = {operation};
		
		Request request = new Request();
		request.setAuthentication(authentication);
		request.setOperations(operations);
		
		return request;
	}
	
	public Request readRequest(String external_id) throws IOException {
		Authentication authentication = new Authentication();
		PropertiesReader props = new PropertiesReader();
	
		
		authentication.setClientProgramNickname(props.getUser());
		authentication.setPassword(props.getPassword());
		
		Operations operation = new Operations();
		operation.setOperator(OperatorEnum.readAdvertiser);
		
		Operand operands = new Operand();
		AdvertiserID advertiserid = new AdvertiserID();
		advertiserid.setExternalId(external_id);
		
		operands.setAdvertiserId(advertiserid);
		operation.setOperands(operands);
		
		Operations[] operations = {operation};
		
		Request request = new Request();
		request.setAuthentication(authentication);
		request.setOperations(operations);
		
		return request;
		
	}
	
	public Request deleteRequest(String external_id) throws IOException {
		/* FM 08.04.2016 */
		Authentication authentication = new Authentication();
		PropertiesReader props = new PropertiesReader();
	
		
		authentication.setClientProgramNickname(props.getUser());
		authentication.setPassword(props.getPassword());
		
		Operations operation = new Operations();
		operation.setOperator(OperatorEnum.deleteAdvertiser);

		Operand operands = new Operand();
		AdvertiserID advertiserid = new AdvertiserID();
		advertiserid.setExternalId(external_id);
		
		operands.setAdvertiserId(advertiserid);
		operation.setOperands(operands);		
		
		Operations[] operations = {operation};	
		
		Request request = new Request();
		request.setAuthentication(authentication);
		request.setOperations(operations);
				
/*	uso não integrado:			
		RESTRequestService rest = new RESTRequestService();
		System.out.println("Request service created");
		rest.makeRequest(request);
*/
		return request;
	}

// FM 03.08.2016
	public Request getSuggGeoModfRequest(GeographicTarget pGeoTarget) throws IOException {
		Authentication authentication = new Authentication();
		PropertiesReader props = new PropertiesReader();	
		
		authentication.setClientProgramNickname(props.getUser());
		authentication.setPassword(props.getPassword());
		
		Operations operation = new Operations();
		operation.setOperator(OperatorEnum.getSuggestedGeoModifiers);
		
		Operand operands = new Operand();
		
		operands.setGeographicTarget(pGeoTarget);;
		operation.setOperands(operands);
		
		Operations[] operations = {operation};
		
		Request request = new Request();
		request.setAuthentication(authentication);
		request.setOperations(operations);
		
		return request;
		
	}

// FM 15.11.2016
	public Request readAdgroupsRequest(boolean pIncludeAdcopies, boolean pIncludeKeyphrases) throws IOException {
		Authentication authentication = new Authentication();
		PropertiesReader props = new PropertiesReader();
		
		authentication.setClientProgramNickname(props.getUser());
		authentication.setPassword(props.getPassword());
		
		Operations operation = new Operations();
		operation.setOperator(OperatorEnum.getTaxonomyReport);
		
		Operand operands = new Operand();
		
// TESTE		operands.setIncludeAdcopy(true);
		operands.setIncludeAdcopy(pIncludeAdcopies);
		operands.setIncludeKeyphrases(pIncludeKeyphrases);
		operation.setOperands(operands);
		
		Operations[] operations = {operation};
		
		Request request = new Request();
		request.setAuthentication(authentication);
		request.setOperations(operations);
		
		return request;
		
	}	
}
