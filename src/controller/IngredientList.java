package controller;

import java.util.List;

import model.Ingredient;
import util.HibernateUtil;

@SuppressWarnings("serial")
public class IngredientList extends BaseList {

	public IngredientList(MainWindowControl c) {
		super(c);

		var session = HibernateUtil.getSessionFactory().openSession();
        
        List<Ingredient> queryIngredient = session.createQuery("from Ingredient").list();
        
        queryIngredient.forEach( v->{
        	this.addRowData(new Object[] { v.getProduct(),"",v.getUnits()});
 		});
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
