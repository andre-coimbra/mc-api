package pt.paginasamarelas.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;





public class HibernateUtil {
	
	
	private static SessionFactory sessionFactory;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal();
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration();
	        configuration.configure("hibernate.cfg.xml");
	        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
	        sessionFactory = configuration.buildSessionFactory(ssrb.build());
			}
        return sessionFactory;
		
	}
	
	public static Session openSession()
	{
		Session session = null;
		if(sessionFactory == null)
		{
			getSessionFactory();
			session = sessionFactory.openSession();
			return session;
		}
		else
		{
			session = sessionFactory.openSession();
			return session;
		}
		
	}

	public static void closeSession() throws HibernateException {
	    Session session = (Session) threadLocal.get();
	    threadLocal.set(null);

	    if (session != null) {
	      session.close();
	    }
	  }
}
