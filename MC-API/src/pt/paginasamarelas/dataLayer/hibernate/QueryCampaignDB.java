package pt.paginasamarelas.dataLayer.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pt.paginasamarelas.dataLayer.entities.PointRadius;
import pt.paginasamarelas.dataLayer.hibernate.entities.Ad_Copy;
import pt.paginasamarelas.dataLayer.hibernate.entities.Ad_Group;
import pt.paginasamarelas.dataLayer.hibernate.entities.Ad_Group_Heading_Name;
import pt.paginasamarelas.dataLayer.hibernate.entities.Ad_Group_Keyword;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtAdgroup;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtAdvert;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtBusinessAddress;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtBusinessPhone;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtGeoloc;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtHeading;
import pt.paginasamarelas.dataLayer.hibernate.entities.Parameters;
import pt.paginasamarelas.dataLayer.hibernate.entities.ca0;
import pt.paginasamarelas.dataLayer.hibernate.entities.dual;
import pt.paginasamarelas.dataLayer.hibernate.entities.Kwac_Adg_Keyword_V;

public class QueryCampaignDB {
	
	//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	//Session session = sessionFactory.openSession();
	
	private String lastRunDate = null;
	private Integer prmRowVersion = 0;
	
	private ApplicationContext context;

	@SuppressWarnings("finally")
	public ca0 getCa0(String nrid, Session session)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		ca0 dbca0 = (ca0) context.getBean("ca0");
		
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
				  	System.out.println("Name: " + dbca0.getVar3());
				  	System.out.println("Network: " + dbca0.getNetworks());
				  	System.out.println("Target Retail Spend: " + dbca0.getTargetRetailSpend());
				  	System.out.println("Start date: " + dbca0.getStartDate());
				  	System.out.println("End date: " + dbca0.getEndDate());
				  	System.out.println("NRID: " + dbca0.getNrid());
				 	System.out.println("-----------------");
// FM08.08.2016				  	session.getTransaction().commit();
				  	session.getTransaction().rollback();
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
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<ExtAdvert> getExAdvert(String nrid, Session session)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		//ExtAdvert dbExtAdvert = null;
		List<ExtAdvert> dbExtAdvertList = null;
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtAdvert] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtAdvert] Checking table 'ExtAdvert' data ORM state...");
		
		try {
				BigDecimal bnrid = new BigDecimal(nrid);
				
			  	session.getTransaction();
			  	
			  	Criteria advCriteria = session.createCriteria(ExtAdvert.class).add(Restrictions.eq("ca0_nrid", bnrid)); 			  	
			  	
			  	dbExtAdvertList = advCriteria.add(Restrictions.isNull("template") ).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
			  	
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
				    	  ExtAdvert exadvert = (ExtAdvert) context.getBean("extadvert");
				    	  exadvert = (ExtAdvert) it.next();
				    	  
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
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<ExtAdgroup> getExAdgroup(String nrid, Session session)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		List<ExtAdgroup> dbExtAdgroupList = null;
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtAdgroup] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtAdgroup] Checking table 'ExtAdgroup' data ORM state...");
		
		try {
				BigDecimal bnrid = new BigDecimal(nrid);
				
			  	session.getTransaction();
			  	
			  	
			  	//dbExtAdgroup = (ExtAdgroup) session.get(ExtAdgroup.class, bnrid);
// FM 20.06.2016			  	dbExtAdgroupList = session.createCriteria(ExtAdgroup.class).add(Restrictions.eq("ca0_nrid", bnrid)).list();
			  	Criteria adgCriteria = session.createCriteria(ExtAdgroup.class).add(Restrictions.eq("nrid", bnrid)); 			  	
			  	
			  	dbExtAdgroupList = adgCriteria.add(Restrictions.isNull("template") ).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();

			  	int counter=1;
			  	if(dbExtAdgroupList != null)
			  	{
			  		System.out.println("[ExtAdgroup] DATA");
			  		System.out.println("-----------------");
			  		
			  		for (Iterator<?> iterator = dbExtAdgroupList.iterator(); iterator.hasNext();){
		            	  System.out.println("");
		            	  System.out.println("ADGROUP"+counter+" :");
		            	  System.out.println("");
		            	  ExtAdgroup exadgroup = (ExtAdgroup) context.getBean("extadgroup");
		            	  exadgroup = (ExtAdgroup) iterator.next();
		            	  
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
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		ExtGeoloc dbExtGeoloc = (ExtGeoloc) context.getBean("extgeoloc");
		
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtGeoloc] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtGeoloc] Checking table 'ExtGeoloc' data ORM state...");
		
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
	public ExtHeading getExtHeading(String nrid, Session session)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		ExtHeading dbExtHeading = (ExtHeading) context.getBean("extheading");
		
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtHeading] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtHeading] Checking table 'ExtHeading' data ORM state...");
		
		try {
				BigDecimal bnrid = new BigDecimal(nrid);
				
			  	session.getTransaction();

// FM 20.06.2016			  	dbExtHeading = (ExtHeading) session.get(ExtHeading.class, bnrid);
			  	Criteria extHeadCriteria = session.createCriteria(ExtHeading.class).add(Restrictions.and(Restrictions.eq("nrid", bnrid),Restrictions.isNull("template")));
			  	extHeadCriteria.setMaxResults(1);
			  	dbExtHeading = (ExtHeading) extHeadCriteria.uniqueResult();
			  	
			  	if(dbExtHeading != null)
			  	{
			  		System.out.println("[ExtHeading] DATA");
			  		System.out.println("-----------------");
				  	System.out.println("Heading: " + dbExtHeading.getHeading());
//				 	System.out.println("NRID: " + dbExtHeading.getCa0_nrid());
				 	System.out.println("NRID: " + dbExtHeading.getCa0_nrid() +"  headingNrid:"+ dbExtHeading.getNrid().toString());
				 	System.out.println("-----------------");
				  	session.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[ExtHeading]Table 'ExtHeading' RETURNED NULL");
			  		return null;
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[ExtHeading]Table 'ExtHeading' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ExtHeading]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
					HibernateUtil.closeSession();
				  	session.close();
				  	System.exit(0);
			
			}catch (NumberFormatException e)
			{
				System.out.println("[ExtHeading] NRID is not in Big Decimal format.");
				return null;
			}
			catch(NullPointerException e)
			{
				System.out.println("[ExtHeading]Table 'ExtHeading' RETURNED NULL");
		  		return null;
			}
			finally
			{
				System.out.println("[ExtHeading] Table 'ExtHeading' data ORM state CORRECT");
				System.out.println("[ExtHeading] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return dbExtHeading;
			}		
		
	}

	@SuppressWarnings("finally")
	public ExtBusinessPhone getExtBusinessPhone(String nrid, Session session)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		ExtBusinessPhone dbExtBusinessPhone = (ExtBusinessPhone) context.getBean("extbusinessphone");
		
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtBusinessPhone] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtBusinessPhone] Checking table 'ExtBusinessPhone' data ORM state...");
		
		try {
				BigDecimal bnrid = new BigDecimal(nrid);
				
			  	session.getTransaction();

/*	FM 17.06.2016		  	Criteria busPhCriteria = session.createCriteria(ExtBusinessPhone.class).add(Restrictions.eq("ca0_nrid", bnrid));
			  	busPhCriteria.add(Restrictions.isNull("template") );
			  	dbExtBusinessPhone = (ExtBusinessPhone) session.get(ExtBusinessPhone.class, bnrid);
*/
			  	Criteria busPhCriteria = session.createCriteria(ExtBusinessPhone.class).add(Restrictions.and(Restrictions.eq("caNrid", bnrid),Restrictions.isNull("template")));

			  	busPhCriteria.setMaxResults(1);
			  	dbExtBusinessPhone = (ExtBusinessPhone) busPhCriteria.uniqueResult();

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
		context = new ClassPathXmlApplicationContext("beans.xml");
		ExtBusinessAddress dbExtBusinessAddress = (ExtBusinessAddress) context.getBean("extbusinessaddress");;
		
		
		System.out.println("------------------------------------------------------------");
		System.out.println("[ExtBusinessAddress] SESSION OPENED");
		session.beginTransaction();
		System.out.println("[ExtBusinessAddress] Checking table 'ExtBusinessAddress' data ORM state...");
		
		try {
				BigDecimal bnca0_rid = new BigDecimal(ca0_nrid);
				
			  	session.getTransaction();

// FM 17.06.2016			  	Criteria busAddCriteria = session.createCriteria(ExtBusinessAddress.class).add(Restrictions.eq("ca0_nrid", bnca0_rid)).add(Restrictions.isNull("template")));
			  	Criteria busAddCriteria = session.createCriteria(ExtBusinessAddress.class).add(Restrictions.and(Restrictions.eq("caNrid", bnca0_rid),Restrictions.isNull("template")));
//			  	busAddCriteria.add(Restrictions.isNull("template") );
//			  	dbExtBusinessAddress = (ExtBusinessAddress) session.get(ExtBusinessAddress.class, bnca0_rid);

			  	busAddCriteria.setMaxResults(1);
			  	dbExtBusinessAddress = (ExtBusinessAddress) busAddCriteria.uniqueResult();
			  	
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
	
	public List<?> getExtCustomKeywordHQL(String ca0_nrid, String heading_nrid, String adgroup_id, Session session)
	{
		String queryString = "SELECT b.ca0_nrid, b.id, b.keyword "
				+ "FROM  ExtAdgroup a, ExtCustomKeyword b, ExtLinkedKeyword c "
				+ "WHERE "
				+ "b.heading_nrid = :headingnrid and "
				+ "c.keyword_nrid = b.nrid and "
				+ "b.template IS NULL and "
				+ "b.disapproved IS NULL and "
				+ "c.template IS NULL and "
				+ "b.ca0_nrid = :ca0nrid and "
				+ "c.adgroup_nrid = a.nrid and "
				+ "a.ca0_nrid = b.ca0_nrid and "
				+ "a.heading_nrid = b.heading_nrid and "
				+ "a.adgroup_id = :adgroupid and "
				+ "not exists (select kv.id from Kwac_Adg_Keyword_V kv where kv.ad_group_id= :adgroupid2 and kv.keyword=Lower(b.keyword))";
		
		Query query = session.createQuery(queryString);
		query.setParameter("headingnrid", new BigDecimal(heading_nrid));
		query.setParameter("ca0nrid", new BigDecimal(ca0_nrid));
		query.setParameter("adgroupid", new BigDecimal(adgroup_id));
		query.setParameter("adgroupid2", new BigDecimal(adgroup_id));
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	
	public List<?> getExtCustomAdcopyHQL(String ca0_nrid, String heading_nrid, String adgroup_id, Session session, boolean includeStdAdcopies)
	{
			String queryString = null;
			if (includeStdAdcopies) {
				queryString = "SELECT b.nrid, b.title, b.line1, b.line2, b.title1, b.title2, b.description "
					+ "FROM ExtAdgroup a, ExtCustomAdcopy b, ExtLinkedAdcopy c "
					+ "WHERE "
					+ "b.heading_nrid = :headingnrid and "
					+ "c.adcopy_nrid = b.nrid and "
					+ "b.disapproved IS NULL and "
					+ "b.template IS NULL and "
					+ "c.template IS NULL and "
					+ "b.ca0_nrid = :ca0nrid and "
					+ "c.adgroup_nrid = a.nrid and "
					+ "a.ca0_nrid = b.ca0_nrid and "
					+ "a.heading_nrid = b.heading_nrid and "
					+ "a.adgroup_id = :adgroupid";
		}
		else {
			queryString = "SELECT b.nrid, b.title, b.line1, b.line2, b.title1, b.title2, b.description "
					+ "FROM ExtAdgroup a, ExtCustomAdcopy b, ExtLinkedAdcopy c "
					+ "WHERE "
					+ "b.heading_nrid = :headingnrid and "
					+ "c.adcopy_nrid = b.nrid and "
					+ "b.disapproved IS NULL and "
					+ "b.template IS NULL and "
					+ "c.template IS NULL and "
					+ "b.ca0_nrid = :ca0nrid and "
					+ "b.title2 IS NOT NULL and "
					+ "c.adgroup_nrid = a.nrid and "
					+ "a.ca0_nrid = b.ca0_nrid and "
					+ "a.heading_nrid = b.heading_nrid and "
					+ "a.adgroup_id = :adgroupid";
		}
		Query query = session.createQuery(queryString);
		query.setParameter("headingnrid", new BigDecimal(heading_nrid));
		query.setParameter("ca0nrid", new BigDecimal(ca0_nrid));
		query.setParameter("adgroupid", new BigDecimal(adgroup_id));
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	public List<?> getExtCustomAdgroupHQL(String advertid, String heading_nrid, String subscrId, Session session)
	{
		
		String queryString = "SELECT b.adgroup_id, b.adgroup_name, a.ca0_nrid, a.heading_nrid "
				+ "FROM ExtAdvert a, ExtAdgroup b "
				+ "WHERE a.heading_nrid = b.heading_nrid AND a.ca0_nrid = b.ca0_nrid "
				+ "AND a.template IS NULL "
				+ "AND b.template IS NULL "
				+ "AND a.heading_nrid=:headingnrid "
				+ "AND charindex('" + subscrId + "',b.adgroup_name)=0 "
				+ "AND a.advert_id = :advertid";

		
		Query query = session.createQuery(queryString);
		query.setParameter("advertid", new BigDecimal(advertid));
		query.setParameter("headingnrid", heading_nrid);
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
		
	public List<?> getKwacCustomAdgroupHQL(String subscrId, String advertid, String heading_nrid, Session session)
	{
// Saca só os Adgroups específicos do Cliente para esta Campanha 		
		String queryString = "SELECT b.adgroup_id, b.adgroup_name, a.ca0_nrid, a.heading_nrid "
				+ "FROM ExtAdvert a, ExtAdgroup b "
				+ "WHERE a.heading_nrid = b.heading_nrid AND a.ca0_nrid = b.ca0_nrid "
				+ "AND a.template IS NULL "
				+ "AND b.template IS NULL "
				+ "AND b.adgroup_name LIKE '%" + subscrId + "%'"
				+ "AND a.heading_nrid=:headingnrid "
				+ "AND a.advert_id = :advertid";
				
		Query query = session.createQuery(queryString);
		query.setParameter("advertid", new BigDecimal(advertid));
		query.setParameter("headingnrid", heading_nrid);
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}

	public List<?> getKwacAdgroupHQL(String adgroup_id, Session session)
	{
		String queryString = "SELECT a.id, a.name, a.status "
				+ "FROM Ad_Group a "
				+ "WHERE "
				+ "a.id = :adgroupid ";
//				+ " AND a.status = 'ACTV' ";
		
		Query query = session.createQuery(queryString);
		query.setParameter("adgroupid", new BigDecimal(adgroup_id));
		List<?> results = query.list();
		
		session.clear();
		HibernateKwacUtil.closeKwacSession();
	  	session.close();
		
		return results;
	}	
	

	public List<?> getKwacAdgroupByXRefIdHQL(String adgroup_name, Session session)
	{
		String queryString = "SELECT a.id, a.name, a.status, a.external_ref_id "
				+ "FROM Ad_Group a "
				+ "WHERE "
				+ "a.external_ref_id = :adgroupXRefId ";
//				+ " AND a.status = 'ACTV' ";
		
		Query query = session.createQuery(queryString);
		query.setParameter("adgroupXRefId", adgroup_name);
		List<?> results = query.list();
		
		session.clear();
		HibernateKwacUtil.closeKwacSession();
	  	session.close();
		
		return results;
	}	
	
	public List<?> getKwacCustomAdcopyHQL(String adgroup_id, Session session, boolean includeStdAdcopies)
	{
		String queryString = null;
		if (includeStdAdcopies) {
			queryString = "SELECT a.id, a.adgroup_id, a.title, a.line1, a.line2, ISNULL(a.title1,'') as title1, "
				+ "ISNULL(a.title2,'') as title2, ISNULL(a.adDescription,'') as adDescription, a.status "
				+ "FROM Ad_Copy a "
				+ "WHERE "
				+ "a.adgroup_id = :adgroupid "
				+ "AND a.status = 'ACTV' ";
		}
		else {
			queryString = "SELECT a.id, a.adgroup_id, a.title, a.line1, a.line2, ISNULL(a.title1,'') as title1, "
					+ "ISNULL(a.title2,'') as title2, ISNULL(a.adDescription,'') as adDescription, a.status "
					+ "FROM Ad_Copy a "
					+ "WHERE ISNULL(a.title2,'***')!='***' AND "
					+ "a.adgroup_id = :adgroupid "
					+ "AND a.status = 'ACTV' ";
		}
// FM 29.12.2016  ---^  PARA JÁ TEM QUE SER POR CAUSA DOS REGISTOS INACTIVOS ! 		
		Query query = session.createQuery(queryString);
		query.setParameter("adgroupid", new BigDecimal(adgroup_id));
		List<?> results = query.list();
		
		session.clear();
//		HibernateKwacUtil.closeKwacSession();
//	  	session.close();
		
		return results;
	}	
	
	public List<?> getKwacCustomKeywordHQL(String adgroup_id, Session session)
	{
		String queryString = "SELECT b.id, b.keyword, b.keyword_type "
				+ "FROM  Ad_Group_Keyword b "
				+ "WHERE "
				+ "b.ad_group_id = :adgroupid AND "
				+ "b.status = 'ACTV' ";
		
		Query query = session.createQuery(queryString);
		query.setParameter("adgroupid", new BigDecimal(adgroup_id));
		List<?> results = query.list();
		
		session.clear();
		HibernateKwacUtil.closeKwacSession();
	  	session.close();
		
		return results;
	}
	
	public List<?> getKwacKeyphrasesHQL(String adgroup_id, Session session)
	{
		String queryString = "SELECT b.id, b.ad_group_id, b.keyword_type_code_group, b.keyword_type, b.keyword, b.normalized_keyword, "
				+ "b.status_code_group, b.status, b.updated_by, b.updated_by_proc, b.version_no, b.is_flagged "
				+ "FROM  Ad_Group_Keyword b "
				+ "WHERE "
				+ "b.ad_group_id = :adgroupid ";
		
		Query query = session.createQuery(queryString);
		query.setParameter("adgroupid", new BigDecimal(adgroup_id));
		List<?> results = query.list();
		
		session.clear();
		HibernateKwacUtil.closeKwacSession();
	  	session.close();
		
		return results;
	}
	
	public List<?> getExtGeolocHQL(String ca0Nrid, String headingNrid, Session session)
	{
		
		String queryString = "SELECT distinct b.radiusPostalCode, b.radius, b.geoloc_name "
				+ "FROM ExtGeoloc b "
				+ "WHERE b.heading_nrid = :headingnrid "
				+ "AND b.template IS NULL "
				+ "AND b.ca0_nrid = :ca0nrid";
		
		
		Query query = session.createQuery(queryString);
		query.setParameter("ca0nrid", new BigDecimal(ca0Nrid));
		query.setParameter("headingnrid", new BigDecimal(headingNrid));
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
/* FM 04.05.2016 - queries que vão ao Dossr ! */
	public String getDossrGeoInfoXHQL(String advertid, String postalCode, Session dossrSession)
	{
/*		
		String namedQuery = "findXcoordCodeNativeSQL";
		
		Query query = dossrSession.createQuery(namedQuery);
		query.setParameter("postalCode", postalCode);
		query.setParameter("advertid", new BigDecimal(advertid));
*/
	 	System.out.println("[getDossrGeoInfoXHQL] AdvId:"+advertid+"  Cp7:"+ postalCode +" \n");
		Query query = dossrSession.getNamedQuery("findXcoordCodeNativeSQL")
				.setString("advertid", advertid);
		String colName = "X_COORDINATE";
		query.setString("columnname", colName);
		query.setString("postalcode", postalCode);

//		<List>? results = (dual) query.list();
		Object rsQry = query.uniqueResult();
		dual results = null;
		if (rsQry == null) {
		 	System.out.println("[getDossrGeoInfoXHQL] rsQry devolveu null!\n");
		 	results = new dual();
			results.setDummy("-8.33395189");
		}
		else
			results = (dual) rsQry;
		
		dossrSession.clear();
// faz para a Y!		HibernateOraUtil.closeOraSession();
//		dossrSession.close();
		
		return results.getDummy();
	}
		
	public String getDossrGeoInfoYHQL(String advertid, String postalCode, Session dossrSession)
	{
/*		
		String queryString = "SELECT DOSSR_BASIC_INFO_2_PKG.GET_NEW_RADIUS_INFO_FCT(:advertid2,NULL,NULL,NULL,NULL,NULL,'Y_COORDINATE','" +postalCode+ "') AS dummy "
				+ "FROM dual";
		
		Query query = dossrSession.createQuery(queryString);
		query.setParameter("advertid2", new BigDecimal(advertid));
*/
		Query query = dossrSession.getNamedQuery("findXcoordCodeNativeSQL")
				.setString("advertid", advertid);
		String colName = "Y_COORDINATE";
		query.setString("columnname", colName);
		query.setString("postalcode", postalCode);

//		<List>? results = (dual) query.list();
		Object rsQry = query.uniqueResult();
		dual results = null;
		if (rsQry == null) {
		 	System.out.println("[getDossrGeoInfoYHQL] rsQry devolveu null!\n");
			results = new dual();
			results.setDummy("39.48254169");
		}
		else
			results = (dual) rsQry;
		
		dossrSession.clear();
		HibernateOraUtil.closeOraSession();
		dossrSession.close();
		
		return results.getDummy();
	}
	
	public void getDossrIntfDate(Session dossrSession, Integer pRowVersion)
	{
		String prmValue = null;

		Parameters param = (Parameters) dossrSession.get(Parameters.class, "MC_API_INTF_RUNDATE");
		prmValue = new String (param.getParamValue());
		pRowVersion = param.getRowVersion();
		
		if (prmValue==null || prmValue.length()==0)
			setLastIntfDate("2016-06-20 12:00:00");
		else
			setLastIntfDate(prmValue);
		
/*
 		Query query = dossrSession.getNamedQuery("getLastRunDate");

		Object rsQry = query.uniqueResult();
		Parameters results = null;
		results = new Parameters();
		if (rsQry == null) {
			results.setParamValue("2016-06-20 12:00:00");
		}
		else
			results.setParamValue( rsQry.getClass() );
		
		dossrSession.clear();
		
		return results.getParamValue();
*/
	}		
		
	@SuppressWarnings("finally")
	public int updDossrIntfDate(Session dossrSession, boolean pNormalRun)
	{
		int retPrc = 0;
		int ret = 0;

		String queryString = null;
		
		if (pNormalRun) {
			queryString = "UPDATE Parameters"
				+ " SET paramValue=to_char(sysdate,'YYYY-MM-DD HH24:MI:SS'),rowVersion=0"
				+ " WHERE paramName='MC_API_INTF_RUNDATE'";
		}
		else {
			queryString = "UPDATE Parameters"
					+ " SET paramValue=to_char(sysdate,'YYYY-MM-DD HH24:MI:SS')"
					+ " WHERE paramName='MC_API_INTF_RUNDATE'";
		}
		System.out.println("------------------------------------------------------------");
		dossrSession.beginTransaction();
		System.out.println("[updIntfDate] ini");
		
		try {
				
				
				dossrSession.getTransaction();
				Query query = dossrSession.createQuery(queryString);
				ret = query.executeUpdate();
				if (ret>0) {
					dossrSession.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[updIntfDate] no_rows UPDATED");
			  		retPrc = 1;
			  		return retPrc;
			  	}

				dossrSession.clear();
				HibernateOraUtil.closeOraSession();
				dossrSession.close();
			  	
			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[Parameters]Table 'parameters' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[Parameters]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					dossrSession.getTransaction().rollback();
					dossrSession.clear();
					dossrSession.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[ca0]Table 'ca0' RETURNED NULL");
		  		retPrc = 2;
		  		return retPrc;
			}
			finally
			{
				System.out.println("[updIntfDate] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
		  		retPrc = 0;
		  		return retPrc;
			}
	}		
	
	@SuppressWarnings("finally")
	public String createKwacAdgroup(Session kwacSession, Ad_Group newAdgroup)
	{
		String retPrc = "0:";
		int ret = 0;

		String insString = null;
		
		if (newAdgroup.getExternal_ref_id()==null) {
			insString = "INSERT INTO dbo.ad_group "
						+ "(search_engine_code"
						+ ",language_locale"
						+ ",cpc_threshold"
						+ ",name"
						+ ",normalized_name, short_name_in_template, long_name_in_template"
						+ ",display_url"
						+ ",status_code_group"
						+ ",status"
						+ ",update_date"
						+ ",updated_by"
						+ ",updated_by_proc"
						+ ",version_no"
						+ ",is_flagged)";
			insString = insString.concat(" VALUES ('" +newAdgroup.getSearch_engine_code()+ "','" +newAdgroup.getLanguage_locale()+ "'," +newAdgroup.getCpc_threshold() + ",'" +newAdgroup.getName()+ "','" +newAdgroup.getNormalized_name() + "','" +newAdgroup.getShort_name_in_template() + "','" +newAdgroup.getLong_name_in_template() + "','" +newAdgroup.getDisplay_url()+ "','ACTIVE_IND','ACTV',getdate(),'MCRAFT_PT','KWAC',0,'0')");
		}
		else {
			insString = "INSERT INTO dbo.ad_group "
				+ "(search_engine_code"
				+ ",language_locale"
				+ ",cpc_threshold"
				+ ",name"
				+ ",normalized_name, short_name_in_template, long_name_in_template"
				+ ",display_url"
				+ ",status_code_group"
				+ ",status"
				+ ",update_date"
				+ ",updated_by"
				+ ",updated_by_proc"
				+ ",version_no"
				+ ",is_flagged"
				+ ",external_ref_id)";
			insString = insString.concat(" VALUES ('" +newAdgroup.getSearch_engine_code()+ "','" +newAdgroup.getLanguage_locale()+ "'," +newAdgroup.getCpc_threshold() + ",'" +newAdgroup.getName()+ "','" +newAdgroup.getNormalized_name() + "','" +newAdgroup.getShort_name_in_template() + "','" +newAdgroup.getLong_name_in_template() + "','" +newAdgroup.getDisplay_url()+ "','ACTIVE_IND','ACTV',getdate(),'MCRAFT_PT','KWAC',0,'0','" +newAdgroup.getExternal_ref_id()+ "')");
		}

      	kwacSession.beginTransaction();
		System.out.println("[insAdg] ini");
		
		try {
				
				kwacSession.getTransaction();
// !!!				Query query = kwacSession.createQuery(insString);
				Query query = kwacSession.createSQLQuery(insString);
				ret = query.executeUpdate();
				if (ret>0) {
					kwacSession.getTransaction().commit();
//					String query2String = "SELECT Cast(Max(id) as VARCHAR(8)) as MaxID FROM dbo.ad_group ";
					String query2String = "SELECT Convert(VARCHAR(8),Max(id)) as MaxID FROM dbo.ad_group ";
					
					
					Query query2 = kwacSession.createQuery(query2String);
					Iterator<?> qry2Iterator = query2.iterate();
					String sMaxId = new String(qry2Iterator.next().toString());
					retPrc.concat(sMaxId);
			  	}
			  	else
			  	{			  		
			  		System.out.println("[insAdg] no_rows INSERTED");
			  		retPrc = "1:no_rows INSERTED";
			  		return retPrc;
			  	}

				kwacSession.clear();
				HibernateKwacUtil.closeKwacSession();;
				kwacSession.close();
			  	
			}
			
			catch (HibernateException e) 
			{
					System.out.println("[insAdg]Table 'ad_group' data ORM state FAILED: ");
					System.out.println(e);
					e.printStackTrace();
			  		retPrc = "2:" + e.toString();
					System.out.println("[insAdg]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					kwacSession.getTransaction().rollback();
					kwacSession.clear();
					kwacSession.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[ca0]Table 'ca0' RETURNED NULL");
		  		retPrc = "2:null";
		  		return retPrc;
			}
			finally
			{
				System.out.println("[insAdg] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
		  		return retPrc;
			}
	}

	@SuppressWarnings("finally")
	public String createKwacAdcopy(Session kwacSession, Ad_Copy newAdcopy)
	{
		String retPrc = "0";
		int ret = 0;

		String insString = null;
		
//			insString = "INSERT INTO Ad_Copy "
		insString = "INSERT INTO dbo.ad_copy "
					+ "(ad_group_id"
				      + ",title"
				      + ",description_line_1"
				      + ",description_line_2"
				      + ",title1,title2,ad_description"
				      + ",status"
				      + ",status_code_group"
				      + ",ad_copy_template_id"
				      + ",update_date"
				      + ",updated_by"
				      + ",updated_by_proc"
				      + ",version_no)";
      		if (newAdcopy.getTitle2()==null)
      			/* STANDARD ADCOPY */
      			insString = insString.concat(" VALUES (" +newAdcopy.getAdgroup_id()+ ",'" +newAdcopy.getTitle()+ "','" +newAdcopy.getLine1()+ "','" +newAdcopy.getLine2()+ "',NULL,NULL,NULL,'ACTV','ACTIVE_IND',NULL,getdate(),'MCRAFT_PT','KWAC',0)");
      		else
      			/* EXPANDED ADCOPY */
      			insString = insString.concat(" VALUES (" +newAdcopy.getAdgroup_id()+ ",'" +newAdcopy.getTitle()+ "','" +newAdcopy.getLine1()+ "','" +newAdcopy.getLine2()+ "','" +newAdcopy.getTitle1() + "','" +newAdcopy.getTitle2() + "','" +newAdcopy.getAdDescription()+ "','ACTV','ACTIVE_IND',NULL,getdate(),'MCRAFT_PT','KWAC',0)");

      	kwacSession.beginTransaction();
		System.out.println("[insAdc] ini");
		
		try {
				
				kwacSession.getTransaction();
// !!!				Query query = kwacSession.createQuery(insString);
				Query query = kwacSession.createSQLQuery(insString);
				ret = query.executeUpdate();
				if (ret>0) {
					kwacSession.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[insAdc] no_rows INSERTED");
			  		retPrc = "1:no_rows INSERTED";
			  		return retPrc;
			  	}

				kwacSession.clear();
				HibernateKwacUtil.closeKwacSession();;
				kwacSession.close();
			  	
			}
			
			catch (HibernateException e) 
			{
					System.out.println("[insAdc]Table 'ad_copy' data ORM state FAILED: ");
					System.out.println(e);
					e.printStackTrace();
					System.out.println("[insAdc]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					kwacSession.getTransaction().rollback();
					kwacSession.clear();
					kwacSession.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[ca0]Table 'ca0' RETURNED NULL");
		  		retPrc = "2:null";
		  		return retPrc;
			}
			finally
			{
				System.out.println("[insAdc] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
		  		retPrc = "0:OK";
		  		return retPrc;
			}
	}

	@SuppressWarnings("finally")
	public String updateKwacAdcopy(Session kwacSession, Ad_Copy newAdcopy)
	{
		String retPrc = "0";
		int ret = 0;

		String updString = null;		

  		if (newAdcopy.getTitle2()==null) {
  			/* STANDARD ADCOPY */
			updString = "UPDATE dbo.ad_copy "
				      + "SET title='" +newAdcopy.getTitle()+ "'"
				      + ",description_line_1='" +newAdcopy.getLine1()+ "'"
				      + ",description_line_2='" +newAdcopy.getLine2()+ "'"
				      + ",update_date=getdate()"
				      + ",updated_by='MCRAFT_PT'"
				      + ",updated_by_proc='KWAC'"
				      + ",version_no=version_no + 1";
		}
      	else {
  			/* EXPANDED ADCOPY */
			updString = "UPDATE dbo.ad_copy "
				      + "SET title='" +newAdcopy.getTitle()+ "'"
				      + ",description_line_1='" +newAdcopy.getLine1()+ "'"
				      + ",description_line_2='" +newAdcopy.getLine2()+ "'"
				      + ",update_date=getdate()"
				      + ",updated_by='MCRAFT_PT'"
				      + ",updated_by_proc='KWAC'"
				      + ",version_no=version_no + 1"
				      + ",title1='" +newAdcopy.getTitle1()+ "'"
				      + ",title2='" +newAdcopy.getTitle2()+ "'"
				      + ",ad_description='" +newAdcopy.getAdDescription()+ "'";
      	}
  		updString = updString.concat(" WHERE id=" +newAdcopy.getId());
      	kwacSession.beginTransaction();
		System.out.println("[updAdc] ini");
		
		try {
				
				kwacSession.getTransaction();
				Query updqry = kwacSession.createSQLQuery(updString);
				ret = updqry.executeUpdate();
				if (ret>0) {
					kwacSession.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[updAdc] no_rows UPDATED");
			  		retPrc = "1:no_rows UPDATED";
			  		return retPrc;
			  	}

				kwacSession.clear();
				HibernateKwacUtil.closeKwacSession();;
				kwacSession.close();
			  	
			}
			
			catch (HibernateException e) 
			{
					System.out.println("[updAdc]Table 'ad_copy' data ORM state FAILED: ");
					System.out.println(e);
					e.printStackTrace();
					System.out.println("[updAdc]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					kwacSession.getTransaction().rollback();
					kwacSession.clear();
					kwacSession.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[updAdc]Table 'ad_copy' RETURNED NULL");
		  		retPrc = "2:null";
		  		return retPrc;
			}
			finally
			{
				System.out.println("[updAdc] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
		  		retPrc = "0:OK";
		  		return retPrc;
			}
	}

	@SuppressWarnings("finally")
	public String createKwacKeyphrase(Session kwacSession, Ad_Group_Keyword newKeyphrase)
	{
		String retPrc = "0";
		int ret = 0;

		String insString = null;
		
		insString = "INSERT INTO dbo.ad_group_keyword "
					+ "(ad_group_id"
				      + ",keyword_type_code_group"
				      + ",keyword_type"
				      + ",keyword"
				      + ",normalized_keyword"
				      + ",status"
				      + ",status_code_group"
				      + ",update_date"
				      + ",updated_by"
				      + ",updated_by_proc"
				      + ",version_no"
				      + ",is_flagged)";
  			insString = insString.concat(" VALUES (" +newKeyphrase.getAd_group_id()+ ",'" +newKeyphrase.getKeyword_type_code_group()+ "','" +newKeyphrase.getKeyword_type()+ "','" +newKeyphrase.getKeyword()+  "','" +newKeyphrase.getNormalized_keyword()+ "','ACTV','ACTIVE_IND',getdate(),'MCRAFT_PT','KWAC',0,'0')");

      	kwacSession.beginTransaction();
		System.out.println("[insKeyw] ini");
		
		try {
				
				kwacSession.getTransaction();
// !!!				Query query = kwacSession.createQuery(insString);
				Query query = kwacSession.createSQLQuery(insString);
				ret = query.executeUpdate();
				if (ret>0) {
					kwacSession.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[insKeyw] no_rows INSERTED");
			  		retPrc = "1:no_rows INSERTED";
			  		return retPrc;
			  	}

				kwacSession.clear();
				HibernateKwacUtil.closeKwacSession();;
				kwacSession.close();
			  	
			}
			
			catch (HibernateException e) 
			{
					System.out.println("[insKeyw]Table 'ad_group_keyword' data ORM state FAILED: ");
					System.out.println(e);
					e.printStackTrace();
					System.out.println("[insKeyw]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					kwacSession.getTransaction().rollback();
					kwacSession.clear();
					kwacSession.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[insKeyw]Table 'ad_group_keyword' RETURNED NULL");
		  		retPrc = "2:null";
		  		return retPrc;
			}
			finally
			{
				System.out.println("[insKeyw] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
		  		retPrc = "0:OK";
		  		return retPrc;
			}
	}

	@SuppressWarnings("finally")
	public String updateKwacKeyphrase(Session kwacSession, Ad_Group_Keyword newKeyphrase, int modeId)
	{
		String retPrc = "0";
		int ret = 0;

		String updString = null;		

		if (modeId==0) {
			updString = "UPDATE dbo.ad_group_keyword "
				      + "SET keyword_type='" +newKeyphrase.getKeyword_type()+ "'"
				      + ",status='" +newKeyphrase.getStatus()+ "'"
				      + ",update_date=getdate()"
				      + ",updated_by='MCRAFT_PT'"
				      + ",updated_by_proc='KWAC'"
				      + ",version_no=version_no + 1";
		}
		else {
// FM 04.02.2017 - Altera só o status:
			updString = "UPDATE dbo.ad_group_keyword "
				      + "SET status='" +newKeyphrase.getStatus()+ "'"
				      + ",update_date=getdate()"
				      + ",updated_by='MCRAFT_PT'"
				      + ",updated_by_proc='KWAC'"
				      + ",version_no=version_no + 1";
		}
			updString = updString.concat(" WHERE id=" +newKeyphrase.getId().toString());
	    if (!(kwacSession.getTransaction().isActive()))
			kwacSession.beginTransaction();
		System.out.println("[updKeyw] ini");
		
		try {
				
				kwacSession.getTransaction();
				Query updqry = kwacSession.createSQLQuery(updString);
				ret = updqry.executeUpdate();
				if (ret>0) {
					kwacSession.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[updKeyw] no_rows UPDATED");
			  		retPrc = "1:no_rows UPDATED";
			  		return retPrc;
			  	}

				kwacSession.clear();
				HibernateKwacUtil.closeKwacSession();;
				kwacSession.close();
			  	
			}
			
			catch (HibernateException e) 
			{
					System.out.println("[updKeyw]Table 'ad_copy' data ORM state FAILED: ");
					System.out.println(e);
					e.printStackTrace();
					System.out.println("[updKeyw]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					kwacSession.getTransaction().rollback();
					kwacSession.clear();
					kwacSession.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[updKeyw]Table 'ad_group_keyword' RETURNED NULL");
		  		retPrc = "2:null";
		  		return retPrc;
			}
			finally
			{
				System.out.println("[updKeyw] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
		  		retPrc = "0:OK";
		  		return retPrc;
			}
	}

	@SuppressWarnings("finally")
	public String createKwacAdgroupHeadingName(Session kwacSession, Ad_Group_Heading_Name newAdgHeadingName)
	{
		String retPrc = "0";
		int ret = 0;

		String insString = null;
		
		insString = "INSERT INTO dbo.ad_group_heading_name "
					+ "(ad_group_id"
				      + ",heading_name_id"
				      + ",language_locale"
				      + ",update_date"
				      + ",updated_by"
				      + ",updated_by_proc"
				      + ",version_no"
				      + ",default_ad_group"
				      + ",seasonal_ind)";
  			insString = insString.concat(" VALUES (" +newAdgHeadingName.getAd_group_id()+ "," +newAdgHeadingName.getHeading_name_id()+ ",'" +newAdgHeadingName.getLanguage_locale()+ "', getdate(),'MCRAFT_PT','KWAC',0,"+ newAdgHeadingName.getDefault_ad_group() +","+ newAdgHeadingName.getSeasonal_ind() +")");

      	kwacSession.beginTransaction();
		System.out.println("[insAHN] ini");
		
		try {
				
				kwacSession.getTransaction();
// !!!				Query query = kwacSession.createQuery(insString);
				Query query = kwacSession.createSQLQuery(insString);
				ret = query.executeUpdate();
				if (ret>0) {
					kwacSession.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[insKeyw] no_rows INSERTED");
			  		retPrc = "1:no_rows INSERTED";
			  		return retPrc;
			  	}

				kwacSession.clear();
				HibernateKwacUtil.closeKwacSession();;
				kwacSession.close();
			  	
			}
			
			catch (HibernateException e) 
			{
					System.out.println("[insKeyw]Table 'ad_group_heading_name' data ORM state FAILED: ");
					System.out.println(e);
					e.printStackTrace();
					System.out.println("[insAHN]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					kwacSession.getTransaction().rollback();
					kwacSession.clear();
					kwacSession.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[insAHN]Table 'ad_group_heading_name' RETURNED NULL");
		  		retPrc = "2:null";
		  		return retPrc;
			}
			finally
			{
				System.out.println("[insAHN] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
		  		retPrc = "0:OK";
		  		return retPrc;
			}
	}

	public String getHeadingNameId(Session kwacSession, String classCode)
	{
	 	System.out.println("[getHeadingNameId]  tit:"+ classCode +" \n");
		Query query = kwacSession.getNamedQuery("findHeadingNameIdSQL")
				.setString("class_code", classCode);
		String colName = "X_COORDINATE";
		
//		<List>? results = (dual) query.list();
		Object rsQry = query.uniqueResult();
		Ad_Group_Heading_Name results = null;
		if (rsQry == null) {
		 	System.out.println("[getHeadingNameId] rsQry devolveu null!\n");
			results.setHeading_name_id( new BigDecimal("0") );
		}
		else
			results = (Ad_Group_Heading_Name) rsQry;
		
		kwacSession.clear();
		
		return results.getHeading_name_id().toString();
	}

	@SuppressWarnings("finally")
	public int updDossrPrmIntfDateRV(Session dossrSession)
	{
		int retPrc = 0;
		int ret = 0;

		String queryString = null;
		
		queryString = "UPDATE Parameters"
			+ " SET rowVersion=1"
			+ " WHERE paramName='MC_API_INTF_RUNDATE' AND rowVersion=0";

		dossrSession.beginTransaction();
		System.out.println("[updIntfDateRV] ini");
		
		try {				
				dossrSession.getTransaction();
				Query query = dossrSession.createQuery(queryString);
				ret = query.executeUpdate();
				if (ret>0) {
					dossrSession.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[updIntfDateRV] no_rows UPDATED");
			  		retPrc = 1;
			  		return retPrc;
			  	}

				dossrSession.clear();
				HibernateOraUtil.closeOraSession();
				dossrSession.close();
			  	
			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[ParametersRV]Table 'parameters' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ParametersRV]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					dossrSession.getTransaction().rollback();
					dossrSession.clear();
					dossrSession.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[ca0]Table 'ca0' RETURNED NULL");
		  		retPrc = 2;
		  		return retPrc;
			}
			finally
			{
				System.out.println("[updIntfDateRV] SESSION CLOSED");				
		  		retPrc = 0;
		  		return retPrc;
			}
	}		
	
	public List<?> getExtSitelinkHQL(String ca0Nrid, String headingNrid, Session session)
	{
		
		String queryString = "SELECT b.sl_name, b.requested_url "
				+ "FROM ExtAdvert a, ExtSitelink b "
				+ "WHERE a.heading_nrid = b.heading_nrid AND a.ca0_nrid = b.ca0_nrid "
				+ "AND isnull(a.template,0)=0 "
				+ "AND isnull(b.template,0)=0 "
				+ "AND a.heading_nrid = :headingnrid "
				+ "AND a.ca0_nrid = :ca0nrid";
//		+ "AND a.template IS NULL "
//		+ "AND b.template IS NULL "
//		+ "AND a.advert_id = :advertid";
		
		Query query = session.createQuery(queryString);
/* FM 14.12.2016 - Não dá, pois o AdvId pode existir em duas campanhas!
		query.setParameter("advertid", new BigDecimal(advertid));
*/
		query.setParameter("ca0nrid", new BigDecimal(ca0Nrid));
		query.setParameter("headingnrid", headingNrid);
		List<?> results = query.list();
		
		session.clear();
		HibernateUtil.closeSession();
	  	session.close();
		
		return results;
	}
	
	public List<?> getNewAdvertisersHQL(Session session, boolean pNormalRun)
	{
		
		Session dossrSession = HibernateOraUtil.openOraSession();
		getDossrIntfDate(dossrSession, prmRowVersion);
//		lastRunDate = "2016-08-17 20:45:00";
		String upperLimitDate = "2016-10-28 20:00:29";

//		+ " WHERE CONVERT(VARCHAR(8), dmod, 112) = CONVERT(VARCHAR(8), GETDATE(), 112) "
		String queryString = null;
		String query2String = null;
		String query3String = null;
		String query4String = null;
		String query5String = null;
		String query6String = null;
		String query7String = null;
		List<?> results2 = null;
		List<?> results3 = null;
		List<?> results4 = null;
		List<?> results5 = null;
		List<?> results6 = null;
		List<?> results7 = null;
		int	nRecs = 0;

/* FM 19.09.2016 - Security patch: avoid running 2 threads simultaneously ! */
		if (prmRowVersion==1 && pNormalRun) {
			System.out.println("[QryCampDB] Já está uma instancia do interface a correr!\n");
			session.clear();
		  	session.close();
			System.exit(0);
		}
/* FM 31.07.2016 - Passa a usar uma nova coluna da tabela ca0: dat4==intfDate !
 *  PRODUCTION!
 * 		
 */
		if (!pNormalRun) {
			queryString = "SELECT externalId, status, var2, var6, num8, 'CAMP' As orig"
					+ " FROM ca0"
					+ " WHERE networks='BUDGET'"
					+ " AND substring(status,1,1) not in ('1','9')"
					+ " AND REF IN ('94963192_3352926_178376') ";
//					+ " AND REF IN ('32675706_3282635_179899','56496561_3332417_170735','114605773_3329021_173928','115905518_3379814_189365','70348108_3378039_188521','70304480_3379706_189305','91632312_3377319_188236','60518294_3342957_174737') ";
//					+ " AND REF IN ('91985830_3326057_168335','47941960_3277615_174128','96052047_3337701_172738','116322719_3340902_173716','71328300_3341357_173956') ";
//			+ " AND REF IN ('72750702_3335937_171915','97081726_3374040_187259','60191197_3367999_185001','66836702_3376761_187963','110841306_3365597_183556','62750507_3337457_172615','71308759_3337700_172737') ";
//			+ " AND REF IN ('11780459_3366440_183937','97081726_3374040_187259','119631684_3371877_185966','28401752_3373418_186516','46098911_3373419_186544','','82868657_3373957_186777') ";
//					+ " AND REF IN ('120060879_3372077_186044') ";
//					+ " AND REF IN ('97863426_3373463_186538') ";
//					+ " AND REF IN ('71209646_3363937_182964','32675706_3282635_179899') ";
//					+ " AND REF IN ('36349243_3371501_185736','60617512_3371490_185721','121583113_3370657_185838','92961334_3367623_184519','46225745_3329385_169341') ";
					
//					+ " AND REF IN ('91066543_3361038_181609','117556246_3370898_185561','83832680_3370158_185319','73217071_3370677_185496','61439392_3367877_184596','12166946_3371760_185965','118269284_3371939_185956','60684284_3370057_185256','95450489_3370503_185420','24000168_3367838_184637','13311149_3367044_184244','66795611_3371940_185958','93247579_3367785_184562','28942643_3366518_184037','116462525_3284098_186039') ";
//			+ " AND REF IN ('116345158_3362920_185036','61599431_3371297_185638','66021956_3371277_185637','60001392_3370397_185560','88003198_3370417_185396','71141930_3370439_185456','64657438_3362519_185483','89634477_3371597_185756') ";
//					+ " AND REF IN ('81991871_3351457_177556','32728777_3324077_167797','41093289_3357718_180496','121095861_3347997_177190','120701875_3335957_173176','11201971_3364500_183175') ";
//					+ " AND REF IN ('118374066_3364740_183321','52051555_3371757_185876','92628216_3360461_181398') ";
//					+ " AND REF IN ('70276974_3345969_175081','71365816_3363480_182760','56448212_3361962_182059','25074748_3363745_182880','32675706_3282635_179899','115514719_3366819_184119','70191507_3361617_181936','46104932_3367004_184224') ";
			//			+ " AND REF IN ('107166823_3361438_181819') ";
//			+ " AND intfDate IS NULL AND DateAdd(hour,1,dmod) > '" + lastRunDate + "' AND DateAdd(hour,1,dmod) < '" + upperLimitDate + "' AND var3 IS NOT NULL ";
/*			query2String = "SELECT c5.externalId, c5.status, c5.var2, c5.var6, c5.num8, 'SLNK' As orig"
					+ " FROM ca0 AS c5"
					+ " WHERE c5.networks='BUDGET'"
					+ " AND substring(c5.status,1,1) not in ('1','6','9')"
					+ " AND (c5.num8 IS NULL OR c5.num8=0)"
					+ " AND c5.intfDate < '" + lastRunDate +"' AND c5.var3 IS NOT NULL"
					+ " AND c5.nrid IN (SELECT distinct e4.ca0_nrid FROM ExtSitelink e4 WHERE DateAdd(hour,1,e4.dmod) > '2016-09-25 20:00:00' "
					+ " AND e4.dmod<'" + upperLimitDate + "' AND e4.dmod>c5.intfDate)";

			query3String = "SELECT c4.externalId, c4.status, c4.var2, c4.var6, c4.num8, 'BusAd' As orig"
					+ " FROM ca0 AS c4"
					+ " WHERE c4.networks='BUDGET'"
					+ " AND substring(c4.status,1,1) not in ('1','6','9')"
					+ " AND (c4.num8 IS NULL OR c4.num8=0)"
					+ " AND c4.intfDate < '" + lastRunDate +"' AND c4.var3 IS NOT NULL"
					+ " AND c4.nrid IN (SELECT distinct e4.caNrid FROM ExtBusinessAddress e4 WHERE DateAdd(hour,1,e4.dmod) > '2016-09-25 20:00:00' "
					+ " AND e4.dmod<'" + upperLimitDate + "' AND e4.dmod>c4.intfDate)";

			query4String = "SELECT c5.externalId, c5.status, c5.var2, c5.var6, c5.num8, 'BusPh' As orig"
					+ " FROM ca0 AS c5"
					+ " WHERE c5.networks='BUDGET'"
					+ " AND substring(c5.status,1,1) not in ('1','6','9')"
					+ " AND (c5.num8 IS NULL OR c5.num8=0)"
					+ " AND c5.intfDate < '" + lastRunDate +"' AND c5.var3 IS NOT NULL"
					+ " AND c5.nrid IN (SELECT distinct e5.caNrid FROM ExtBusinessPhone e5 WHERE DateAdd(hour,1,e5.dmod) > '2017-09-25 20:00:00' "
					+ " AND e5.dmod<'" + upperLimitDate + "' AND e5.dmod>c5.intfDate)";
*/
			query2String = null;
			query3String = null;
			query4String = null;
		}
		else {
			if (lastRunDate.substring(11, 13).equals("20") || lastRunDate.substring(11, 15).equals("19:5")) {
				queryString = "SELECT externalId, status, var2, var6, num8, 'CANC' As orig"
						+ " FROM ca0"
						+ " WHERE networks='BUDGET'"
						+ " AND status='6 - Cancelled'"
						+ " AND startDate > DateAdd(month,-13,getdate())"
				+ " AND intfDate IS NULL AND DateAdd(hour,1,dmod) > '" + lastRunDate 
				+ "' AND var3 IS NOT NULL ";
//			+ " AND DateAdd(hour,1,dmod) > '" + lastRunDate + "' AND intfDate IS NULL AND var3 IS NOT NULL ";			
//			+ " AND intfDate IS NULL AND DateAdd(hour,1,dmod) > '2016-08-01 08:00:00'  AND DateAdd(hour,1,dmod) < '" + upperLimitDate
// FM 11.08.2016 - Passa a ir buscar as campanhas no estado 4 para checkar o seu estado na Matchcraft
				query2String = "SELECT externalId, status, var2, var6, num8, 'SENT' As orig"
						+ " FROM ca0"
						+ " WHERE networks='BUDGET'"
						+ " AND substring(status,1,1)='4'"
						+ " AND startDate > DateAdd(year,-1,getdate())"
						+ " AND var3 IS NOT NULL ";
//					+ " AND REF LIKE '2%' ";
// FM 21.09.2016
				query3String = "SELECT c5.externalId, c5.status, c5.var2, c5.var6, c5.num8, 'SLNK' As orig"
						+ " FROM ca0 AS c5"
						+ " WHERE c5.networks='BUDGET'"
						+ " AND substring(c5.status,1,1) not in ('1','6','9')"
						+ " AND (c5.num8 IS NULL OR c5.num8=0)"
						+ " AND c5.startDate > DateAdd(month,-13,getdate())"
						+ " AND c5.intfDate < '" + lastRunDate +"' AND c5.var3 IS NOT NULL"
						+ " AND c5.nrid IN (SELECT distinct e4.ca0_nrid FROM ExtSitelink e4 WHERE DateAdd(hour,1,e4.dmod) > '" + lastRunDate + "' AND e4.dmod>c5.intfDate)";

				query4String = "SELECT c4.externalId, c4.status, c4.var2, c4.var6, c4.num8, 'BusAd' As orig"
						+ " FROM ca0 AS c4"
						+ " WHERE c4.networks='BUDGET'"
						+ " AND substring(c4.status,1,1) not in ('1','6','9')"
						+ " AND (c4.num8 IS NULL OR c4.num8=0)"
						+ " AND c4.startDate > DateAdd(month,-13,getdate())"
						+ " AND c4.intfDate < '" + lastRunDate +"' AND c4.var3 IS NOT NULL"
						+ " AND c4.nrid IN (SELECT distinct e4.caNrid FROM ExtBusinessAddress e4 WHERE DateAdd(hour,1,e4.dmod) > '" + lastRunDate + "' AND e4.dmod>c4.intfDate)";

				query5String = "SELECT c5.externalId, c5.status, c5.var2, c5.var6, c5.num8, 'BusPh' As orig"
						+ " FROM ca0 AS c5"
						+ " WHERE c5.networks='BUDGET'"
						+ " AND substring(c5.status,1,1) not in ('1','6','9')"
						+ " AND (c5.num8 IS NULL OR c5.num8=0)"
						+ " AND c5.startDate > DateAdd(month,-13,getdate())"
						+ " AND c5.intfDate < '" + lastRunDate +"' AND c5.var3 IS NOT NULL"
						+ " AND c5.nrid IN (SELECT distinct e5.caNrid FROM ExtBusinessPhone e5 WHERE DateAdd(hour,1,e5.dmod) > '" + lastRunDate + "' AND e5.dmod>c5.intfDate)";

			}
			else {
				queryString = "SELECT externalId, status, var2, var6, num8, 'CAMP' As orig"
						+ " FROM ca0"
						+ " WHERE networks='BUDGET'"
						+ " AND substring(status,1,1) not in ('1','9')"
						+ " AND intfDate IS NULL "
						+ " AND startDate > DateAdd(month,-13,getdate())"
						+ " AND DateAdd(hour,1,dmod) > '" + lastRunDate + "' AND var3 IS NOT NULL ";
//				+ " AND var3 IS NOT NULL ";
//				+ " AND REF IN ('70276974_3345969_175081','71365816_3363480_182760','56448212_3361962_182059','25074748_3363745_182880','32675706_3282635_179899','115514719_3366819_184119','70191507_3361617_181936','46104932_3367004_184224')"

//					+ " AND REF IN ('114643315_3299675_181377') ";
//					+ " AND REF IN ('114667959_3360019_181336') ";
//					+ " AND REF IN ('28080786_3357062_180236') ";
//					+ " AND REF IN ('120239670_3324697_168797') ";
//					+ " AND REF IN ('116360511_3340337_180316') ";
//					+ " AND intfDate IS NULL AND DateAdd(hour,1,dmod) > '2016-09-08 17:11:38'  AND DateAdd(hour,1,dmod) < '" + upperLimitDate + "' AND var3 IS NOT NULL ";
//					+ " AND REF IN ('119080461_3310835_162258') ";
//					+ " AND REF IN ('46123877_3317055_164356') ";
//					+ " AND REF IN ('11701056_3349192_176411') ";
//					+ " AND REF IN ('114643315_3299675_165376') ";
//					+ " AND REF IN ('118987669_3351717_178016') ";
//			+ " AND REF IN ('107151713_3287675_156617') ";
//			+ " AND REF IN ('93257355_3354238_179096') ";
//					+ " AND REF IN ('107152281_3356440_180110') ";
//					+ " AND REF='60472846_3356257_180317' ";


				query2String = "SELECT c2.externalId, c2.status, c2.var2, c2.var6, c2.num8, 'GEO' As orig"
						+ " FROM ca0 AS c2"
						+ " WHERE c2.networks='BUDGET'"
						+ " AND substring(c2.status,1,1) not in ('1','6','9')"
						+ " AND (c2.num8 IS NULL OR c2.num8=0)"
						+ " AND c2.startDate > DateAdd(month,-13,getdate())"
						+ " AND DateAdd(hour,1,c2.dmod) > '" + lastRunDate +"' AND c2.var3 IS NOT NULL"
				+ " AND c2.nrid IN (SELECT distinct e1.ca0_nrid FROM ExtGeoloc AS e1 WHERE DateAdd(hour,1,e1.dmod) > '" + lastRunDate + "' AND e1.dmod>c2.intfDate)";
	
				query3String = "SELECT c3.externalId, c3.status, c3.var2, c3.var6, c3.num8, 'ADCP' As orig"
						+ " FROM ca0 AS c3"
						+ " WHERE c3.networks='BUDGET'"
						+ " AND substring(c3.status,1,1) not in ('1','6','9')"
						+ " AND (c3.num8 IS NULL OR c3.num8=0)"
						+ " AND c3.startDate > DateAdd(month,-13,getdate())"
						+ " AND c3.intfDate < '" + lastRunDate +"' AND c3.var3 IS NOT NULL"
				+ " AND c3.nrid IN (SELECT distinct e2.ca0_nrid FROM ExtCustomAdcopy e2 WHERE DateAdd(hour,1,e2.dmod) > '" + lastRunDate + "' AND e2.dmod>c3.intfDate)";
	
				query4String = "SELECT c4.externalId, c4.status, c4.var2, c4.var6, c4.num8, 'KEYW' As orig"
						+ " FROM ca0 AS c4"
						+ " WHERE c4.networks='BUDGET'"
						+ " AND substring(c4.status,1,1) not in ('1','6','9')"
						+ " AND (c4.num8 IS NULL OR c4.num8=0)"
						+ " AND c4.startDate > DateAdd(month,-13,getdate())"
						+ " AND c4.intfDate < '" + lastRunDate +"' AND c4.var3 IS NOT NULL"
						+ " AND c4.nrid IN (SELECT distinct e3.ca0_nrid FROM ExtCustomKeyword e3 WHERE DateAdd(hour,1,e3.dmod) > '" + lastRunDate + "' AND e3.dmod>c4.intfDate)";
	
				query5String = "SELECT c5.externalId, c5.status, c5.var2, c5.var6, c5.num8, 'ADV' As orig"
						+ " FROM ca0 AS c5"
						+ " WHERE c5.networks='BUDGET'"
						+ " AND substring(c5.status,1,1) not in ('1','6','9')"
						+ " AND (c5.num8 IS NULL OR c5.num8=0)"
						+ " AND c5.startDate > DateAdd(month,-13,getdate())"
						+ " AND c5.intfDate < '" + lastRunDate +"' AND c5.var3 IS NOT NULL"
						+ " AND c5.nrid IN (SELECT distinct e4.ca0_nrid FROM ExtAdvert e4 WHERE DateAdd(hour,1,e4.dmod) > '" + lastRunDate + "' AND e4.dmod>c5.intfDate)";

// FM 14.09.2016 - Passa a ir buscar tb as campanhas que têm KWs alteradas no KWAC
				query6String = "SELECT c6.externalId, c6.status, c6.var2, c6.var6, c6.num8, 'KWAC_KWs' As orig"
						+ " FROM ca0 AS c6"
						+ " WHERE c6.networks='BUDGET'"
						+ " AND substring(c6.status,1,1) not in ('1','6','9')"
						+ " AND (c6.num8 IS NULL OR c6.num8=0)"
						+ " AND c6.startDate > DateAdd(month,-13,getdate())"
						+ " AND c6.intfDate < '" + lastRunDate +"' AND c6.var3 IS NOT NULL"
						+ " AND c6.nrid IN (SELECT distinct a6.ca0_nrid "
						+ " FROM ExtHeading b, ExtAdgroup a6, Kwac_Adg_Keyword_V kv "
						+ "WHERE "
						+ "b.template IS NULL and "
						+ "b.ca0_nrid = c6.nrid and "
						+ "a6.template IS NULL and "
						+ "a6.ca0_nrid = b.ca0_nrid and "
						+ "a6.heading_nrid = b.nrid and "
						+ "a6.adgroup_id = kv.ad_group_id and "
						+ "kv.updated_by != 'MCRAFT_PT' and "
				+ " DateAdd(hour,1,kv.updateDate) > '" + lastRunDate + "' AND kv.updateDate>c6.intfDate)";

// FM 20.02.2017 - Passa a ir buscar tb as campanhas que têm Adcopies alterados no KWAC
				query7String = "SELECT c7.externalId, c7.status, c7.var2, c7.var6, c7.num8, 'KWAC_ADCs' As orig"
						+ " FROM ca0 AS c7"
						+ " WHERE c7.networks='BUDGET'"
						+ " AND substring(c7.status,1,1) not in ('1','6','9')"
						+ " AND (c7.num8 IS NULL OR c7.num8=0)"
						+ " AND c7.startDate > DateAdd(month,-13,getdate())"
						+ " AND c7.intfDate < '" + lastRunDate +"' AND c7.var3 IS NOT NULL"
						+ " AND c7.nrid IN (SELECT distinct a7.ca0_nrid "
						+ " FROM ExtHeading b, ExtAdgroup a7, Kwac_Adg_Adcopy_V kv7 "
						+ "WHERE "
						+ "b.template IS NULL and "
						+ "b.ca0_nrid = c7.nrid and "
						+ "a7.template IS NULL and "
						+ "a7.ca0_nrid = b.ca0_nrid and "
						+ "a7.heading_nrid = b.nrid and "
						+ "a7.adgroup_id = kv7.ad_group_id and "
						+ "kv7.updated_by != 'MCRAFT_PT' and "
				+ " DateAdd(hour,1,kv7.updateDate) > '" + lastRunDate + "' AND kv7.updateDate>c7.intfDate)";
			}
			
//			+ " AND REF IN ('120443929_3350982_179704') ";
			//		+ " AND REF IN ('10570500_3279415_175511') ";
					//					+ " AND intfDate IS NULL AND DateAdd(hour,1,dmod) > '" + lastRunDate + "' AND var3 IS NOT NULL ";
			
//					+ " AND REF IN ('120038666_3318741_168658') ";
// caso update que da erro					+ " AND REF IN ('108462211_3337219_172556') ";

//					+ " AND intfDate IS NULL AND DateAdd(hour,1,dmod) > '2016-08-01 08:00:00' AND var3 IS NOT NULL ";

					//					+ " AND REF='115546922_3342162_174420' ";
//			+ " AND REF IN ('46066208_3356001_179785') ";
//					+ " AND DateAdd(hour,1,dmod) > '" + lastRunDate + "'  AND DateAdd(hour,1,dmod) < '" + upperLimitDate + "' AND var3 IS NOT NULL ";
		}
// PRD:		+ " AND DateAdd(hour,1,dmod) > '" + lastRunDate + "' AND var3 IS NOT NULL ";

		//				+ " AND dmod > '" + lastRunDate + "' AND var3 IS NOT NULL ";
//	+ " AND REF NOT IN ('46512936_3339476_173539','56071808_3342534_176602','56071808_3342534_176603','61967620_3300080_157881','71104980_3343981_179698','86230100_3352797_179701','88213476_3300417_177516','92228654_3354800_179627','120443929_3350982_179704','121080740_3351538_179482') ";

// LIVE		+ " AND REF='46512936_3339476_173539' ";

	//				+ " AND DateAdd(hour,1,dmod) > '" + lastRunDate + "' AND var3 IS NOT NULL ";
// teste 14.Jun.2016: 88537557_3341778_174116, 120840668_3338958_174422, 46109120_3342357_174456, 13687142_3342338_174437
	//				+ " AND REF IN ('952283862_3288835_159747','115546922_3342162_174420','9117661619_3341771_174142') AND var3 IS NOT NULL ";

//		+ " AND REF IN ('17054747_3278356_175618','198772079_3344537_175044','103010577_3338437_172975') AND var3 IS NOT NULL ";
//		+ " AND REF='118960191_3343906_174837' AND var3 IS NOT NULL ";
//		+ " AND REF='46706598_3340907_173804' AND var3 IS NOT NULL ";
		//		+ " AND REF IN ('13687142_3342338_174438','90437619_3342159_174297','71014652_3340897_174324','101494033_3337702_174016','32569774_3319359_170900','64944550_3333597_171075') AND var3 IS NOT NULL ";
//		+ " AND CONVERT(VARCHAR(8), dmod, 112) = CONVERT(VARCHAR(8), GETDATE(), 112) AND var3 IS NOT NULL ";
				
//		+ " AND REF IN ('119789069_3301655_171022','120694660_3334298_171721','120670082_3333977_171776','46069621_3337081_172438','118933870_3340278_173457','85397142_3339472_173406','56266610_3338877_173199','55617771_3340937_173801') AND var3 IS NOT NULL ";
//				+ " AND REF IN ('31386942_3332004_171026','46001892_3333145_170940','118933870_3340278_173457') AND var3 IS NOT NULL ";
//		+ " AND REF='72750702_3335937_171915' AND var3 IS NOT NULL ";
//				+ " AND REF='120594545_3330058_170818' AND var3 IS NOT NULL ";
//				+ " AND REF='118468901_3326397_171615' AND var3 IS NOT NULL ";		
// teste		+ " AND REF='120408891_3327798_170904' AND var3 IS NOT NULL ";

			Query query = session.createQuery(queryString);
			List<?> results = query.list();
			List<?> mergedResults = null;
			session.clear();

			/* FM 04.08.2016 - Em vez de fazer UNION faz o join das várias listas! */
			if (!(query2String==null)) {
				if (!(session.isOpen()))
					session = HibernateUtil.getSessionFactory().openSession();
				Query query2 = session.createQuery(query2String);
				results2 = query2.list();
			}
			if (!(query3String==null)) {
				if (!(session.isOpen()))
					session = HibernateUtil.getSessionFactory().openSession();
				Query query3 = session.createQuery(query3String);
				results3 = query3.list();
			}
			if (!(query4String==null)) {
				if (!(session.isOpen()))
					session = HibernateUtil.getSessionFactory().openSession();
				Query query4 = session.createQuery(query4String);
				results4 = query4.list();
			}
			if (!(query5String==null)) {
				if (!(session.isOpen()))
					session = HibernateUtil.getSessionFactory().openSession();
				Query query5 = session.createQuery(query5String);
				results5 = query5.list();
			}
			if (!(query6String==null)) {
				if (!(session.isOpen()))
					session = HibernateUtil.getSessionFactory().openSession();
				Query query6 = session.createQuery(query6String);
				results6 = query6.list();
			}
			// FM 20.02.2017
			if (!(query7String==null)) {
				if (!(session.isOpen()))
					session = HibernateUtil.getSessionFactory().openSession();
				Query query7 = session.createQuery(query7String);
				results7 = query7.list();
			}
		// FM 21.06.2016 - Store last-run date
/* TESTE		if (!dossrSession.isOpen())
			dossrSession = HibernateOraUtil.openOraSession();
		int retUpd = updDossrIntfDate(dossrSession);
*/
			session.clear();
			HibernateUtil.closeSession();
		  	session.close();
			
		  	if ((!(results2==null) && results2.size()>0) || 
		  	     (!(results3==null) && results3.size()>0) || 
		  	     (!(results4==null) && results4.size()>0) || 
		  	     (!(results5==null) && results5.size()>0) || 
		  	     (!(results6==null) && results6.size()>0) || 
		  	     (!(results7==null) && results7.size()>0) ) {
		  		if (!(results2==null) && results2.size()>0) {
		  			mergedResults = ListUtils.union(results,results2);
			  		if (!(results3==null) && results3.size()>0) {
			  			/* grupos 2 e 3 */
			  			mergedResults = ListUtils.union(mergedResults,results3);
				  		if (!(results4==null) && results4.size()>0) {
				  			/* grupos 2, 3 e 4 */
				  			mergedResults = ListUtils.union(mergedResults,results4);
					  		if (!(results5==null) && results5.size()>0) {
					  			/* grupos 2, 3, 4 e 5 */
					  			mergedResults = ListUtils.union(mergedResults,results5);
						  		if (!(results6==null) && results6.size()>0) {
						  			/* grupos 2, 3, 4, 5 e 6 */
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 4, 5, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
				  		}
				  		else {
					  		if (!(results5==null) && results5.size()>0) {
					  			/* grupos 2, 3 e 5 */
					  			mergedResults = ListUtils.union(mergedResults,results5);
						  		if (!(results6==null) && results6.size()>0) {
						  			/* grupos 2, 3, 5 e 6 */
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 4, 5, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
					  		else {
						  		if (!(results6==null) && results6.size()>0) {
						  			/* grupos 2, 3 e 6 */
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
				  		}
			  		}
			  		else {
				  		if (!(results4==null) && results4.size()>0)
				  			mergedResults = ListUtils.union(mergedResults,results4);
				  		else {
					  		if (!(results5==null) && results5.size()>0) {
					  			mergedResults = ListUtils.union(mergedResults,results5);
						  		if (!(results6==null) && results6.size()>0) {
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 5, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
					  		else {
						  		if (!(results6==null) && results6.size()>0) {
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
				  		}
			  		}
		  		}
		  		else {
// result2=null
		  			if (!(results3==null) && results3.size()>0) {
			  			mergedResults = ListUtils.union(results,results3);
				  		if (!(results4==null) && results4.size()>0) {
				  			mergedResults = ListUtils.union(mergedResults,results4);
					  		if (!(results5==null) && results5.size()>0) {
					  			mergedResults = ListUtils.union(mergedResults,results5);
						  		if (!(results6==null) && results6.size()>0) {
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
				  		}
				  		else {
					  		if (!(results5==null) && results5.size()>0) {
					  			mergedResults = ListUtils.union(mergedResults,results5);
						  		if (!(results6==null) && results6.size()>0) {
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
					  		else {
					  		// result5=null
						  		if (!(results6==null) && results6.size()>0) {
						  			mergedResults = ListUtils.union(results,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
				  		}
			  		}
			  		else {
// result3=null
				  		if (!(results4==null) && results4.size()>0) {
				  			mergedResults = ListUtils.union(results,results4);
					  		if (!(results5==null) && results5.size()>0) {
					  			mergedResults = ListUtils.union(results,results5);
						  		if (!(results6==null) && results6.size()>0) {
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 5, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 5 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}  			
						  		}
					  		}
				  		}
				  		else {
				  		// result4=null
					  		if (!(results5==null) && results5.size()>0) {
					  			mergedResults = ListUtils.union(results,results5);
						  		if (!(results6==null) && results6.size()>0) {
						  			mergedResults = ListUtils.union(mergedResults,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 5, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 5 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
					  		else {
					  		// result5=null
						  		if (!(results6==null) && results6.size()>0) {
						  			mergedResults = ListUtils.union(results,results6);
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
						  		else {
							  		if (!(results7==null) && results7.size()>0) {
							  			/* grupos 2, 3, 6 e 7 */
							  			mergedResults = ListUtils.union(mergedResults,results7);
							  		}
						  		}
					  		}
				  		}
			  		}
		  		}
			  	return mergedResults;
		  	}
		  	else
			  	return results;

	}
	
	
	@SuppressWarnings("finally")
	public boolean setCampaignStatus(Session session, String ca0_ref, String newStatus, String newStatusMsg)
	{
		int ret=0;
		String queryString = null;
		if (newStatus.equals("99 - Error")) {
			queryString = "UPDATE ca0"
					+ " SET var12='" + newStatus + "',var23='" + newStatusMsg + "', intfDate=getdate()"
					+ " WHERE ref='" +ca0_ref+ "'";
		}
		else {
			queryString = "UPDATE ca0"
					+ " SET var12='" + newStatus + "',intfDate=getdate()"
					+ " WHERE ref='" +ca0_ref+ "'";			
		}
				
		System.out.println("------------------------------------------------------------");
		session.beginTransaction();
		System.out.println("[updCa0Sts] Update ca0[" +ca0_ref+ "].var12 -> " + newStatus);
		
		try {				
				
			  	session.getTransaction();
				Query query = session.createQuery(queryString);
				ret = query.executeUpdate();
				if (ret>0) {
				  	session.getTransaction().commit();
			  	}
			  	else
			  	{
			  		
			  		System.out.println("[updCa0Sts] no_rows RETURNED");
			  		return false;
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
			}
			catch(NullPointerException e)
			{
				System.out.println("[ca0]Table 'ca0' RETURNED NULL");
		  		return false;
			}
			finally
			{
				System.out.println("[updCa0Sts] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return true;
			}
		
	}
// FM 01.08.2016
	@SuppressWarnings("finally")
	public boolean setInterfaceDate(Session session, String ca0_ref)
	{
		int ret=0;
		String queryString = "UPDATE ca0"
				+ " SET intfDate=getdate()"
				+ " WHERE ref='" +ca0_ref+ "'";
				
		System.out.println("------------------------------------------------------------");
		session.beginTransaction();
		System.out.println("[updCa0IntfDate] Update ca0[" +ca0_ref+ "].intfDate = dataProcessamento");
		
		try {
								
			  	session.getTransaction();
				Query query = session.createQuery(queryString);
				ret = query.executeUpdate();
				if (ret>0) {
				  	session.getTransaction().commit();
			  	}
			  	else
			  	{			  		
			  		System.out.println("[updCa0IntfDate] no_rows RETURNED");
			  		return false;
			  	}

			}
			
			catch (HibernateException e) 
			{
					
					System.out.println("[updCa0IntfDate]Table 'ca0' data ORM state FAILED: ");
					System.out.println(e);
					System.out.println("[ca0]ROLLBACK AND CLOSE SESSION ");
					System.out.println("------------------------------------------------------------");
					session.getTransaction().rollback();
					session.clear();
				  	session.close();
				  	System.exit(0);
			}
			catch(NullPointerException e)
			{
				System.out.println("[ca0]Table 'ca0' RETURNED NULL");
		  		return false;
			}
			finally
			{
				System.out.println("[updCa0Sts] SESSION CLOSED");
				System.out.println("------------------------------------------------------------");
				
				session.clear();
				HibernateUtil.closeSession();
			  	session.close();
			  	
			  	return true;
			}		
	}

	public void setLastIntfDate(String pIntfDate) {
		this.lastRunDate = pIntfDate;
	}

	public String getLastIntfDate() {
		return lastRunDate;
	}
}
