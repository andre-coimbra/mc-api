package pt.paginasamarelas.logicLayer.operations;

import java.io.IOException;

import pt.paginasamarelas.dataLayer.entities.Advertiser;
import pt.paginasamarelas.dataLayer.entities.AdvertiserID;
import pt.paginasamarelas.dataLayer.entities.Authentication;
import pt.paginasamarelas.dataLayer.entities.Operand;
import pt.paginasamarelas.dataLayer.entities.Operations;
import pt.paginasamarelas.dataLayer.entities.Request;
import pt.paginasamarelas.dataLayer.entities.Operations.OperatorEnum;

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

	
	public Request deleteRequest(String external_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
