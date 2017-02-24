package pt.paginasamarelas.logicLayer.controller;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pt.paginasamarelas.dataLayer.entities.Advertiser;
import pt.paginasamarelas.dataLayer.entities.Request;
import pt.paginasamarelas.logicLayer.RESTRequester.RESTRequestService;
import pt.paginasamarelas.logicLayer.operations.RequestBuilder;

public class UpdateCampaignController {

	
	private ApplicationContext context;
	public String updateMatchcraftCampaign(Advertiser mcModAdvertiser) throws IOException
	{
		String response = null;
		context = new ClassPathXmlApplicationContext("beans.xml");
		//Instanciate RequestBuilder, Request and RESTRequestService obj
		RequestBuilder requestbuilder = (RequestBuilder) context.getBean("requestbuilder");
		RESTRequestService rest = (RESTRequestService) context.getBean("rest");
		Request matchcraftRequest = (Request) context.getBean("request");
				
		//Build update request with Matchcraft Advertiser
		matchcraftRequest = requestbuilder.updateRequest(mcModAdvertiser);
		
		//Send the request to matchcraft API and get the response
		System.out.println("[UpdCamp] now call makeRequest\n");
		response = rest.makeRequest(matchcraftRequest);
		
		return response;
	}

}
