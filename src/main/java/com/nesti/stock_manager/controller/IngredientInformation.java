package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.nesti.stock_manager.dao.ProductDao;
import com.nesti.stock_manager.dao.UnitDao;
import com.nesti.stock_manager.form.EditableListFieldContainer;
import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Product;

public class IngredientInformation extends BaseInformation<Ingredient> {
	private static final long serialVersionUID = 1775908299271902575L;

	public IngredientInformation(MainWindowControl c, Ingredient ingredient) {
		super(c, ingredient);
	}
	
	@Override
	public void refreshTab() {
		super.refreshTab();
		
		final var product= item;
		var dao = item.getDao();
		var unitDao = new UnitDao();
		// left of the screen, ingredient's information
		
		var ingredientForm = new JPanel();
		ingredientForm.setPreferredSize(new Dimension(500, 0));
		ingredientForm.setLayout(new BoxLayout(ingredientForm, BoxLayout.Y_AXIS));
		
		var titleIngredientInformation = new JLabel("Ingrédient");
		ingredientForm.add(titleIngredientInformation);
		
		var descriptionFieldContainer = new FieldContainer("Description", this);
		descriptionFieldContainer.bind(
				product.getName(),
				(s)-> product.setName(s),
				(fieldValue)->dao.findOneBy("name", fieldValue) == null);
		ingredientForm.add(descriptionFieldContainer);
		
		var codeFieldContainer = new FieldContainer("Référence", this);
		codeFieldContainer.bind(
				product.getReference(),
				(s)-> product.setReference(s),
				(fieldValue)->dao.findOneBy("reference", fieldValue) == null);
		ingredientForm.add(codeFieldContainer);
		
		var unitListContainer = new EditableListFieldContainer("Unité", this);
		unitListContainer.getList().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		unitDao.findAll().forEach(unit -> {
			unitListContainer.getListModel().addElement(unit.getName());				
		});
		unitListContainer.bindMultiple(
				product.getUnitsNames(),
			(s)->product.setUnitsFromNames(s) );
		ingredientForm.add(unitListContainer);

		ingredientForm.add(unitListContainer);

		ingredientForm.add(Box.createVerticalGlue());
		
		this.add(ingredientForm, BorderLayout.WEST);
	}
	
	@Override
	public void saveItem() {
		final var product= (Product) item;
		(new ProductDao()).saveOrUpdate(product);
		this.mainControl.getIngredientList().refreshTab();
	}
	
	@Override
	public void addButtonListeners() {
		super.addButtonListeners();
		this.buttonValidate.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getIngredientList());
		});
		
		this.buttonCancel.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getIngredientList());
		});
	}
}