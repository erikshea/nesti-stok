package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.security.InvalidParameterException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nesti.stock_manager.dao.ArticleDao;
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
import com.nesti.stock_manager.model.Utensil;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.UnavailableArticleException;
public class ArticleInformation extends BaseInformation<Article> {
	private static final long serialVersionUID = 1775908299271902575L;

	// left of the screen, article's information
	
	public ArticleInformation(MainWindowControl c, Article article) {
		super(c, article);
	}
	
	public String getTitle() {
		var result = "";
		if (item.getName() == null) {
			if ( item.getProduct() instanceof Utensil) {
				result = "Nouvel Article (ustensile)";
			} else {
				result = "Nouvel Article (ingrédient)";
			}
		} else {
			result = "Article : " + item.getName();
		}
		
		return result;
	}
	
	@Override
	public void preRefreshTab() {
		super.preRefreshTab();

		final var article = item;
		this.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	//	this.setBackground(AppAppereance.LIGHT_COLOR);
		
		
		var dao = item.getDao();
		var ingredientDao = new IngredientDao();
		
		var supplierPriceList = new ArticleSupplierList(article);
		supplierPriceList.setBackground(AppAppereance.LIGHT_COLOR);
		this.add(supplierPriceList, BorderLayout.EAST);
		

		var articleForm = new JPanel();
		articleForm.setBackground(AppAppereance.LIGHT_COLOR);
		articleForm.setPreferredSize(new Dimension(500, 0));
		articleForm.setLayout(new BoxLayout(articleForm, BoxLayout.Y_AXIS));

		var titleArticleInformation = new JLabel("Article");
		titleArticleInformation.setBackground(AppAppereance.LIGHT_COLOR);
		articleForm.add(titleArticleInformation);

		var descriptionFieldContainer = new FieldContainer("Description", this);
		descriptionFieldContainer.setBackground(AppAppereance.LIGHT_COLOR);
		descriptionFieldContainer.bind(
			article.getName(),
			(s)-> article.setName(s),
			(fieldValue)->dao.findOneBy("name", fieldValue) == null);
		articleForm.add(descriptionFieldContainer);

		var codeFieldContainer = new FieldContainer("Code Article", this);
		codeFieldContainer.setBackground(AppAppereance.LIGHT_COLOR);
		codeFieldContainer.bind(
			article.getCode(),
			(s)->article.setCode(s),
			(fieldValue)->dao.findOneBy("code", fieldValue) == null);
		articleForm.add(codeFieldContainer);

		var eanFieldContainer = new FieldContainer("EAN", this);
		eanFieldContainer.setBackground(AppAppereance.LIGHT_COLOR);
		eanFieldContainer.bind(
			article.getEan(),
			(s)->article.setEan(s),
			(fieldValue)->dao.findOneBy("ean", fieldValue) == null);
		articleForm.add(eanFieldContainer);
		
		if (!article.containsUtensil()) {
			var ingredientListContainer = new ListFieldContainer("Ingrédient:", "name", Ingredient.class);
			ingredientListContainer.setBackground(AppAppereance.LIGHT_COLOR);
			ingredientListContainer.bindSelection(
				article.getProduct().getName(),
				(s)->article.setProduct(ingredientDao.findOneBy("name",s)));
			articleForm.add(ingredientListContainer);
		}

		var quantityFieldContainer = new FieldContainer("Quantité", this);
		quantityFieldContainer.setBackground(AppAppereance.LIGHT_COLOR);
		quantityFieldContainer.bind(
			String.valueOf(article.getQuantity()),
			(s)->article.setQuantity(Double.parseDouble(s)));
		articleForm.add(quantityFieldContainer);

		var weightFieldContainer = new FieldContainer("Poids", this);
		weightFieldContainer.setBackground(AppAppereance.LIGHT_COLOR);
		weightFieldContainer.bind(
			String.valueOf(article.getWeight()),
			(s)->article.setWeight(Double.parseDouble(s)));
		articleForm.add(weightFieldContainer);

		var unitDao = new UnitDao();
		var unitListContainer = new EditableListFieldContainer("Unité:", "name", Unit.class);
		unitListContainer.setBackground(AppAppereance.LIGHT_COLOR);
		unitListContainer.bindSelection(
			article.getUnit().getName(),
			(s)->article.setUnit(unitDao.findOneBy("name",s)));
		articleForm.add(unitListContainer);

		var packagingDao = new PackagingDao();
		var packagingListContainer = new EditableListFieldContainer("Emballage:", "name", Packaging.class);
		packagingListContainer.setBackground(AppAppereance.LIGHT_COLOR);
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