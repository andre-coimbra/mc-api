package pt.paginasamarelas.Entities;

import java.io.IOException;


public class Authentication {

	private String clientProgramNickname;
	private String password;
	
	public Authentication() throws IOException
	{
	
	}
	
	public String getClientProgramNickname() {
		return clientProgramNickname;
	}
	public void setClientProgramNickname(String clientProgramNickname) {
		this.clientProgramNickname = clientProgramNickname;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
