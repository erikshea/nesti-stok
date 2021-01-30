package com.nesti.stock_manager.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.nesti.stock_manager.model.BaseEntity;
import com.nesti.stock_manager.util.HibernateUtil;


/**
 * Base class on which all other DAOs are based
 * 
 * @param <E> Entity class, defined in sublasses
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("unchecked")
public abstract class BaseDao<E> {

	public final static String ACTIVE = "a";  // Active flag
	public final static String WAITING = "w"; // Waiting flag
	public final static String DELETED = "b"; // Deleted flag
	public final static String DEFAULT = ACTIVE;  // Default flag is active
	
	/**
	 * Convenience method to get Hibernate session
	 * @return Session Hibernate session
	 */
	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	/**
	 * find and entity by its id
	 * @param id
	 * @return corresponding Entity
	 */
	public E findById(final Serializable id) {
		return getSession().get(this.getEntityClass(), id); // use built-in session method
	}

	
	/**
	 * Flags an Entity for saving at next commit
	 * @param entity to save
	 * @return new Entity ID
	 */
	public Serializable save(E entity) {
		return getSession().save(entity);
	}

	
	/**
	 * save and entity (if new) or update it (if existing)
	 * @param entity
	 */
	public void saveOrUpdate(E entity) {
		getSession().saveOrUpdate(entity);
	}

	
	/**
	 * Flags an entity for deletiona t next commit
	 * @param entity to delete
	 */
	public void delete(E entity) {
		getSession().delete(entity);
	}

	
	/**
	 * Delete all entities associated with this DAO
	 */
	public void deleteAll() {
		List<E> entities = findAll();
		for (E entity : entities) {
			getSession().delete(entity);
		}
	}

	
	/**
	 * finds all rows in the table associated with DAO, and return corresponding entities
	 * 
	 * @return list of entities corresponding to all rows in table
	 */
	public List<E> findAll() {
		var cr = this.getCriteriaQuery();
		var root = cr.from(this.getEntityClass());
		cr.select(root);
		return this.getSession().createQuery(cr).getResultList();
	}

	
	
	/**
	 * find all entities in a table that have a flag that is equal to a specified value
	 * @param flag to filter entities
	 * @return list of entities corresponding to all rows in table that have the flag 
	 */
	public List<E> findAll(String flag) {
		var cr = this.getCriteriaQuery();
		var root = cr.from(this.getEntityClass());
		var cb =this.getSession().getCriteriaBuilder();
		try {
			cr.where(cb.equal(root.get("flag"), flag)); // Add criteria for  flag
		} catch (Exception E) {} 						// Unless no flag column in DAO's table
		
		return this.getSession().createQuery(cr).getResultList();
	}

	
	/**
	 * get first element in a table (the one witht he lowest ID)
	 * @return first element or null if no rows in table
	 */
	public E getFirstInTable() {
		var hql = "Select x from " + getEntityClass().getSimpleName() + " x "
				+ "WHERE x.id = (SELECT MIN(xx.id)" 
				+ "FROM " + getEntityClass().getSimpleName() + " xx)";
		var query = getSession().createQuery(hql);
		var results = query.list();
		E result = null;
		if (results.size() > 0) { // If table contains at least one row
			result = (E) results.get(0);
		}
		return result;
	}
	
	
	/**
	 * Get the single element in a table whose property value equals a passed parameter
	 * 
	 * @param <T>
	 * @param propertyName name of property to check
	 * @param value value that must be equal
	 * @return first element found that matches. if no element matches, null
	 */
	public <T> E findOneBy(String propertyName, T value) {
			var cr = this.getCriteriaQuery();
	        var root = cr.from(this.getEntityClass());
	        var cb =this.getSession().getCriteriaBuilder();
	        cr.where(cb.equal(root.get(propertyName), value)); // add equality criteria for property name and value
	        return this.getSession().createQuery(cr).getResultStream()
        		.findFirst() 	// return first result
        		.orElse(null); 	// or null if no result
	}

	/**
	 * Create a criteria builder for DAO's entity class
	 * @return criteria builder associated with entity class
	 */
	public CriteriaQuery<E> getCriteriaQuery() {
		CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
		return cb.createQuery(this.getEntityClass());
	}

	
	/**
	 * Finds E's class using reflection
	 * @return E's class
	 */
	public Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * Static method to get the DAO associated with an Entity
	 * @param entityClass for corresponding to returned DAO
	 * @return DAO object, or null if no DAO corresponds
	 */
	public static BaseDao<?> getDao(Class<?> entityClass) {
		BaseDao<?> result = null;
		try {
			var entity = (BaseEntity) entityClass.getConstructor().newInstance();
			result = entity.getDao(); // Use BaseEntity interface method getDao to find Dao
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}