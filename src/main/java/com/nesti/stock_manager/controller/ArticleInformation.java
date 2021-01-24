package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nesti.stock_manager.dao.IngredientDao;
import com.nesti.stock_manager.dao.PackagingDao;
import com.nesti.stock_manager.dao.UnitDao;
import com.nesti.stock_manager.form.EditableListFieldContainer;
import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.form.ListFieldContainer;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Packaging;
import com.nesti.stock_manager.model.Unit;
public class ArticleInformation extends BaseInformation<Article> {
	private static final long serialVersionUID = 1775908299271902575L;

	// left of the screen, article's information
	
	public ArticleInformation(MainWindowControl c, Article article) {
		super(c, article);
	}
	
	@Override
	public void refreshTab() {
		super.refreshTab();

		final var article = item;
		var dao = item.getDao();
		var ingredientDao = new IngredientDao();
		
		var supplierPriceList = new ArticleSupplierList(article);
		this.add(supplierPriceList, BorderLayout.EAST);

		var articleForm = new JPanel();
		articleForm.setPreferredSize(new Dimension(500, 0));
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

		var descriptionFieldContainer = new FieldContainer("Description", this);
		descriptionFieldContainer.bind(
			article.getName(),
			(s)-> article.setName(s),
			(fieldValue)->dao.findOneBy("name", fieldValue) == null);
		articleForm.add(descriptionFieldContainer);

		var codeFieldContainer = new FieldContainer("Code Article", this);
		codeFieldContainer.bind(
			article.getCode(),
			(s)->article.setCode(s),
			(fieldValue)->dao.findOneBy("code", fieldValue) == null);
		articleForm.add(codeFieldContainer);

		var eanFieldContainer = new FieldContainer("EAN", this);
		eanFieldContainer.bind(
			article.getEan(),
			(s)->article.setEan(s),
			(fieldValue)->dao.findOneBy("ean", fieldValue) == null);
		articleForm.add(eanFieldContainer);
		
		if (!article.containsUtensil()) {
			//var ingredientListContainer = new ListFieldContainer<Ingredient>("Ingrédient:", "name", ingredientDao);
			var ingredientListContainer = new ListFieldContainer("Ingrédient:", "name", Ingredient.class);

			ingredientListContainer.bindSelection(
				article.getProduct().getName(),
				(s)->article.setProduct(ingredientDao.findOneBy("name",s)));
			articleForm.add(ingredientListContainer);
		}

		var quantityFieldContainer = new FieldContainer("Quantité", this);
		quantityFieldContainer.bind(
			String.valueOf(article.getQuantity()),
			(s)->article.setQuantity(Double.parseDouble(s)));
		articleForm.add(quantityFieldContainer);

		var weightFieldContainer = new FieldContainer("Poids", this);
		weightFieldContainer.bind(
			String.valueOf(article.getWeight()),
			(s)->article.setWeight(Double.parseDouble(s)));
		articleForm.add(weightFieldContainer);

		var unitDao = new UnitDao();
		var unitListContainer = new EditableListFieldContainer("Unité:", "name", Unit.class);
		unitDao.findAll().forEach(u -> {
			unitListContainer.getListModel().addElement(u.getName());				
		});
		unitListContainer.bindSelection(
			article.getUnit().getName(),
			(s)->article.setUnit(unitDao.findOneBy("name",s)));
		articleForm.add(unitListContainer);

		var packagingDao = new PackagingDao();
		var packagingListContainer = new EditableListFieldContainer("Emballage:", "name", Packaging.class);

		packagingListContainer.bindSelection(
			article.getPackaging().getName(),
			(s)->article.setPackaging(packagingDao.findOneBy("name",s)));
		articleForm.add(packagingListContainer);
		
		articleForm.add(Box.createVerticalGlue());
		
		this.add(articleForm, BorderLayout.WEST);
	}
	
	
	@Override
	public void closeTab() {
		super.closeTab();
		this.mainControl.getMainPane().setSelectedComponent(this.mainControl.getArticleDirectory());
	}


	public void saveItem() {
		final var article= (Article) item;
		article.getDao().saveOrUpdate(article);
	};
}