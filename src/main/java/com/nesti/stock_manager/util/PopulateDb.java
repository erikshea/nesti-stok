package com.nesti.stock_manager.util;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.query.Query;

import com.nesti.stock_manager.dao.*;
import com.nesti.stock_manager.model.*;

public class PopulateDb {

	public static void main(String[] args) {
		Query<?> query;
		
		query =
				HibernateUtil.getSession().createQuery("DELETE FROM Offer");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM Article");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM OrdersArticle");
		query.executeUpdate();
		
		query = HibernateUtil.getSession().createQuery("DELETE FROM Order");
		query.executeUpdate();


		query = HibernateUtil.getSession().createQuery("DELETE FROM Packaging");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM Unit");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM Utensil");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM Product");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM Ingredient");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM Supplier");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM User");
		query.executeUpdate();

		var packagingDao = new PackagingDao();
		packagingDao.saveOrUpdate(new Packaging("boite"));
		packagingDao.saveOrUpdate(new Packaging("bouteille"));
		packagingDao.saveOrUpdate(new Packaging("carton"));
		packagingDao.saveOrUpdate(new Packaging("sac"));
		packagingDao.saveOrUpdate(new Packaging("film plastique"));

		var unitDao = new UnitDao();
		unitDao.saveOrUpdate(new Unit("gramme"));
		unitDao.saveOrUpdate(new Unit("kilos"));
		unitDao.saveOrUpdate(new Unit("litre"));
		unitDao.saveOrUpdate(new Unit("pièce"));

		var utensilDao = new UtensilDao();
		utensilDao.saveOrUpdate(new Utensil("FOU54", "Fouet en bois"));
		utensilDao.saveOrUpdate(new Utensil("CASS154", "Casserole anti adhérente"));
		utensilDao.saveOrUpdate(new Utensil("LOU", "Louche en inox"));

		var ingredientDao = new IngredientDao();
		var ingredient = new Ingredient("OEUF", "Oeuf de poule");
		ingredient.setUnitsFromNames(List.of("pièce"));
		ingredientDao.saveOrUpdate(ingredient);

		ingredient = new Ingredient("CHOCHOC", "Chocolat au lait");
		ingredient.setUnitsFromNames(List.of("gramme", "kilos"));
		ingredientDao.saveOrUpdate(ingredient);

		ingredient = new Ingredient("LAIT", "Lait");
		ingredient.setUnitsFromNames(List.of("Litre"));
		ingredientDao.saveOrUpdate(ingredient);

		ingredient = new Ingredient("SUC", "Sucre en poudre");
		ingredient.setUnitsFromNames(List.of("gramme", "kilos"));
		ingredientDao.saveOrUpdate(ingredient);

		var articleDao = new ArticleDao();
		var article = new Article("CASS125", "Casserole en inox", "3262154569874", 550, 1, 25);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("CASS154");
		articleDao.saveOrUpdate(article);

		article = new Article("LOUCH45", "louche inox rouge à pois vert", "3354874123456", 225, 1, 18);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("sac");
		article.setProductFromReference("LOU");
		articleDao.saveOrUpdate(article);

		article = new Article("CHOC74", "plaquette de chocolat", "3354654174584", 255, 1, 23);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("OEUF");
		articleDao.saveOrUpdate(article);

		article = new Article("OEUF6", "une boite de six oeufs", "3354654123401", 0.58, 6, 4);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("OEUF");
		articleDao.saveOrUpdate(article);

		article = new Article("OEUF12", "une boite de douze oeufs", "3354654123457", 136, 12, 6);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("OEUF");
		articleDao.saveOrUpdate(article);

		article = new Article("LAI85", "bouteille de lait de chèvre", "3359654124557", 125, 1, 32);
		article.setUnitFromName("litre");
		article.setPackagingFromName("bouteille");
		article.setProductFromReference("LAIT");
		articleDao.saveOrUpdate(article);

		var SupplierDao = new SupplierDao();
		SupplierDao.saveOrUpdate(new Supplier("O'Sel Fin", "12 rue des lilas", "Bat E", "34000", "Montpellier",
				"Jean Jacques", "FRANCE", "0492546547"));
		SupplierDao.saveOrUpdate(new Supplier("Oeufs en folie", "125 avenue Martin", "", "13013", "Marseille",
				"Martine Martin", "FRANCE", "0491546978"));
		SupplierDao.saveOrUpdate(
				new Supplier("Perfect cooking", "Eight Avenue", "", "35240", "NYC", "Edward", "USA", "0084 564 874"));

		var offerDao = new OfferDao();
		Date now = new Date();
		Date tomorrow = DateUtils.addHours(now, 24);

		var offer = new Offer(2.55);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(now);
		offerDao.saveOrUpdate(offer);

		offer = new Offer(4.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(now);
		offerDao.saveOrUpdate(offer);

		offer = new Offer(4.85);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(now);
		offerDao.saveOrUpdate(offer);

		offer = new Offer(3.25);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(now);
		offerDao.saveOrUpdate(offer);

		offer = new Offer(4.72);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(tomorrow);
		offerDao.saveOrUpdate(offer);

		offer = new Offer();
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(now);
		offerDao.saveOrUpdate(offer);

		offer = new Offer(18.90);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(now);
		offerDao.saveOrUpdate(offer);

		var userDao = new UserDao();
		var user = new User("james", "James Bond", now, "super-administrator");
		user.setPasswordHashFromPlainText("1234");
		userDao.saveOrUpdate(user);

		user = new User("erik", "Erik Shea", now, "administrator");
		user.setPasswordHashFromPlainText("1234");
		userDao.saveOrUpdate(user);

		var orderDao = new OrderDao();
		var order = new Order("257", now, tomorrow);
		order.setSupplierFromName("Oeufs en folie");
		order.setUserFromLogin("james");
		orderDao.saveOrUpdate(order);

		var day2 = DateUtils.addHours(now, 48);
		order = new Order("546", now, day2);
		order.setSupplierFromName("O'Sel Fin");
		order.setUserFromLogin("erik");
		orderDao.saveOrUpdate(order);

		order = new Order("658", now, tomorrow);
		order.setSupplierFromName("O'Sel Fin");
		order.setUserFromLogin("erik");
		orderDao.saveOrUpdate(order);

		order = new Order("555", tomorrow, day2);
		order.setSupplierFromName("Perfect cooking");
		order.setUserFromLogin("erik");
		orderDao.saveOrUpdate(order);

		var ordersArticleDao = new OrdersArticleDao();
		var ordersArticle = new OrdersArticle(5);
		ordersArticle.setOrderFromNumber("257");
		ordersArticle.setArticleFromCode("OEUF6");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		ordersArticle = new OrdersArticle(3);
		ordersArticle.setOrderFromNumber("257");
		ordersArticle.setArticleFromCode("OEUF12");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		ordersArticle = new OrdersArticle(12);
		ordersArticle.setOrderFromNumber("546");
		ordersArticle.setArticleFromCode("OEUF12");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		ordersArticle = new OrdersArticle(2);
		ordersArticle.setOrderFromNumber("546");
		ordersArticle.setArticleFromCode("LAI85");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		ordersArticle = new OrdersArticle(24);
		ordersArticle.setOrderFromNumber("658");
		ordersArticle.setArticleFromCode("LAI85");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		ordersArticle = new OrdersArticle(0);
		ordersArticle.setOrderFromNumber("555");
		ordersArticle.setArticleFromCode("CASS125");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		ordersArticle = new OrdersArticle(1);
		ordersArticle.setOrderFromNumber("555");
		ordersArticle.setArticleFromCode("LOUCH45");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		HibernateUtil.getSession().getTransaction().commit();

	}
}
