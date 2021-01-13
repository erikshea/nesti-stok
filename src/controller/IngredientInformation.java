package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import form.EditableListFieldContainer;
import form.FieldContainer;
import form.ListFieldContainer;

public class IngredientInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public IngredientInformation(MainWindowControl c) {
		super(c);

// left of the screen, ingredient's information
		
		var ingredientForm = new JPanel();
		ingredientForm.setPreferredSize(new Dimension(500, 0));
		ingredientForm.setLayout(new BoxLayout(ingredientForm, BoxLayout.Y_AXIS));
		
		var titleIngredientInformation = new JLabel("Ingrédient");
		ingredientForm.add(titleIngredientInformation);
		
		var descriptionFieldContainer = new FieldContainer("Description");
		descriptionFieldContainer.getField().setText("Lait");
		ingredientForm.add(descriptionFieldContainer);
		
		var codeFieldContainer = new FieldContainer("Référence");
		codeFieldContainer.getField().setText("III3");
		ingredientForm.add(codeFieldContainer);
		
		var unitFieldContainer = new EditableListFieldContainer("Unité");
		unitFieldContainer.populateList(List.of("kg","pièce","litre"));
		ingredientForm.add(unitFieldContainer);

		var weightFieldContainer = new FieldContainer("Poids unitaire");
		weightFieldContainer.getField().setText("1140");
		ingredientForm.add(weightFieldContainer);

		ingredientForm.add(Box.createVerticalGlue());
		
		this.add(ingredientForm, BorderLayout.WEST);
		
	}
}