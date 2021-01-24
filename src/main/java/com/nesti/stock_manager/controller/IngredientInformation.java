package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.nesti.stock_manager.dao.ProductDao;
import com.nesti.stock_manager.form.EditableListFieldContainer;
import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Product;
import com.nesti.stock_manager.model.Unit;
import com.nesti.stock_manager.util.HibernateUtil;

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
		
		var unitListContainer = new EditableListFieldContainer("Unité", "name", Unit.class);
		unitListContainer.getList().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		unitListContainer.bindMultiple(
				product.getUnitsNames(),
			(s)->product.setUnitsFromNames(s) );
		ingredientForm.add(unitListContainer);

		ingredientForm.add(Box.createVerticalGlue());
		
		this.add(ingredientForm, BorderLayout.WEST);
		
		HibernateUtil.getSession().evict(item);
	}
	
	@Override
	public void saveItem() {
		final var product= (Product) item;
		(new ProductDao()).saveOrUpdate(product);
	}
	
	@Override
	public void closeTab() {
		super.closeTab();
		this.mainControl.getMainPane().setSelectedComponent(this.mainControl.getIngredientDirectory());
	}
}