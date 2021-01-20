package test;

import org.hibernate.query.Query;

import dao.*;
import model.*;
import util.HibernateUtil;

public class TestManue {
	public static void main(String[] args) {

		// Exemple HQL: récupérer article d'id 4
		String hql = "FROM Article a WHERE a.id = :article_id";
		var query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("article_id",4);
		var results = query.list();
		var article = (Article) results.get(0);
		System.out.println(article.getName());
		
		
	}

	

}
