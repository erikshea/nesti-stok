package controller;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class ArticleInformation extends JPanel {
	private static final long serialVersionUID = 1775908299271902575L;

	public ArticleInformation() {
		this.setLayout(new BorderLayout());
		var testLabelLeft = new JLabel("Label de test pour la section gauche!");
		var testLabelRight = new JLabel("Label de test pour la section droite!");
		var testLabelBotton = new JLabel("Label de test pour la section du BAS");

		this.add(testLabelRight, BorderLayout.LINE_END);

		/**
		 * create Page_End
		 */
		var buttonBottomBar = new JPanel();
		var buttonCancel = new JButton("Annuler");
		var buttonValidate = new JButton("Envoyer");

		// add layout to the button group
		var buttonBottomBarLayout = new GroupLayout(buttonBottomBar);
		buttonBottomBar.setLayout(buttonBottomBarLayout);

		// specify horizontal dispatch into the layout
		// on précise la colonne (chaque bouton a une colonne)
		buttonBottomBarLayout.setHorizontalGroup(buttonBottomBarLayout.createSequentialGroup()
				// allows to push button on the right
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(buttonBottomBarLayout.createParallelGroup().addComponent(buttonCancel))
				.addGroup(buttonBottomBarLayout.createParallelGroup().addComponent(buttonValidate)));

		// on précise la ligne (une seule puisqu'ils sont sur la meme ligne)
		buttonBottomBarLayout.setVerticalGroup(buttonBottomBarLayout.createSequentialGroup().addGroup(
				buttonBottomBarLayout.createParallelGroup().addComponent(buttonCancel).addComponent(buttonValidate)));

		this.add(buttonBottomBar, BorderLayout.PAGE_END);

		/**
		 * Create Line_Start, left of the screen
		 */

		var infoArticleLeft = new JPanel();
		
		var addBasketTextfield = new JTextField("0");
		var quantityAddBasket = new JButton("Ajouter");

		var titleInformationList = new JLabel("Articles dsq ssq  qsd ssqsd qs sqdqs dsqd s qdsqd sq dsq d");

		var articleCodeLabel = new JLabel("Code article");
		var articleCodetextfield = new JTextField("AAA1");

		var eanLabel = new JLabel("EAN");
		var eanTextfield = new JTextField("12345678910");

		var ingredientLabel = new JLabel("Ingrédient");
		var ingredientTextfield = new JTextField("Oeuf");

		var descriptionLabel = new JLabel("Description article");
		var descriptionTextfield = new JTextField("1 boite d'oeuf de poule élevée en plein air");

		var packingLabel = new JLabel("Contenance");
		var packingTextfield = new JTextField("12");

		var weightLabel = new JLabel("Poids unitaire");
		var weightTextfield = new JTextField("0.95");

		var infoArticleLeftLayout = new GroupLayout(infoArticleLeft);
		infoArticleLeft.setLayout(infoArticleLeftLayout);

		infoArticleLeftLayout.setHorizontalGroup(infoArticleLeftLayout.createSequentialGroup()
				//.addGroup(infoArticleLeftLayout.createParallelGroup()	)
				
				.addGroup(infoArticleLeftLayout.createParallelGroup()	.addComponent(addBasketTextfield)
																		.addComponent(quantityAddBasket)
																		.addComponent(articleCodeLabel)
																		.addComponent(eanLabel)
																		.addComponent(ingredientLabel)
																		.addComponent(descriptionLabel)
																		.addComponent(packingLabel)
																		.addComponent(weightLabel))
				.addGroup(infoArticleLeftLayout.createParallelGroup()	
																		.addComponent(articleCodetextfield)
																		.addComponent(eanTextfield)
																		.addComponent(ingredientTextfield)
																		.addComponent(descriptionTextfield)
																		.addComponent(packingTextfield)
																		.addComponent(weightTextfield))
																		
				
				);

		infoArticleLeftLayout.setVerticalGroup(infoArticleLeftLayout.createSequentialGroup()
				.addGroup(infoArticleLeftLayout.createParallelGroup().addComponent(addBasketTextfield))
				.addGroup(infoArticleLeftLayout.createParallelGroup().addComponent(quantityAddBasket))
				
				.addGroup(infoArticleLeftLayout.createParallelGroup()	.addComponent(articleCodeLabel)
																		.addComponent(articleCodetextfield))
				
				.addGroup(infoArticleLeftLayout.createParallelGroup()	.addComponent(eanLabel)
																		.addComponent(eanTextfield))
				
				.addGroup(infoArticleLeftLayout.createParallelGroup()	.addComponent(ingredientLabel)
																		.addComponent(ingredientTextfield))
				
				.addGroup(infoArticleLeftLayout.createParallelGroup()	.addComponent(descriptionLabel)
																		.addComponent(descriptionTextfield))
				
				.addGroup(infoArticleLeftLayout.createParallelGroup()	.addComponent(packingLabel)
																		.addComponent(packingTextfield))
				
				.addGroup(infoArticleLeftLayout.createParallelGroup()	.addComponent(weightLabel)
																		.addComponent(weightTextfield))
				);

		this.add(infoArticleLeft, BorderLayout.LINE_START);

	}
}
