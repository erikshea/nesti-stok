package com.nesti.stock_manager.test;

import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.dao.ArticleDao;
import java.util.HashSet;
import java.util.List;

import org.hibernate.query.Query;

import com.nesti.stock_manager.util.HibernateUtil;

public class TestFeatures {
	public static void main(String[] args) {
		var test = (new ArticleDao()).findById(2);
		System.out.println(test.getIsDefaults());
	}


	public static Article tedst(int articleId) { 
		String hql = "FROM Article a WHERE a.id = :my_parameter";
		var query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("my_parameter",articleId);
		var results = query.list();
		Article result = null;
		
		if (results.size() > 0) { // si l'article existe, c'est le r�sultat. sinon il reste null
			result = (Article) results.get(0); 
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Object> lastestOffer(int supplierId) { 
		String hql = 	"select o from Offer o "
				+ 		"where o.startDate =  (select max(oo.startDate) from Offer oo where oo.id.idSupplier = :supplier_id)";
		var query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("supplier_id",supplierId);
		//query.setParameter("article_id",articleId);
		return (List<Object>) query.list();
	}
	@SuppressWarnings("unchecked")
	
	public static List<Object> LowestOffer(int articleId) { 
		String hql = 	"select o from Offer o "
				+ 		"where o.price =  (select max(oo.price) from Offer oo where oo.id.idArticle = :article_id)";
		var query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("article_id",articleId);
		//query.setParameter("article_id",articleId);
		return (List<Object>) query.list();
	}
}
