package pt.paginasamarelas.Tests;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import pt.paginasamarelas.dataLayer.hibernate.HibernateUtil;
import pt.paginasamarelas.dataLayer.hibernate.QueryCampaignDB;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtAdgroup;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtAdvert;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtBusinessAddress;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtBusinessPhone;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtGeoloc;
import pt.paginasamarelas.dataLayer.hibernate.entities.ca0;
import pt.paginasamarelas.logicLayer.operations.AdvertiserCreator;


public class HibernateTest {
	
	
	public static void main(String[] args)
	{
		//AdvertiserCreator c = new AdvertiserCreator();
		//c.createAdvertiser("13909005_3300955_158191");
		getCampaign();
	}
	
	
	@Test
	public static void getCampaign()
	{
		Session session = HibernateUtil.openSession();
		
		QueryCampaignDB q = new QueryCampaignDB();
		
		//q.getCa0("50506631_2312578_EEA   _TVE TRANSPORTES VAMOS ENTREGAR LIMITADA", session);
		//session = HibernateUtil.openSession();
		//q.getExAdvert("32579637599313", session);
		//session = HibernateUtil.openSession();
		//q.getExAdgroup("32579637599313", session);
		/*session = HibernateUtil.openSession();
		q.getExtGeoloc("25769477590340", session);
		session = HibernateUtil.openSession();
		q.getExtBusinessAddress("25769477590340", session);
		session = HibernateUtil.openSession();
		q.getExtBusinessPhone("25769477590340", session);*/
		//q.getExtCustomKeywordHQL("8883", session);
		//q.getExtCustomAdcopyHQL("9115", session);
		//q.getExtCustomAdgroupHQL("7083559", session);
		//q.getExtGeolocHQL("7083559", session);
		//q.getExtGeolocHQL("7083559", session);
		q.getExtCustomAdcopyHQL("8883", session);
		//q.getExtSitelinkHQL("7083559", session);
		
		
		
			
	}
	
	
	
	//@Test
	public void readClient() {
		
		//51785550_3231876_133724
		//46056693_3207463_126332
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		try {
			
			  	session.getTransaction();

			  	ca0 dbCa0 = (ca0) session.get(ca0.class, "46056693_3207463_126332");
			
			  	System.out.println(dbCa0.getExternalId() + " - " + dbCa0.getName());
			
			  	session.getTransaction().commit();
			  	
			  	
			
				}
			
				catch (HibernateException e) {
			
					e.printStackTrace();
			
					session.getTransaction().rollback();
			
				}
		System.out.println("deu");
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();

    }
	
	//@Test
	public void readCA0() {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println("------------------------------------------------------------");
		System.out.println("[TEST 1]Checking table 'ca0' data ORM state...");
		System.out.println("");
		
		try {
			
			  	session.getTransaction();

			  	/*ca0 dbCa0 = (ca0) session.get(ca0.class, "70135952_2362948_FCN   _RESTAURANTE VITORIA");
			  	//70135952_2362948_FCN   _RESTAURANTE VITORIA
			  	//51785550_3231876_133724
			  	System.out.println("ExternalId: " + dbCa0.getExternalId());
			  	System.out.println("Name: " + dbCa0.getName());
			  	System.out.println("Network: " + dbCa0.getNetworks());
			  	System.out.println("Target Retail Spend: " + dbCa0.getTargetRetailSpend());
			  	System.out.println("Start date: " + dbCa0.getStartDate());
			  	System.out.println("End date: " + dbCa0.getEndDate());
			  	System.out.println("NRID: " + dbCa0.getNrid());
			  	
			  	session.getTransaction().commit();*/
			  	//ca0 dbCa0 = (ca0) session.get(ca0.class, "70135952_2362948_FCN   _RESTAURANTE VITORIA");
			  	//List Ca0s = session.createCriteria(ca0.class).add(Restrictions.eq("externalId", "46056693_3207463_126332")).list();
			  	List<?> Ca0s = session.createCriteria(ca0.class).add(Restrictions.eq("externalId", "46056693_3207463_126332")).list();
			  	
			  	//List Ca0s = session.createQuery("FROM Ca0").list(); 
		         for (Iterator<?> iterator1 = Ca0s.iterator(); iterator1.hasNext();){
		            ca0 Ca0 = (ca0) iterator1.next(); 
		            System.out.println("ExternalId: " + Ca0.getExternalId());
				  	System.out.println("Name: " + Ca0.getName());
				  	System.out.println("Network: " + Ca0.getNetworks());
				  	System.out.println("Target Retail Spend: " + Ca0.getTargetRetailSpend());
				  	System.out.println("Start date: " + Ca0.getStartDate());
				  	System.out.println("End date: " + Ca0.getEndDate());
				  	System.out.println("NRID: " + Ca0.getNrid());
				  	
		            List<?> adgroups = null;
		            System.out.println("");
		    		System.out.println("ADGROUPS");
		    		System.out.println("------------------------------------------------------------");
		    		int counter=1;
		            for (Iterator<?> iterator2 = adgroups.iterator(); iterator2.hasNext();){
		            	  System.out.println("");
		            	  System.out.println("ADGROUP"+counter+" :");
		            	  System.out.println("");
		                  ExtAdgroup adgroup = (ExtAdgroup) iterator2.next(); 
		                  System.out.println("Advert ID: " + adgroup.getAdgroup_id());
		  			  	  System.out.println("Advert Language: " + adgroup.getAdgroup_name());
		  			  	  System.out.println("NRID: " + adgroup.getCa0_nrid());
		  			  	  counter++;
		            }
		            System.out.println("------------------------------------------------------------");
		         }
		         session.getTransaction().commit();

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[TEST 1]Table 'ca0' data ORM state FAILED: ");
					System.out.println("");
					System.out.println(e);
					System.out.println("------------------------------------------------------------");
					
			
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}finally
			{
				System.out.println();
				System.out.println("[TEST 1]Table 'ca0' data ORM state CORRECT");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			}

	}
	
	//@Test
	public void readExtBusinessAddress() {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println("------------------------------------------------------------");
		System.out.println("[TEST 2]Checking table 'ExtBusinessAddress' data ORM state...");
		System.out.println("");
		
		try {
			
			  	session.getTransaction();
			  	
			  	//31792136495404
			  	//31191434413817
			  	ExtBusinessAddress dbExtBusinessAddress = (ExtBusinessAddress) session.get(ExtBusinessAddress.class, new BigDecimal(31792136495404L));
			
			  	System.out.println("City: " + dbExtBusinessAddress.getCity());
			  	System.out.println("Line1: " + dbExtBusinessAddress.getLine1());
			  	System.out.println("Line2: " + dbExtBusinessAddress.getLine2());
			  	System.out.println("Postal Code: " + dbExtBusinessAddress.getPostalCode());
			  	System.out.println("Region: " + dbExtBusinessAddress.getRegion());
			  	System.out.println("NRID: " + dbExtBusinessAddress.getCaNrid());		  	
			  	
			  	session.getTransaction().commit();

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[TEST 2]Table 'ExtBusinessAddress' data ORM state FAILED: ");
					System.out.println();
					System.out.println(e);
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}catch(NullPointerException e){
				
				System.out.println("[TEST 2]Table 'ExtBusinessAddress' data ORM state FAILED: NULL VALUE ");
				System.out.println();
				System.out.println(e);
				System.out.println("------------------------------------------------------------");
				session.getTransaction().rollback();
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	System.exit(0);
			
			}finally
			{
				System.out.println("");
				System.out.println("[TEST 2]Table 'ExtBusinessAddress' data ORM state CORRECT");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			}

    }
	
	//@Test
	public void readExtBusinessPhone() {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println("------------------------------------------------------------");
		System.out.println("[TEST 3]Checking table 'ExtBusinessPhone' data ORM state...");
		System.out.println("");
		
		try {
			
			  	session.getTransaction();

			  	ExtBusinessPhone dbExtBusinessPhone = (ExtBusinessPhone) session.get(ExtBusinessPhone.class, new BigDecimal(31191434413817L));
			  	
			  	if(dbExtBusinessPhone != null)
			  	{
			  		System.out.println("Phone Number: " + dbExtBusinessPhone.getPhoneNumber());
				  	System.out.println("NRID: " + dbExtBusinessPhone.getCaNrid());			  	
				  	
				  	session.getTransaction().commit();
			  		
			  	}else
			  	{
			  		System.out.println();
			  		System.out.println("[TEST 3]Table 'ExtBusinessPhone' returned 0 results");
			  	}
			
			  	

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[TEST 3]Table 'ExtBusinessPhone' data ORM state FAILED: ");
					System.out.println();
					System.out.println(e);
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}finally
			{
				
				System.out.println("[TEST 3]Table 'ExtBusinessPhone' data ORM state CORRECT");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			}

    }
	
	//@Test
	public void readExtAdvert() {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println("------------------------------------------------------------");
		System.out.println("[TEST 4]Checking table 'ExtAdvert' data ORM state...");
		System.out.println("");
		
		try {
			
			  	session.getTransaction();

			  	ExtAdvert dbExtAdvert = (ExtAdvert) session.get(ExtAdvert.class, new BigDecimal(31191434413817L));
			  	
			  	if(dbExtAdvert != null)
			  	{
			
				  	System.out.println("Advert ID: " + dbExtAdvert.getAdvert_id());
				  	System.out.println("Advert Language: " + dbExtAdvert.getAdvertLanguage());
				  	System.out.println("Campaign URL: " + dbExtAdvert.getCampaignUrl());
				  	System.out.println("Display URL: " + dbExtAdvert.getDisplayUrl());
				 	System.out.println("NRID: " + dbExtAdvert.getCa0_nrid());			  	
				 	System.out.println();
				  	session.getTransaction().commit();
			  	}
			  	else
			  	{
			  		System.out.println();
			  		System.out.println("[TEST 4]Table 'ExtAdvert' returned 0 results");
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[TEST 4]Table 'ExtAdvert' data ORM state FAILED: ");
					System.out.println();
					System.out.println(e);
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}finally
			{
				System.out.println("[TEST 4]Table 'ExtAdvert' data ORM state CORRECT");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			}
		
		

    }
	
	
	//@Test
	public void readExtAdgroup() {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println("------------------------------------------------------------");
		System.out.println("[TEST 5]Checking table 'ExtAdgroup' data ORM state...");
		System.out.println("");
		
		try {
			
			  	session.getTransaction();

			  	ExtAdgroup dbExtAdgroup = (ExtAdgroup) session.get(ExtAdgroup.class, new BigDecimal(25649377660340L));
			
			  	System.out.println("Advert ID: " + dbExtAdgroup.getAdgroup_id());
			  	System.out.println("Advert Language: " + dbExtAdgroup.getAdgroup_name());
			 	System.out.println("NRID: " + dbExtAdgroup.getCa0_nrid());
			  	
			  	session.getTransaction().commit();

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[TEST 5]Table 'ExtAdgroup' data ORM state FAILED: ");
					System.out.println();
					System.out.println(e);
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}finally
			{
				System.out.println("");
				System.out.println("[TEST 5]Table 'ExtAdgroup' data ORM state CORRECT");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			}

    }
	
	//@Test
	public void readExtGeoloc() {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println("------------------------------------------------------------");
		System.out.println("[TEST 6]Checking table 'ExtGeoloc' data ORM state...");
		System.out.println("");
		
		try {
			
			  	session.getTransaction();

			  	ExtGeoloc dbExtGeoloc = (ExtGeoloc) session.get(ExtGeoloc.class, new BigDecimal(31191434413817L));
			  	
			  	if(dbExtGeoloc != null)
			  	{
			
				  	System.out.println("Radius Postal Code: " + dbExtGeoloc.getRadiusPostalCode());
				  	System.out.println("Radius: " + dbExtGeoloc.getRadius());
				 	System.out.println("NRID: " + dbExtGeoloc.getCa0_nrid());  	
				  	
				  	session.getTransaction().commit();
				  	System.out.println();
			  	}
			  	else
			  	{
			  		System.out.println();
			  		System.out.println("[TEST 6]Table 'ExtGeoloc' returned 0 results ");
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[TEST 6]Table 'ExtGeoloc' data ORM state FAILED: ");
					System.out.println();
					System.out.println(e);
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}finally
			{
				
				System.out.println("[TEST 6]Table 'ExtGeoloc' data ORM state CORRECT");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			}

    }

}
