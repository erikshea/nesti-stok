package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.IngredientDao;
import com.nesti.stock_manager.model.*;

@SuppressWarnings("serial")
public class IngredientList extends BaseList<Ingredient> {

	public IngredientList(MainWindowControl c) {
		super(c);
		
		refresh();
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
			var selectedIngredient = (new IngredientDao()).findOneBy("reference",ref);

			this.mainController.addCloseableTab(
					"Ingrédient: " + selectedIngredient.getName(),
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
					"Nouvel Ingréient",
					new IngredientInformation(this.mainController,selectedIngredient)
			);
		});*/
	}
	@Override
	public void deleteRow(int rowIndex) {
		var dao = new IngredientDao();
		var entity = dao.findOneBy("reference", this.table.getValueAt(rowIndex, 0));
		dao.delete(entity);
	}
	
	@Override
	public void addRow(Ingredient entity) {
		this.addRowData(new Object[] {entity.getReference(),entity.getName()," "});//TODO 
	}
}
