package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ArticleList extends BaseList {

	public ArticleList() {
		super();

		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();

		addToCartField.setMaximumSize(new Dimension(100, 30));
        addToCartField.setPreferredSize(new Dimension(100, 0));

		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);

		this.buttonBar.add(addToCart, 4);

		// Detail of the article List
		this.addRowData(new Object[] {"Couteau de cuisine en inox","ACME","12","Ustensil","50","18"});
		this.addRowData(new Object[] {"1 boite d'oeuf de poule","Poulen'Herbe","2.5","Ingrédient","18","4"});
		this.addRowData(new Object[] {"fouet en plastique","Tout pour la cuisine","3.30","Ustensil","451","11.60"});
	}

	@Override
	public String getTitle() {
		return "Liste d'article";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Description", "Fournisseur par défaut", "Prix d'achat","Type","Stock","PV Conseillé" };
	}
	
}
