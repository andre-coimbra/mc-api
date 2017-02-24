package pt.paginasamarelas.logicLayer.operations;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pt.paginasamarelas.dataLayer.entities.AdSchedule;
import pt.paginasamarelas.dataLayer.entities.Advertiser;
import pt.paginasamarelas.dataLayer.entities.Budget;
import pt.paginasamarelas.dataLayer.entities.BusinessAddress;
import pt.paginasamarelas.dataLayer.entities.Callout;
import pt.paginasamarelas.dataLayer.entities.Campaign;
import pt.paginasamarelas.dataLayer.entities.CategoryID;
import pt.paginasamarelas.dataLayer.entities.CategoryRef;
import pt.paginasamarelas.dataLayer.entities.CustomAdCopy;
import pt.paginasamarelas.dataLayer.entities.CustomCategory;
import pt.paginasamarelas.dataLayer.entities.CustomKeyphrase;
import pt.paginasamarelas.dataLayer.entities.Diagnostic;
import pt.paginasamarelas.dataLayer.entities.GeographicTarget;
import pt.paginasamarelas.dataLayer.entities.Location;
import pt.paginasamarelas.dataLayer.entities.LocationId;
import pt.paginasamarelas.dataLayer.entities.Operations;
import pt.paginasamarelas.dataLayer.entities.PointRadius;
import pt.paginasamarelas.dataLayer.entities.Response;
import pt.paginasamarelas.dataLayer.entities.Sitelink;
import pt.paginasamarelas.dataLayer.hibernate.entities.ExtAdvert;
import pt.paginasamarelas.logicLayer.common.log4j.AppendLog4jLogger;
import pt.paginasamarelas.logicLayer.common.log4j.LogKeys;
import pt.paginasamarelas.logicLayer.controller.UpdateCampaignController;

public class CampaignUpdater {

	private ApplicationContext context;
	private boolean swLocationMismatch=false;
	private boolean swBudgetMismatch=false;
	private boolean swBudgetChanged=false;
	private boolean swGeoTargetMismatch=false;
	private boolean swAbortUpdate=false;
	private int cntCategoryRefs=0;
	
		private boolean busPhoneMismatch(Advertiser mcCamp, Advertiser cmcCamp)
		{
			int difsNo = 0;
			String mcBusPhone = mcCamp.getBusinessPhone();
			
			 if (mcBusPhone==null) {
				 if (cmcCamp.getBusinessPhone()==null)
					 /* campanha não tem busPhone */
					 difsNo=0;
				 else {
						 /* não tinha e passou a ter busPhone */
						 difsNo++;
				 }
			 }
			 else {
				 if (cmcCamp.getBusinessPhone()==null)
					 /* tinha e deixou de ter busPhone */
					 difsNo++;
				 else {
					 if (!(mcCamp.getBusinessPhone().equals(cmcCamp.getBusinessPhone())) )
						 difsNo++;
				 }
			 }
			/* logar diferenca !*/
			
			if (difsNo>0)
				return true;
			else
				return false;
		}

		public CustomAdCopy[] createAdcopies(List<CustomAdCopy> mcAdcopyList, List<CustomAdCopy> cmcAdcopyList) throws MalformedURLException, URISyntaxException
		{
			CustomAdCopy[] refreshedAdcopies = new CustomAdCopy[cmcAdcopyList.size()];			
			
			Iterator<?> adcopyIterator = cmcAdcopyList.listIterator();
			boolean adcopyFound=false;
			boolean swAdcopyContinua=true;
			int count=0;
			int count2=0;
			
		    while(adcopyIterator.hasNext()) 
		    {
		    	adcopyFound=false;
		    	CustomAdCopy cmcAdcopy = (CustomAdCopy) context.getBean("customadcopy");
		    	cmcAdcopy = (CustomAdCopy) adcopyIterator.next();
		    	CustomAdCopy newAdcopy = new CustomAdCopy();
		    	if (!(cmcAdcopy==null)) {
			    	swAdcopyContinua=true;

			    	count2 = 0;
			    	Iterator<?> mcAdcopyIterator = mcAdcopyList.listIterator();
			    	while (swAdcopyContinua && mcAdcopyIterator.hasNext()) {
			    		CustomAdCopy mcAdcopy = (CustomAdCopy) context.getBean("customadcopy");
			    		mcAdcopy = (CustomAdCopy) mcAdcopyIterator.next();
			    		count2++;
			    		String adcopyTitle = mcAdcopy.getTitle();
			    		String mcAdcopyLine2 = mcAdcopy.getLine2();
			    		/* Um Adcopy só é igual quando todos os 3 componentes são iguais ! */
			    		if (cmcAdcopy.getLine2()==null) {
			    			/* cmc-Adcopy é Expanded */
			    			if (mcAdcopyLine2==null) {
				    			/* mc-Adcopy também é Expanded */
			    				if (adcopyTitle.equalsIgnoreCase(cmcAdcopy.getTitle()) &&
			    						mcAdcopy.getTitle2().equalsIgnoreCase(cmcAdcopy.getTitle2()) &&
			    						mcAdcopy.getLine1().equalsIgnoreCase(cmcAdcopy.getLine1())) {
					    			adcopyFound=true;
							    	swAdcopyContinua=false;
							    	newAdcopy = (CustomAdCopy) mcAdcopy;
			    				}		    				
			    			}
			    		}
			    		else {
			    			/* assume-se que cmc-Adcopy é Standard. mc-Adcopy também tem que o ser ! */
			    			if (mcAdcopy.getTitle2()==null) {
			    				if (adcopyTitle.equalsIgnoreCase(cmcAdcopy.getTitle()) &&
			    						mcAdcopy.getLine1().equalsIgnoreCase(cmcAdcopy.getLine1()) &&
			    						mcAdcopy.getLine2().equalsIgnoreCase(cmcAdcopy.getLine2())) {
					    			adcopyFound=true;
							    	swAdcopyContinua=false;
							    	newAdcopy = (CustomAdCopy) mcAdcopy;
			    				}
			    			}
			    		}
			    	}
			    	
			    	if (!adcopyFound) {
/* cria Adcopy com info do CMC */	
			    		swLocationMismatch = true;
	
			    		newAdcopy.setTitle(cmcAdcopy.getTitle());
			    		/* FM 06.12.2016 */
			    		if (!(cmcAdcopy.getTitle2()==null))
				    		newAdcopy.setTitle2(cmcAdcopy.getTitle2());
			    		newAdcopy.setLine1(cmcAdcopy.getLine1());
			    		if (!(cmcAdcopy.getLine2()==null)) {
				    		newAdcopy.setLine2(cmcAdcopy.getLine2());
							System.out.println( "New STD (custom)Adcopy - tit:" + newAdcopy.getTitle() + "  Line1:" + newAdcopy.getLine1() + "  Line2:" + newAdcopy.getLine2());
			    		}
			    		else {
							System.out.println( "New XPD (custom)Adcopy - tit:" + newAdcopy.getTitle() + "  tit2:" + newAdcopy.getTitle2() + "  Line1:" + newAdcopy.getLine1());
			    		}
	
						//fill location array
			    		refreshedAdcopies[count] = newAdcopy;					
						count++;
			    	}
			    	else {
			    		/* faz update com base no Adcopy da Matchcraft ! */
			    		if (cmcAdcopy.getLine2()==null && newAdcopy.getLine2()==null) {
			    			/* FM 06.12.2016 - EXPANDED ad-copy */
				    		if (!(newAdcopy.getTitle2().equals(cmcAdcopy.getTitle2())) ||
				    				!(newAdcopy.getLine1().equals(cmcAdcopy.getLine1())) ) {
								System.out.println( "Adcopy [" + newAdcopy.getTitle() + "] changed - Title2:" + newAdcopy.getTitle2() + " -> " + cmcAdcopy.getTitle2() + "  and  Line1:" + newAdcopy.getLine1() + " -> " + cmcAdcopy.getLine1());
				    			newAdcopy.setTitle2(cmcAdcopy.getTitle2());
				    			newAdcopy.setLine1(cmcAdcopy.getLine1());
				    			swLocationMismatch = true;
				    		}
			    		}
			    		else {
			    			/* FM 06.12.2016 - STANDARD ad-copy */
				    		if (!(newAdcopy.getLine1().equals(cmcAdcopy.getLine1())) ||
				    				!(newAdcopy.getLine2().equals(cmcAdcopy.getLine2())) ) {
								System.out.println( "Adcopy [" + newAdcopy.getTitle() + "] changed - Line1:" + newAdcopy.getLine1() + " -> " + cmcAdcopy.getLine1() + "  and  Line2:" + newAdcopy.getLine2() + " -> " + cmcAdcopy.getLine2());
				    			newAdcopy.setLine1(cmcAdcopy.getLine1());
				    			newAdcopy.setLine2(cmcAdcopy.getLine2());
				    			swLocationMismatch = true;
				    		}
			    		}
			    		//fill location array
			    		refreshedAdcopies[count] = newAdcopy;					
						count++;
			    	}
		    	}
		    }
	    	return refreshedAdcopies;
		}

		public CustomKeyphrase[] createKeyphrases(List<CustomKeyphrase> mcKeyphraseList, List<CustomKeyphrase> cmcKeyphraseList) throws MalformedURLException, URISyntaxException
		{
			CustomKeyphrase[] refreshedKeyphrases = new CustomKeyphrase[cmcKeyphraseList.size()];			
			
			Iterator<?> keyphraseIterator = cmcKeyphraseList.listIterator();
			boolean keyphraseFound=false;
			boolean swKeyphraseContinua=true;
			int count=0;
			
		    while(keyphraseIterator.hasNext()) 
		    {
		    	keyphraseFound=false;
		    	CustomKeyphrase cmcKeyphrase = (CustomKeyphrase) context.getBean("customkeyphrase");
		    	cmcKeyphrase = (CustomKeyphrase) keyphraseIterator.next();
	    		String cmcPhrase = cmcKeyphrase.getPhrase().toLowerCase().trim();
		    	CustomKeyphrase newKeyphrase = new CustomKeyphrase();
		    	swKeyphraseContinua=true;
		    	
				Iterator<?> mcKeyphraseIterator = mcKeyphraseList.listIterator();
		    	while (swKeyphraseContinua && mcKeyphraseIterator.hasNext()) {
		    		CustomKeyphrase mcKeyphrase = (CustomKeyphrase) context.getBean("customkeyphrase");
		    		mcKeyphrase = (CustomKeyphrase) mcKeyphraseIterator.next();
		    		if (mcKeyphrase.getPhrase().equals( cmcPhrase )) {
				    	keyphraseFound=true;
				    	swKeyphraseContinua=false;
				    	newKeyphrase = (CustomKeyphrase) mcKeyphrase;
		    		}
		    	}
		    	
		    	if (!keyphraseFound) {
/* cria Keyphrase com info do CMC */	
		    		swLocationMismatch = true;

		    		newKeyphrase.setPhrase(cmcKeyphrase.getPhrase());
		    		newKeyphrase.setSearchEngines(new String[] {"google"} );

					System.out.println( "New Keyphrase:" + cmcKeyphrase.getPhrase());

					//fill location array
					refreshedKeyphrases[count] = newKeyphrase;					
					count++;
		    	
		    	}
		    	else {
		    		/* faz update com base na Keyphrase da Matchcraft ! */
		    		if (!(newKeyphrase.getPhrase().equals(cmcPhrase))) {
		    			if (!swLocationMismatch)
							System.out.println( "Keyphrase changed:" + newKeyphrase.getPhrase() +" -> ["+ cmcPhrase +"]");
		    			newKeyphrase.setPhrase( cmcPhrase );
		    			swLocationMismatch = true;
		    		}
					//fill location array
					refreshedKeyphrases[count] = newKeyphrase;					
					count++;

		    	}
		    }
	    	return refreshedKeyphrases;
		}

		public CategoryRef[] createCategoryRefs(String subscrId, List<CategoryRef> mcCatRefList, List<CategoryRef> cmcCatRefList, int pSimpleReferenceInfo) throws MalformedURLException, URISyntaxException
		{
			CategoryRef[] refreshedCatRefs = new CategoryRef[cmcCatRefList.size()];			
			
			Iterator<?> catRefIterator = cmcCatRefList.listIterator();
			boolean catRefFound=false;
			boolean swCatRefContinua=true;
			int count=0;
			
		    while(catRefIterator.hasNext()) 
		    {
		    	catRefFound=false;
		    	CategoryRef cmcCatRef = (CategoryRef) context.getBean("categoryref");
		    	cmcCatRef = (CategoryRef) catRefIterator.next();
		    	CategoryRef newCatRef = new CategoryRef();
		    	swCatRefContinua=true;

				Iterator<?> mcCatRefIterator = mcCatRefList.listIterator();
		    	while (swCatRefContinua && mcCatRefIterator.hasNext()) {
		    		CategoryRef mcCatRef = (CategoryRef) context.getBean("categoryref");
			    	mcCatRef = (CategoryRef) mcCatRefIterator.next();
		    		if (mcCatRef.getExternalId().equals(cmcCatRef.getExternalId())) {
				    	catRefFound=true;
				    	swCatRefContinua=false;
				    	newCatRef = (CategoryRef) mcCatRef;
		    		}
		    	}
		    	
		    	if (!catRefFound) {
		    		// se é uma Adgroup específico deste cliente, para já assume que ainda não existe na MC
			    	// no futuro vai ter que olhar para a data no Kwac para saber se já passou para a MC ou não
			    	if ((cmcCatRef.getName()).indexOf(subscrId)<0) {
			    		/* cria Category-Ref com info do CMC */	
			    		swLocationMismatch = true;
	
		    			// FM 17.02.2017
			    		if (cmcCatRef.getExternalId().equals("12920")) {
			    			newCatRef.setExternalId("BKL");
			    		}
			    		else {
				    		if (cmcCatRef.getExternalId().equals("12934")) {
				    			newCatRef.setExternalId("5135073");
				    		}
				    		else {
					    		if (cmcCatRef.getExternalId().equals("12945")) {
					    			newCatRef.setExternalId("5140401");
					    		}
					    		else
						    		newCatRef.setExternalId(cmcCatRef.getExternalId());
				    		}
			    		}
			    		newCatRef.setName(cmcCatRef.getName());
			    		newCatRef.setUserStatus("active");
			    		if (pSimpleReferenceInfo==0) {
				    		newCatRef.setAdcopies(cmcCatRef.getAdcopies());
							newCatRef.setKeyphrases(cmcCatRef.getKeyphrases());
			    		}
	
						System.out.println( "New Category-Ref:" + cmcCatRef.getExternalId() +" "+ cmcCatRef.getName());
						//fill location array
						refreshedCatRefs[count] = newCatRef;					
						count++;
			    	}
		    	}
		    	else {
		    		/* faz update com base na Location da Matchcraft ! */
		    		if (!(newCatRef.getName().equals(cmcCatRef.getName()))) {
		    			if (!swLocationMismatch)
							System.out.println( "CatRef changed:" + newCatRef.getName() +"->"+ cmcCatRef.getName());
		    			newCatRef.setName(cmcCatRef.getName());
		    			swLocationMismatch = true;
		    		}

		    		// Pode ter N customAdCopies
		    		CustomAdCopy[] cmcAdcopies = cmcCatRef.getAdcopies();
		    		CustomAdCopy[] matchcraftAdcopies = newCatRef.getAdcopies();
		    		if (matchcraftAdcopies==null) {
		    			if  (!(cmcAdcopies==null)) {
							newCatRef.setAdcopies(cmcAdcopies);
		    			}
		    		}
		    		else {
		    			if (!(cmcAdcopies==null)) {
							List<CustomAdCopy> matchcraftAdcopyList =  new ArrayList<CustomAdCopy>(Arrays.asList(matchcraftAdcopies));
				    		if (matchcraftAdcopies!=null || cmcAdcopies!=null) {
					    		List<CustomAdCopy> cmcAdcopyList =  new ArrayList<CustomAdCopy>(Arrays.asList(cmcAdcopies));
					    		CustomAdCopy[] newAdcopies = null;
								newAdcopies = createAdcopies(matchcraftAdcopyList, cmcAdcopyList );
								
								newCatRef.setAdcopies(newAdcopies);
				    		}
			    		}
		    			else {
		    				// FM 12.12.2016 - Se tinha customAdcopies e deixou de ter...
				    		CustomAdCopy[] newAdcopiesEmpty = null;		    				
							newCatRef.setAdcopies(newAdcopiesEmpty);
		    			}
		    		}

		    		// Pode ter N (custom) keywords
		    		CustomKeyphrase[] cmcKeyphrases = cmcCatRef.getKeyphrases();
		    		CustomKeyphrase[] matchcraftKeyphrases = newCatRef.getKeyphrases();
		    		if (matchcraftKeyphrases==null) {
		    			if  (!(cmcKeyphrases==null)) {
							newCatRef.setKeyphrases(cmcKeyphrases);
		    			}
		    		}
		    		else {
		    			if (cmcKeyphrases==null) {
							newCatRef.setKeyphrases(matchcraftKeyphrases);		    				
		    			}
		    			else {
							List<CustomKeyphrase> matchcraftKeyphraseList =  new ArrayList<CustomKeyphrase>(Arrays.asList(matchcraftKeyphrases));
				    		if (matchcraftKeyphrases!=null || cmcKeyphrases!=null) {
								List<CustomKeyphrase> cmcKeyphraseList =  new ArrayList<CustomKeyphrase>(Arrays.asList(cmcKeyphrases));
					    		CustomKeyphrase[] newKeyphrases = null;
								newKeyphrases = createKeyphrases(matchcraftKeyphraseList, cmcKeyphraseList );						
								newCatRef.setKeyphrases(newKeyphrases);
				    		}
		    			}
		    		}

		    		//fill location array
					refreshedCatRefs[count] = newCatRef;					
					count++;
		    	}
		    }
		    if (count==0)
		    	refreshedCatRefs = null;
	    	return refreshedCatRefs;
		}

		public Sitelink[] createSitelinks(List<Sitelink> mcSitelinkList, List<Sitelink> cmcSitelinkList) throws MalformedURLException, URISyntaxException
		{
			Sitelink[] refreshedSitelinks = new Sitelink[cmcSitelinkList.size()];			
			
			Iterator<?> sitelinkIterator = cmcSitelinkList.listIterator();
			boolean sitelinkFound=false;
			boolean swSLContinua=true;
			int count=0;
			
		    while(sitelinkIterator.hasNext()) 
		    {
		    	sitelinkFound=false;
		    	Sitelink cmcSitelink = (Sitelink) context.getBean("sitelink");
		    	cmcSitelink = (Sitelink) sitelinkIterator.next();
		    	Sitelink newSitelink = new Sitelink();
		    	swSLContinua=true;

				Iterator<?> mcSLIterator = mcSitelinkList.listIterator();
		    	while (swSLContinua && mcSLIterator.hasNext()) {
		    		Sitelink mcSitelink = (Sitelink) context.getBean("sitelink");
			    	mcSitelink = (Sitelink) mcSLIterator.next();
		    		if (mcSitelink.getName().equals(cmcSitelink.getName())) {
				    	sitelinkFound=true;
				    	swSLContinua=false;
				    	newSitelink = (Sitelink) mcSitelink;
		    		}
		    	}
		    	
		    	if (!sitelinkFound) {
/* cria Sitelink com info do CMC */	
		    		swLocationMismatch = true;

		    		newSitelink.setName(cmcSitelink.getName());
					if (cmcSitelink.getName().replace('~', '\'').length()>25) {
// FM 14.02.2017
						System.out.println("[createSitelinks] slName:" + cmcSitelink.getName().replace('~', '\'') + " tem mais de 25 caracteres - vai ser truncado!");
						newSitelink.setName(cmcSitelink.getName().replace('~', '\'').substring(0, 25));
					}
					else {
						newSitelink.setName(cmcSitelink.getName().replace('~', '\''));
					}

		    		newSitelink.setRequestedUrl(cmcSitelink.getRequestedUrl());
					newSitelink.setUserStatus(cmcSitelink.getUserStatus());

					System.out.println( "New Sitelink:" + cmcSitelink.getName() +" "+ cmcSitelink.getRequestedUrl());
					//fill sitelink array
					refreshedSitelinks[count] = newSitelink;					
					count++;
		    	
		    	}
		    	else {
		    		/* faz update com base no Sitelink da Matchcraft ! */
		    		if (!(newSitelink.getRequestedUrl().equals(cmcSitelink.getRequestedUrl()))) {
		    			newSitelink.setRequestedUrl(cmcSitelink.getRequestedUrl());
		    			swLocationMismatch = true;
		    		}
		    		if (!(newSitelink.getUserStatus().equals(cmcSitelink.getUserStatus()))) {
		    			newSitelink.setUserStatus(cmcSitelink.getUserStatus());
		    			swLocationMismatch = true;
		    		}

		    		//fill location array
					refreshedSitelinks[count] = newSitelink;					
					count++;
		    	}
		    }
	    	return refreshedSitelinks;
		}

		/* FM 29.07.2016 - workaround quando não há externalId considera que há match quando encontra um Adcopy igual ! */
		public boolean adcopyMatch(CustomAdCopy[] mcAdcopies, CustomAdCopy[] cmcAdcopies)
		{
			boolean swAdcopyMatch=false;
			boolean swAdcopyContinua=true;
			int count=0;
			
			if (mcAdcopies==null || cmcAdcopies==null)
				swAdcopyMatch=false;
			else {
				List<CustomAdCopy> mcAdcopyList =  new ArrayList<CustomAdCopy>(Arrays.asList(mcAdcopies));
				List<CustomAdCopy> cmcAdcopyList =  new ArrayList<CustomAdCopy>(Arrays.asList(cmcAdcopies));
				Iterator<?> adcopyIterator = cmcAdcopyList.listIterator();
				while(adcopyIterator.hasNext() && (!swAdcopyMatch)) 
			    {
			    	CustomAdCopy cmcAdcopy = (CustomAdCopy) context.getBean("customadcopy");
			    	cmcAdcopy = (CustomAdCopy) adcopyIterator.next();
			    	if (!(cmcAdcopy==null)) {
				    	swAdcopyContinua=true;
		
						Iterator<?> mcAdcopyIterator = mcAdcopyList.listIterator();
				    	while (swAdcopyContinua && mcAdcopyIterator.hasNext()) {
				    		CustomAdCopy mcAdcopy = (CustomAdCopy) context.getBean("customadcopy");
				    		mcAdcopy = (CustomAdCopy) mcAdcopyIterator.next();
				    		String adcopyTitle = mcAdcopy.getTitle();
				    		String adcopyLine1 = mcAdcopy.getLine1();
				    		String adcopyLine2 = mcAdcopy.getLine2();
/* FM 06.12.2016
			    		if (adcopyTitle.equalsIgnoreCase(cmcAdcopy.getTitle()) &&
			    				adcopyLine1.equalsIgnoreCase(cmcAdcopy.getLine1()) &&
			    				adcopyLine2.equalsIgnoreCase(cmcAdcopy.getLine2())) {
*/
					    	if (adcopyTitle.equalsIgnoreCase(cmcAdcopy.getTitle()) &&
						    			((cmcAdcopy.getTitle2()==null && mcAdcopy.getTitle2()==null && adcopyLine1.equalsIgnoreCase(cmcAdcopy.getLine1()) && adcopyLine2.equalsIgnoreCase(cmcAdcopy.getLine2())) || 
						    			 (cmcAdcopy.getLine2()==null && mcAdcopy.getLine2()==null && mcAdcopy.getTitle2().equalsIgnoreCase(cmcAdcopy.getTitle2()) && adcopyLine1.equalsIgnoreCase(cmcAdcopy.getLine1()))) ) {
				    			swAdcopyMatch=true;
						    	swAdcopyContinua=false;
				    		}
				    	}
			    	}
			    }
		    }
	    	return swAdcopyMatch;
		}
		
		public boolean keyphraseMatch(CustomKeyphrase[] mcKeyphrases, CustomKeyphrase[] cmcKeyphrases)
		{
			boolean swKeyphraseMatch=false;
			boolean swKeyphraseContinua=true;
			int cntKeyphrIguais=0;
			
			if (mcKeyphrases==null || cmcKeyphrases==null)
				swKeyphraseMatch=false;
			else {
				List<CustomKeyphrase> mcKeyphraseList =  new ArrayList<CustomKeyphrase>(Arrays.asList(mcKeyphrases));
				List<CustomKeyphrase> cmcKeyphraseList =  new ArrayList<CustomKeyphrase>(Arrays.asList(cmcKeyphrases));
				Iterator<?> keyphrIterator = cmcKeyphraseList.listIterator();
				while(keyphrIterator.hasNext() && (!swKeyphraseMatch)) 
			    {
					CustomKeyphrase cmcKeyphr = (CustomKeyphrase) context.getBean("customkeyphrase");
			    	cmcKeyphr = (CustomKeyphrase) keyphrIterator.next();
			    	swKeyphraseContinua=true;
	
					Iterator<?> mcKeyphrIterator = mcKeyphraseList.listIterator();
			    	while (swKeyphraseContinua && mcKeyphrIterator.hasNext()) {
			    		CustomKeyphrase mcKeyphr = (CustomKeyphrase) context.getBean("customkeyphrase");
			    		mcKeyphr = (CustomKeyphrase) mcKeyphrIterator.next();
			    		if (!(mcKeyphr.getPhrase().startsWith("-")) &&
			    				mcKeyphr.getPhrase().equalsIgnoreCase(cmcKeyphr.getPhrase()) ) {
			    			cntKeyphrIguais++;
			    			if (cntKeyphrIguais>2) {
				    			swKeyphraseMatch=true;
				    			swKeyphraseContinua=false;
			    			}
			    		}
			    	}
			    }
		    }
	    	return swKeyphraseMatch;
		}
		
// FM 25.05.2016
		public CustomCategory[] createCustomCategories(List<CustomCategory> mcCatRefList, List<CustomCategory> cmcCatRefList) throws MalformedURLException, URISyntaxException
		{
			CustomCategory[] refreshedCustomCategs = new CustomCategory[cmcCatRefList.size()];			
			
			Iterator<?> catRefIterator = cmcCatRefList.listIterator();
			boolean catRefFound=false;
			boolean swCatRefContinua=true;
			int count=0;
			String mcCatRefExternalId=null;
			
		    while(catRefIterator.hasNext()) 
		    {
		    	catRefFound=false;
		    	CustomCategory cmcCatRef = (CustomCategory) context.getBean("customcategory");
		    	cmcCatRef = (CustomCategory) catRefIterator.next();
		    	CustomCategory newCustomCateg = new CustomCategory();
		    	CategoryID mcCatId = new CategoryID();
		    	swCatRefContinua=true;

				Iterator<?> mcCatRefIterator = mcCatRefList.listIterator();
		    	while (swCatRefContinua && mcCatRefIterator.hasNext()) {
		    		CustomCategory mcCatRef = (CustomCategory) context.getBean("customcategory");
			    	mcCatRef = (CustomCategory) mcCatRefIterator.next();
			    	int auxMcCatIdRipId = mcCatRef.getCategoryId().getRipId();
// FM 29.07.2016 - MC pode não ter o externalId para esta CustomCategory !
			    	mcCatRefExternalId = mcCatRef.getCategoryId().getExternalId();
			    	if (mcCatRefExternalId==null) {
// FM 18.08.2016
			    		if (!(auxMcCatIdRipId==4801054 || 
		    					auxMcCatIdRipId==4801057) && mcCatRef.getName().indexOf("|")>0 && mcCatRef.getName().indexOf( cmcCatRef.getCategoryId().getExternalId() ) > 0) {
					    	catRefFound=true;
					    	swCatRefContinua=false;
/* FM 14.02.2017					    	newCustomCateg = (CustomCategory) mcCatRef;
					    	newCustomCateg.getCategoryId().setRipId( mcCatRef.getCategoryId().getRipId() );
tem que "filtrar" os Adcopies standard !!
*/
					    	CategoryID newCustomCateg_categId = new CategoryID();
					    	newCustomCateg_categId.setRipId( mcCatRef.getCategoryId().getRipId() );
					    	newCustomCateg_categId.setExternalId(cmcCatRef.getCategoryId().getExternalId());
					    	newCustomCateg.setCategoryId(newCustomCateg_categId);
					    	newCustomCateg.setName(mcCatRef.getName());
					    	newCustomCateg.setUserStatus("active");
					    	newCustomCateg.setAdcopies(cmcCatRef.getAdcopies());
					    	newCustomCateg.setKeyphrases(cmcCatRef.getKeyphrases());
					    	if (!(mcCatRef.getImages()==null)) {
					    		newCustomCateg.setImages(mcCatRef.getImages());
					    	}
			    			System.out.println( "MC Custom-Category match by Adgroup-Id found - RipId:" + mcCatRef.getCategoryId().getRipId() + " <-> extId:" + cmcCatRef.getCategoryId().getExternalId());
			    			AppendLog4jLogger.info(LogKeys.errorReportLogKey, "MC Custom-Category match by Adgroup-Id found - RipId:" + mcCatRef.getCategoryId().getRipId() + " <-> extId:" + cmcCatRef.getCategoryId().getExternalId());
			    		}
			    		else {
			    			if (!(auxMcCatIdRipId==4801054 || 
			    					auxMcCatIdRipId==4801057) &&
			    					((auxMcCatIdRipId==4801056 && cmcCatRef.getCategoryId().getExternalId().equals("12656")) || 
			    						(auxMcCatIdRipId==4801055 && cmcCatRef.getCategoryId().getExternalId().equals("12657")) ||
			    						!(auxMcCatIdRipId==4801054 || auxMcCatIdRipId==4801055 || 
				    					auxMcCatIdRipId==4801056 || auxMcCatIdRipId==4801057)) && 
			    					(adcopyMatch(mcCatRef.getAdcopies(), cmcCatRef.getAdcopies()) ||
			    				keyphraseMatch(mcCatRef.getKeyphrases() , cmcCatRef.getKeyphrases() ))) {
						    	catRefFound=true;
						    	swCatRefContinua=false;
						    	newCustomCateg = (CustomCategory) mcCatRef;
						    	newCustomCateg.getCategoryId().setRipId( mcCatRef.getCategoryId().getRipId() );
				    			System.out.println( "MC Custom-Category match found - RipId:" + mcCatRef.getCategoryId().getRipId() + " <-> extId:" + cmcCatRef.getCategoryId().getExternalId());
				    			AppendLog4jLogger.info(LogKeys.errorReportLogKey, "MC Custom-Category match found - RipId:" + mcCatRef.getCategoryId().getRipId() + " <-> extId:" + cmcCatRef.getCategoryId().getExternalId());
				    		}
				    		else {
				    			System.out.println( "MC Custom-Category not found:" + mcCatRef.getCategoryId().getRipId());
				    			AppendLog4jLogger.info(LogKeys.errorReportLogKey, "MC Custom-Category not found:" + mcCatRef.getCategoryId().getRipId());
				    		}
			    		}
			    	}
			    	else {
			    		if (mcCatRef.getCategoryId().getExternalId().equals(cmcCatRef.getCategoryId().getExternalId())) {
					    	catRefFound=true;
					    	swCatRefContinua=false;
					    	newCustomCateg = (CustomCategory) mcCatRef;
					    	newCustomCateg.getCategoryId().setRipId( mcCatRef.getCategoryId().getRipId() );
			    		}
		    		}
		    	}
		    	
		    	if (!catRefFound) {
/* cria Category-Ref com info do CMC */	
		    		swLocationMismatch = true;

		    		CategoryID catId = new CategoryID();
		    		catId.setExternalId(cmcCatRef.getCategoryId().getExternalId());
		    		
		    		newCustomCateg.setCategoryId(catId);
// FM 06.09.2016
		    		if (cmcCatRef.getName().indexOf(cmcCatRef.getCategoryId().getExternalId())>=0)
		    			newCustomCateg.setName(cmcCatRef.getName());
		    		else {
			    		newCustomCateg.setName(cmcCatRef.getName() + "|" + cmcCatRef.getCategoryId().getExternalId());
		    		}
		    		newCustomCateg.setUserStatus(new String("active"));
		    		newCustomCateg.setAdcopies(cmcCatRef.getAdcopies());
		    		newCustomCateg.setKeyphrases(cmcCatRef.getKeyphrases());

					System.out.println( "New Custom-Category:" + cmcCatRef.getCategoryId().getExternalId() +" "+ cmcCatRef.getName());
	    			AppendLog4jLogger.info(LogKeys.errorReportLogKey, "New Custom-Category:" + cmcCatRef.getCategoryId().getExternalId() +" "+ cmcCatRef.getName());
					//fill location array
					refreshedCustomCategs[count] = newCustomCateg;					
					count++;
		    	
		    	}
		    	else {
// catRefFound...
		    		//CategoryID
			    	int mcCatIdRipId = newCustomCateg.getCategoryId().getRipId();
// FM 17.11.2016 - Caso especial
			    	if (mcCatIdRipId==4801054 || mcCatIdRipId==4801057) { 
						System.out.println( "Xcase:" + mcCatIdRipId +"/"+ cmcCatRef.getCategoryId().getExternalId() +" MC category ignored !");
		    		}
		    		else {
				    	if (!(mcCatIdRipId==0))
					    	mcCatId.setRipId(mcCatIdRipId);
				    	String mcCatIdExternalId = newCustomCateg.getCategoryId().getExternalId();
// FM 04.10.2016			    	if (!(mcCatIdExternalId==null))
				    	if (!(mcCatIdExternalId==null) && 
				    			!(mcCatIdRipId==4598078) &&
				    			mcCatIdRipId>0 && mcCatIdRipId<4590000)
					    	mcCatId.setExternalId(mcCatIdExternalId);
// FM 06.10.2016
				    	else {
			    			if (mcCatIdExternalId==null && ((mcCatIdRipId==94801057) ||
			    			     (mcCatIdRipId==94801056)) )
								System.out.println( "Xcase:" + cmcCatRef.getCategoryId().getExternalId() +" MC external ID a null !");
			    			else
						    	mcCatId.setExternalId(cmcCatRef.getCategoryId().getExternalId());
				    	}
				    	
			    		newCustomCateg.setCategoryId(mcCatId);
			    		/* faz update com base na Location da Matchcraft ! */
			    		if (!(newCustomCateg.getName().equals(cmcCatRef.getName())) &&
			    				!(newCustomCateg.getName().equals(cmcCatRef.getName() +"|"+cmcCatRef.getCategoryId().getExternalId())) ) {
			    			if (!swLocationMismatch) {
								System.out.println( "CustomCateg changed:" + newCustomCateg.getName() +"->"+ cmcCatRef.getName());
				    			AppendLog4jLogger.info(LogKeys.errorReportLogKey, "CustomCateg changed:" + newCustomCateg.getName() +"->"+ cmcCatRef.getName());
			    			}
			    			newCustomCateg.setName(cmcCatRef.getName());
			    			swLocationMismatch = true;
			    		}

			    		// Pode ter N customAdCopies
			    		CustomAdCopy[] cmcAdcopies = cmcCatRef.getAdcopies();
			    		CustomAdCopy[] matchcraftAdcopies = newCustomCateg.getAdcopies();
			    		if (matchcraftAdcopies==null) {
			    			if  (!(cmcAdcopies==null)) {
			    				newCustomCateg.setAdcopies(cmcAdcopies);
			    			}
			    		}
			    		else {
							List<CustomAdCopy> matchcraftAdcopyList =  new ArrayList<CustomAdCopy>(Arrays.asList(matchcraftAdcopies));
				    		if (matchcraftAdcopies!=null || cmcAdcopies!=null) {
					    		List<CustomAdCopy> cmcAdcopyList =  new ArrayList<CustomAdCopy>(Arrays.asList(cmcAdcopies));
					    		CustomAdCopy[] newAdcopies = null;
								newAdcopies = createAdcopies(matchcraftAdcopyList, cmcAdcopyList );
								if (!(newAdcopies==null))
									newCustomCateg.setAdcopies(newAdcopies);
				    		}
			    		}

			    		// Pode ter N (custom) keywords
			    		CustomKeyphrase[] cmcKeyphrases = cmcCatRef.getKeyphrases();
			    		CustomKeyphrase[] matchcraftKeyphrases = newCustomCateg.getKeyphrases();
			    		if (matchcraftKeyphrases==null) {
			    			if  (!(cmcKeyphrases==null)) {
			    				newCustomCateg.setKeyphrases(cmcKeyphrases);
			    			}
			    		}
			    		else {
							List<CustomKeyphrase> matchcraftKeyphraseList =  new ArrayList<CustomKeyphrase>(Arrays.asList(matchcraftKeyphrases));
				    		if (matchcraftKeyphrases!=null || cmcKeyphrases!=null) {
								List<CustomKeyphrase> cmcKeyphraseList =  new ArrayList<CustomKeyphrase>(Arrays.asList(cmcKeyphrases));
					    		CustomKeyphrase[] newKeyphrases = null;
								newKeyphrases = createKeyphrases(matchcraftKeyphraseList, cmcKeyphraseList );						
								if (!(newKeyphrases==null))
									newCustomCateg.setKeyphrases(newKeyphrases);
				    		}
			    		}

		    		//fill custom_category array
						refreshedCustomCategs[count] = newCustomCateg;					
						count++;
		    		}
		    	}
		    }
	    	return refreshedCustomCategs;
		}


// FM 02.05.2016
		public PointRadius[] createPointRadii(List<PointRadius> mcPtRadiusList, List<PointRadius> cmcPtRadiusList) throws MalformedURLException, URISyntaxException
		{
			PointRadius[] refreshedPtRadius = new PointRadius[cmcPtRadiusList.size()];			
			
			Iterator<?> pointRadiusIterator = cmcPtRadiusList.listIterator();
			boolean pointRadiusFound=false;
			boolean swPRContinua=true;
			int count=0;
    		swGeoTargetMismatch = false;
			
		    while(pointRadiusIterator.hasNext()) 
		    {
		    	pointRadiusFound=false;
		    	PointRadius cmcPointRadius = (PointRadius) context.getBean("pointradius");
		    	cmcPointRadius = (PointRadius) pointRadiusIterator.next();
		    	PointRadius newPointRadius = new PointRadius();
		    	swPRContinua=true;

				Iterator<?> mcPRIterator = mcPtRadiusList.listIterator();
		    	while (swPRContinua && mcPRIterator.hasNext()) {
		    		PointRadius mcPointRadius = (PointRadius) context.getBean("pointradius");
			    	mcPointRadius = (PointRadius) mcPRIterator.next();
		    		if (mcPointRadius.getLatitude() == cmcPointRadius.getLatitude() &&
		    				mcPointRadius.getLongitude() == cmcPointRadius.getLongitude()	) {
		    			pointRadiusFound=true;
				    	swPRContinua=false;
				    	newPointRadius = (PointRadius) mcPointRadius;
		    		}
		    	}
		    	
		    	if (!pointRadiusFound) {
/* cria PointRadius com info do CMC */	
		    		swLocationMismatch = true;
		    		swGeoTargetMismatch = true;

		    		newPointRadius.setLatitude(cmcPointRadius.getLatitude());
		    		newPointRadius.setLongitude(cmcPointRadius.getLongitude());
					newPointRadius.setRadius(cmcPointRadius.getRadius());
					newPointRadius.setDescription(cmcPointRadius.getDescription());

					System.out.println( "New PointRadius - X:" + cmcPointRadius.getLongitude() +" Y:"+ cmcPointRadius.getLatitude());
					//fill pointRadius array
					refreshedPtRadius[count] = newPointRadius;					
					count++;
		    	
		    	}
		    	else {
		    		/* faz update com base no PointRadius da Matchcraft ! */
		    		if (!(newPointRadius.getRadius() == cmcPointRadius.getRadius()) ) {
						System.out.println( "PointRadius changed - X:" + cmcPointRadius.getLongitude() +" Y:"+ cmcPointRadius.getLatitude() + " radius " + newPointRadius.getRadius() + " -> " + cmcPointRadius.getRadius());
		    			newPointRadius.setRadius(cmcPointRadius.getRadius());
		    			newPointRadius.setDescription(cmcPointRadius.getDescription());
		    			swLocationMismatch = true;
			    		swGeoTargetMismatch = true;
		    		}

		    		//fill pointRadius array
					refreshedPtRadius[count] = newPointRadius;					
					count++;
		    	}
		    }
	    	return refreshedPtRadius;
		}

		public boolean naoTemCustomAdcopies(CategoryRef[] pCatRefs)
		{
			boolean swCustomAdcopiesFlag = true;
			
			/* Se encontra um CustmAdcopy, devolve false */
			if (!(pCatRefs==null)) {
				List<CategoryRef> cmcCatRefList = null;
				cmcCatRefList =  new ArrayList<CategoryRef>(Arrays.asList(pCatRefs));
				Iterator<?> catRefIterator = cmcCatRefList.listIterator();
			    while(catRefIterator.hasNext()) 
			    {
			    	CategoryRef cmcCatRef = (CategoryRef) context.getBean("categoryref");
			    	cmcCatRef = (CategoryRef) catRefIterator.next();
			    	CustomAdCopy[] cmcCatAdcopies = cmcCatRef.getAdcopies();
			    	if (!(cmcCatAdcopies==null) && cmcCatAdcopies.length>0) {
			    		swCustomAdcopiesFlag = false;
			    	}
			    }
			}
			return swCustomAdcopiesFlag;
		}
		
/* FM 13.09.2016 */
		public boolean naoTemCustomKeywords(CategoryRef[] pCatRefs)
		{
			boolean swCustomKeywordsFlag = true;
			
			/* Se encontra uma CustomKeyword, devolve false */
			if (!(pCatRefs==null)) {
				List<CategoryRef> cmcCatRefList = null;
				cmcCatRefList =  new ArrayList<CategoryRef>(Arrays.asList(pCatRefs));
				Iterator<?> catRefIterator = cmcCatRefList.listIterator();
			    while(catRefIterator.hasNext()) 
			    {
			    	CategoryRef cmcCatRef = (CategoryRef) context.getBean("categoryref");
			    	cmcCatRef = (CategoryRef) catRefIterator.next();
			    	CustomKeyphrase[] cmcCatKeywords = cmcCatRef.getCustomKeyphrases();
			    	if (!(cmcCatKeywords==null) && cmcCatKeywords.length>0) {
			    		swCustomKeywordsFlag = false;
			    	}
			    }
			}
			return swCustomKeywordsFlag;
		}
		
		public Location[] createNewLocations(String subscrId, String campStatus,
								List<Location> mcLocList,
								List<Location> cmcLocList,
								boolean prmCampanhaGAespecial,
								boolean prmIncludeStdAdcopies) throws MalformedURLException, URISyntaxException
		{
			Location[] refreshedLocations = new Location[cmcLocList.size()];			
			
			Iterator<?> locIterator = cmcLocList.listIterator();
			boolean locFound=false;
			boolean locPreApiFound=false;
			boolean swLocContinua=true;
			int count=0;
			int simpleReferenceInfo=0;
			int sosiaId=0;
			int auxLen=0;
			int aux2Len=0;
			
		    while(locIterator.hasNext()) 
		    {
		    	locFound=false;
		    	Location cmcLoc = (Location) context.getBean("location");
		    	cmcLoc = (Location) locIterator.next();
	    		Location newLoc = new Location();
	    		swLocContinua=true;
	    		sosiaId=0;
	    		auxLen = cmcLoc.getLocationId().getExternalId().length();

				Iterator<?> mcLocIterator = mcLocList.listIterator();
		    	while (swLocContinua && mcLocIterator.hasNext()) {
			    	Location mcLoc = (Location) context.getBean("location");
			    	mcLoc = (Location) mcLocIterator.next();
		    		aux2Len = mcLoc.getLocationId().getExternalId().length();
			    	String mcLocExternalId = null;
			    	if (auxLen<=28 && aux2Len>34) {
			    		mcLocExternalId = mcLoc.getLocationId().getExternalId();
			    	}
			    	else {
			    		if (aux2Len>auxLen)
			    			mcLocExternalId = mcLoc.getLocationId().getExternalId().substring(0, auxLen );
			    		else {
			    			mcLocExternalId = mcLoc.getLocationId().getExternalId();
			    		}
			    	}
			    	String cmcLocRefId = cmcLoc.getLocationId().getExternalId().substring(0,cmcLoc.getLocationId().getExternalId().indexOf('-'));
			    	String cmcLocClassCode = cmcLoc.getLocationId().getExternalId().substring( cmcLoc.getLocationId().getExternalId().lastIndexOf('-') + 1);
		    		if (mcLocExternalId.equals(cmcLoc.getLocationId().getExternalId()) || 
		    			(!(prmCampanhaGAespecial) && mcLocExternalId.indexOf(cmcLocRefId)>=0 && mcLocExternalId.indexOf(cmcLocClassCode)>=0)) {
/* Antes de 1719792 são as Locations que foram enviadas via Feed */
		    			if (mcLoc.getLocationId().getRipId() > 1719792
		    					&& mcLoc.getLocationId().getRipId()!=1719897
		    					&& mcLoc.getLocationId().getRipId()!=1753190
		    					&& mcLoc.getLocationId().getRipId()!=1753191
		    					&& mcLoc.getLocationId().getRipId()!=1753192) {
			    			if (mcLoc.getUserStatus().equals("active")) {
						    	locFound=true;
						    	swLocContinua=false;
						    	newLoc = (Location) mcLoc;
			    			}
			    			else
			    				sosiaId++;
		    			}
		    			else {
		    				if (mcLoc.getLocationId().getRipId()==1753190
			    					|| mcLoc.getLocationId().getRipId()==1753191
			    					|| mcLoc.getLocationId().getRipId()==1753192
			    					|| mcLoc.getLocationId().getRipId()==1677415
			    					|| mcLoc.getLocationId().getRipId()==1708642
			    					|| mcLoc.getLocationId().getRipId()==1708643)
		    					sosiaId++;
		    				else {
			    				if (!(auxLen<=28 && aux2Len>34)) {
			    					sosiaId++;
			    				}
		    				}
		    				locPreApiFound = true;
		    			}
		    		}
		    	}
		    	
		    	if (!locFound) {
			    	//LocationID
//			    	LocationId locationId = (LocationId) context.getBean("locationID");
		    		LocationId locationId = new LocationId();
			    	String newExternalId = null;
			    	if (sosiaId==0)
			    		newExternalId=cmcLoc.getLocationId().getExternalId();
			    	else {
			    		String strSosiaId = "" + sosiaId;
			    		newExternalId=cmcLoc.getLocationId().getExternalId().concat(strSosiaId);
			    	}
			    	
			    	locationId.setExternalId(newExternalId);
		    		if (campStatus.equals("3 - Ready to send to BMS") || locPreApiFound) {
/* cria Location com info do CMC */	
			    		swLocationMismatch = true;
						//LocationID
						newLoc.setLocationId(locationId);
						newLoc.setName(cmcLoc.getName());
						newLoc.setUserStatus("active");
						String[] languages = {"pt"};
						newLoc.setLanguages(languages);
						String[] networks = {"google_search","search_network"};
						newLoc.setNetworks(networks);
	
						newLoc.setUrl(cmcLoc.getUrl());
						newLoc.setRequestedDisplayUrl(cmcLoc.getRequestedDisplayUrl());

			    		CategoryRef[] newCategoryRefs = cmcLoc.getCategoryRefs();
			    			
			    		if (!(newCategoryRefs==null)) {
			    			// FM 17.02.2017
				    		if (newCategoryRefs[0].getExternalId().equals("12920")) {
				    			newCategoryRefs[0].setExternalId("BKL");
				    		}
				    		if (newCategoryRefs[0].getExternalId().equals("12934")) {
				    			newCategoryRefs[0].setExternalId("5135073");
				    		}
				    		if (newCategoryRefs[0].getExternalId().equals("12945")) {
				    			newCategoryRefs[0].setExternalId("5140401");
				    		}
				    		if (newCategoryRefs.length>1 && !(newCategoryRefs[1]==null)) {
					    		if (newCategoryRefs[1].getExternalId().equals("12920")) {
					    			newCategoryRefs[1].setExternalId("BKL");
					    		}
					    		if (newCategoryRefs[1].getExternalId().equals("12934")) {
					    			newCategoryRefs[1].setExternalId("5135073");
					    		}
					    		if (newCategoryRefs[1].getExternalId().equals("12945")) {
					    			newCategoryRefs[1].setExternalId("5140401");
					    		}
				    		}
							newLoc.setCategoryRefs( newCategoryRefs );
			    		}
		    			CustomCategory[] newCustomCategs = cmcLoc.getCustomCategories();
						if (!(newCustomCategs==null)) {
							newLoc.setCustomCategories(newCustomCategs);
							System.out.println( "New Location:" + locationId.getExternalId() + " with " + newCustomCategs.length + " CustomCategories");
			    			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"New Location:" + locationId.getExternalId() + " with " + newCustomCategs.length + " CustomCategories" );
						}
						
						newLoc.setGeographicTarget( cmcLoc.getGeographicTarget() );
						if (!(cmcLoc.getGeographicTarget()==null))
							newLoc.setGeoKeywordTarget( cmcLoc.getGeoKeywordTarget() );
						newLoc.setSitelinks( cmcLoc.getSitelinks() );
	
						System.out.println( "New Location:" + locationId.getExternalId());
		    			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"New Location:" + locationId.getExternalId() );
	
						//fill location array
						refreshedLocations[count] = newLoc;					
						count++;
		    		}
		    		else {
		    			swAbortUpdate=true;
		    			System.out.println( "New Location:" + locationId.getExternalId() +" não confirmada!");
		    			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"New Location:" + locationId.getExternalId() +" não confirmada!");
		    		}
		    	}
		    	else {
/* FM 02.08.2016 - teste !
		    		LocationId newLocationId = (LocationId) context.getBean("locationID");
		    		newLocationId.setRipId(newLoc.getLocationId().getRipId());
		    		newLoc.setLocationId(newLocationId);
*/
					newLoc.setUserStatus("active");
		    		/* faz update com base na Location da Matchcraft ! */
		    		String cmcReqURL = cmcLoc.getUrl();
		    		if (!(newLoc.getUrl().equals(cmcLoc.getUrl()))) {
						if(!cmcReqURL.contains("http://") && !cmcReqURL.contains("https://"))
						{
							cmcReqURL = "http://" + cmcReqURL;
						}
		    			newLoc.setUrl(cmcLoc.getUrl());
		    			swLocationMismatch = true;
		    		}
		    		String mcReqDisplayUrl = newLoc.getRequestedDisplayUrl();
		    		String cmcReqDisplayUrl = cmcLoc.getRequestedDisplayUrl();
		    		if (mcReqDisplayUrl==null) {
	    				System.out.println("MC-Location sem requestedDisplayURL\n");
		    			if (cmcReqDisplayUrl==null)
		    				System.out.println("Location sem reqDisplayURL\n");
		    			else {
			    			newLoc.setRequestedDisplayUrl(cmcReqDisplayUrl);
			    			swLocationMismatch = true;	    				
		    			}
		    		}
		    		else {
		    			if (!(cmcReqDisplayUrl==null) && !(newLoc.getRequestedDisplayUrl().equals(cmcLoc.getRequestedDisplayUrl()))) {
			    			newLoc.setRequestedDisplayUrl(cmcReqDisplayUrl);
			    			swLocationMismatch = true;
		    			}
	    			}
// compara geographicTargets
		    		GeographicTarget mcGeoTarget = new GeographicTarget();
		    		if (!(newLoc.getGeographicTarget()==null))
		    			mcGeoTarget = newLoc.getGeographicTarget();
					
					 if (mcGeoTarget==null) {
						 if (!(cmcLoc.getGeographicTarget()==null)) {
								 /* não tinha e passou a ter geoTarget */
							swLocationMismatch=true;
							newLoc.setGeographicTarget((GeographicTarget) cmcLoc.getGeographicTarget());
						 }
					 }
					 else {
						 if (cmcLoc.getGeographicTarget()==null)
							 /* tinha e deixou de ter geoTarget */
							 swLocationMismatch=true;
						 else {
/* um geoTarget pode ter N pointRadii !...
   Compara pointRadii
 */
					    		PointRadius[] cmcPtRadii = cmcLoc.getGeographicTarget().getPointRadii();
					    		GeographicTarget mcGeographicTarget = newLoc.getGeographicTarget();
					    		PointRadius[] newPtRadii = null;
								List<PointRadius> cmcPtRadiiList =  new ArrayList<PointRadius>(Arrays.asList(cmcPtRadii));
								if (cmcPtRadiiList.size()>0 && mcGeographicTarget==null) {
									mcGeoTarget.setPointRadii(cmcPtRadii);
								}
								else {
						    		PointRadius[] matchcraftPtRadii = mcGeographicTarget.getPointRadii();
									List<PointRadius> matchcraftPtRadiiList =  new ArrayList<PointRadius>(Arrays.asList(matchcraftPtRadii));

									newPtRadii = createPointRadii(matchcraftPtRadiiList, cmcPtRadiiList );
									mcGeoTarget.setPointRadii(newPtRadii);
								}
							 
								newLoc.setGeographicTarget( mcGeoTarget );
								if (swGeoTargetMismatch)
									newLoc.setGeoKeywordTarget( cmcLoc.getGeoKeywordTarget() );

/*							 
							 if ( !(mcGeoTarget.getPointRadii().getLatitude() == cmcLoc.getGeographicTarget().getPointRadii().getLatitude()) ||
									 !(mcGeoTarget.getPointRadii().getLongitude() == cmcLoc.getGeographicTarget().getPointRadii().getLongitude()) ||
									 !(mcGeoTarget.getPointRadii().getRadius() == cmcLoc.getGeographicTarget().getPointRadii().getRadius()) ) {
								swNeedsUpdate=true;
								newLoc.setGeographicTarget((GeographicTarget) cmcLoc.getGeographicTarget(1));
							}
*/
						 
						 }
					 }

// Compara categoryRefs
					 simpleReferenceInfo = 0;
					//categoryRef
		    		CategoryRef[] cmcCatRefs = cmcLoc.getCategoryRefs();
		    		CategoryRef[] matchcraftCatRefs = newLoc.getCategoryRefs();
					CategoryRef[] newCategoryRefs = null;
					if (cmcCatRefs==null) {
						System.out.println(subscrId+" CMC-campaign sem normal-adgroup!\n");
						AppendLog4jLogger.info(LogKeys.errorReportLogKey, subscrId+" CMC-campaign sem normal-adgroup!\n");
						cntCategoryRefs = 0;
					}
					else {
/* FM 02.08.2016, se é uma category já existente envia só o externalId e nome !! */
/* FM 13.09.2016						if (newLoc.getLocationId().getRipId()>0 && naoTemCustomAdcopies(cmcCatRefs)) { */
						if (newLoc.getLocationId().getRipId()>0 && naoTemCustomAdcopies(cmcCatRefs) 
								&& naoTemCustomKeywords(cmcCatRefs)) {
							 simpleReferenceInfo = 1;
							System.out.println(newLoc.getLocationId().getExternalId() +" CMC-campanha já enviada: não leva categoryRefs!\n");
							AppendLog4jLogger.info(LogKeys.errorReportLogKey, newLoc.getLocationId().getExternalId() +" CMC-campanha já enviada: não leva categoryRefs!!\n");				
						}
						else {	

							if (!(cmcCatRefs==null && matchcraftCatRefs==null)) {
								List<CategoryRef> cmcCatRefList = null;
								if (!(cmcCatRefs==null))
									cmcCatRefList =  new ArrayList<CategoryRef>(Arrays.asList(cmcCatRefs));
								List<CategoryRef> matchcraftCatRefList = null;
								if (!(matchcraftCatRefs==null))
									matchcraftCatRefList =  new ArrayList<CategoryRef>(Arrays.asList(matchcraftCatRefs));
		
								if (!(cmcCatRefs==null)) {
									newCategoryRefs = createCategoryRefs(subscrId, matchcraftCatRefList, cmcCatRefList, simpleReferenceInfo);
									if (!(newCategoryRefs==null))
										newLoc.setCategoryRefs(newCategoryRefs);
								}
							}
						}
					}
						// Compara customCategories
			    		CustomCategory[] cmcCustomCategs = cmcLoc.getCustomCategories();
			    	if (cntCategoryRefs==0 && cmcCustomCategs==null) {
						System.out.println(subscrId+" CMC-campaign tb não tem custom-adgroup!\n");
						AppendLog4jLogger.info(LogKeys.errorReportLogKey, subscrId+" CMC-campaign tb não tem custom-adgroup!\n");
							swAbortUpdate=true;
			    	}
			    	else {
		    			CustomCategory[] matchcraftCustomCategs = newLoc.getCustomCategories();
			    		CustomCategory[] newCustomCategs = null;
						if (!(cmcCustomCategs==null && matchcraftCustomCategs==null)) {
				    		List<CustomCategory> cmcCustomCatList = null;
							if (!(cmcCustomCategs==null))
					    		cmcCustomCatList =  new ArrayList<CustomCategory>(Arrays.asList(cmcCustomCategs));
							List<CustomCategory> matchcraftCustomCatList = null;
							if (!(matchcraftCustomCategs==null)) {
								matchcraftCustomCatList =  new ArrayList<CustomCategory>(Arrays.asList(matchcraftCustomCategs));
	
								if (!(cmcCustomCategs==null)) {
									newCustomCategs = createCustomCategories(matchcraftCustomCatList, cmcCustomCatList );	
									if (!(newCustomCategs==null))
										newLoc.setCustomCategories(newCustomCategs);
								}
								else {
									/* FM 03.11.2016 */
									System.out.println(subscrId+" CMC-campaign tinha CustomCategories e deixou de as ter !\n");	
								}
							}
							else {
/* FM 14.09.2016 */
								if (!(cmcCustomCategs==null))
									newLoc.setCustomCategories(cmcCustomCategs);									
							}
						}
			    	}
					if (!swAbortUpdate) {
						// Compara sitelinks
						if (cmcLoc.getSitelinks()==null && !(newLoc.getSitelinks()==null)) {
							swLocationMismatch=true;
							System.out.println(subscrId+" CMC-campaign tinha Sitelinks e deixou de os ter !\n");
						}
						else {
							Sitelink[] cmcSitelinks = cmcLoc.getSitelinks();
							Sitelink[] matchcraftSitelinks = newLoc.getSitelinks();
							Sitelink[] newSitelinks = null;
							if (!(cmcSitelinks==null && matchcraftSitelinks==null)) {
								List<Sitelink> cmcSitelinkList = null;
								if (!(cmcSitelinks==null))
									cmcSitelinkList = new ArrayList<Sitelink>(Arrays.asList(cmcSitelinks));
								List<Sitelink> matchcraftSitelinkList = null;
								if (!(matchcraftSitelinks==null)) {
									matchcraftSitelinkList =  new ArrayList<Sitelink>(Arrays.asList(matchcraftSitelinks));
		
									newSitelinks = createSitelinks(matchcraftSitelinkList, cmcSitelinkList );
									if (!(newSitelinks==null))
										newLoc.setSitelinks(newSitelinks);
								}
								else {
/* FM 14.09.2016 */
									if (!(cmcSitelinks==null))
										newLoc.setSitelinks(cmcSitelinks);									
								}
							}
						}
// FM 27.07.2016 - Adds Callout and AdSchedule support
						AdSchedule matchcraftAdSchedule = newLoc.getAdSchedule();
						if (!(matchcraftAdSchedule==null))
							newLoc.setAdSchedule(matchcraftAdSchedule);
						
						Callout[] matchcraftCallouts = newLoc.getCallouts();
						if (!(matchcraftCallouts==null))
							newLoc.setCallouts(matchcraftCallouts);
// FM 15.02.2017
						float mcBudgetPreference = newLoc.getBudgetPreference();
						if (mcBudgetPreference>0)
							newLoc.setBudgetPreference(mcBudgetPreference);
						
						//fill location array
						refreshedLocations[count] = newLoc;					
						count++;
					}
		    	}
				
		    }
			return refreshedLocations;
		} /* createNewLocations */
		
		/* As nossas Campanhas GA só têm um budget e
		 * só vai dar para alterar a data de Fim e o budget própriamente dito */
		public Budget[] createNewBudgets(Advertiser pMatchcraftAdvertiser, List<Budget> mcBudgetList, List<Budget> cmcBudgetList) throws MalformedURLException, URISyntaxException
		{
			Budget[] refreshedBudgets = new Budget[cmcBudgetList.size()];			
			
			Iterator<?> budgIterator = cmcBudgetList.listIterator();
			boolean budgFound=false;
			boolean swBudgContinua=true;
			int count=0;
			float budgMediaFraction = 1;
			int targetIni = 0;
			
		    while(budgIterator.hasNext()) 
		    {
		    	budgFound=false;
		    	Budget cmcBudget = (Budget) context.getBean("budget");
		    	cmcBudget = (Budget) budgIterator.next();
		    	Budget newBudget = new Budget();
		    	swBudgContinua=true;

				Iterator<?> mcBudgIterator = mcBudgetList.listIterator();
		    	while (swBudgContinua && mcBudgIterator.hasNext()) {
		    		Budget mcBudget = (Budget) context.getBean("budget");
			    	mcBudget = (Budget) mcBudgIterator.next();
		    		if ((mcBudget.getContractBeginDate() == cmcBudget.getContractBeginDate()) ||
		    				(mcBudget.getContractBeginDate() < cmcBudget.getContractBeginDate())) {
				    	budgFound=true;
				    	swBudgContinua=false;
				    	newBudget = (Budget) mcBudget;
		    		}
		    	}
		    	
		    	if (!budgFound) {
/* campanha criada sem Budget !? -> cria Budget com info do CMC */	
		    		swBudgetMismatch = true;

		    		newBudget.setContractBeginDate(cmcBudget.getContractBeginDate());
		    		newBudget.setContractEndDate(cmcBudget.getContractEndDate());

	    			newBudget.setTargetMediaSpend(cmcBudget.getTargetMediaSpend());

		    		newBudget.setMediaFraction(budgMediaFraction);
		    		newBudget.setTargetRetailSpend(cmcBudget.getTargetRetailSpend());
		    		newBudget.setTargetClicks(targetIni);
		    		newBudget.setTargetImpressions(targetIni);
		    		newBudget.setSuppressRolloverAndExtension(false);

					System.out.println( "New Budget:" + newBudget.getContractBeginDate() );

					//fill location array
					refreshedBudgets[count] = newBudget;					
					count++;
		    	
		    	}
		    	else {
		    		/* faz update com base no Budget da Matchcraft ! */
		    		if (!(newBudget.getContractEndDate() == cmcBudget.getContractEndDate())) {
						AppendLog4jLogger.info(LogKeys.errorReportLogKey, "CampUpdater budgetEndDate change:" + newBudget.getContractEndDate() + " -> " + cmcBudget.getContractEndDate());
		    			newBudget.setContractEndDate(cmcBudget.getContractEndDate());
		    			swBudgetMismatch = true;
		    		}
		    		if (!(newBudget.getTargetMediaSpend() == cmcBudget.getTargetMediaSpend())) {
						AppendLog4jLogger.info(LogKeys.errorReportLogKey, "CampUpdater budgetDiffs:" + newBudget.getTargetMediaSpend() + " -> " + cmcBudget.getTargetMediaSpend());
			    		if (pMatchcraftAdvertiser.getAdvertiserId().getExternalId().equals("68735395_3362337_182240")) {
			    			newBudget.setTargetMediaSpend(4340);
			    		}
			    		else {
			    			newBudget.setTargetMediaSpend(cmcBudget.getTargetMediaSpend());
			    		}
		    			swBudgetMismatch = true;
		    			swBudgetChanged = true;
		    		}
		    		if (newBudget.isSuppressRolloverAndExtension() && !(cmcBudget.isSuppressRolloverAndExtension())) {
		    			newBudget.setSuppressRolloverAndExtension(false);
		    			swBudgetMismatch = true;
		    		}
		    		if (!(newBudget.isSuppressRolloverAndExtension()) && cmcBudget.isSuppressRolloverAndExtension()) {
		    			newBudget.setSuppressRolloverAndExtension(true);
		    			swBudgetMismatch = true;
		    		}
					
					//fill budget array
					refreshedBudgets[count] = newBudget;					
					count++;

		    	}
				
		    }
			return refreshedBudgets;
		} /* createNewBudgets */

		public String campaignUpdater(String subscrId, String campStatus, 
				Campaign matchcraftCampaign,
				Campaign cmcCampaign,
				boolean prmCampanhaGAespecial, 
				boolean prmIncludeStdAdcopies) throws URISyntaxException
		{
			context = new ClassPathXmlApplicationContext("beans.xml");

			String opSuccess=null;
			boolean swNeedsUpdate=false;
			String response = null;
			swAbortUpdate=false;
			swBudgetMismatch=false;
			swBudgetChanged=false;
			cntCategoryRefs=0;
			UpdateCampaignController updateCampaignController = (UpdateCampaignController) context.getBean("updatecampaigncontroller");
			JacksonConverter jacksonConverter = (JacksonConverter) context.getBean("jacksonConverter");
			Response responseobj = (Response) context.getBean("response");
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			objectMapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
			//Esta configuraÃ§Ã£o evita que os dados a null sejam inseridos no JSON final
			objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
			objectMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
			objectMapper.setSerializationInclusion(Inclusion.NON_DEFAULT);
			
			try {
			
				Advertiser matchcraftAdvertiser = matchcraftCampaign.getAdvertiser();
				Advertiser cmcAdvertiser = cmcCampaign.getAdvertiser();
				if (busPhoneMismatch(matchcraftAdvertiser, cmcAdvertiser)) {
					System.out.println( "CampUpdater busPhnDiffs:" + matchcraftAdvertiser.getBusinessPhone() + " -> " + cmcAdvertiser.getBusinessPhone());
					AppendLog4jLogger.info(LogKeys.errorReportLogKey, "CampUpdater busPhnDiffs:" + matchcraftAdvertiser.getBusinessPhone() + " -> " + cmcAdvertiser.getBusinessPhone());
					String auxPhone = cmcAdvertiser.getBusinessPhone();
					if (auxPhone == null)
						matchcraftAdvertiser.setBusinessPhone(null);
					else
						matchcraftAdvertiser.setBusinessPhone(auxPhone);
					swNeedsUpdate=true;
				}
				BusinessAddress mcBusAddr = matchcraftAdvertiser.getBusinessAddress();
				
				 if (mcBusAddr==null) {
					 if (!(cmcAdvertiser.getBusinessAddress()==null)) {
							 /* não tinha e passou a ter busPhone */
						swNeedsUpdate=true;
						System.out.println( "CampUpdater - " +cmcAdvertiser.getBusinessAddress().getPostalCode()+ " - passou a ter busAddress");
						matchcraftAdvertiser.setBusinessAddress((BusinessAddress) cmcAdvertiser.getBusinessAddress());
					 }
				 }
				 else {
					 if (cmcAdvertiser.getBusinessAddress()==null) {
						 /* tinha e deixou de ter busAddress */
							swNeedsUpdate=true;
							System.out.println( "CampUpdater - " +cmcAdvertiser.getAdvertiserId()+ " - deixou de ter busAddress");
							matchcraftAdvertiser.setBusinessAddress(null);
					 }
					 else {
						if ( !(matchcraftAdvertiser.getBusinessAddress().equals((BusinessAddress) cmcAdvertiser.getBusinessAddress())) ) {
							swNeedsUpdate=true;
							System.out.println( "CampUpdater - " +cmcAdvertiser.getAdvertiserId()+ " -  alterou busAddress:" + matchcraftAdvertiser.getBusinessAddress().getPostalCode() +" -> "+ cmcAdvertiser.getBusinessAddress().getPostalCode());
							matchcraftAdvertiser.setBusinessAddress((BusinessAddress) cmcAdvertiser.getBusinessAddress());
						}
					 }
				 }

				// For each location
				Location[] cmcLocs = cmcAdvertiser.getLocations();
				Location[] matchcraftLocs = matchcraftAdvertiser.getLocations();
				List<Location> cmcLocsList = new ArrayList<Location>(Arrays.asList(cmcLocs));
				List<Location> matchcraftLocsList =  new ArrayList<Location>(Arrays.asList(matchcraftLocs));
				Location[] newMcLocs = createNewLocations(subscrId, campStatus, matchcraftLocsList,cmcLocsList, prmCampanhaGAespecial, prmIncludeStdAdcopies);

				if (matchcraftAdvertiser.getAdvertiserId().getExternalId().equals("32675706_3282635_179899")) {
					swNeedsUpdate=true;
				}
				else  {
					if (swLocationMismatch) {					
						System.out.println( "CampUpdater - " +matchcraftAdvertiser.getAdvertiserId().getExternalId() + " -  alterou location(s)" );
						matchcraftAdvertiser.setLocations(newMcLocs);
						swNeedsUpdate=true;
					}
				}

				// For each budget
				Budget[] cmcBudg = cmcAdvertiser.getBudgets();
				Budget[] matchcraftBudg = matchcraftAdvertiser.getBudgets();
				List<Budget> cmcBudgetList = new ArrayList<Budget>(Arrays.asList(cmcBudg));
				List<Budget> matchcraftBudgetList =  new ArrayList<Budget>(Arrays.asList(matchcraftBudg));
				Budget[] newMcBudgets = createNewBudgets(matchcraftAdvertiser,matchcraftBudgetList,cmcBudgetList);

				if (swBudgetMismatch) {					
/* FM 06.09.2016 */
					List<Budget> newBudgetList =  new ArrayList<Budget>(Arrays.asList(newMcBudgets));
					Iterator<?> budgIterator = newBudgetList.listIterator();
					Budget newMcBudget = (Budget) budgIterator.next();
					if(newMcBudget.getTargetMediaSpend()==0) {
		    			swNeedsUpdate=false;
		    			swAbortUpdate=true;
		    			System.out.println( "CampUpdater - " +matchcraftAdvertiser.getAdvertiserId().getExternalId()+ " -  alteração para Budget nulo !" );
		    			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"CampUpdater - " +matchcraftAdvertiser.getAdvertiserId().getExternalId()+ " -  alteração para Budget nulo !");
					}
					else {
						if (campStatus.equals("3 - Ready to send to BMS")) {
			    			System.out.println( "CampUpdater - " +matchcraftAdvertiser.getAdvertiserId().getExternalId()+ " -  alterou o Budget" );
			    			matchcraftAdvertiser.setBudgets(newMcBudgets);
			    			swNeedsUpdate=true;
			    		}
			    		else {
			    			if (swBudgetChanged) {
				    			swNeedsUpdate=false;
				    			swAbortUpdate=true;
				    			System.out.println( "CampUpdater - " +matchcraftAdvertiser.getAdvertiserId().getExternalId()+ " -  alteração de Budget não validada !" );
				    			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"CampUpdater - " +matchcraftAdvertiser.getAdvertiserId().getExternalId()+ " -  alteração de Budget não validada !");
			    			}
			    			else {
				    			System.out.println( "CampUpdater - " +matchcraftAdvertiser.getAdvertiserId().getExternalId()+ " -  alterou o Budget" );
				    			matchcraftAdvertiser.setBudgets(newMcBudgets);
				    			swNeedsUpdate=true;	    				
			    			}
			    		}
					}
				}
				
				if (swAbortUpdate) {
				    opSuccess = "0:Campanha sem budget";					
				}
				else {
					if (swNeedsUpdate) { 
	/* FM 28.07.2016 */					
						matchcraftAdvertiser.setUserStatus(new String("active"));
						/* para ja: mostra como vai ficar o MC_Advertiser modificado ! */

					   // display to console
					   Object json = objectMapper.readValue(
					     objectMapper.writeValueAsString(matchcraftAdvertiser), Object.class);
					   
					   String jsonString = objectMapper.writerWithDefaultPrettyPrinter()
					     .writeValueAsString(json);
					   System.out.println(jsonString);
// FM 31.07.2016 COMENTAR !!
					   AppendLog4jLogger.info(LogKeys.errorReportLogKey,jsonString);
							   
						/* make update request */
	       				response = updateCampaignController.updateMatchcraftCampaign(matchcraftAdvertiser);
            			jacksonConverter.refactoredResponse(response);
            			responseobj = jacksonConverter.responseToJSON(response);
        				Operations[] auxOps = responseobj.getOperations();

            			/* if camp exists faz update else cria campanha */
//            			Diagnostic[] auxDiagsRU = null;
            			Diagnostic[] auxDiagsRU = auxOps[0].getDiagnostics();
            			if (!(auxDiagsRU==null)) {
            				System.out.println("**!! [CampUpdater] " + cmcCampaign.getAdvertiser().getAdvertiserId().getExternalId() + " deu erro no Update - code:" + auxDiagsRU[0].getCode() + " msg:" + auxDiagsRU[0].getMessage());
            				AppendLog4jLogger.info(LogKeys.errorReportLogKey, "**!! [CampUpdater] " + cmcCampaign.getAdvertiser().getAdvertiserId().getExternalId() + " deu erro no Update - code:" + auxDiagsRU[0].getCode() + " msg:" + auxDiagsRU[0].getMessage());
        				    opSuccess = "0:" + auxDiagsRU[0].getCode() ;					
            			}
            			else {	       				
		       				AppendLog4jLogger.info(LogKeys.errorReportLogKey, "[CampUpdater] campanha - " +cmcCampaign.getAdvertiser().getAdvertiserId().getExternalId()+ " - alterada ");	
						    opSuccess = "1";
            			}
					}
					else {
					    opSuccess = "2";
					}
				}
			} catch (JsonGenerationException e) {
			   e.printStackTrace();
			    opSuccess = "0";
			    return opSuccess;
			  } catch (JsonMappingException e) {
				    opSuccess = "0";
				    e.printStackTrace();
				    return opSuccess;
			  } catch (IOException e) {
				    opSuccess = "0";
				    e.printStackTrace();
				    return opSuccess;
			  } catch (Exception e) {
				    opSuccess = "0";
				    System.out.println( "CampUpdater - " +cmcCampaign.getAdvertiser().getAdvertiserId().getExternalId()+ " - deu ERRO !!" );
				    AppendLog4jLogger.info(LogKeys.errorReportLogKey,  "CampUpdater - " +cmcCampaign.getAdvertiser().getAdvertiserId().getExternalId()+ " - deu ERRO !!" );
				    e.printStackTrace();
				    return opSuccess;
			  }
			return opSuccess;
		}
		
		
}
