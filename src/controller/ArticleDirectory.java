package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ArticleDirectory extends BaseDirectory {

	public ArticleDirectory(MainWindowControl c) {
		super(c);

		var articleList = new ArticleList();
		this.add(articleList, BorderLayout.WEST);
		
		
		this.setPreferredSize(new Dimension(2000, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	

		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();

		//!!!!!!!!!!!!!!! NON FONCTIONNEL taille pas ok!!!!!!!!!!!!!!!!!!
		addToCartField.setMaximumSize(new Dimension(90, 60));
		addToCartField.setMinimumSize(new Dimension(90, 40));

		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);

	//	barHeadButton.add(addToCart);

	

		

		
		
		// Title of the article List		
		var titleLabel = new JLabel("Liste d'article");
		this.add(titleLabel);

		// Detail of the article List
		var articleDirectoryList = new ArticleDirectoryList();
		this.add(articleDirectoryList);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
}