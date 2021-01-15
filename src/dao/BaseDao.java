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
public abstract class BaseDao<E> {
	private SessionFactory sessionFactory;
	private final Class<E> entityClass;

	public BaseDao() {
		this.sessionFactory = HibernateUtil.getSessionFactory();
		this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected Session getSession() {
		if (!this.sessionFactory.getCurrentSession().getTransaction().isActive()) {
			this.sessionFactory.getCurrentSession().getTransaction().begin();
		}
		return this.sessionFactory.getCurrentSession();
	}

	public E findById(final Serializable id) {
		return (E) getSession().get(this.entityClass, id);
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
		var root = cr.from(this.entityClass);
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
	        var cb =this.getSession().getCriteriaBuilder();
	        var root = cr.from(this.entityClass);
	        cr.where(cb.equal(root.get(propertyName), value));
	        var results = this.getSession().createQuery(cr).getResultList();
	        E result = null;
	        if ( results.size() != 0 ) {
	            result = results.get(0);
	        }

	        return result;
	}

	public CriteriaQuery<E> getCriteriaQuery() {
		CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
		return cb.createQuery(this.entityClass);
	}
}