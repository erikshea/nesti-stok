package controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class IngredientDirectory extends BaseDirectory {

	public IngredientDirectory(MainWindowControl c) {
		super(c);

		var ingredientList = new IngredientList(c);
		this.add(ingredientList, BorderLayout.CENTER);

	}
}