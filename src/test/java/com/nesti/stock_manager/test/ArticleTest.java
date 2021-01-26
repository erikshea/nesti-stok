package com.nesti.stock_manager.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.PopulateDb;

public class ArticleTest {

	public static ArticleDao dao = new ArticleDao();;
	public static SupplierDao daoSupplier = new SupplierDao();

	@BeforeAll
	public static void populateDb() {
		HibernateUtil.setCurrentEnvironment("test");
		PopulateDb.populate();
	}

	@Test
	public void FindCurrentOffersTest() {
		var article = dao.findOneBy("code", "OEUF6");
		var supplier = daoSupplier.findOneBy("name", "L'empire des fruits");
		
	//	var offerOne = article.getOfferAt(stringToDate("18-03-2021 11:15:55"),
		
		
		
	}

	@Test
	public void FindCurrentOffersTest2() {

	}
}
