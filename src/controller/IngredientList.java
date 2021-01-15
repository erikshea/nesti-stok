package controller;

import java.util.List;

import dao.*;
import model.Ingredient;
import util.HibernateUtil;

@SuppressWarnings("serial")
public class IngredientList extends BaseList {

	public IngredientList(MainWindowControl c) {
		super(c);

		  var dao = new IngredientDao();
	        var ingredients = dao.findAll();
	        ingredients.forEach( i->{
	        	var units = "";
	        	
	        	for (var u:i.getUnits()) {
	        		units+="," + u.getName()
	        		;
	        	}
	    		this.addRowData(new Object[] {i.getProduct().getReference(),i.getProduct().getName(),units});
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
