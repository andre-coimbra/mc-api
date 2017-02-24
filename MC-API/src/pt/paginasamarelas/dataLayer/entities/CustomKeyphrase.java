package pt.paginasamarelas.dataLayer.entities;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)

public class CustomKeyphrase {
	
	private String phrase;
	private String[] searchEngines;
		
	
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public String[] getSearchEngines() {
		return searchEngines;
	}
	public void setSearchEngines(String[] searchEngines) {
		this.searchEngines = searchEngines;
	}

	
}
