package pt.paginasamarelas.hibernate.entities;

import java.math.BigDecimal;

public class ExtCustomKeyword {
	
	private BigDecimal nrid;
	private int id;
	private String keyword;
	
	
	public BigDecimal getNrid() {
		return nrid;
	}
	public void setNrid(BigDecimal nrid) {
		this.nrid = nrid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
