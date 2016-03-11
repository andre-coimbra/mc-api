package pt.paginasamarelas.dataLayer.Entities;

public class Language {
	private String language;

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setLanguage(LanguageEnum language) {
		this.language = language.toString();
	}

}
