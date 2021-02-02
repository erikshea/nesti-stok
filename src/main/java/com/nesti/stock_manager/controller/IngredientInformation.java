package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.nesti.stock_manager.form.EditableListFieldContainer;
import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Unit;
import com.nesti.stock_manager.util.AppAppereance;

/**
 * Allows seeing/changing an ingredient's fields, and select associated units
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class IngredientInformation extends BaseInformation<Ingredient> {
	private static final long serialVersionUID = 1775908299271902575L;

	public IngredientInformation(MainWindowControl c, Ingredient ingredient) {
		super(c, ingredient);
	}
	
	public String getTitle() {
		var result = "";
		if (item.getName() == null) { // If newly created 
			result = "Nouvel Ingrédient";
		} else { // Else, show name
			result = "Ingrédient : " + item.getName();
		}
		
		return result;
	}
	
	/**
	 * Called first at tab refresh. Build and add swing elements.
	 */
	@Override
	public void preRefreshTab() {
		super.preRefreshTab();
		
		final var product= item;
		var dao = item.getDao();
		
		var ingredientForm = new JPanel();
		ingredientForm.setPreferredSize(new Dimension(500, 0));
		ingredientForm.setLayout(new BoxLayout(ingredientForm, BoxLayout.Y_AXIS));
		
		var titleIngredientInformation = new JLabel(getTitle());
		titleIngredientInformation.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		titleIngredientInformation.setFont(AppAppereance.TITLE_FONT);
		ingredientForm.add(titleIngredientInformation);
		
		var descriptionFieldContainer = new FieldContainer("Description", this);
		descriptionFieldContainer.bind(
				product.getName(), // Starting value
				(s)-> product.setName(s), // On change, update corresponding product property
				(fieldValue)->dao.findOneBy("name", fieldValue) == null); // Only valid if no other product exists with the same property
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
		
	}
	
	
	@Override
	public void closeTab() {
		super.closeTab();
		// On tab close, go back to ingredient directory
		this.mainControl.getMainPane().setSelectedComponent(this.mainControl.getIngredientDirectory());
	}
}