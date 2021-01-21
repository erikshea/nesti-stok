package com.nesti.stock_manager.util;

import java.util.List;

import com.nesti.stock_manager.dao.*;
import com.nesti.stock_manager.model.*;

public class PopulateDb {

	public static void blabla(String[] args) {
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
		
		HibernateUtil.getSession().getTransaction().commit();
		
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
		
		ingredient = new Ingredient("LAI85V", "Lait");
		ingredient.setUnitsFromNames(List.of("Litre"));
		ingredientDao.saveOrUpdate(ingredient);

		
		var articleDao = new ArticleDao();
		var article = new Article("CASS125", "Casserole en inox", "3262154569874",550,1,25);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
		article.setProductFromReference("CASS154");
		articleDao.saveOrUpdate(article);
		
		
		
		article = new Article ("OEUF6", "une boite de 6 oeufs", "3354654123401", 0.58,6,4);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
	//	article.setProductFromCode("OEUF");
		articleDao.saveOrUpdate(article);
		
		article = new Article ("OEUF12", "une boite de douze oeufs", "3354654123457", 136,12,6);
		article.setUnitFromName("pièce");
		article.setPackagingFromName("boite");
	//	article.setProductFromCode("OEUF");
		articleDao.saveOrUpdate(article);
		
	}
}
