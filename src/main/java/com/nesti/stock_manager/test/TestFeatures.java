package com.nesti.stock_manager.test;

import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Packaging;
import com.nesti.stock_manager.model.Unit;
import com.nesti.stock_manager.model.Utensil;
import com.nesti.stock_manager.dao.*;
import com.nesti.stock_manager.util.*;

import java.util.HashSet;
import java.util.List;

import org.hibernate.query.Query;

import com.nesti.stock_manager.util.HibernateUtil;

public class TestFeatures {
	public static void main(String[] args) {
		var query = HibernateUtil.getSession()
			.createQuery("DELETE FROM Packaging");
		query.executeUpdate();
		query = HibernateUtil.getSession()
				.createQuery("DELETE FROM Unit");
		query.executeUpdate();
		query = HibernateUtil.getSession()
				.createQuery("DELETE FROM Utensil");
		query.executeUpdate();
		query = HibernateUtil.getSession()
				.createQuery("DELETE FROM Ingredient");
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
		
		ingredient = new Ingredient("CHOCCHOC4", "Chocolat au lait");
		ingredient.setUnitsFromNames(List.of("gramme", "kilos"));
		ingredientDao.saveOrUpdate(ingredient);
		
		ingredient = new Ingredient("LAI85V", "Lait");
		ingredient.setUnitsFromNames(List.of("Litre"));
		ingredientDao.saveOrUpdate(ingredient);

		
		HibernateUtil.getSession().getTransaction().commit();

	}
}
