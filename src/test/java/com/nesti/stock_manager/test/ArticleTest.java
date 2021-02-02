package com.nesti.stock_manager.test;

import static com.nesti.stock_manager.util.FormatUtil.stringToDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.entity.Article;
import com.nesti.stock_manager.entity.Utensil;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.PopulateDb;

public class ArticleTest {

	public static ArticleDao dao = new ArticleDao();
	public static SupplierDao daoSupplier = new SupplierDao();

	@BeforeAll
	public static void populateDb() {
		HibernateUtil.setCurrentEnvironment("test");
		PopulateDb.populate();
	}

	@Test
	public void findOffersAtTest() {
		var article = dao.findOneBy("code", "OEUF6");
		var supplier = daoSupplier.findOneBy("name", "L'empire des fruits");

		var offerOne = article.getOfferAt(stringToDate("18-03-2021 11:15:55"), supplier);

		assertEquals(offerOne.getPrice(), 5.04);
	}

	@Test
	public void findCurrentOffersTest() {
		var supplier = daoSupplier.findOneBy("name", "L'empire des fruits");

		var article = dao.findOneBy("code", "OEUF6");
		var fuitOffer = article.getCurrentOffers().get(supplier);

		assertEquals(fuitOffer.getPrice(), 3.92);

	}

	@Test
	public void findLowestOfferTest() {

		var article = dao.findOneBy("code", "OEUF6");
		var lowestOffer = article.getLowestOffer().getPrice();

		assertEquals(lowestOffer, 3.04);

	}

	
	@Test
	public void findDefaultSupplier() {
		var article = dao.findOneBy("code", "OEUF6");
		assertEquals(article.getDefaultSupplier().getName(),"Oeufs en folie");
	}
	
	
	@Test
	public void cascadeTowardsProductOnCommitTest() {
		var article = new Article("OEUF18", "une boite de dix huit oeufs", "3354654123789", 422, 18, 9);
		
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProduct(new Utensil());
		article.getProduct().setName("blazbla");
		article.getProduct().setReference("uten");

		assertNull(article.getIdArticle());
		assertNull(article.getProduct().getIdProduct());

		
		dao.saveOrUpdate(article);
		HibernateUtil.getSession().getTransaction().commit();
		
		assertNotNull(article.getIdArticle());
		assertNotNull(article.getProduct().getIdProduct());
		
	}

}
