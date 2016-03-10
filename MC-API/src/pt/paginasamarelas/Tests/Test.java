package pt.paginasamarelas.Tests;

import java.util.ArrayList;
import java.util.List;

public class Test {
	private int id;
	private List<String> names = new ArrayList<String>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	
	public void setNames(String name) {
		this.names.add(name);
	}
	

}
