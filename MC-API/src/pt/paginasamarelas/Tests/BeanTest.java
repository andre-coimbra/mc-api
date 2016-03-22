package pt.paginasamarelas.Tests;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

import pt.paginasamarelas.dataLayer.entities.Advertiser;
import pt.paginasamarelas.dataLayer.hibernate.QueryCampaignDB;
import pt.paginasamarelas.logicLayer.operations.AdvertiserCreator;

public class BeanTest {

	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		 
		QueryCampaignDB q = (QueryCampaignDB) context.getBean("queryCampaignDB");
		
		Advertiser a = (Advertiser) context.getBean("advertiser");
		AdvertiserCreator ac = (AdvertiserCreator) context.getBean("advertiserCreator");
		
		a = ac.createAdvertiser("13909005_3300955_158191");
		String dummy="";
	}

}
