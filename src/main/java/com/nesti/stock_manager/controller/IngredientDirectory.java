package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.ArticleDao;
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
		return "Ingrédients";
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

			this.mainController.getMainPane().addCloseableTab(new IngredientInformation(this.mainController,selectedIngredient));
		});
		
		this.buttonAdd.addActionListener( e->{
			this.mainController.getMainPane().addCloseableTab(new IngredientInformation(this.mainController,new Ingredient()));
		});

		
		this.buttonDuplicate.addActionListener(e -> {
			var ref = this.table.getValueAt(this.table.getSelectedRow(), 0);
			var selectedIngredient = (new IngredientDao()).findOneBy("reference",ref);
			mainController.getMainPane().addCloseableTab(new IngredientInformation(mainController, selectedIngredient.duplicate()));
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
		this.addRowData(new Object[] {entity.getReference(),entity.getName(), String.join(", ", entity.getUnitsNames())});//TODO 
	}
}
