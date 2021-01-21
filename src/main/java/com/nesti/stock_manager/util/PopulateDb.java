package com.nesti.stock_manager.util;

import java.util.Date;
import java.util.List;

import com.nesti.stock_manager.dao.*;
import com.nesti.stock_manager.model.*;

public class PopulateDb {

	public static void main(String[] args) {
		
		

		var query = HibernateUtil.getSession()
                .createQuery("DELETE FROM Article");
	        query.executeUpdate();
				
	        query = HibernateUtil.getSession()
	            .createQuery("DELETE FROM Packaging");
	        query.executeUpdate();
	        query = HibernateUtil.getSession()
	                .createQuery("DELETE FROM Unit");
	        query.executeUpdate();
	        query = HibernateUtil.getSession()
	                .createQuery("DELETE FROM Utensil");
	        query.executeUpdate();

	        query = HibernateUtil.getSession()
	                .createQuery("DELETE FROM Product");
	        query.executeUpdate();
	        
	        query = HibernateUtil.getSession()
	                .createQuery("DELETE FROM Ingredient");
	        query.executeUpdate();
	        
	        query = HibernateUtil.getSession()
	                .createQuery("DELETE FROM Supplier");
	        query.executeUpdate();
		
		var packagingDao =  new PackagingDao();
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
		utensilDao.saveOrUpdate(new Utensil("FOU54","Fouet en bois"));
		utensilDao.saveOrUpdate(new Utensil("CASS154","Casserole anti adhérente"));
		utensilDao.saveOrUpdate(new Utensil("LOU","Louche en inox"));	
		
		var ingredientDao = new IngredientDao();
		var ingredient = new Ingredient("OEUF", "Oeuf de poule");
		ingredient.setUnitsFromNames(List.of("pièce"));
		ingredientDao.saveOrUpdate(ingredient);
		
		ingredient = new Ingredient("CHOCHOC4", "Chocolat au lait");
		ingredient.setUnitsFromNames(List.of("gramme", "kilos"));
		ingredientDao.saveOrUpdate(ingredient);
		
		ingredient = new Ingredient("LAIT", "Lait");
		ingredient.setUnitsFromNames(List.of("Litre"));
		ingredientDao.saveOrUpdate(ingredient);
	
		var articleDao = new ArticleDao();
		var article = new Article("CASS125", "Casserole en inox", "3262154569874",550,1,25);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("CASS154");
		articleDao.saveOrUpdate(article);
		
		article = new Article ("LOUCH45", "louche rouge à pois vert", "3354874123456", 225,1,18);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("sac");
		article.setProductFromReference("LOU");
		articleDao.saveOrUpdate(article);
		
		article = new Article ("OEUF6", "une boite de six oeufs", "3354654123401", 0.58,6,4);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("OEUF");
		articleDao.saveOrUpdate(article);
		
		article = new Article ("OEUF12", "une boite de douze oeufs", "3354654123457", 136,12,6);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("OEUF");
		articleDao.saveOrUpdate(article);
		
		article = new Article ("LAI85", "bouteille de lait de chèvre", "3359654124557", 125,1,32);
		article.setUnitFromName("litre");
		article.setPackagingFromName("bouteille");
		article.setProductFromReference("LAIT");
		articleDao.saveOrUpdate(article);
		
		var SupplierDao = new SupplierDao();
		SupplierDao.saveOrUpdate(new Supplier("O'Sel Fin","12 rue des lilas", "Bat E", "34000", "Montpellier","Jean Jacques","FRANCE","0492546547"));
		SupplierDao.saveOrUpdate(new Supplier("Oeufs en folie","125 avenue Martin", "", "13013", "Marseille","Martine Martin","FRANCE","0491546978"));
		SupplierDao.saveOrUpdate(new Supplier("Perfect cooking","Eight Avenue", "", "35240", "NYC","Edward","USA","0084 564 874"));

	
		var offerDao = new OfferDao();
		var dateTest = new Date();
		var offer = new Offer();
		offer.setPrice(1.1);
		offer.setStartDate(dateTest);
		offer.setSupplierFromName("Perfect cooking");
		offer.setArticleFromCode("OEUF6");
		System.out.println(offer.getId());
		System.out.println(offer.getSupplier());
		System.out.println(offer.getArticle());
		offerDao.saveOrUpdate(offer);
		
		HibernateUtil.getSession().getTransaction().commit();
	
	}
}
