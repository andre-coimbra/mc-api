package pt.paginasamarelas.logicLayer.scheduler;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pt.paginasamarelas.dataLayer.entities.Operations;
import pt.paginasamarelas.dataLayer.entities.Response;
import pt.paginasamarelas.dataLayer.hibernate.HibernateUtil;
import pt.paginasamarelas.dataLayer.hibernate.QueryCampaignDB;
import pt.paginasamarelas.logicLayer.controller.*;
import pt.paginasamarelas.logicLayer.operations.JacksonConverter;

public class SpringScheduler 
{
	
	QueryCampaignDB query = new QueryCampaignDB();
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = sessionFactory.openSession();
	private ApplicationContext context;
	
		
	public void decider() throws IOException
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		List<?> newCampaignsList = query.getNewAdvertisersHQL(session);
		Iterator<?> newCampaignsIterator = newCampaignsList.listIterator();
		
		ReadCampaignController readCampaignController = (ReadCampaignController) context.getBean("readcampaigncontroller");
		JacksonConverter jacksonConverter = (JacksonConverter) context.getBean("jacksonConverter");
		Response responseobj = (Response) context.getBean("response");
		
		while(newCampaignsIterator.hasNext()) 
	    {
			Object[] newCampaign = (Object[]) newCampaignsIterator.next();
			String status = newCampaign[1].toString();
			String external_id = newCampaign[0].toString();
			switch (status) 
			{
	            case "3 - Ready to send to BMS":  
	            			String response = readCampaignController.getMatchcraftCampaign(external_id);
	            			jacksonConverter.refactoredResponse(response);
	            			//responseobj = jacksonConverter.responseToJSON(response);
	            			
	                     	break;
	            case "5a - Running":  
	            			readCampaignController.getMatchcraftCampaign(external_id);
	                     	break;
	                     	
	            case "6 - Cancelled":  
	            			DeleteCampaignController deleteCampaignController = (DeleteCampaignController) context.getBean("deletecampaigncontroller");
	            			//deleteCampaignController
	            			break;
	            			
	            case "7a - Completed by date":  
	            	
	                     	break;
	           
	            default: 
	                     	
	            break;
			}
			
			//readCampaignController.getMatchcraftCampaign();
	    }
		
		
		
		
	}
	
	
	
	

}
