package pt.paginasamarelas.Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;
import org.junit.Test;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import pt.paginasamarelas.dataLayer.entities.*;
import pt.paginasamarelas.dataLayer.entities.Operations.OperatorEnum;
import pt.paginasamarelas.dataLayer.entities.TargetDemographics.Gender;
import pt.paginasamarelas.logicLayer.RESTRequester.RESTRequestService;
import pt.paginasamarelas.logicLayer.operations.AdvertiserCreator;
import pt.paginasamarelas.logicLayer.operations.PropertiesReader;
import pt.paginasamarelas.logicLayer.operations.RequestBuilder;



public class JacksonTest {

	public static void main(String[] args) throws IOException, URISyntaxException 
	{
		RESTRequestService rest = new RESTRequestService();
		
		AdvertiserCreator c = new AdvertiserCreator();
		RequestBuilder requestb = new RequestBuilder();
		
		//Advertiser advertiser =  c.createAdvertiser("13909005_3300955_158191");
		//Request request = requestb.createRequest(advertiser);
		
		//String jsonRequest = requestToJson(request);
		
		//rest.makeRequest(request);
		
		//advertiserToJson(advertiser);
		/*JacksonTest jacksonTest = new JacksonTest();
		jacksonTest.objectToJ8son();
		
		*/
		
		/*Request request = new Request();
		request = fillRequestObj();
		System.out.println("Request obj created");
				
				
		RESTRequestService rest = new RESTRequestService();
		System.out.println("Request service created");
		rest.makeRequest(request);*/
		
		//Authentication auth = new Authentication();
		//System.out.println("user: "+auth.getClientProgramNickname());
		//System.out.println("password: "+auth.getPassword());
		

	}
	
	//@Test
	public void convertCampaignToJson() throws IOException
	{
		Campaign campaign = new Campaign();
		campaignToJson(campaign);
		System.out.println("Campaign obj created");
		
		System.out.println("---------------------------------------------------");
		System.out.println("---------------------------------------------------");
	}
	
	//@Test
	public void convertRequestToJson() throws IOException
	{
		Request request = new Request();
		requestToJson(request);
		System.out.println("Request obj created");
		
		System.out.println("---------------------------------------------------");
		System.out.println("---------------------------------------------------");
	}
	
	
	@Test
	public void requestMatchcraft() throws IOException
	{
		Request request = new Request();
		request = fillRequestObj();
		System.out.println("Request obj created");
				
				
		RESTRequestService rest = new RESTRequestService();
		System.out.println("Request service created");
		rest.makeRequest(request);
		
		System.out.println("---------------------------------------------------");
		System.out.println("---------------------------------------------------");
	}
	public static void advertiserToJson(Advertiser advertiser) throws IOException 
	{

		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		//Esta configura��o evita que os dados a null sejam inseridos no JSON final
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		objectMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
		objectMapper.setSerializationInclusion(Inclusion.NON_DEFAULT);
		
		try {

			   //objectMapper.writeValue(new File("c:\\employee.json"), campaign);

			   // display to console
			   Object json = objectMapper.readValue(
			     objectMapper.writeValueAsString(advertiser), Object.class);
			   
			   String jsonString = objectMapper.writerWithDefaultPrettyPrinter()
			     .writeValueAsString(json);
			   


			   System.out.println(jsonString);
			   
			   Advertiser advertiser2 = objectMapper.readValue(jsonString, Advertiser.class);
			   //Campaign campaign2 = objectMapper.readValue(jsonString, Campaign.class);
			   
			   if (advertiser == advertiser2){System.out.print("true");}

			  } catch (JsonGenerationException e) {
			   e.printStackTrace();
			  } catch (JsonMappingException e) {
			   e.printStackTrace();
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
	
	}
	
	public void campaignToJson(Campaign campaign) throws IOException 
	{

		campaign = fillCampaignObj(campaign);
		 //Request request = fillRequestObj();
		//tests = fillTestObj();
		
		 
		

		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		//Esta configuração evita que os dados a null sejam inseridos no JSON final
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		objectMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
		objectMapper.setSerializationInclusion(Inclusion.NON_DEFAULT);
		
		try {

			   //objectMapper.writeValue(new File("c:\\employee.json"), campaign);

			   // display to console
			   Object json = objectMapper.readValue(
			     objectMapper.writeValueAsString(campaign), Object.class);
			   
			   String jsonString = objectMapper.writerWithDefaultPrettyPrinter()
			     .writeValueAsString(json);
			   //jsonString = jsonString.replaceAll("]", "}");
			   //jsonString = jsonString.replaceAll("\\[", "{");


			   System.out.println(jsonString);
			   
			   Campaign campaign2 = objectMapper.readValue(jsonString, Campaign.class);
			   //Campaign campaign2 = objectMapper.readValue(jsonString, Campaign.class);
			   
			   if (campaign == campaign2){System.out.print("true");}

			  } catch (JsonGenerationException e) {
			   e.printStackTrace();
			  } catch (JsonMappingException e) {
			   e.printStackTrace();
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
	
	}
	
	public static String requestToJson(Request request) throws IOException 
	{		
		 //request = fillRequestObj();
		String jsonString="";
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		//Esta configuração evita que os dados a null sejam inseridos no JSON final
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		objectMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
		objectMapper.setSerializationInclusion(Inclusion.NON_DEFAULT);
		
		try {

			   //objectMapper.writeValue(new File("c:\\employee.json"), campaign);

			   // display to console
			   Object json = objectMapper.readValue(
			     objectMapper.writeValueAsString(request), Object.class);
			   
			   jsonString = objectMapper.writerWithDefaultPrettyPrinter()
			     .writeValueAsString(json);


			   System.out.println(jsonString);
			   
			   Request request2 = objectMapper.readValue(jsonString, Request.class);
			   //Campaign campaign2 = objectMapper.readValue(jsonString, Campaign.class);
			   
			   if (request == request2){System.out.print("true");}

			  } catch (JsonGenerationException e) {
			   e.printStackTrace();
			  } catch (JsonMappingException e) {
			   e.printStackTrace();
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		
		return jsonString;
	
	}
	
	public static Request fillRequestObj() throws IOException
	{
		Authentication authentication = new Authentication();
		PropertiesReader props = new PropertiesReader();
	
		
		authentication.setClientProgramNickname(props.getUser());
		authentication.setPassword(props.getPassword());
		
		Operations operations = new Operations();
		operations.setOperator(OperatorEnum.readAdvertiser);
		
		Operand operands = new Operand();
		AdvertiserID advertiserid = new AdvertiserID();
		advertiserid.setExternalId("20157135_3214374_128578");
		
		operands.setAdvertiserId(advertiserid);
		operations.setOperands(operands);
		
		
		Request request = new Request();
		request.setAuthentication(authentication);
		//request.setOperations(operations);
		
		return request;
	}
	
	public Campaign fillCampaignObj(Campaign campaign)
	{
		//campaign.getAdvertiser();
		
		Advertiser ad = new Advertiser();
		
		//Advertiser ID obj
		AdvertiserID adId = new AdvertiserID();
		adId.setExternalId("ourStableId0001");
		//adId.setRipId("");
		
		ad.setAdvertiserId(adId);
		
		ad.setUserStatus("active");
		
		ad.setName("'Frank\'s Barber Shop");
		
		ad.setGoogleProvisioningId("Frank\'s cohort");
		
		//Contact obj
		Contact contact = new Contact();
		contact.setEmail("frank@franksbarbershop.com");
		contact.setFirstName("Frank");
		contact.setLastName("Furter");
		ad.setContact(contact);
		
		//Business Address obj
		BusinessAddress ba = new BusinessAddress();
		ba.setLine1("123 Cherry Street");
		ba.setLine2("Box 456");
		ba.setCity("Boston");
		ba.setRegion("Massachusetts");
		ba.setPostalCode("02113");
		ba.setCountryCode("US");
		ad.setBusinessAddress(ba);
		
		//Target Demographics obj
		TargetDemographics td = new TargetDemographics();
		td.setMinAge(22);
		td.setMaxAge(66);
		td.setGender(Gender.male);
		ad.setTargetDemographics(td);
		ad.setUseReverseProxy(true);
		
		//CallTracking obj
		CallTracking callTracking = new CallTracking();
		//CallTrackingNumber obj
		CallTrackingNumber callTrackingNumbers = new CallTrackingNumber();
		callTrackingNumbers.setDestinationNumber("3104560000");
		callTrackingNumbers.setNickname("test number");
		callTrackingNumbers.setPreferTollFreeNumber(true);
		CallTrackingNumber[] callTrackingNumbersA ={callTrackingNumbers};
		callTracking.setCallTrackingNumbers(callTrackingNumbersA);
		//additionalPhoneNumberReplacements obj
		AdditionalPhoneNumberReplacement apnr = new AdditionalPhoneNumberReplacement();
		apnr.setReplaceThis("12345");
		apnr.setWithThis("54321");
		AdditionalPhoneNumberReplacement[] apnrA={apnr};
		callTracking.setAdditionalPhoneNumberReplacements(apnrA);
		ad.setCallTracking(callTracking);
		
		String[] searchengines = {"google","bing"};
		String[] devices = {"desktop","tablet","mobile"};
		
		ad.setSearchEngines(searchengines);
		
		
		ad.setDevices(devices);
		
		
		ad.setNotes("Frank likes to emphasize buzz cuts");
		
		//ManagementHierarchyRef obj
		ManagementHierarchyRef mhref = new ManagementHierarchyRef();
		mhref.setExternalId("55");
		mhref.setName("Bob James");
		ad.setManagementHierarchyRef(mhref);
		
		CustomAttributes customAtt = new CustomAttributes();
		customAtt.setName("product");
		customAtt.setValue("CMZ-88");
		
		
		CustomAttributes customAtt2 = new CustomAttributes();
		customAtt.setName("division");
		customAtt.setValue("sales east");
		
		CustomAttributes[] customAttA = {customAtt,customAtt2};
		
		ad.setCustomAttributes(customAttA);
		
		//Budget obj
		Budget budget = new Budget();
		budget.setContractBeginDate(20130501);
		budget.setContractEndDate(20140430);
		budget.setTargetMediaSpend(2000);
		budget.setMediaFraction((float) 0.65);
		Budget budget2 = new Budget();
		
		budget2.setContractBeginDate(20140501);
		budget2.setContractEndDate(20150430);
		budget2.setTargetMediaSpend(3000);
		budget2.setMediaFraction((float) 0.75);
		
		Budget[] budgetA = {budget,budget2};
		//ad.setBudgets(budgetA);
		
		//Location obj
		Location location = new Location ();
		//LocationId obj
		LocationId locationId = new LocationId();
		locationId.setRipId(50019);
		location.setLocationId(locationId);
		location.setUserStatus("active");
		//location.setLanguages("en");
		location.setName("Frank\'s Buzz Promotion");
		location.setUrl("http://franksbarbershop.com");
		location.setRequestedDisplayUrl("franksbarbershop.com");
		//CategoryRef obj
		CategoryRef categoryRefs = new CategoryRef();
		categoryRefs.setExternalId("123");
		categoryRefs.setName("barber");
		//location.setCategoryRef(categoryRefs);
		categoryRefs = new CategoryRef();
		categoryRefs.setExternalId("124");
		categoryRefs.setName("hair stylist");
		//location.setCategoryRef(categoryRefs);
		//GeographicTarget obj
		GeographicTarget geoTrg = new GeographicTarget();
			//PointRadius obj
			PointRadius pointRadii = new PointRadius();
			pointRadii.setLatitude((float) 42.358);
			pointRadii.setLongitude((float) -71.059);
			pointRadii.setRadius(10);
			pointRadii.setDescription("Boston, MA");
			
			PointRadius[] pointRadiiA = {pointRadii};
			
			geoTrg.setPointRadii(pointRadiiA);
			//PostalCodeRadius obj
			PostalCodeRadius postalCodeRadii = new PostalCodeRadius();
			postalCodeRadii.setPostalCode("02113");
			postalCodeRadii.setRadius(10);
			postalCodeRadii.setCountryCode("US");
			postalCodeRadii.setDescription("Boston, north end");
			
			PostalCodeRadius[] postalCodeRadiiA={postalCodeRadii};
			
			geoTrg.setPostalCodeRadii(postalCodeRadiiA);
		//GeoKeywordTarget obj
		GeoKeywordTarget geoKeywordTarget = new GeoKeywordTarget();
		//BackgroundTarget obj
		BackgroundTarget backgroundTarget = new BackgroundTarget();
		backgroundTarget.setMirrorGeographicTarget(true);
		
		String[] geoModifiersA = {"boston"};
		
		geoKeywordTarget.setGeoModifiers(geoModifiersA);
		geoKeywordTarget.setBackgroundTarget(backgroundTarget);
		
		location.setGeoKeywordTarget(geoKeywordTarget);
		
		
		//ad.setLocation(location);
		
		Location location2 = new Location ();
		//LocationId obj
		locationId = new LocationId();
		locationId.setRipId(50020);
		location2.setLocationId(locationId);
		location2.setUserStatus("active");
		//location2.setLanguages("en");
		location2.setName("Frank\'s General Campaign");
		location2.setUrl("http://franksbarbershop.com");
		location2.setRequestedDisplayUrl("franksbarbershop.com");
		//CategoryRef obj
		CategoryRef categoryRefs2 = new CategoryRef();
		categoryRefs2.setExternalId("123");
		categoryRefs2.setName("barber");
		//location2.setCategoryRef(categoryRefs2);
		CategoryRef categoryRefs3 = new CategoryRef();
		categoryRefs3.setExternalId("124");
		categoryRefs3.setName("hair stylist");
		//location2.setCategoryRef(categoryRefs3);
		//GeographicTarget obj
		geoTrg = new GeographicTarget();
		//PointRadius obj
		pointRadii = new PointRadius();
		pointRadii.setLatitude((float) 42.358);
		pointRadii.setLongitude((float) -71.059);
		pointRadii.setRadius(10);
		//pointRadii.setDescription("Boston, MA");
		pointRadiiA[0] = pointRadii;
		
		geoTrg.setPointRadii(pointRadiiA);
		//PostalCodeRadius obj
		postalCodeRadii = new PostalCodeRadius();
		postalCodeRadii.setPostalCode("02113");
		postalCodeRadii.setRadius(10);
		postalCodeRadii.setCountryCode("US");
		//postalCodeRadii.setDescription("Boston, north end");
		postalCodeRadiiA[0] = postalCodeRadii;
		
		geoTrg.setPostalCodeRadii(postalCodeRadiiA);
		location2.setGeographicTarget(geoTrg);
		
		geoKeywordTarget = new GeoKeywordTarget();
		backgroundTarget = new BackgroundTarget();
		backgroundTarget.setMirrorGeographicTarget(true);
		String[] geoModifiers = {"boston"};
		geoKeywordTarget.setGeoModifiers(geoModifiers);
		geoKeywordTarget.setBackgroundTarget(backgroundTarget);
		location2.setGeoKeywordTarget(geoKeywordTarget);
		Location[] locationA = {location,location2};
		ad.setLocations(locationA);
		

		
		campaign.setAdvertiser(ad);
		return campaign;
		
	}
	
	
	

}
