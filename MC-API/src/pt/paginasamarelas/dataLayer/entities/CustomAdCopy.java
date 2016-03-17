package pt.paginasamarelas.dataLayer.entities;

public class CustomAdCopy {
	private String title;
	private String line1;
	private String line2;
	private boolean suppressKeywordInsertion;
	private boolean mobilePreferred;
	private String[] searchEngines;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public boolean isSuppressKeywordInsertion() {
		return suppressKeywordInsertion;
	}
	public void setSuppressKeywordInsertion(boolean suppressKeywordInsertion) {
		this.suppressKeywordInsertion = suppressKeywordInsertion;
	}
	public boolean isMobilePreferred() {
		return mobilePreferred;
	}
	public void setMobilePreferred(boolean mobilePreferred) {
		this.mobilePreferred = mobilePreferred;
	}
	public String[] getSearchEngines() {
		return searchEngines;
	}
	public void setSearchEngines(String[] searchEngines) {
		this.searchEngines = searchEngines;
	}
	
	
	
}
