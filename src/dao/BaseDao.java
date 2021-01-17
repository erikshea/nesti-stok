package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import model.Article;
import util.HibernateUtil;

@SuppressWarnings("unchecked")
public  class BaseDao<E> {

	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	public E findById(final Serializable id) {
		return (E) getSession().get(this.getEntityClass(), id);
	}

	public Serializable save(E entity) {
        var result = getSession().save(entity);
        getSession().getTransaction().commit();
		return result;
	}

	public void saveOrUpdate(E entity) {
		getSession().saveOrUpdate(entity);
        getSession().getTransaction().commit();
	}

	public void delete(E entity) {
		getSession().delete(entity);
        getSession().getTransaction().commit();
	}

	public void deleteAll() {
		List<E> entities = findAll();
		for (E entity : entities) {
			getSession().delete(entity);
		}
        getSession().getTransaction().commit();
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

	public <T> E findOneBy(String propertyName, T value) {
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
}