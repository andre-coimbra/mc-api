package pt.paginasamarelas.Operations;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
//import org.junit.Test;

import pt.paginasamarelas.Entities.Advertiser;
import pt.paginasamarelas.Entities.AdvertiserID;
import pt.paginasamarelas.Entities.Budget;
import pt.paginasamarelas.Entities.BusinessAddress;
import pt.paginasamarelas.Entities.CategoryRef;
import pt.paginasamarelas.Entities.CustomAdCopy;
import pt.paginasamarelas.Entities.CustomKeyphrase;
import pt.paginasamarelas.Entities.GeographicTarget;
import pt.paginasamarelas.Entities.Location;
import pt.paginasamarelas.Entities.LocationId;
import pt.paginasamarelas.Entities.PostalCodeRadius;
import pt.paginasamarelas.Entities.Sitelink;
import pt.paginasamarelas.hibernate.HibernateUtil;
import pt.paginasamarelas.hibernate.QueryCampaignDB;
import pt.paginasamarelas.hibernate.entities.*;


public class AdvertiserCreator 
{
	
	
	
	public Advertiser createAdvertiser(String externalId) throws MalformedURLException, URISyntaxException
	{
		//Load data from DB
		
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = new QueryCampaignDB();
		
		ca0 ca0 = q.getCa0(externalId, session);
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
		Advertiser advertiser = new Advertiser();
		
		AdvertiserID advertiserid = new AdvertiserID();
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
			BusinessAddress businessAddress = new BusinessAddress();
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
		Budget budget = new Budget();
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
	    	ExtAdvert extadvert = (ExtAdvert) advertIterator.next();
	    	  
	    	//LocationID
	    	LocationId locationId = new LocationId();
			locationId.setExternalId(extadvert.getAdvert_id().toString());
			//LocationID
				
				
			Location location = new Location();
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
			GeographicTarget geographicTarget = null;
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
		QueryCampaignDB q = new QueryCampaignDB();
		
		List<?> adgroups = q.getExtCustomAdgroupHQL(advertid, session);
		CategoryRef[] categoryRefs = new CategoryRef[adgroups.size()];
		
		Iterator<?> iterator = adgroups.iterator();
		int count = 0;
	    while(iterator.hasNext()) 
	    {
	    	CategoryRef categoryRef = new CategoryRef();
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
		QueryCampaignDB q = new QueryCampaignDB();
		List<?> adcopies = q.getExtCustomAdcopyHQL(adgroupid, session);
		CustomAdCopy[] customAdcopies = new CustomAdCopy[adcopies.size()];
		if(adcopies.size()!=0)
		{
			Iterator<?> iterator = adcopies.listIterator();
			
			int count = 0;
			while(iterator.hasNext())
			{
				Object[] extCustomAdcopy = (Object[]) iterator.next();
				CustomAdCopy customAdCopy = new CustomAdCopy();
				
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
		QueryCampaignDB q = new QueryCampaignDB();
		List<?> keyphrases = q.getExtCustomKeywordHQL(adgroupid, session);
		CustomKeyphrase[] customkeyphrases = new CustomKeyphrase[keyphrases.size()];
		
		if(keyphrases.size()!=0)
		{
			Iterator<?> iterator = keyphrases.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{
				Object[] extCustomKeyphrase = (Object[]) iterator.next();
				CustomKeyphrase customKeyphrase = new CustomKeyphrase();
				
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
		QueryCampaignDB q = new QueryCampaignDB();
		List<?> geolocs = q.getExtGeolocHQL(advertid, session);
		
		GeographicTarget geographicTargets = new GeographicTarget();
		PostalCodeRadius[] postalCodesRadius = new PostalCodeRadius[geolocs.size()];
		if(geolocs.size()!=0)
		{
			Iterator<?> iterator = geolocs.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{				
				Object[] extGeoloc = (Object[]) iterator.next();
				
				PostalCodeRadius postalCodeRadius = new PostalCodeRadius();
				
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
		QueryCampaignDB q = new QueryCampaignDB();
		List<?> extsitelinks = q.getExtSitelinkHQL(advertid, session);
		
		Sitelink[] sitelinks = new Sitelink[extsitelinks.size()];
		
		if(extsitelinks.size()!=0)
		{
			Iterator<?> iterator = extsitelinks.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{				
				Object[] extSitelink = (Object[]) iterator.next();
				
				Sitelink sitelink = new Sitelink();
				
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
