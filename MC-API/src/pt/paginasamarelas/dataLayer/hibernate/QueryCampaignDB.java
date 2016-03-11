package pt.paginasamarelas.dataLayer.hibernate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import pt.paginasamarelas.dataLayer.hibernate.entities.ExtAdgroup;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtAdvert;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtBusinessAddress;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtBusinessPhone;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtCustomAdcopy;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtGeoloc;
import pt.paginasamarelas.dataLayer.hibernate.entities.ca0;

public class QueryCampaignDB {
	
	//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	//Session session = sessionFactory.openSession();
	
	@SuppressWarnings("finally")
	public ca0 getCa0(String nrid, Session session)
	{
		ca0 dbca0 = null;
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ca0] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ca0] Checking table 'ExtAdvert' data ORM state...");
		
		try {
				
				
			  	session.getTransaction();

			  	dbca0 = (ca0) session.get(ca0.class, nrid);
			  	
			  	if(dbca0 != null)
			  	{
			  		System.out.println("[ca0] DATA");
			  		System.out.println("-----------------");
			  		System.out.println("ExternalId: " + dbca0.getExternalId());
				  	System.out.println("Name: " + dbca0.getName());
				  	System.out.println("Network: " + dbca0.getNetworks());
				  	System.out.println("Target Retail Spend: " + dbca0.getTargetRetailSpend());
				  	System.out.println("Start date: " + dbca0.getStartDate());
				  	System.out.println("End date: " + dbca0.getEndDate());
				  	System.out.println("NRID: " + dbca0.getNrid());
				 	System.out.println("-----------------");
				  	session.getTransaction().commit();
			  	}
			  	else
			  	{
			  		
			  		System.out.println("[ca0]Table 'ca0' RETURNED NULL");
			  		return null;
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[ca0]Table 'ca0' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ca0]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
				  	session.close();
				  	System.exit(0);
			
			}catch (NumberFormatException e)
			{
				System.out.println("[ca0] NRID is not in Big Decimal format.");
				return null;
			}
			catch(NullPointerException e)
			{
				System.out.println("[ca0]Table 'ca0' RETURNED NULL");
		  		return null;
			}
			finally
			{
				System.out.println("[ca0] Table 'ExtAdgroup' data ORM state CORRECT");
				System.out.println("[ca0] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return dbca0;
			}
		
		
		
	}
	
	@SuppressWarnings("finally")
	public List<ExtAdvert> getExAdvert(String nrid, Session session)
	{
		
		//ExtAdvert dbExtAdvert = null;
		List<ExtAdvert> dbExtAdvertList = null;
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtAdvert] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtAdvert] Checking table 'ExtAdvert' data ORM state...");
		
		try {
				BigDecimal bnrid = new BigDecimal(nrid);
				
			  	session.getTransaction();
			  	
			  	
			  	dbExtAdvertList = session.createCriteria(ExtAdvert.class).add(Restrictions.eq("ca0_nrid", bnrid)).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
			  	
			  	//Unique advertid list
			  	
			  	//dbExtAdvert = (ExtAdvert) session.get(ExtAdvert.class, bnrid);
			  	int counter=1;
			  	
			  	
			  	if(dbExtAdvertList != null)
			  	{
			  		System.out.println("[ExtAdvert] DATA");
			  		System.out.println("-----------------");
			  		Iterator<?> it = dbExtAdvertList.listIterator();
				    while(it.hasNext()) 
				    {
				    	  ExtAdvert exadvert = (ExtAdvert) it.next();
				    	  System.out.println("");
		            	  System.out.println("ADVERT"+counter+" :");
		            	  System.out.println("");
		            	  
		            	  
		                  System.out.println("Advert ID: " + exadvert.getAdvert_id());
		  			  	  System.out.println("Advert Language: " + exadvert.getAdvertLanguage());
		  			  	  System.out.println("Campaign URL: " + exadvert.getCampaignUrl());
					  	  System.out.println("Display URL: " + exadvert.getDisplayUrl());
		  			  	  System.out.println("NRID: " + exadvert.getCa0_nrid());
		  			  	  System.out.println("Heading NRID: " + exadvert.getHeading_nrid());
		  			  	  counter++;
				         
				      }
			  		
			  		session.getTransaction().commit();
			  		System.out.println("-----------------");
			  		
	
			  	}
			  	else
			  	{
			  		
			  		System.out.println("[ExtAdvert]Table 'ExtAdvert' RETURNED NULL");
			  		return null;
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[ExtAdvert]Table 'ExtAdvert' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ExtAdvert]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}catch (NumberFormatException e)
			{
				System.out.println("[ExtAdvert] NRID is not in Big Decimal format.");
				return null;
			}
			catch(NullPointerException e)
			{
				System.out.println("[ExtAdvert]Table 'ExtAdvert' RETURNED NULL");
		  		return null;
			}
			finally
			{
				System.out.println("[ExtAdvert] Table 'ExtAdvert' data ORM state CORRECT");
				System.out.println("[ExtAdvert] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return dbExtAdvertList;
			}
		
		
		
	}
	
	@SuppressWarnings("finally")
	public List<ExtAdgroup> getExAdgroup(String nrid, Session session)
	{
		
		ExtAdgroup dbExtAdgroup = null;
		List<ExtAdgroup> dbExtAdgroupList = null;
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtAdgroup] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtAdgroup] Checking table 'ExtAdgroup' data ORM state...");
		
		try {
				BigDecimal bnrid = new BigDecimal(nrid);
				
			  	session.getTransaction();
			  	
			  	
			  	//dbExtAdgroup = (ExtAdgroup) session.get(ExtAdgroup.class, bnrid);
			  	dbExtAdgroupList = session.createCriteria(ExtAdgroup.class).add(Restrictions.eq("ca0_nrid", bnrid)).list();
			  	int counter=1;
			  	if(dbExtAdgroupList != null)
			  	{
			  		System.out.println("[ExtAdgroup] DATA");
			  		System.out.println("-----------------");
			  		
			  		for (Iterator<?> iterator = dbExtAdgroupList.iterator(); iterator.hasNext();){
		            	  System.out.println("");
		            	  System.out.println("ADGROUP"+counter+" :");
		            	  System.out.println("");
		            	  ExtAdgroup exadgroup = (ExtAdgroup) iterator.next(); 
		            	  
		            	  System.out.println("Adgroup ID: " + exadgroup.getAdgroup_id());
						  System.out.println("Adgroup Name: " + exadgroup.getAdgroup_name());
						  System.out.println("NRID: " + exadgroup.getCa0_nrid());
						  System.out.println("Heading NRID: " + exadgroup.getHeading_nrid());
		  			  	  counter++;
		            }
			  		session.getTransaction().commit();
			  		System.out.println("-----------------");
			  		
			  	}
			  	else
			  	{
			  		
			  		System.out.println("[ExtAdgroup]Table 'ExtAdgroup' RETURNED NULL");
			  		return null;
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[ExtAdgroup]Table 'ExtAdgroup' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ExtAdgroup]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}catch (NumberFormatException e)
			{
				System.out.println("[ExtAdgroup] NRID is not in Big Decimal format.");
				return null;
			}
			catch(NullPointerException e)
			{
				System.out.println("[ExtAdgroup]Table 'ExtAdgroup' RETURNED NULL");
		  		return null;
			}
			finally
			{
				System.out.println("[ExtAdgroup] Table 'ExtAdgroup' data ORM state CORRECT");
				System.out.println("[ExtAdgroup] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return dbExtAdgroupList;
			}
		
		
		
	}
	
	@SuppressWarnings("finally")
	public ExtGeoloc getExtGeoloc(String nrid, Session session)
	{
		
		ExtGeoloc dbExtGeoloc = null;
		
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtGeoloc] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtGeoloc] Checking table 'ExtAdvert' data ORM state...");
		
		try {
				BigDecimal bnrid = new BigDecimal(nrid);
				
			  	session.getTransaction();

			  	dbExtGeoloc = (ExtGeoloc) session.get(ExtGeoloc.class, bnrid);
			  	
			  	if(dbExtGeoloc != null)
			  	{
			  		System.out.println("[ExtGeoloc] DATA");
			  		System.out.println("-----------------");
			  		System.out.println("Radius Postal Code: " + dbExtGeoloc.getRadiusPostalCode());
				  	System.out.println("Radius: " + dbExtGeoloc.getRadius());
				 	System.out.println("NRID: " + dbExtGeoloc.getCa0_nrid());
				 	System.out.println("-----------------");
				  	session.getTransaction().commit();
			  	}
			  	else
			  	{
			  		
			  		System.out.println("[ExtGeoloc]Table 'ExtAdgroup' RETURNED NULL");
			  		return null;
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[ExtGeoloc]Table 'ExtAdgroup' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ExtGeoloc]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}catch (NumberFormatException e)
			{
				System.out.println("[ExtGeoloc] NRID is not in Big Decimal format.");
				return null;
			}
			catch(NullPointerException e)
			{
				System.out.println("[ExtGeoloc]Table 'ExtGeoloc' RETURNED NULL");
		  		return null;
			}
			finally
			{
				System.out.println("[ExtGeoloc] Table 'ExtGeoloc' data ORM state CORRECT");
				System.out.println("[ExtGeoloc] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return dbExtGeoloc;
			}
		
		
		
	}
	
	@SuppressWarnings("finally")
	public ExtBusinessPhone getExtBusinessPhone(String nrid, Session session)
	{
		
		ExtBusinessPhone dbExtBusinessPhone = null;
		
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtBusinessPhone] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtBusinessPhone] Checking table 'ExtBusinessPhone' data ORM state...");
		
		try {
				BigDecimal bnrid = new BigDecimal(nrid);
				
			  	session.getTransaction();

			  	dbExtBusinessPhone = (ExtBusinessPhone) session.get(ExtBusinessPhone.class, bnrid);
			  	
			  	if(dbExtBusinessPhone != null)
			  	{
			  		System.out.println("[ExtBusinessPhone] DATA");
			  		System.out.println("-----------------");
			  		System.out.println("Phone Number: " + dbExtBusinessPhone.getPhoneNumber());
				  	System.out.println("NRID: " + dbExtBusinessPhone.getCaNrid());
				 	System.out.println("-----------------");
				  	session.getTransaction().commit();
			  	}
			  	else
			  	{
			  		
			  		System.out.println("[ExtBusinessPhone]Table 'ExtBusinessPhone' RETURNED NULL");
			  		return null;
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[ExtBusinessPhone]Table 'ExtBusinessPhone' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ExtBusinessPhone]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}catch (NumberFormatException e)
			{
				System.out.println("[ExtBusinessPhone] NRID is not in Big Decimal format.");
				return null;
			}
			catch(NullPointerException e)
			{
				System.out.println("[ExtBusinessPhone]Table 'ExtBusinessPhone' RETURNED NULL");
		  		return null;
			}
			finally
			{
				System.out.println("[ExtBusinessPhone] Table 'ExtBusinessPhone' data ORM state CORRECT");
				System.out.println("[ExtBusinessPhone] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return dbExtBusinessPhone;
			}
		
		
		
	}
	
	@SuppressWarnings("finally")
	public ExtBusinessAddress getExtBusinessAddress(String ca0_nrid, Session session)
	{
		
		ExtBusinessAddress dbExtBusinessAddress = null;
		
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtBusinessAddress] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtBusinessAddress] Checking table 'ExtBusinessAddress' data ORM state...");
		
		try {
				BigDecimal bnca0_rid = new BigDecimal(ca0_nrid);
				
			  	session.getTransaction();

			  	dbExtBusinessAddress = (ExtBusinessAddress) session.get(ExtBusinessAddress.class, bnca0_rid);
			  	
			  	if(dbExtBusinessAddress != null)
			  	{
			  		System.out.println("[ExtBusinessAddress] DATA");
			  		System.out.println("-----------------");
			  		System.out.println("City: " + dbExtBusinessAddress.getCity());
				  	System.out.println("Line1: " + dbExtBusinessAddress.getLine1());
				  	System.out.println("Line2: " + dbExtBusinessAddress.getLine2());
				  	System.out.println("Postal Code: " + dbExtBusinessAddress.getPostalCode());
				  	System.out.println("Region: " + dbExtBusinessAddress.getRegion());
				  	System.out.println("NRID: " + dbExtBusinessAddress.getCaNrid());
				 	System.out.println("-----------------");
				  	session.getTransaction().commit();
			  	}
			  	else
			  	{
			  		
			  		System.out.println("[ExtBusinessAddress]Table 'ExtBusinessAddress' RETURNED NULL");
			  		return null;
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[ExtBusinessAddress]Table 'ExtBusinessAddress' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ExtBusinessAddress]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}catch (NumberFormatException e)
			{
				System.out.println("[ExtBusinessAddress] NRID is not in Big Decimal format.");
				return null;
			}
			catch(NullPointerException e)
			{
				System.out.println("[ExtBusinessAddress]Table 'ExtBusinessAddress' RETURNED NULL");
		  		return null;
			}
			finally
			{
				System.out.println("[ExtBusinessAddress] Table 'ExtBusinessAddress' data ORM state CORRECT");
				System.out.println("[ExtBusinessAddress] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return dbExtBusinessAddress;
			}
		
		
		
	}
	
	public List<?> getExtCustomKeywordHQL(String adgroup_id, Session session)
	{
		String queryString = "SELECT a.adgroup_id, b.id, b.keyword "
				+ "FROM  ExtAdgroup a , ExtCustomKeyword b, ExtLinkedKeyword c "
				+ "WHERE "
				+ "c.adgroup_nrid = a.nrid and "
				+ "c.keyword_nrid = b.nrid and "
				+ "a.adgroup_id = :adgroupid";

		Query query = session.createQuery(queryString);
		query.setParameter("adgroupid", new BigDecimal(adgroup_id));
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	
	public List<?> getExtCustomAdcopyHQL(String adgroup_id, Session session)
	{
		String queryString = "SELECT b.nrid, b.title,b.line1,b.line2 "
				+ "FROM ExtAdgroup a, ExtCustomAdcopy b, ExtLinkedAdcopy c "
				+ "WHERE "
				+ "c.adgroup_nrid = a.nrid and "
				+ "c.adcopy_nrid = b.nrid and "
				+ "a.adgroup_id = :adgroupid";
		
		Query query = session.createQuery(queryString);
		query.setParameter("adgroupid", new BigDecimal(adgroup_id));
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	public List<?> getExtCustomAdgroupHQL(String advertid, Session session)
	{
		
		String queryString = "SELECT b.adgroup_id, b.adgroup_name "
				+ "FROM ExtAdvert a, ExtAdgroup b "
				+ "WHERE a.heading_nrid = b.heading_nrid AND a.ca0_nrid = b.ca0_nrid "
				+ "AND a.template IS NULL "
				+ "AND b.template IS NULL "
				+ "AND a.advert_id = :advertid";
		
		
		Query query = session.createQuery(queryString);
		query.setParameter("advertid", new BigDecimal(advertid));
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	public List<?> getExtGeolocHQL(String advertid, Session session)
	{
		
		String queryString = "SELECT distinct (b.radiusPostalCode), b.radius "
				+ "FROM ExtAdvert a, ExtGeoloc b "
				+ "WHERE a.heading_nrid = b.heading_nrid AND a.ca0_nrid = b.ca0_nrid "
				+ "AND a.template IS NULL "
				+ "AND b.template IS NULL "
				+ "AND a.advert_id = :advertid";
		
		
		Query query = session.createQuery(queryString);
		query.setParameter("advertid", new BigDecimal(advertid));
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	public List<?> getExtSitelinkHQL(String advertid, Session session)
	{
		
		String queryString = "SELECT b.sl_name, b.requested_url "
				+ "FROM ExtAdvert a, ExtSitelink b "
				+ "WHERE a.heading_nrid = b.heading_nrid AND a.ca0_nrid = b.ca0_nrid "
				+ "AND a.template IS NULL "
				+ "AND b.template IS NULL "
				+ "AND a.advert_id = :advertid";
		
		
		Query query = session.createQuery(queryString);
		query.setParameter("advertid", new BigDecimal(advertid));
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	public List<?> getNewAdvertisersHQL(Session session)
	{
		
		String queryString = "SELECT b.sl_name, b.requested_url "
				+ "FROM ca0 a"
				+ "WHERE a.heading_nrid = b.heading_nrid AND a.ca0_nrid = b.ca0_nrid "
				+ "AND a.template IS NULL "
				+ "AND b.template IS NULL ";
		
		
		Query query = session.createQuery(queryString);
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	

}
