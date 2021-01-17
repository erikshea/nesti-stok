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

import dao.IngredientDao;
import form.*;
import model.Article;
import model.Product;

public class ArticleInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public ArticleInformation(MainWindowControl c, Article article) {
		super(c, article);

		if (article == null) {
			article = new Article();
			article.setProduct(new Product());
		}
		
		var supplierPriceList = new ArticleSupplierList(article);
		if (article.getIdArticle()==0) {
			supplierPriceList.getAddButton().setEnabled(false);
		}
		this.add(supplierPriceList, BorderLayout.EAST);
		
		System.out.println(article.getName());

// left of the screen, article's information
		var articleForm = new JPanel();
		articleForm.setPreferredSize(new Dimension(500, 0));
		// on spécifie l'axe d'empillement
		articleForm.setLayout(new BoxLayout(articleForm, BoxLayout.Y_AXIS));

		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();
		addToCartField.setMaximumSize(new Dimension(40, 30));
		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);

		addToCart.setAlignmentX(Component.LEFT_ALIGNMENT);
		articleForm.add(addToCart);

		var titleArticleInformation = new JLabel("Article");
		articleForm.add(titleArticleInformation);

		var descriptionFieldContainer = new FieldContainer("Description");
		descriptionFieldContainer.getField().setText(article.getName());
		articleForm.add(descriptionFieldContainer);

		var codeFieldContainer = new FieldContainer("Code Article");
		codeFieldContainer.getField().setText(article.getCode());
		articleForm.add(codeFieldContainer);

		var eanFieldContainer = new FieldContainer("EAN");
		eanFieldContainer.getField().setText(article.getEan());
		articleForm.add(eanFieldContainer);

		var i = (new IngredientDao()).findOneBy("idProduct", article.getProduct().getIdProduct());
		if (i != null) {
			var ingredientListContainer = new ListFieldContainer("Ingrédient:");
			var listModel = ingredientListContainer.getListModel();
			var dao = new IngredientDao();
			dao.findAll().forEach(ing -> {
				listModel.addElement(ing.getProduct().getName());				
			});
			ingredientListContainer.getList().setSelectedValue(article.getProduct().getName(), true);
			articleForm.add(ingredientListContainer);
		}

		var quantityFieldContainer = new FieldContainer("Quantité");
		quantityFieldContainer.getField().setText(String.valueOf(article.getQuantity()));
		articleForm.add(quantityFieldContainer);

		var weightFieldContainer = new FieldContainer("Poids");
		weightFieldContainer.getField().setText(String.valueOf(article.getWeight()));
		articleForm.add(weightFieldContainer);

		articleForm.add(Box.createVerticalGlue());

		this.add(articleForm, BorderLayout.WEST);

	}
}