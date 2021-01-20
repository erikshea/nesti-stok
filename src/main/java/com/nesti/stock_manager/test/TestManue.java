package com.nesti.stock_manager.test;

import com.nesti.stock_manager.model.Article;
import org.hibernate.query.Query;

import com.nesti.stock_manager.util.HibernateUtil;

public class TestManue {
	public static void main(String[] args) {

		// Exemple HQL: r�cup�rer article d'id 4
		String hql = "FROM Article a WHERE a.id = :article_id";
		var query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("article_id",4);
		var results = query.list();
		var article = (Article) results.get(0);
		System.out.println(article.getName());
		
		
	}

	

}
