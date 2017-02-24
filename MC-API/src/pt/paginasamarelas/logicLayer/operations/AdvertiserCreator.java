package pt.paginasamarelas.logicLayer.operations;

import java.io.IOException;
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
import pt.paginasamarelas.dataLayer.hibernate.HibernateKwacUtil;
import pt.paginasamarelas.dataLayer.hibernate.HibernateOraUtil;
import pt.paginasamarelas.dataLayer.hibernate.HibernateUtil;
import pt.paginasamarelas.dataLayer.hibernate.QueryCampaignDB;
import pt.paginasamarelas.dataLayer.hibernate.entities.*;
import pt.paginasamarelas.logicLayer.controller.CreateCampaignController;
import pt.paginasamarelas.logicLayer.operations.JacksonConverter;


public class AdvertiserCreator 
{
	
	
	
	private ApplicationContext context;
	

	public Advertiser createAdvertiser(String externalId, boolean prmCampanhaGAespecial, boolean prmIncludeStdAdcopies) throws MalformedURLException, URISyntaxException
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
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
		Location[] location = createLocation(ca0.getVar2(), externalId, ca0.getVar6(), ca0.getVar3() , extadverts, prmCampanhaGAespecial, prmIncludeStdAdcopies);
		
		//Advertiser		
		Advertiser advertiser =  (Advertiser) context.getBean("advertiser");
		
		//AdvertiserID advertiserid = new AdvertiserID();
		AdvertiserID advertiserid = (AdvertiserID) context.getBean("advertiserID");
		advertiserid.setExternalId(externalId);
		
		advertiser.setAdvertiserId(advertiserid);
		advertiser.setName(ca0.getVar3());
		advertiser.setUserStatus("active");
	
		if ( !(extbusinessaddress==null) )
			advertiser.setBusinessAddress(createBusinessAddress(extbusinessaddress));
		if ( !(extbusinessphone==null) )
			advertiser.setBusinessPhone(extbusinessphone.getPhoneNumber());
		advertiser.setBudgets(budgets);
		advertiser.setLocations(location);
		//Avertiser
		
		
		return advertiser;

	}
	
	
	
	public BusinessAddress createBusinessAddress(ExtBusinessAddress extbusinessaddress)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		if(extbusinessaddress != null)
		{
			BusinessAddress businessAddress = (BusinessAddress) context.getBean("businessaddress");
			businessAddress.setLine1(extbusinessaddress.getLine1());
			businessAddress.setLine2(extbusinessaddress.getLine2());
			businessAddress.setPostalCode(extbusinessaddress.getPostalCode());
			businessAddress.setCity(extbusinessaddress.getCity());
			businessAddress.setRegion(extbusinessaddress.getRegion());
			businessAddress.setCountryCode("PT");
			
			return businessAddress;
		}
		else return null;
		
		
		
	}
	
	public Budget[] createBudgets(ca0 ca0)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
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
	
	public Location[] createLocation(String pSubscrId, String pRef, String pAuxProduct, String pName, List<ExtAdvert> dbExtAdvertList, boolean prmCampanhaGAespecial, boolean pIncludeStdAdcopies) throws MalformedURLException, URISyntaxException
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q2 = new QueryCampaignDB();
		CreateCampaignController createCampaignController = (CreateCampaignController) context.getBean("createcampaigncontroller");
		JacksonConverter jacksonConverter = (JacksonConverter) context.getBean("jacksonConverter");
		Response2 responseobj = (Response2) context.getBean("response2");
		String response= null;
		
		Location[] locations = new Location[dbExtAdvertList.size()];		
		
		Iterator<?> advertIterator = dbExtAdvertList.listIterator();
		int count=0;
		String auxProduct = null;
		if (pAuxProduct == null)
			auxProduct = "N";
		else
			auxProduct = pAuxProduct;
		
		try {
			while(advertIterator.hasNext()) 
		    {
		    	ExtAdvert extadvert = (ExtAdvert) context.getBean("extadvert");
		    	extadvert = (ExtAdvert) advertIterator.next();
		    	  
		    	//LocationID
		    	LocationId locationId = (LocationId) context.getBean("locationID");
					
				// Get class_code (heading) by reading extHeading CMC table
				session = HibernateUtil.openSession();
				ExtHeading extheading = q2.getExtHeading(extadvert.getHeading_nrid(), session);
				
	// FM 30.05.2016 - ID fica com a Ref-Campª + Advert-Id + Class-Code
//				locationId.setExternalId(pRef +"-"+ extadvert.getAdvert_id().toString() +"-"+ extheading.getHeading().trim());
// FM 19.08.2016 - ID fica só com a Ref-Campª + Class-Code !
/* FM 15.09.2016 - no caso de campanhas especiais (=há um título com mais que uma figuração GAWH/Advert!)
				tem que se incluír o documentId na chave !
   FM 27.09.2016 - Precaver o caso em que não há Heading !!
*/
				if (extheading==null) {
					System.out.println("Campanha" + pRef + " tem Heading a null!");
				}
				else {
					if (prmCampanhaGAespecial==true) {
						locationId.setExternalId(pRef +"-"+ extadvert.getDocumentId().toString()+"-"+ extheading.getHeading().trim());
					}
					else {
						locationId.setExternalId(pRef +"-"+ extheading.getHeading().trim());
					}
					Location location = (Location) context.getBean("location");
					location.setLocationId(locationId);
//			location.setName(extadvert.getAdvert_id().toString() +"-"+ extheading.getHeading().trim());
					location.setName(pName +" pt-PT");
					location.setUserStatus("active");
					String[] languages = {"pt"};
					location.setLanguages(languages);
					String[] networks = {"google_search","search_network"};
					location.setNetworks(networks);
	
					String campaignUrl = null;
					if (auxProduct.equals("O")) 
						campaignUrl = extadvert.getCampaignUrl();
					else {
						if (extadvert.getCampaignUrl().indexOf("pai.pt") >= 0)
							campaignUrl = extadvert.getCampaignUrl() + "?WT.srch=1&WT.mc_id=" + pRef + "&adrecip=MatchCraft";
						else {
							if (extadvert.getCampaignUrl().indexOf("?") == -1)
								campaignUrl = extadvert.getCampaignUrl() + "?utm_source=google&utm_medium=cpc&utm_campaign=truvo";
							else {
								// se o URL já tiver um ? junta um ampersand character...
								campaignUrl = extadvert.getCampaignUrl() + "&utm_source=google&utm_medium=cpc&utm_campaign=truvo";
							}
						}
					}
					String displayUrl = extadvert.getDisplayUrl();
					
					if(!campaignUrl.contains("http://"))
					{
						campaignUrl = "http://" + campaignUrl;
					}
					
					
					location.setUrl(campaignUrl);
					location.setRequestedDisplayUrl(displayUrl);
						
					//categoryRef
					CategoryRef[] categoryRefs = null;
					categoryRefs = createCategoryRefs(pSubscrId, extadvert.getAdvert_id().toString(), extadvert.getHeading_nrid(), pIncludeStdAdcopies);
					if (!(categoryRefs==null))
						location.setCategoryRefs(categoryRefs);
		
					// customCategories
					CustomCategory[] customCategories = null;
					customCategories = createKwacCustomCategories(pSubscrId, extadvert.getAdvert_id().toString(), extadvert.getHeading_nrid(), pIncludeStdAdcopies);
					if (!(customCategories==null))
						location.setCustomCategories(customCategories);
				
				//GeographicTarget
					GeographicTarget geographicTarget = (GeographicTarget) context.getBean("geographictarget");
					geographicTarget = createGeoloc(extadvert.getAdvert_id().toString(), extadvert.getCa0_nrid().toString() , extadvert.getHeading_nrid() );
					
					GeoKeywordTarget geoModifiers = (GeoKeywordTarget) context.getBean("geokeywordtarget");
					if (!(geographicTarget==null)) {
						location.setGeographicTarget(geographicTarget);
					/* FM 03.08.2016 */
						response = createCampaignController.getGeoModifiers(geographicTarget);
	        			jacksonConverter.refactoredResponse(response);
	        			responseobj = jacksonConverter.response2ToJSON(response);
	        			Operations2[] opArr = responseobj.getOperations();
	        			String[] mcSuggLocalities = opArr[0].getResult();
	        			if (mcSuggLocalities.length>0) {
							geoModifiers.setGeoModifiers(mcSuggLocalities);
							location.setGeoKeywordTarget(geoModifiers);
	        			}
					}
				
					//Sitelink
					Sitelink[] sitelinks = createSitelink(extadvert.getAdvert_id().toString(), extadvert.getCa0_nrid().toString(), extadvert.getHeading_nrid().toString());
					if (!(sitelinks==null))
						location.setSitelinks(sitelinks);
				
				
					//fill location array
					locations[count] = location;
				
					count++;
				}
		    }		
		}
		catch (IOException e) {
		    e.printStackTrace();			
		}
		
		return locations;
	}

	public CustomCategory[] createKwacCustomCategories(String subscrId, String advertid, String heading_nrid, boolean pIncludeStdAdcopies)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		
		List<?> customAdgroups = q.getKwacCustomAdgroupHQL(subscrId, advertid, heading_nrid, session);
		CustomCategory[] customCategories = new CustomCategory[customAdgroups.size()];
		
		Iterator<?> iterator = customAdgroups.iterator();
		int count = 0;
	    while(iterator.hasNext()) 
	    {
	    	CustomCategory customCategory = (CustomCategory) context.getBean("customcategory");
	    	Object[] extadgroup = (Object[]) iterator.next();
	    	System.out.println(extadgroup[0].toString());
	    	
	    	CategoryID catId = new CategoryID();
	    	catId.setExternalId(extadgroup[0].toString());
	    	
	    	customCategory.setCategoryId(catId);
	    	customCategory.setName(extadgroup[1].toString() +"|"+ extadgroup[0].toString());
	    	customCategory.setUserStatus(new String("active"));
	    	
	    	CustomAdCopy[] customAdcopies = createKwacCustomAdcopies(extadgroup[0].toString(), pIncludeStdAdcopies); /* params: adgroup_id */
	    	customCategory.setAdcopies(customAdcopies);
	    	
	    	CustomKeyphrase[] customKeyphrases = createKwacCustomKeyphrases(extadgroup[0].toString()); /* params: adgroup_id  */
	    	customCategory.setKeyphrases(customKeyphrases);
	    	
	    	customCategories[count] = customCategory;
	    	count++;
	    	
	    }

		return customCategories;
	}
	
	public CategoryRef[] createCategoryRefs(String subscrId, String advertid, String heading_nrid, boolean pIncludeStdAdcopies)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		
		List<?> adgroups = q.getExtCustomAdgroupHQL(advertid, heading_nrid, subscrId, session);
		CategoryRef[] categoryRefs = null;
		if (!(adgroups==null)) {
			categoryRefs = new CategoryRef[adgroups.size()];
			
			Iterator<?> iterator = adgroups.iterator();
			int count = 0;
		    while(iterator.hasNext()) 
		    {
		    	CategoryRef categoryRef = (CategoryRef) context.getBean("categoryref");
		    	Object[] extadgroup = (Object[]) iterator.next();
		    	System.out.println("AG:" + extadgroup[0].toString());
	    	
// se é uma Adgroup específico deste cliente, para já assume que ainda não existe na MC
	    	// no futuro vai ter que olhar para a data no Kwac para saber se já passou para a MC ou não
		    	if ((extadgroup[1].toString()).indexOf(subscrId)<0) {
			    	categoryRef.setExternalId(extadgroup[0].toString());
			    	categoryRef.setName(extadgroup[1].toString());
			    	
			    	CustomAdCopy[] allAdcopies = createCustomAdcopies(extadgroup[2].toString(), extadgroup[3].toString(), extadgroup[0].toString(), pIncludeStdAdcopies); /* params: ca0_nrid, heading_nrid e adgroup_id */
			    	categoryRef.setAdcopies(allAdcopies);
			    	
			    	CustomKeyphrase[] customKeyphrases = createCustomKeyphrase(extadgroup[2].toString(), extadgroup[3].toString(), extadgroup[0].toString()); /* params: ca0_nrid, heading_nrid e adgroup_id  */
			    	categoryRef.setKeyphrases(customKeyphrases);	    	
/* FM 13.09.2016 - para já uso a mesma função porque se há customAdopies estes é que valem e não os que estão no KWAC */	    	
			    	CustomAdCopy[] customAdcopies = createCustomAdcopies(extadgroup[2].toString(), extadgroup[3].toString(), extadgroup[0].toString(), pIncludeStdAdcopies); /* params: ca0_nrid, heading_nrid e adgroup_id */
			    	categoryRef.setCustomAdcopies(customAdcopies);
			    	CustomKeyphrase[] customKeywords = getCustomKeywords(extadgroup[2].toString(), extadgroup[3].toString(), extadgroup[0].toString()); /* params: ca0_nrid, heading_nrid e adgroup_id  */
			    	categoryRef.setCustomKeyphrases(customKeywords);	    	
			    	
			    	categoryRefs[count] = categoryRef;
			    	count++;
		    	}
		    }
		    if (count==0)
		    	categoryRefs = null;
		}

		return categoryRefs;
	}
	
	public CustomAdCopy[] createCustomAdcopies(String ca0_nrid,String heading_nrid, String adgroup_id, boolean pIncludeStdAdcopies)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> adcopies = q.getExtCustomAdcopyHQL(ca0_nrid, heading_nrid, adgroup_id, session, pIncludeStdAdcopies);
		CustomAdCopy[] customAdcopies = new CustomAdCopy[adcopies.size()];
		if(adcopies.size()!=0)
		{
			Iterator<?> iterator = adcopies.listIterator();
			
			int count = 0;
			while(iterator.hasNext())
			{
				Object[] extCustomAdcopy = (Object[]) iterator.next();
				CustomAdCopy customAdCopy = (CustomAdCopy) context.getBean("customadcopy");
				
// FM 26.10.2016 - GA Expanded AdCopies:
				if (!(extCustomAdcopy[4]==null)) {
					customAdCopy.setTitle(extCustomAdcopy[4].toString().replace('|', '&'));
					customAdCopy.setTitle2(extCustomAdcopy[5].toString().replace('|', '&'));
					customAdCopy.setLine1(extCustomAdcopy[6].toString().replace('|', '&'));
					customAdcopies[count] = new CustomAdCopy();
					customAdcopies[count].setTitle(customAdCopy.getTitle().replace('~', '\''));
					customAdcopies[count].setTitle2(customAdCopy.getTitle2().replace('~', '\''));
					customAdcopies[count].setLine1(customAdCopy.getLine1().replace('~', '\''));
					count++;
				}
				else {
					if (pIncludeStdAdcopies) {
						customAdCopy.setTitle(extCustomAdcopy[1].toString().replace('|', '&'));
						customAdCopy.setLine1(extCustomAdcopy[2].toString().replace('|', '&'));
						customAdCopy.setLine2(extCustomAdcopy[3].toString().replace('|', '&'));
						customAdcopies[count] = new CustomAdCopy();
						customAdcopies[count].setTitle(customAdCopy.getTitle().replace('~', '\''));
						customAdcopies[count].setLine1(customAdCopy.getLine1().replace('~', '\''));
						customAdcopies[count].setLine2(customAdCopy.getLine2().replace('~', '\''));
						count++;
					}
				}
				
			}
		}else
		{
			return null;
		}
		
		return customAdcopies;
	}
	
	public CustomKeyphrase[] createCustomKeyphrase(String ca0_nrid,String heading_nrid,String adgroup_id)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> keyphrases = q.getExtCustomKeywordHQL(ca0_nrid, heading_nrid, adgroup_id, session);
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
				customkeyphrases[count]= new CustomKeyphrase();
				customkeyphrases[count].setPhrase(extCustomKeyphrase[2].toString());
				customkeyphrases[count].setSearchEngines(new String[] {"google"} );
				count++;
			}
// FM 07.06.2016 - Se tem Keywords específicas, tem_meter também todas as KWs normais do Adgroup !
			if (count>0) {
				int count2=0;
				Session kwacSession = HibernateKwacUtil.openKwacSession();
				List<?> kwacKeyphrases = q.getKwacCustomKeywordHQL(adgroup_id, kwacSession);
				CustomKeyphrase[] defaultKeyphrases = new CustomKeyphrase[kwacKeyphrases.size()+count];
				
				for (int ii=0;ii<count;ii++) {
					defaultKeyphrases[count2]= new CustomKeyphrase();
					defaultKeyphrases[count2].setPhrase(customkeyphrases[ii].getPhrase());
					defaultKeyphrases[count2].setSearchEngines(new String[] {"google"} );
					count2++;
				}
				
				if(kwacKeyphrases.size()!=0)
				{
					Iterator<?> kwacIterator = kwacKeyphrases.listIterator();
					while(kwacIterator.hasNext())
					{
						Object[] extCustomKeyphrase2 = (Object[]) kwacIterator.next();
						
						String keyphrase2 = null;
						if (extCustomKeyphrase2[2].toString().equals("EXACT")) {
							keyphrase2 = "["+ extCustomKeyphrase2[1].toString() +"]";
						}
						else {
							if (extCustomKeyphrase2[2].toString().equals("PHRASE")) {
								keyphrase2 = "\""+ extCustomKeyphrase2[1].toString() +"\"";
							}
							else {
								if (extCustomKeyphrase2[2].toString().equals("NPHRAS")) {
									keyphrase2 = "-\""+ extCustomKeyphrase2[1].toString() +"\"";
								}
								else {
									if (extCustomKeyphrase2[2].toString().equals("NEXACT")) {
										keyphrase2 = "-["+ extCustomKeyphrase2[1].toString() +"]";
									}
									else {
										if (extCustomKeyphrase2[2].toString().equals("NBROAD")) {
											if (extCustomKeyphrase2[1].toString().contains(" ")) {
												/* se a keyword tiver um ou mais espaços assume-se que é NEGATIVE_PHRASE */
												keyphrase2 = "-\""+ extCustomKeyphrase2[1].toString() +"\"";
											}
											else {
												keyphrase2 = "-"+ extCustomKeyphrase2[1].toString() ;	
											}
										}
										else {
										// assume que é BROAD...
											keyphrase2 = extCustomKeyphrase2[1].toString();
										}
									}
								}
							}
						}

						defaultKeyphrases[count2]= new CustomKeyphrase();
						defaultKeyphrases[count2].setPhrase(keyphrase2);
						defaultKeyphrases[count2].setSearchEngines(new String[] {"google"} );
						count2++;
					}
					/* array inclui as keywords específicas à cabeça, mais as default (vindas do KWAC) a seguir */
					return defaultKeyphrases; 
				}
			}
		}
		else
		{
			return null;
		}
		return customkeyphrases;
		
	}
	
/* FM 13.09.2016 */
	public CustomKeyphrase[] getCustomKeywords(String ca0_nrid,String heading_nrid,String adgroup_id)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> keyphrases = q.getExtCustomKeywordHQL(ca0_nrid, heading_nrid, adgroup_id, session);
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
				customkeyphrases[count]= new CustomKeyphrase();
/* FM 04.11.2016 - As keywords personalizadas deverão ser do tipo PHRASE !!!				customkeyphrases[count].setPhrase(extCustomKeyphrase[2].toString());
	*/
				customkeyphrases[count].setPhrase("\"" + extCustomKeyphrase[2].toString() + "\"");
				customkeyphrases[count].setSearchEngines(new String[] {"google"} );
				count++;
			}
		}
		else
		{
			return null;
		}
		return customkeyphrases;
		
	}
	
	public CustomAdCopy[] createKwacCustomAdcopies(String adgroup_id, boolean pIncludeStdAdcopies)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateKwacUtil.openKwacSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> adcopies = q.getKwacCustomAdcopyHQL(adgroup_id, session, pIncludeStdAdcopies);
		CustomAdCopy[] customAdcopies = new CustomAdCopy[adcopies.size()];
		if(adcopies.size()!=0)
		{
			Iterator<?> iterator = adcopies.listIterator();
			
			int count = 0;
			while(iterator.hasNext())
			{
				Object[] extCustomAdcopy = (Object[]) iterator.next();
				CustomAdCopy customAdCopy = (CustomAdCopy) context.getBean("customadcopy");
				
/* FM 28.12.2016
 * 				customAdCopy.setTitle(extCustomAdcopy[2].toString().replace('|', '&'));
				customAdCopy.setLine1(extCustomAdcopy[3].toString().replace('|', '&'));
				customAdCopy.setLine2(extCustomAdcopy[4].toString().replace('|', '&'));
				customAdcopies[count] = new CustomAdCopy();
				customAdcopies[count].setTitle(customAdCopy.getTitle().replace('~', '\''));
				customAdcopies[count].setLine1(customAdCopy.getLine1().replace('~', '\''));
				customAdcopies[count].setLine2(customAdCopy.getLine2().replace('~', '\''));
*/
				// FM 26.10.2016 - GA Expanded AdCopies:
				if (!(extCustomAdcopy[5]==null) && extCustomAdcopy[5].toString().length()>0) {
					if (extCustomAdcopy[8].toString().equals("ACTV")) {
						customAdCopy.setTitle(extCustomAdcopy[5].toString().replace('|', '&'));
						customAdCopy.setTitle2(extCustomAdcopy[6].toString().replace('|', '&'));
						customAdCopy.setLine1(extCustomAdcopy[7].toString().replace('|', '&'));
						customAdcopies[count] = new CustomAdCopy();
						customAdcopies[count].setTitle(customAdCopy.getTitle().replace('~', '\''));
						customAdcopies[count].setTitle2(customAdCopy.getTitle2().replace('~', '\''));
						customAdcopies[count].setLine1(customAdCopy.getLine1().replace('~', '\''));
						count++;
					}
					else {
						System.out.println("Adgroup " + adgroup_id + " tem Expanded-Adcopy " + extCustomAdcopy[5].toString() + " inactivo!");
					}
				}
				else {
					if (pIncludeStdAdcopies) {
						if (extCustomAdcopy[8].toString().equals("ACTV")) {
							customAdCopy.setTitle(extCustomAdcopy[2].toString().replace('|', '&'));
							customAdCopy.setLine1(extCustomAdcopy[3].toString().replace('|', '&'));
							customAdCopy.setLine2(extCustomAdcopy[4].toString().replace('|', '&'));
							customAdcopies[count] = new CustomAdCopy();
							customAdcopies[count].setTitle(customAdCopy.getTitle().replace('~', '\''));
							customAdcopies[count].setLine1(customAdCopy.getLine1().replace('~', '\''));
							customAdcopies[count].setLine2(customAdCopy.getLine2().replace('~', '\''));
							count++;
						}
						else {
							System.out.println("Adgroup " + adgroup_id + " tem Std-Adcopy " + extCustomAdcopy[2].toString() + " inactivo!");
						}
					}
				}				
				
			}
		}else
		{
			return null;
		}
		
		return customAdcopies;
	}
	
	public CustomKeyphrase[] createKwacCustomKeyphrases(String adgroup_id)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateKwacUtil.openKwacSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> keyphrases = q.getKwacCustomKeywordHQL(adgroup_id, session);
		CustomKeyphrase[] customkeyphrases = new CustomKeyphrase[keyphrases.size()];
		
		if(keyphrases.size()!=0)
		{
			Iterator<?> iterator = keyphrases.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{
				Object[] extCustomKeyphrase = (Object[]) iterator.next();
				CustomKeyphrase customKeyphrase = (CustomKeyphrase) context.getBean("customkeyphrase");
				
				String keyphrase = null;
				if (extCustomKeyphrase[2].toString().equals("EXACT")) {
					keyphrase = "["+ extCustomKeyphrase[1].toString() +"]";
				}
				else {
					if (extCustomKeyphrase[2].toString().equals("PHRASE")) {
						keyphrase = "\""+ extCustomKeyphrase[1].toString() +"\"";
					}
					else {
						if (extCustomKeyphrase[2].toString().equals("NPHRAS")) {
							keyphrase = "-\""+ extCustomKeyphrase[1].toString() +"\"";
						}
						else {
							if (extCustomKeyphrase[2].toString().equals("NEXACT")) {
								keyphrase = "-["+ extCustomKeyphrase[1].toString() +"]";
							}
							else {
								if (extCustomKeyphrase[2].toString().equals("NBROAD")) {
									if (extCustomKeyphrase[1].toString().contains(" ")) {
										/* se a keyword tiver um ou mais espaços assume-se que é NEGATIVE_PHRASE */
										keyphrase = "-\""+ extCustomKeyphrase[1].toString() +"\"";
									}
									else {
										keyphrase = "-"+ extCustomKeyphrase[1].toString() ;	
									}
								}
								else {
								// assume que é BROAD...
									keyphrase = extCustomKeyphrase[1].toString();
								}
							}
						}
					}
				}

				customkeyphrases[count]= new CustomKeyphrase();
				customkeyphrases[count].setPhrase(keyphrase);
				customkeyphrases[count].setSearchEngines(new String[] {"google"} );
				count++;
			}
		}
		else
		{
			return null;
		}
		
		return customkeyphrases;		
	}
	
	public GeographicTarget createGeoloc(String advertid, String ca0_nrid, String heading_nrid)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateUtil.openSession();
		Session dossrSession = HibernateOraUtil.openOraSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
//		List<?> geolocs = q.getExtGeolocHQL(advertid, session);
		List<?> geolocs = q.getExtGeolocHQL(ca0_nrid, heading_nrid, session);
		
		GeographicTarget geographicTargets = (GeographicTarget) context.getBean("geographictarget");
//		PostalCodeRadius newPostalCodeRadius = null;
//		PostalCodeRadius[] postalCodesRadius = new PostalCodeRadius[geolocs.size()];
		PointRadius newPointRadius = null;
		PointRadius[] pointRadii = new PointRadius[geolocs.size()];
		if(geolocs.size()!=0)
		{
			Iterator<?> iterator = geolocs.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{				
				Object[] extGeoloc = (Object[]) iterator.next();
				
/*				newPostalCodeRadius = new PostalCodeRadius();				
				newPostalCodeRadius.setPostalCode(extGeoloc[0].toString());
				newPostalCodeRadius.setRadius(Integer.parseInt(extGeoloc[1].toString()));
				newPostalCodeRadius.setCountryCode("PT");
				newPostalCodeRadius.setDescription(extGeoloc[2].toString());
*/				
				newPointRadius = new PointRadius();
				QueryCampaignDB q2 = (QueryCampaignDB) context.getBean("queryCampaignDB");
				String postalCode = null;
				if (extGeoloc[0]==null)
					postalCode="2250-079";
				else {
					postalCode = extGeoloc[0].toString();
				}
				/* X=Longitude  e  Y=Latitude*/
				if (!dossrSession.isOpen())
					dossrSession = HibernateOraUtil.openOraSession();
				String extDossrGeoInfo = q2.getDossrGeoInfoXHQL(advertid, postalCode, dossrSession);
				newPointRadius.setLongitude(Float.parseFloat(extDossrGeoInfo));
/* Y */
				if (!dossrSession.isOpen())
					dossrSession = HibernateOraUtil.openOraSession();
				extDossrGeoInfo = q2.getDossrGeoInfoYHQL(advertid, postalCode, dossrSession);
				newPointRadius.setLatitude(Float.parseFloat(extDossrGeoInfo));
				
		/*				List<?> dossrGeoInfoX = q2.getDossrGeoInfoXHQL(advertid, postalCode, dossrSession);
				Iterator<?> dossrIterator = dossrGeoInfoX.listIterator();
				while(dossrIterator.hasNext())
				{	
					Object[] extDossrGeoInfo = (Object[]) dossrIterator.next();
/					String[] extDossrGeoInfo = (String[]) dossrIterator.next();
/					String extDossrGeoInfo = (String) dossrIterator.next();

					
/					newPointRadius.setLongitude(Float.parseFloat(extDossrGeoInfo));
					newPointRadius.setLongitude(Float.parseFloat(extDossrGeoInfo[0].toString()));
				}

				List<?> dossrGeoInfoY = q2.getDossrGeoInfoYHQL(advertid, postalCode, dossrSession);
				Iterator<?> dossrIteratorY = dossrGeoInfoY.listIterator();
				while(dossrIteratorY.hasNext())
				{		
					Object[] extDossrGeoInfoY = (Object[]) dossrIteratorY.next();
/					String[] extDossrGeoInfoY = (String[]) dossrIteratorY.next();
/					String extDossrGeoInfoY = (String) dossrIteratorY.next();

/					newPointRadius.setLatitude(Float.parseFloat(extDossrGeoInfoY));
					newPointRadius.setLatitude(Float.parseFloat(extDossrGeoInfoY[0].toString()));
				}
*/
// FM 09.08.2016 - Evitar que estoire se vier a null !
				if (extGeoloc[1]==null)
					newPointRadius.setRadius(Integer.parseInt("325"));
				else {
					newPointRadius.setRadius(Integer.parseInt(extGeoloc[1].toString()));
				}
				newPointRadius.setDescription(extGeoloc[2].toString() +" ("+ postalCode +")");
				// FM 02.05.2016 - carrega tb pointRadii
				pointRadii[count] = newPointRadius;
				
// FM 10.05.2016				postalCodesRadius[count]=newPostalCodeRadius;
				count++;
			}
/* Deixa de enviar os CP7s, envia só as coordenadas X e Y respectivas:
 * 			geographicTargets.setPostalCodeRadii(postalCodesRadius); */
			geographicTargets.setPointRadii(pointRadii);
		}
		else
		{
			return null;
		}
		
		return geographicTargets;
	}
	
	public Sitelink[] createSitelink(String advertid, String ca0_nrid, String heading_nrid)
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		
		Session session = HibernateUtil.openSession();
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		List<?> extsitelinks = q.getExtSitelinkHQL(ca0_nrid, heading_nrid, session);
		
		Sitelink[] sitelinks = new Sitelink[extsitelinks.size()];
		
		if(extsitelinks.size()!=0)
		{
			Iterator<?> iterator = extsitelinks.listIterator();
			int count = 0;
			while(iterator.hasNext())
			{				
				Object[] extSitelink = (Object[]) iterator.next();
				
				Sitelink sitelink = (Sitelink) context.getBean("sitelink");
				
				String auxSitelinkName = extSitelink[0].toString().replace('|', '&');
				if (auxSitelinkName.replace('~', '\'').length()>25) {
// FM 14.02.2017
					System.out.println("[createSitelink-" +advertid+ "] slName:" + auxSitelinkName.replace('~', '\'') + " tem mais de 25 caracteres - vai ser truncado!");
					sitelink.setName(auxSitelinkName.replace('~', '\'').substring(0, 25));
				}
				else {
					sitelink.setName(auxSitelinkName.replace('~', '\''));
				}
				String requestedURL = extSitelink[1].toString();
				if(!requestedURL.contains("http://") && !requestedURL.contains("https://"))
				{
					requestedURL = "http://" + requestedURL;
				}
				sitelink.setRequestedUrl(requestedURL);
				sitelink.setUserStatus("active");
				
//				sitelinks[count]=sitelink;
				sitelinks[count] = new Sitelink();
				sitelinks[count].setName(sitelink.getName());
				sitelinks[count].setRequestedUrl(requestedURL);
				sitelinks[count].setUserStatus(sitelink.getUserStatus());
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
