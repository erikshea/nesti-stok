package controller;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class ArticleInformation extends JPanel {
	private static final long serialVersionUID = 1775908299271902575L;
	private JTextField textField;
	private JTextField descriptionTextfield;
	private JTextField packingTextfield;

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

		// on précise la ligne (une seule pousiqu'ils sont sur la mmee ligne)
		buttonBottomBarLayout.setVerticalGroup(buttonBottomBarLayout.createSequentialGroup().addGroup(
				buttonBottomBarLayout.createParallelGroup().addComponent(buttonCancel).addComponent(buttonValidate)));

		this.add(buttonBottomBar, BorderLayout.PAGE_END);

		/**
		 * Create Line_Start, left of the screen
		 */

		var infoArticleLeft = new JPanel();
		var addBasketTextfield = new JTextField("0");
		var quantityAddBasket = new JButton("Ajouter");
		
	//	var titleInformationList = new JLabel("Articles");
		
		var eanLabel = new JLabel("EAN");
		var articleCodetextfield = new JTextField("AAA1");
		
	/*	var eanLabel = new JLabel("EAN");
		var eanTextfield = new JTextField("12345678910");
		
		var ingredientLabel = new JLabel("Ingrédient");
		var ingredientTextfield = new JTextField("Oeuf");
		
		var descriptionLabel = new JLabel("Description article");
		var descriptionTextfield = new JTextField("1 boite d'oeuf de poule élevée en plein air");
		
		var packingLabel = new JLabel("Contenance");
		var apackingTextfield = new JTextField("12");
		
		var weightLabel = new JLabel("Poids unitaire");
		var weightTextfield = new JTextField("0.95");
		*/
		textField = new JTextField("3836987456125");
		
		JLabel articleCodeLabel_1 = new JLabel("Code article");
		
		JLabel ingredientLabel = new JLabel("Ingr\u00E9dient");
		
		JScrollBar scrollBar = new JScrollBar();
		
		JList<String> listIngredient = new JList<String>();
		DefaultListModel<String> model = new DefaultListModel<String>();
		listIngredient.setModel(model);
		
		listIngredient.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		var JlistIngredientElement = new JTextField();
		JlistIngredientElement.setBounds(107, 52, 156, 46);
		infoArticleLeft.add(JlistIngredientElement);
		JlistIngredientElement.setText("Oeufs");
		
		JLabel descriptionLabel = new JLabel("Description");
		
		descriptionTextfield = new JTextField("Boite d'oeuf de poule");
		
		JLabel packingLabel = new JLabel("Contenance");
		
		packingTextfield = new JTextField("12");

		var infoArticleLeftLayout = new GroupLayout(infoArticleLeft);
		infoArticleLeftLayout.setHorizontalGroup(
			infoArticleLeftLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(infoArticleLeftLayout.createSequentialGroup()
					.addGroup(infoArticleLeftLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(addBasketTextfield, Alignment.TRAILING, 69, 69, 69)
						.addComponent(quantityAddBasket, Alignment.TRAILING)
						.addGroup(Alignment.TRAILING, infoArticleLeftLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(articleCodeLabel_1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(articleCodetextfield, 69, 69, 69))
						.addGroup(Alignment.TRAILING, infoArticleLeftLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(eanLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, infoArticleLeftLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(ingredientLabel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
							.addComponent(listIngredient, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, infoArticleLeftLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(descriptionLabel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(descriptionTextfield, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, infoArticleLeftLayout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(packingLabel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(packingTextfield, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		infoArticleLeftLayout.setVerticalGroup(
			infoArticleLeftLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(infoArticleLeftLayout.createSequentialGroup()
					.addComponent(addBasketTextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(quantityAddBasket)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(infoArticleLeftLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(articleCodetextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(articleCodeLabel_1))
					.addGap(22)
					.addGroup(infoArticleLeftLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(eanLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(infoArticleLeftLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(infoArticleLeftLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(infoArticleLeftLayout.createSequentialGroup()
							.addGap(31)
							.addGroup(infoArticleLeftLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(infoArticleLeftLayout.createSequentialGroup()
									.addComponent(listIngredient, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
									.addGap(12))
								.addComponent(ingredientLabel))))
					.addGap(31)
					.addGroup(infoArticleLeftLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(descriptionLabel)
						.addComponent(descriptionTextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(34)
					.addGroup(infoArticleLeftLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(infoArticleLeftLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(packingLabel))
						.addComponent(packingTextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(990))
		);
		infoArticleLeft.setLayout(infoArticleLeftLayout);

		
		/*
		infoArticleLeftLayout.setHorizontalGroup(infoArticleLeftLayout.createSequentialGroup()
				.addGroup(infoArticleLeftLayout.createParallelGroup().addComponent(articleCodeLabel))
				.addGroup(infoArticleLeftLayout.createParallelGroup().addComponent(articleCodetextfield)));

		infoArticleLeftLayout.setVerticalGroup(infoArticleLeftLayout.createSequentialGroup().addGroup(
				infoArticleLeftLayout.createParallelGroup().addComponent(articleCodeLabel).addComponent(articleCodetextfield)));
*/
		this.add(infoArticleLeft, BorderLayout.LINE_START);
		


	}
}
