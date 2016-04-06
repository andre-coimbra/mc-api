package pt.paginasamarelas.logicLayer.operations;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import pt.paginasamarelas.dataLayer.entities.Request;
import pt.paginasamarelas.dataLayer.entities.Response;

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
	
	public Boolean refactoredResponse(String response) throws JsonParseException, JsonMappingException, IOException
	{
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readValue(response, JsonNode.class);
		JsonNode operations = node.get("diagnostics");
		JsonNode resultNode = node.get("operations");
		String result = resultNode.asText();
		
		
		return null;
	}
	
	
}
