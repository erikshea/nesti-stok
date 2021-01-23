package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.IngredientDao;
import com.nesti.stock_manager.model.Ingredient;

@SuppressWarnings("serial")
public class IngredientDirectory extends BaseDirectory<Ingredient> {

	public IngredientDirectory(MainWindowControl c) {
		super(c);
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
	public void setUpButtonBarListeners()  {
		super.setUpButtonBarListeners();
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
					new IngredientInformation(this.mainController,new Ingredient())
			);
		});

	}
	@Override
	public void deleteRow(int rowIndex) {
		var dao = new IngredientDao();
		var entity = dao.findOneBy("reference", this.table.getValueAt(rowIndex, 0));
		entity.setFlag(BaseDao.DELETED);
	}
	
	@Override
	public void addRow(Ingredient entity) {
		this.addRowData(new Object[] {entity.getReference(),entity.getName()," "});//TODO 
	}
}
