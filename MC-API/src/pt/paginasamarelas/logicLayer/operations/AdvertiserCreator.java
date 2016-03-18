package pt.paginasamarelas.logicLayer.operations;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
//import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pt.paginasamarelas.dataLayer.entities.*;
import pt.paginasamarelas.dataLayer.hibernate.HibernateUtil;
import pt.paginasamarelas.dataLayer.hibernate.QueryCampaignDB;
import pt.paginasamarelas.dataLayer.hibernate.entities.*;


public class AdvertiserCreator 
{
	
	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
	public Advertiser createAdvertiser(String externalId) throws MalformedURLException, URISyntaxException
	{
		//Load data from DB
		
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = new QueryCampaignDB();
		
		ca0 ca0 = (ca0) context.getBean("ca0");
		
		ca0 = q.getCa0(externalId, session);
		BigDecimal BNRID = ca0.getNrid();
		String nrid = BNRID.toString();
		
		
		session = HibernateUtil.openSession();
		List <ExtAdvert> extadverts = null;
		extadverts = q.getExAdvert(nrid, session);
		
		
		session = HibernateUtil.openSession();
		ExtBusinessAddress extbusinessaddress = q.getExtBusinessAddress(nrid, session);
		
		
		session = HibernateUtil.openSession();
		ExtBusinessPhone extbusinessphone = q.getExtBusinessPhone(nrid, session);
		//Load data from DB
		
		//Budget
		Budget[] budgets = createBudgets(ca0);
		
		//Location
		Location[] location = createLocation(extadverts);
		
		//Advertiser
		
		Advertiser advertiser =  (Advertiser) context.getBean("advertiser");
		
		//AdvertiserID advertiserid = new AdvertiserID();
		AdvertiserID advertiserid = (AdvertiserID) context.getBean("advertiserID");
		advertiserid.setExternalId(externalId);
		
		advertiser.setAdvertiserId(advertiserid);
		advertiser.setName(ca0.getName());
		advertiser.setUserStatus("active");
	
		advertiser.setBusinessAddress(createBusinessAddress(extbusinessaddress));
		advertiser.setBusinessPhone(extbusinessphone.getPhoneNumber());
		advertiser.setBudgets(budgets);
		advertiser.setLocations(location);
		//Avertiser
		
		
		return advertiser;

	}
	
	
	
	public BusinessAddress createBusinessAddress(ExtBusinessAddress extbusinessaddress)
	{
		if(extbusinessaddress != null)
		{
			BusinessAddress businessAddress = (BusinessAddress) context.getBean("businessaddress");
			businessAddress.setLine1(extbusinessaddress.getLine1());
			businessAddress.setLine2(extbusinessaddress.getLine2());
			businessAddress.setPostalCode(extbusinessaddress.getPostalCode());
			businessAddress.setCity(extbusinessaddress.getCity());
			businessAddress.setRegion(extbusinessaddress.getRegion());
			
			return businessAddress;
		}
		else return null;
		
		
		
	}
	
	public Budget[] createBudgets(ca0 ca0)
	{
		Budget budget = (Budget) context.getBean("budget");
		if(ca0.getTargetRetailSpend() != null)
			budget.setTargetRetailSpend(ca0.getTargetRetailSpend().floatValue());
		if(ca0.getTargetMediaSpend() != null)
			budget.setTargetMediaSpend(ca0.getTargetMediaSpend().floatValue());
		if(ca0.getTargetClicks() != null)
			budget.setTargetClicks(ca0.getTargetClicks().intValue());
		if(ca0.getTargetImpressions() != null)
			budget.setTargetImpressions(ca0.getTargetImpressions().intValue());
		
		//DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyyMMdd");
		String start_date = newFormat.format(ca0.getStartDate());
		String end_date = newFormat.format(ca0.getEndDate());
		
		
		Date date = new Date();
		String actual_date = newFormat.format(date);
		
		if(Integer.parseInt(start_date)< Integer.parseInt(actual_date))
		{
			budget.setContractBeginDate(Integer.parseInt(actual_date));
		}else
		{
			budget.setContractBeginDate(Integer.parseInt(start_date));
		}
		
		budget.setContractEndDate(Integer.parseInt(end_date));
		
		budget.setMediaFraction(1F);
		
		
		Budget[] budgets = new Budget[1];
		budgets[0] = budget;
		
		return budgets;
	}
	
	public Location[] createLocation(List<ExtAdvert> dbExtAdvertList) throws MalformedURLException, URISyntaxException
	{
		
		
		Location[] locations = new Location[dbExtAdvertList.size()];
		
		
		Iterator<?> advertIterator = dbExtAdvertList.listIterator();
		int count=0;
		
	    while(advertIterator.hasNext()) 
	    {
	    	ExtAdvert extadvert = (ExtAdvert) context.getBean("extadvert");
	    	extadvert = (ExtAdvert) advertIterator.next();
	    	  
	    	//LocationID
	    	LocationId locationId = (LocationId) context.getBean("locationID");
			locationId.setExternalId(extadvert.getAdvert_id().toString());
			//LocationID
				
				
			Location location = (Location) context.getBean("location");
			location.setLocationId(locationId);
			location.setName(" ");
			location.setUserStatus("active");
				//---location.setUserStatus();
			String[] languages = {"pt"};
			location.setLanguages(languages);
			String campaignUrl = extadvert.getCampaignUrl();
			String displayUrl = extadvert.getDisplayUrl();
			
			if(!campaignUrl.contains("http://"))
			{
				campaignUrl = "http://" + campaignUrl;
			}
			
			
			
			
			location.setUrl(campaignUrl);
			location.setRequestedDisplayUrl(displayUrl);
				
			//categoryRef
			CategoryRef[] categoryRefs = null;
			categoryRefs = createCategoryRefs( extadvert.getAdvert_id().toString());
			
			location.setCategoryRefs(categoryRefs);
			
			//GeographicTarget
			GeographicTarget geographicTarget = (GeographicTarget) context.getBean("geographictarget");
			geographicTarget = createGeoloc(extadvert.getAdvert_id().toString());
			
			location.setGeographicTarget(geographicTarget);
			
			//Sitelink
			Sitelink[] sitelinks = createSitelink(extadvert.getAdvert_id().toString());
			location.setSitelinks(sitelinks);
			
			
			//fill location array
			locations[count] = location;
			
			count++;
	    }
		

		
		return locations;
	}
	
	public CategoryRef[] createCategoryRefs(String advertid)
	{
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		
		List<?> adgroups = q.getExtCustomAdgroupHQL(advertid, session);
		CategoryRef[] categoryRefs = new CategoryRef[adgroups.size()];
		
		Iterator<?> iterator = adgroups.iterator();
		int count = 0;
	    while(iterator.hasNext()) 
	    {
	    	CategoryRef categoryRef = (CategoryRef) context.getBean("categoryref");
	    	Object[] extadgroup = (Object[]) iterator.next();
	    	System.out.println(extadgroup[0].toString());
	    	
	    	categoryRef.setExternalId(extadgroup[0].toString());
	    	categoryRef.setName(extadgroup[1].toString());
	    	
	    	CustomAdCopy[] customAdcopies = createCustomAdcopies(extadgroup[0].toString());
	    	categoryRef.setAdcopies(customAdcopies);
	    	
	    	CustomKeyphrase [] customKeyphrases = createCustomKeyphrase(extadgroup[0].toString());
	    	categoryRef.setKeyphrases(customKeyphrases);
	    	
	    	
	    	categoryRefs[count] = categoryRef;
	    	count++;
	    	
	    }

		return categoryRefs;
	}
	
	public CustomAdCopy[] createCustomAdcopies(String adgroupid)
	{
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> adcopies = q.getExtCustomAdcopyHQL(adgroupid, session);
		CustomAdCopy[] customAdcopies = new CustomAdCopy[adcopies.size()];
		if(adcopies.size()!=0)
		{
			Iterator<?> iterator = adcopies.listIterator();
			
			int count = 0;
			while(iterator.hasNext())
			{
				Object[] extCustomAdcopy = (Object[]) iterator.next();
				CustomAdCopy customAdCopy = (CustomAdCopy) context.getBean("customadcopy");
				
				customAdCopy.setLine1(extCustomAdcopy[2].toString());
				customAdCopy.setLine2(extCustomAdcopy[3].toString());
				customAdCopy.setTitle(extCustomAdcopy[1].toString());
				customAdcopies[count] = customAdCopy;
				count++;
				
			}
		}else
		{
			return null;
		}
		
		return customAdcopies;
	}
	
	public CustomKeyphrase[] createCustomKeyphrase(String adgroupid)
	{
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> keyphrases = q.getExtCustomKeywordHQL(adgroupid, session);
		CustomKeyphrase[] customkeyphrases = new CustomKeyphrase[keyphrases.size()];
		
		if(keyphrases.size()!=0)
		{
			Iterator<?> iterator = keyphrases.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{
				Object[] extCustomKeyphrase = (Object[]) iterator.next();
				CustomKeyphrase customKeyphrase = (CustomKeyphrase) context.getBean("customkeyphrase");
				
				customKeyphrase.setPhrase(extCustomKeyphrase[2].toString());
				customkeyphrases[count]=customKeyphrase;
				count++;
			}
		}
		else
		{
			return null;
		}
		
		return customkeyphrases;
	}
	
	public GeographicTarget createGeoloc(String advertid)
	{
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> geolocs = q.getExtGeolocHQL(advertid, session);
		
		GeographicTarget geographicTargets = (GeographicTarget) context.getBean("geographictarget");
		PostalCodeRadius[] postalCodesRadius = new PostalCodeRadius[geolocs.size()];
		if(geolocs.size()!=0)
		{
			Iterator<?> iterator = geolocs.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{				
				Object[] extGeoloc = (Object[]) iterator.next();
				
				PostalCodeRadius postalCodeRadius = (PostalCodeRadius) context.getBean("postalcoderadius");
				
				postalCodeRadius.setPostalCode(extGeoloc[0].toString());
				postalCodeRadius.setRadius(Integer.parseInt(extGeoloc[1].toString()));
				postalCodeRadius.setCountryCode("PT");
				postalCodeRadius.setDescription("");
				
				
				postalCodesRadius[count]=postalCodeRadius;
				count++;
			}
			geographicTargets.setPostalCodeRadii(postalCodesRadius);
		}
		else
		{
			return null;
		}
		
		return geographicTargets;
	}
	
	public Sitelink[] createSitelink(String advertid)
	{
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> extsitelinks = q.getExtSitelinkHQL(advertid, session);
		
		Sitelink[] sitelinks = new Sitelink[extsitelinks.size()];
		
		if(extsitelinks.size()!=0)
		{
			Iterator<?> iterator = extsitelinks.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{				
				Object[] extSitelink = (Object[]) iterator.next();
				
				Sitelink sitelink = (Sitelink) context.getBean("sitelink");
				
				sitelink.setName(extSitelink[0].toString());
				String requestedURL = extSitelink[1].toString();
				if(!requestedURL.contains("http://"))
				{
					requestedURL = "http://" + requestedURL;
				}
				sitelink.setRequestedUrl(requestedURL);
				sitelink.setUserStatus("active");
				
				sitelinks[count]=sitelink;
				count++;
			}
			
		}
		else
		{
			return null;
		}
		
		return sitelinks;
	}

}
