package pt.paginasamarelas.logicLayer.operations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	private String user;
	private String password;
	private String url;
	public PropertiesReader() throws IOException
	{
		InputStream input;
		String workingDir = System.getProperty("user.dir");
		Properties prop = new Properties();
//		System.out.println("[PropertiesReader] workingDir:" + workingDir + "\n");
		if (workingDir.equals("c:\\TEMP") || workingDir.substring(0, 8).equals("C:\\DOCUM")) {
			input = new FileInputStream("\\\\ciclope\\dossr_desenv\\MC_API\\Exe\\resources\\config.properties");
		} else {
			input = new FileInputStream(".\\resources\\config.properties");
		}

		prop.load(input);
		
		setUser(prop.getProperty(Property.user.toString()));
		setPassword(prop.getProperty(Property.password.toString()));
		setUrl(prop.getProperty(Property.url.toString()));
	}
	
	public enum Property
	{
		user,password,url
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
