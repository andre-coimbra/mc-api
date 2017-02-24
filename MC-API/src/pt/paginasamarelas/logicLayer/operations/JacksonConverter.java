package pt.paginasamarelas.logicLayer.operations;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import pt.paginasamarelas.dataLayer.entities.CustomAdCopy;
import pt.paginasamarelas.dataLayer.entities.Location;
import pt.paginasamarelas.dataLayer.entities.PayloadCategory;
import pt.paginasamarelas.dataLayer.entities.Request;
import pt.paginasamarelas.dataLayer.entities.Response;
import pt.paginasamarelas.dataLayer.entities.Response2;
import pt.paginasamarelas.dataLayer.entities.Response3;

public class JacksonConverter {
	public Response responseToJSON(String responseJSON){
		   
			Response response = null;
	      ObjectMapper mapper = new ObjectMapper();
	      //String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";
	      
	      //map json to student
			
	      try{
	         response = mapper.readValue(responseJSON, Response.class);
	         
	         //System.out.println(request);
	         
	         mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
	         //response = mapper.writeValueAsString(request);
	         
	         //System.out.println(response);
	      }
	      catch (JsonParseException e) { e.printStackTrace();}
	      catch (JsonMappingException e) { e.printStackTrace(); }
	      catch (IOException e) { e.printStackTrace(); }
	      
	      return response;
	      
	   }
	
	public Response2 response2ToJSON(String responseJSON){
		   
		Response2 response = null;
      ObjectMapper mapper = new ObjectMapper();
      //String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";
      
      //map json to student
		
      try{
         response = mapper.readValue(responseJSON, Response2.class);
         
         //System.out.println(request);
         
         mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
         //response = mapper.writeValueAsString(request);
         
         //System.out.println(response);
      }
      catch (JsonParseException e) { e.printStackTrace();}
      catch (JsonMappingException e) { e.printStackTrace(); }
      catch (IOException e) { e.printStackTrace(); }
      
      return response;
      
   }

	public PayloadCategory[] createAdgroups(List<PayloadCategory> mcCategoryList) throws MalformedURLException, URISyntaxException
	{
		PayloadCategory[] newPaylCategs = new PayloadCategory[mcCategoryList.size()];
		Iterator<?> categIterator = mcCategoryList.listIterator();
//		Iterator<PayloadCategory> categIterator = mcCategoryList.listIterator();
		
		int count=0;
		
	    while(categIterator.hasNext()) 
	    {
	    	PayloadCategory mcCateg = new PayloadCategory();
	    	/* Object nextCateg = categIterator.next();
	    	   mcCateg = (PayloadCategory) nextCateg; */
	    	mcCateg = (PayloadCategory) categIterator.next();
			PayloadCategory newCategory = new PayloadCategory();
			newCategory.setName(mcCateg.getName());
			newCategory.setExternalId(mcCateg.getExternalId());
			if (!(mcCateg.getParentExternalId()==null))
				newCategory.setParentExternalId(mcCateg.getParentExternalId());
			newCategory.setAdcopies(mcCateg.getAdcopies());
			newCategory.setKeyphrases(mcCateg.getKeyphrases());
			//fill location array
			newPaylCategs[count] = newCategory;					
			count++;	
		}
	
		return newPaylCategs;
	}

	public Response3 response3ToJSON(String responseJSON){
		   
		Response3 response = new Response3();
      ObjectMapper mapper = new ObjectMapper();
      
      //map json to Response3 (=PayloadCategory[])
      try {
          TypeFactory typeFactory = mapper.getTypeFactory();
//          CollectionType collectionType = typeFactory.constructCollectionType(
/*          CollectionType collectionType = typeFactory.constructSimpleType(
        		  List.class, Response3.class);
          response = mapper.readValue(responseJSON, collectionType);
*/
          JavaType collectionType = typeFactory.constructType(
        		  List.class, Response3.class);
          List matchcraftCategoryList = mapper.readValue(responseJSON, collectionType);
          List<PayloadCategory> newAdgroupsList = mapper.convertValue(matchcraftCategoryList, new TypeReference<List<PayloadCategory>>() { });

          PayloadCategory[] newAdgroups = null;
			newAdgroups = createAdgroups(newAdgroupsList);

			response.setPayloadCategories(newAdgroups);
			return response;
      }
      catch (JsonParseException e) { e.printStackTrace();}
      catch (JsonMappingException e) { e.printStackTrace(); }
      catch (IOException e) {
          e.printStackTrace();
      }
      catch (URISyntaxException e) {
		e.printStackTrace();
      }
      return null;		
            
   }

	public Boolean refactoredResponse(String response) throws JsonParseException, JsonMappingException, IOException
	{
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readValue(response, JsonNode.class);
		JsonNode operations = node.get("diagnostics");
		JsonNode resultNode = node.get("operations");
		String result = resultNode.asText();
		
		
		return null;
	}
	
	public Boolean simpleRefactoredResponse(String response) throws JsonParseException, JsonMappingException, IOException
	{
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode resultNode = mapper.readValue(response, JsonNode.class);
		String result = resultNode.asText();
		
		
		return null;
	}
	
	public JsonNode[] responseToJsonAdgroupNodes(String responseJSON){
		   
      ObjectMapper mapper = new ObjectMapper();
      
      //map json to Response3 (=PayloadCategory[])
      try {
          TypeFactory typeFactory = mapper.getTypeFactory();
//          CollectionType collectionType = typeFactory.constructCollectionType(
/*          CollectionType collectionType = typeFactory.constructSimpleType(
        		  List.class, Response3.class);
          response = mapper.readValue(responseJSON, collectionType);
*/
          JavaType collectionType = typeFactory.constructType(
        		  List.class, Response3.class);
          List matchcraftCategoryList = mapper.readValue(responseJSON, collectionType);
          List<JsonNode> newAdgroupsList = mapper.convertValue(matchcraftCategoryList, new TypeReference<List<JsonNode>>() { });

          JsonNode[] newAdgroups = (JsonNode[]) newAdgroupsList.toArray();

          return newAdgroups;
      }
      catch (JsonParseException e) { e.printStackTrace();}
      catch (JsonMappingException e) { e.printStackTrace(); }
      catch (IOException e) {
          e.printStackTrace();
      }
      return null;		
            
   }
}
