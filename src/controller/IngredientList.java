package controller;

import java.util.List;

import dao.*;
import model.Ingredient;
import model.Product;
import util.HibernateUtil;

@SuppressWarnings("serial")
public class IngredientList extends BaseList {

	public IngredientList(MainWindowControl c) {
		super(c);
		
		refresh();
	}

	public void refresh() {
		this.tableModel.getDataVector().removeAllElements();
		// Detail of the article List
		var dao = new IngredientDao();
		var ingredients = dao.findAll();
		ingredients.forEach( i->{
			this.addRowData(new Object[] {i.getProduct().getReference(),i.getProduct().getName()," "});//TODO 
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
	
	@Override
	public void setUpButtonListeners()  {
		super.setUpButtonListeners();
		this.buttonModify.addActionListener( e->{
			var ref = this.table.getValueAt(this.table.getSelectedRow(), 0);
			var selectedIngredient = (new ProductDao()).findOneBy("reference",ref).getIngredient();

			this.mainController.addCloseableTab(
					"Ingrédient: " + selectedIngredient.getProduct().getName(),
					new IngredientInformation(this.mainController,selectedIngredient)
			);
		});
		
		this.buttonAdd.addActionListener( e->{
			this.mainController.addCloseableTab(
					"Nouvel Ingrédient",
					new IngredientInformation(this.mainController,null)
			);
		});
		/*
		this.buttonDelete.addActionListener( e->{
			var dao = new IngredientDao();
			
			for ( var rowIndex : this.table.getSelectedRows()) {
				var ingredient = dao.findOneBy("reference", this.table.getValueAt(rowIndex, 0));
				dao.delete(ingredient);
			}
			
			refresh();
		});
		
		this.buttonDuplicate.addActionListener( e->{
			var ref = this.table.getValueAt(this.table.getSelectedRow(), 0);
			var selectedIngredient = (new ProductDao()).findOneBy("reference",ref).getIngredient();
			var product = selectedIngredient.getProduct();
			product.setIdProduct(0);
			this.mainController.addCloseableTab(
					"Nouvel Ingrédient",
					new IngredientInformation(this.mainController,selectedIngredient)
			);
		});*/
	}
}
