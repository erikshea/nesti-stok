package com.nesti.stock_manager.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.nesti.stock_manager.model.*;

import com.nesti.stock_manager.util.HibernateUtil;

@SuppressWarnings("unchecked")
public  class BaseDao<E> {

	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	public E findById(final Serializable id) {
		return getSession().get(this.getEntityClass(), id);
	}

	
	
	public Serializable save(E entity) {
		return getSession().save(entity);
	}

	public void saveOrUpdate(E entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(E entity) {
		getSession().delete(entity);
	}

	public void deleteAll() {
		List<E> entities = findAll();
		for (E entity : entities) {
			getSession().delete(entity);
		}
	}

	public List<E> findAll() {
		var cr = this.getCriteriaQuery();
		var root = cr.from(this.getEntityClass());
		cr.select(root);
		return this.getSession().createQuery(cr).getResultList();
	}

	public void clear() {
		getSession().clear();

	}

	public void flush() {
		getSession().flush();
	}

	public <T> E findOneBy(String propertyName, T value)  {
		String hql = "FROM " + getEntityClass().getSimpleName() + " X WHERE X." + propertyName + " = :value";
		Query<E> query = getSession().createQuery(hql);
		query.setParameter("value",value);
		var results = query.list();
		E result = null;
		if (results.size() > 0) {
			result = results.get(0);
		}
		return result;
	}
	
	
	public <T> E findOsneBy(String propertyName, T value) {
			var cr = this.getCriteriaQuery();
	        var root = cr.from(this.getEntityClass());
	        var cb =this.getSession().getCriteriaBuilder();
	        cr.where(cb.equal(root.get(propertyName), value));
	        return this.getSession().createQuery(cr).getResultStream()
        		.findFirst()
        		.orElse(null);
	}

	public CriteriaQuery<E> getCriteriaQuery() {
		CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
		return cb.createQuery(this.getEntityClass());
	}

	
	public Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public static BaseDao<?> getDao(Class<?> entityClass) {
		BaseDao<?> result = null;
		try {
			var entity = (BaseEntity) entityClass.getConstructor().newInstance();
			result = entity.getDao();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}