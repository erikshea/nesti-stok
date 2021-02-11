package com.nesti.stock_manager.util;


import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
/**
 * @author Emmanuelle Gay, Erik Shea
 * Class through which all hibernate sessions are accessed
 */
public class HibernateUtil {
	// Hashmap of session factories by environment (in case we want to switch on the fly)
    private static Map<String,SessionFactory> sessionFactories;
    private static final String DEFAULT_ENVIRONMENT = "dev"; 
    
    // environment determines which .cfg.xml hibernate uses
    private static String currentEnvironment = DEFAULT_ENVIRONMENT;


	/**
	 * Session factory provides access to sessions
	 * @param environment environment for database settings cfg file ("prod", "dev", "test"...)
	 * @return session factory
	 */
	public static SessionFactory getSessionFactory(String environment) {
    	if ( sessionFactories == null ) {
    		sessionFactories = new HashMap<>(); // Create hashmap if it doesn't exist
    	}

    	if (!sessionFactories.containsKey(environment)) {
    		// Load configuration that corresponds to environment (ie "hibernate_dev.cfg.xml")
    		Configuration configuration = new Configuration().configure("file:/" + System.getProperty("user.dir") + "/database_config/hibernate_" + environment + ".cfg.xml");
    		
    		// set with environment as key, session facory as value
    		sessionFactories.put(environment,configuration.buildSessionFactory());
        }
         
        return sessionFactories.get(environment);
    }

	public static Session getSession() {
		if (!getSessionFactory(currentEnvironment).getCurrentSession().getTransaction().isActive()) {
			getSessionFactory(currentEnvironment).getCurrentSession().getTransaction().begin();
		}
		return getSessionFactory(currentEnvironment).getCurrentSession();
	}
	
    
    public static String getCurrentEnvironment() {
		return currentEnvironment;
	}

	public static void setCurrentEnvironment(String e) {
		currentEnvironment = e;
	}
}