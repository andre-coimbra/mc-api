package pt.paginasamarelas.Operations;

import java.io.IOException;

import pt.paginasamarelas.Entities.AdvertiserID;
import pt.paginasamarelas.Entities.Authentication;
import pt.paginasamarelas.Entities.Operand;
import pt.paginasamarelas.Entities.Operations;
import pt.paginasamarelas.Entities.Request;
import pt.paginasamarelas.Entities.Operations.OperatorEnum;

public class Filler {
	
	public  Request fillReadRequestObj(String external_id) throws IOException
	{
		PropertiesReader props = new PropertiesReader();
		Authentication authentication = new Authentication();
		
		authentication.setClientProgramNickname(props.getUser());
		authentication.setPassword(props.getPassword());
		
		Operations operations = new Operations();
		operations.setOperator(OperatorEnum.readAdvertiser);
		
		Operand operands = new Operand();
		AdvertiserID advertiserid = new AdvertiserID();
		advertiserid.setExternalId(external_id);
		
		operands.setAdvertiserId(advertiserid);
		operations.setOperands(operands);
		
		
		
		Request request = new Request();
		request.setAuthentication(authentication);
		//request.setOperations(operations);
		
		return request;
	}
	
	
	
	
	
	
	
	
	
	

}
