package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.IngredientDao;
import com.nesti.stock_manager.entity.Ingredient;

/**
 * Shows list of all ingredients, and provides buttons to manipulate them 
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class IngredientDirectory extends BaseDirectory<Ingredient> {

	public IngredientDirectory(MainWindowControl c) {
		super(c);
	}
	

	@Override
	public String getTitle() {
		return "Ingrédients";
	}

	@Override
	public Object[] getTableModelColumnNames() {
		return new Object[] {"Réf","Nom","Unités"};
	}
	
	@Override
	public void setUpButtonBarListeners()  {
		super.setUpButtonBarListeners();
		// "Modify" button action
		this.buttonModify.addActionListener( e->{
			var ref = this.table.getValueAt(this.table.getSelectedRow(), 0);
			var selectedIngredient = (new IngredientDao()).findOneBy("reference",ref);

			this.mainController.getMainPane().addCloseableTab(new IngredientInformation(this.mainController,selectedIngredient));
		});
		// New article action
		this.buttonAdd.addActionListener( e->{
			this.mainController.getMainPane().addCloseableTab(new IngredientInformation(this.mainController,new Ingredient()));
		});

		// "Duplicate" button action
		this.buttonDuplicate.addActionListener(e -> {
			var ref = this.table.getValueAt(this.table.getSelectedRow(), 0);
			var selectedIngredient = (new IngredientDao()).findOneBy("reference",ref); // get ingredient from selected row ref column
			mainController.getMainPane().addCloseableTab(
				new IngredientInformation(mainController, selectedIngredient.duplicate()) // create new tab with duplicated ingredient
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
		this.addRowData(new Object[] {entity.getReference(),entity.getName(), String.join(", ", entity.getUnitsNames())});
	}
}
