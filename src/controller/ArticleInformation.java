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

<<<<<<< HEAD
import form.*;
=======
import form.FieldContainer;
import form.ListFieldContainer;
>>>>>>> refs/remotes/origin/Manue

public class ArticleInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public ArticleInformation(MainWindowControl c) {
		super(c);

// left of the screen, article's information
		
		var articleForm = new JPanel();
		articleForm.setPreferredSize(new Dimension(500, 0));
		// on spécifie l'axe d'empillement
		articleForm.setLayout(new BoxLayout(articleForm, BoxLayout.Y_AXIS));
		
		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();
		addToCartField.setMaximumSize(new Dimension(40,30));
		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);
		
		addToCart.setAlignmentX(Component.LEFT_ALIGNMENT);
		articleForm.add(addToCart);

		var titleArticleInformation = new JLabel("Article");
		articleForm.add(titleArticleInformation);
		
		var descriptionFieldContainer = new FieldContainer("Description");
		descriptionFieldContainer.getField().setText("Boîte d'oeufs");
		articleForm.add(descriptionFieldContainer);
		
		var codeFieldContainer = new FieldContainer("Code Article");
		codeFieldContainer.getField().setText("AAA3");
		articleForm.add(codeFieldContainer);
		
		var eanFieldContainer = new FieldContainer("EAN");
		eanFieldContainer.getField().setText("4654456456654");
		articleForm.add(eanFieldContainer);
		
		var ingredientListContainer = new ListFieldContainer("Ingrédient:");
		ingredientListContainer.populateList( List.of("Oeuf", "Lait", "Sucre", "Farine", "blaSSDh", "blah3", "blah", "blargh","SSSSS","DDDDD22D"));
		articleForm.add(ingredientListContainer);
		
		var quantityFieldContainer = new FieldContainer("Quantité");
		quantityFieldContainer.getField().setText("12 unités");
		articleForm.add(quantityFieldContainer);

		var weightFieldContainer = new FieldContainer("Poids");
		weightFieldContainer.getField().setText("0.95");
		articleForm.add(weightFieldContainer);

		articleForm.add(Box.createVerticalGlue());
		
		this.add(articleForm, BorderLayout.WEST);
		
	
		var supplierPriceList = new SupplierPriceList();
		this.add(supplierPriceList, BorderLayout.EAST);
	}
}