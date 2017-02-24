package pt.paginasamarelas.logicLayer.controller;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pt.paginasamarelas.dataLayer.entities.Advertiser;
import pt.paginasamarelas.dataLayer.entities.Campaign;
import pt.paginasamarelas.dataLayer.entities.GeographicTarget;
import pt.paginasamarelas.dataLayer.entities.Request;
import pt.paginasamarelas.logicLayer.RESTRequester.RESTRequestService;
import pt.paginasamarelas.logicLayer.operations.RequestBuilder;

public class CreateCampaignController {
	
	private ApplicationContext context;
	public String createMatchcraftCampaign(Campaign mcCamp) throws IOException
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		//Instanciate RequestBuilder, Request and RESTRequestService obj
		RequestBuilder requestbuilder = (RequestBuilder) context.getBean("requestbuilder");
		RESTRequestService rest = (RESTRequestService) context.getBean("rest");
		Request matchcraftRequest = (Request) context.getBean("request");
		
		
		//Build delete request with external_id campaign
		Advertiser mcAdv = mcCamp.getAdvertiser();
		matchcraftRequest = requestbuilder.createRequest(mcAdv);
		
		//Send the request to matchcraft API and get the response
		String response = rest.makeRequest(matchcraftRequest);
		
		return response;
	}
// FM 03.08.2016
	public String getGeoModifiers(GeographicTarget geoTarget) throws IOException
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		//Instanciate RequestBuilder, Request and RESTRequestService obj
		RequestBuilder requestbuilder = (RequestBuilder) context.getBean("requestbuilder");
		RESTRequestService rest = (RESTRequestService) context.getBean("rest");
		Request matchcraftRequest = (Request) context.getBean("request");
		
		
		//Build delete request with external_id campaign
		matchcraftRequest = requestbuilder.getSuggGeoModfRequest(geoTarget);
		
		//Send the request to matchcraft API and get the response
		String response = rest.makeRequest(matchcraftRequest);
		
		return response;
	}
}
