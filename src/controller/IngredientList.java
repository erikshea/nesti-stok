package controller;

import java.util.List;

import model.*;
import util.HibernateUtil;

@SuppressWarnings("serial")
public class IngredientList extends BaseList {

	public IngredientList() {
		super();

		var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        
        List<Ingredient> queryIngredient = session.createQuery("from Ingredient").list();
        
        queryIngredient.forEach( v->{
        	this.addRowData(new Object[] { v.getProduct(),"",v.getUnits()});
 		});

		transaction.commit();
		
		
	}

	// Title of the article List
	@Override
	public String getTitle() {
		return "Liste des ingrédients";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Réf","Nom","Unité"};
	}
	
}
