package pt.paginasamarelas.logicLayer.scheduler;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import pt.paginasamarelas.dataLayer.hibernate.HibernateUtil;
import pt.paginasamarelas.dataLayer.hibernate.QueryCampaignDB;

public class SpringScheduler {
	QueryCampaignDB query = new QueryCampaignDB();
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = sessionFactory.openSession();
	
	
	public void execute()
	{
		System.out.println("10 seconds!");
	}
	
	public void newAdvertisers()
	{
		List newCampaignsList = query.getNewAdvertisersHQL(session);
		
		
	}

}
