package pt.paginasamarelas.logicLayer.controller;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pt.paginasamarelas.dataLayer.entities.Request;
import pt.paginasamarelas.logicLayer.RESTRequester.RESTRequestService;
import pt.paginasamarelas.logicLayer.operations.RequestBuilder;

public class DeleteCampaignController {
	
	private ApplicationContext context;
	public String deleteMatchcraftCampaign(String external_id) throws IOException
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		//Instanciate RequestBuilder, Request and RESTRequestService obj
		RequestBuilder requestbuilder = (RequestBuilder) context.getBean("requestbuilder");
		RESTRequestService rest = (RESTRequestService) context.getBean("rest");
		Request matchcraftRequest = (Request) context.getBean("request");
		
		
		//Build read request with external_id campaign
		matchcraftRequest = requestbuilder.deleteRequest(external_id);
		
		//Send the request to matchcraft API and get the response
		String response = rest.makeRequest(matchcraftRequest);
		
		return null;
	}

}
