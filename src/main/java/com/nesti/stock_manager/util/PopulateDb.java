package com.nesti.stock_manager.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.IngredientDao;
import com.nesti.stock_manager.dao.OfferDao;
import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.dao.OrdersArticleDao;
import com.nesti.stock_manager.dao.PackagingDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.dao.UnitDao;
import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.dao.UtensilDao;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.Order;
import com.nesti.stock_manager.model.OrdersArticle;
import com.nesti.stock_manager.model.Packaging;
import com.nesti.stock_manager.model.Supplier;
import com.nesti.stock_manager.model.Unit;
import com.nesti.stock_manager.model.User;
import com.nesti.stock_manager.model.Utensil;

/**
 * @author Emmanuelle Gay, Erik Shea
 * clears then populates Database with test values 
 */
public class PopulateDb {

	public static void main(String[] args) {
		populate();
	}
	
	public static void populate() {
		Query<?> query;
		
		query =
				HibernateUtil.getSession().createQuery("DELETE FROM Offer");
		query.executeUpdate();
		query = HibernateUtil.getSession().createQuery("DELETE FROM OrdersArticle");
		query.executeUpdate();

		query = HibernateUtil.getSession().createQuery("DELETE FROM Article");
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
		unitDao.saveOrUpdate(new Unit("livre"));

		var utensilDao = new UtensilDao();
		utensilDao.saveOrUpdate(new Utensil("FOU54", "Fouet en bois"));
		utensilDao.saveOrUpdate(new Utensil("CASS154", "Casserole anti adhérente"));
		utensilDao.saveOrUpdate(new Utensil("CASS300", "Casserole en fonte"));
		utensilDao.saveOrUpdate(new Utensil("LOU", "Louche en inox"));
		utensilDao.saveOrUpdate(new Utensil("LOUB", "Louche en bois"));
		
		
		var ingredientDao = new IngredientDao();
		var ingredient = new Ingredient("OEUF", "Oeuf de poule");
		ingredient.setUnitsFromNames(List.of("pièce"));
		ingredientDao.saveOrUpdate(ingredient);

		ingredient = new Ingredient("OEUFC", "Oeuf de caille");
		ingredient.setUnitsFromNames(List.of("pièce"));
		ingredientDao.saveOrUpdate(ingredient);
		
		ingredient = new Ingredient("CHOCN", "Chocolat noir");
		ingredient.setUnitsFromNames(List.of("gramme", "kilos"));
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

		ingredient = new Ingredient("ORA", "Jus d'orange");
		ingredient.setUnitsFromNames(List.of("Litre"));
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

		article = new Article("CASSF300", "casserole en fonte 300mm", "3262154569785", 1500, 1, 8);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("CASS300");
		articleDao.saveOrUpdate(article);

		article = new Article("LOUCH50B", "louche en bois courte", "33548741556", 225, 1, 180);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("sac");
		article.setProductFromReference("LOUB");
		articleDao.saveOrUpdate(article);

		article = new Article("CHOC94", "plaquette de chocolat noir du togo", "3354631231284", 220, 200, 223);
		article.setUnitFromName("gramme");
		article.setPackagingFromName("boite");
		article.setProductFromReference("CHOCN");
		articleDao.saveOrUpdate(article);

		article = new Article("OEUFC10", "une boite de dix oeufs de caille", "45645623401", 420, 10, 44);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("OEUFC");
		articleDao.saveOrUpdate(article);

		article = new Article("OEUFC20", "une boite de vingt oeufs de caille", "36455623457", 886, 20, 66);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("OEUFC");
		articleDao.saveOrUpdate(article);

		article = new Article("ORA150", "bouteille de jus d'orange", "335966545546124557", 1600, 1.5, 432);
		article.setUnitFromName("litre");
		article.setPackagingFromName("bouteille");
		article.setProductFromReference("ORA");
		articleDao.saveOrUpdate(article);
		
		
		var SupplierDao = new SupplierDao();
		SupplierDao.saveOrUpdate(new Supplier("O'Sel Fin", "12 rue des lilas", "Bat E", "34000", "Montpellier",
				"Jean Jacques", "FRANCE", "0492546547"));
		SupplierDao.saveOrUpdate(new Supplier("Oeufs en folie", "125 avenue Martin", "", "13013", "Marseille",
				"Martine Martin", "FRANCE", "0491546978"));
		SupplierDao.saveOrUpdate(
				new Supplier("Perfect cooking", "Eight Avenue", "", "35240", "NYC", "Edward", "USA", "0084 564 874"));
		SupplierDao.saveOrUpdate(
				new Supplier("L'empire des fruits", "4 rue des chats", "", "34040", "Montpellier", "Gaston", "France", "0604040550"));
		SupplierDao.saveOrUpdate(
				new Supplier("Pacher Distribution", "7 avenue du roi", "", "80810", "Alès", "Charles Durant", "France", "0404557440"));

		var offerDao = new OfferDao();

		var offer = new Offer(2.55);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(stringToDate("5-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(3.55);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(stringToDate("13-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(4.55);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(stringToDate("15-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.04);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(stringToDate("18-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.45);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(stringToDate("5-01-2021 11:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(4.15);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(stringToDate("13-01-2021 11:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.55);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(stringToDate("15-01-2021 11:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(5.04);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("OEUF6");
		offer.setStartDate(stringToDate("18-01-2021 11:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.45);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("CHOC94");
		offer.setStartDate(stringToDate("5-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(4.15);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("CHOC94");
		offer.setStartDate(stringToDate("13-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.55);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("CHOC94");
		offer.setStartDate(stringToDate("15-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(5.04);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("CHOC94");
		offer.setStartDate(stringToDate("18-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		
		offer = new Offer(4.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(stringToDate("12-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(5.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(stringToDate("13-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(stringToDate("14-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(2.8);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(stringToDate("15-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(4.28);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(stringToDate("12-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.28);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(stringToDate("13-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		
		offer = new Offer(5.28);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(stringToDate("14-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(8.28);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("OEUF12");
		offer.setStartDate(stringToDate("15-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		
		offer = new Offer(6.28);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(stringToDate("12-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(5.28);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(stringToDate("13-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		
		offer = new Offer(4.28);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(stringToDate("14-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.28);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(stringToDate("15-01-2021 10:15:55"));
		offerDao.saveOrUpdate(offer);
		
		
		
		offer = new Offer(3.25);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(stringToDate("12-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(4.72);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(stringToDate("13-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(3.25);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(stringToDate("14-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(8.72);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("LAI85");
		offer.setStartDate(stringToDate("15-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(4.25);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("CHOC94");
		offer.setStartDate(stringToDate("12-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(4.52);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("CHOC94");
		offer.setStartDate(stringToDate("13-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(5.25);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("CHOC94");
		offer.setStartDate(stringToDate("14-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(3.71);
		offer.setSupplierFromName("O'Sel Fin");
		offer.setArticleFromCode("CHOC94");
		offer.setStartDate(stringToDate("15-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);
		
		
		offer = new Offer(20.);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(stringToDate("12-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(18.90);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(stringToDate("13-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(15.);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(stringToDate("14-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(30.90);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(stringToDate("15-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(20.);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(stringToDate("12-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(18.90);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(stringToDate("13-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(15.);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(stringToDate("14-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(30.90);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(stringToDate("15-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);
		
		
		
		offer = new Offer(17.);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(stringToDate("12-01-2021 5:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(15.90);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(stringToDate("13-01-2021 5:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(14.);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(stringToDate("14-01-2021 5:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(17.90);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("CASS125");
		offer.setStartDate(stringToDate("15-01-2021 5:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(14.);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(stringToDate("12-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(16.60);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(stringToDate("13-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(14.1);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(stringToDate("14-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(13.90);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("LOUCH45");
		offer.setStartDate(stringToDate("15-01-2021 1:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(3.45);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("ORA150");
		offer.setStartDate(stringToDate("5-01-2021 16:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(4.15);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("ORA150");
		offer.setStartDate(stringToDate("13-01-2021 16:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(3.55);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("ORA150");
		offer.setStartDate(stringToDate("15-01-2021 16:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(5.04);
		offer.setSupplierFromName("L'empire des fruits");
		offer.setArticleFromCode("ORA150");
		offer.setStartDate(stringToDate("18-01-2021 16:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(7.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUFC10");
		offer.setStartDate(stringToDate("12-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(6.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUFC10");
		offer.setStartDate(stringToDate("13-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(9.90);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUFC10");
		offer.setStartDate(stringToDate("14-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(7.87);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUFC10");
		offer.setStartDate(stringToDate("15-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(13.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUFC20");
		offer.setStartDate(stringToDate("12-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(14.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUFC20");
		offer.setStartDate(stringToDate("13-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(12.90);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUFC20");
		offer.setStartDate(stringToDate("14-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(18.87);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("OEUFC20");
		offer.setStartDate(stringToDate("15-01-2021 19:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(12.);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("OEUFC20");
		offer.setStartDate(stringToDate("12-01-2021 13:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(14.60);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("OEUFC20");
		offer.setStartDate(stringToDate("13-01-2021 13:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(10.18);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("OEUFC20");
		offer.setStartDate(stringToDate("14-01-2021 13:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(16.90);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("OEUFC20");
		offer.setStartDate(stringToDate("15-01-2021 13:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(9.);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("LOUCH50B");
		offer.setStartDate(stringToDate("12-01-2021 13:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(9.20);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("LOUCH50B");
		offer.setStartDate(stringToDate("13-01-2021 13:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(10.99);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("LOUCH50B");
		offer.setStartDate(stringToDate("14-01-2021 13:15:55"));
		offerDao.saveOrUpdate(offer);

		offer = new Offer(11.11);
		offer.setSupplierFromName("Pacher Distribution");
		offer.setArticleFromCode("LOUCH50B");
		offer.setStartDate(stringToDate("15-01-2021 13:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(12.99);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("LOUCH50B");
		offer.setStartDate(stringToDate("12-01-2021 11:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(10.90);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("LOUCH50B");
		offer.setStartDate(stringToDate("13-01-2021 11:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(12.10);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("LOUCH50B");
		offer.setStartDate(stringToDate("14-01-2021 11:15:55"));
		offerDao.saveOrUpdate(offer);
		
		offer = new Offer(9.87);
		offer.setSupplierFromName("Oeufs en folie");
		offer.setArticleFromCode("LOUCH50B");
		offer.setStartDate(stringToDate("15-01-2021 11:15:55"));
		offerDao.saveOrUpdate(offer);
		
		var userDao = new UserDao();
		var user = new User("james", "James Bond", stringToDate("15-01-2018 1:15:55"), "super-administrator");
		user.setPasswordHashFromPlainText("1234");
		userDao.saveOrUpdate(user);

		user = new User("erik", "Erik Shea", stringToDate("15-01-2017 1:15:55"), "administrator");
		user.setPasswordHashFromPlainText("1234");
		userDao.saveOrUpdate(user);

		var orderDao = new OrderDao();
		var order = new Order("257", stringToDate("13-01-2021 1:15:55"), stringToDate("17-01-2021 1:15:55"));
		order.setSupplierFromName("Oeufs en folie");
		order.setUserFromLogin("james");
		orderDao.saveOrUpdate(order);
		
		order = new Order("612", stringToDate("14-01-2021 1:15:55"), stringToDate("21-01-2021 1:15:55"));
		order.setSupplierFromName("Oeufs en folie");
		order.setUserFromLogin("erik");
		orderDao.saveOrUpdate(order);

		order = new Order("546", stringToDate("15-01-2021 1:15:55"), stringToDate("24-01-2021 1:15:55"));
		order.setSupplierFromName("O'Sel Fin");
		order.setUserFromLogin("erik");
		orderDao.saveOrUpdate(order);

		order = new Order("658", stringToDate("12-01-2021 23:15:55"), stringToDate("16-01-2021 1:15:55"));
		order.setSupplierFromName("O'Sel Fin");
		order.setUserFromLogin("erik");
		orderDao.saveOrUpdate(order);

		order = new Order("555", stringToDate("13-01-2021 23:15:55"), stringToDate("30-02-2021 1:15:55"));
		order.setSupplierFromName("Perfect cooking");
		order.setUserFromLogin("erik");
		orderDao.saveOrUpdate(order);

		order = new Order("567", stringToDate("12-01-2021 23:15:55"), stringToDate("30-02-2021 1:15:55"));
		order.setSupplierFromName("Pacher Distribution");
		order.setUserFromLogin("james");
		orderDao.saveOrUpdate(order);
		
		order = new Order("7467", stringToDate("24-01-2021 23:15:55"), stringToDate("30-02-2021 1:15:55"));
		order.setSupplierFromName("Pacher Distribution");
		order.setUserFromLogin("james");
		orderDao.saveOrUpdate(order);
		
		order = new Order("10467", stringToDate("13-01-2021 23:15:55"), stringToDate("30-02-2021 1:15:55"));
		order.setSupplierFromName("L'empire des fruits");
		order.setUserFromLogin("james");
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
		
		ordersArticle = new OrdersArticle(8);
		ordersArticle.setOrderFromNumber("612");
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

		ordersArticle = new OrdersArticle(12);
		ordersArticle.setOrderFromNumber("555");
		ordersArticle.setArticleFromCode("LAI85");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		ordersArticle = new OrdersArticle(1);
		ordersArticle.setOrderFromNumber("555");
		ordersArticle.setArticleFromCode("LOUCH45");
		ordersArticleDao.saveOrUpdate(ordersArticle);

		ordersArticle = new OrdersArticle(30);
		ordersArticle.setOrderFromNumber("10467");
		ordersArticle.setArticleFromCode("OEUF6");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(4);
		ordersArticle.setOrderFromNumber("10467");
		ordersArticle.setArticleFromCode("CHOC94");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(31);
		ordersArticle.setOrderFromNumber("10467");
		ordersArticle.setArticleFromCode("ORA150");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(3);
		ordersArticle.setOrderFromNumber("7467");
		ordersArticle.setArticleFromCode("OEUFC20");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(3);
		ordersArticle.setOrderFromNumber("7467");
		ordersArticle.setArticleFromCode("LOUCH45");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(3);
		ordersArticle.setOrderFromNumber("7467");
		ordersArticle.setArticleFromCode("CASS125");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(3);
		ordersArticle.setOrderFromNumber("7467");
		ordersArticle.setArticleFromCode("LOUCH50B");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		
		ordersArticle = new OrdersArticle(10);
		ordersArticle.setOrderFromNumber("567");
		ordersArticle.setArticleFromCode("OEUFC20");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(34);
		ordersArticle.setOrderFromNumber("567");
		ordersArticle.setArticleFromCode("LOUCH45");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(3);
		ordersArticle.setOrderFromNumber("567");
		ordersArticle.setArticleFromCode("CASS125");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		ordersArticle = new OrdersArticle(5);
		ordersArticle.setOrderFromNumber("567");
		ordersArticle.setArticleFromCode("LOUCH50B");
		ordersArticleDao.saveOrUpdate(ordersArticle);
		
		
		HibernateUtil.getSession().getTransaction().commit();
	}
	
	
	public static Date stringToDate(String dateString) {
		var formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date result = null;
		try{
			result = formatter.parse(dateString);
		}catch (Exception e) {}
		
		return result;
	}
}
