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

public class ArticleList extends JPanel {

	public ArticleList() {

		this.setPreferredSize(new Dimension(2000, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Define all the button of the head on the article list
		var barHeadButton = new JPanel();
		barHeadButton.setLayout(new BoxLayout(barHeadButton, BoxLayout.X_AXIS));

		var buttonAdd = new JButton("Créer");
		var buttonDelete = new JButton("Supprimer");
		var buttonModify = new JButton("Modifier");
		var buttonDuplicate = new JButton("Dupliquer");

		barHeadButton.add(buttonAdd);
		barHeadButton.add(buttonDelete);
		barHeadButton.add(buttonModify);
		barHeadButton.add(buttonDuplicate);

		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();

		//!!!!!!!!!!!!!!! NON FONCTIONNEL taille pas ok!!!!!!!!!!!!!!!!!!
		addToCartField.setMaximumSize(new Dimension(90, 60));
		addToCartField.setMinimumSize(new Dimension(90, 40));

		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);

		barHeadButton.add(addToCart);

		barHeadButton.add(Box.createHorizontalGlue());

		// Title of the article List		
		var titleLabel = new JLabel("Liste d'article");
		this.add(titleLabel);

		// Detail of the article List
		var articleDirectoryList = new ArticleDirectoryList();
		this.add(articleDirectoryList);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		
		this.add(barHeadButton);

	}
}
