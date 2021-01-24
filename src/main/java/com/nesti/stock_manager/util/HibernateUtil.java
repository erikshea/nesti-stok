package com.nesti.stock_manager.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateUtil {
    private static Map<String,SessionFactory> sessionFactories;
    private static final String DEFAULT_ENVIRONMENT = "dev";
    private static String currentEnvironment = DEFAULT_ENVIRONMENT;

    //private static EntityManagerFactory entityManagerFactory;
	/*public static EntityManager getEntityManager() {
    	if (entityManagerFactory == null) {
    		entityManagerFactory = getSession().getEntityManagerFactory();
    	}
    	
		return entityManagerFactory.createEntityManager();
    }*/


	public static SessionFactory getSessionFactory(String environment) {
    	if ( sessionFactories == null ) {
    		sessionFactories = new HashMap<>();
    	}
    	
    	if (!sessionFactories.containsKey(environment)) {
    		Configuration configuration = new Configuration().configure("META-INF/hibernate_" + environment + ".cfg.xml");
    		
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