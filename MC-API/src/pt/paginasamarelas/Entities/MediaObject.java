package pt.paginasamarelas.Entities;

public class MediaObject {

	private int id;
	private String name;
	private boolean isPathUrl;
	private String path;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPathUrl() {
		return isPathUrl;
	}
	public void setPathUrl(boolean isPathUrl) {
		this.isPathUrl = isPathUrl;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
