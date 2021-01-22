package com.nesti.stock_manager.util;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static EntityManagerFactory entityManagerFactory;
    
    public static EntityManager getEntityManager() {
    	if (entityManagerFactory == null) {
    		entityManagerFactory = getSession().getEntityManagerFactory();
    	}
    	
		return entityManagerFactory.createEntityManager();
    }
    
    public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null) {
    		Configuration configuration = new Configuration().configure("META-INF/hibernate.cfg.xml");
    		
			sessionFactory = configuration.buildSessionFactory();
        }
         
        return sessionFactory;
    }
    
	public static Session getSession() {
		if (!getSessionFactory().getCurrentSession().getTransaction().isActive()) {
			getSessionFactory().getCurrentSession().getTransaction().begin();
		}
		return getSessionFactory().getCurrentSession();
	}
}