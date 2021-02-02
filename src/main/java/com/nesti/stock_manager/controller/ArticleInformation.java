package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

/**
 * Allows seeing/changing an article's fields, and select associated packaging and unit
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class ArticleInformation extends BaseInformation<Article> {
	private static final long serialVersionUID = 1775908299271902575L;

	public ArticleInformation(MainWindowControl c, Article article) {
		super(c, article);
	}
	

	@Override
	public String getTitle() {
		var result = "";
		if (item.getName() == null) { // If newly created article
			// Title depends on product type
			if ( item.getProduct() instanceof Utensil) {
				result = "Nouvel Article (ustensile)";
			} else {
				result = "Nouvel Article (ingrédient)";
			}
		} else { // Else, just show article name
			result = "Article : " + item.getName();
		}
		
		return result;
	}
	
	/**
	 * Called first at tab refresh. Build and add swing elements.
	 */
	@Override
	public void preRefreshTab() {
		super.preRefreshTab();

		final var article = item;

		var dao = item.getDao();
		var ingredientDao = new IngredientDao();
		
		// Create price list, add to the right of border pane
		var supplierPriceList = new ArticleSupplierList(article);
		this.add(supplierPriceList, BorderLayout.EAST);
		var articleForm = new JPanel();
		articleForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		articleForm.setPreferredSize(new Dimension(500, 0));
		articleForm.setLayout(new BoxLayout(articleForm, BoxLayout.Y_AXIS));

		var titleArticleInformation = new JLabel(getTitle());
		titleArticleInformation.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		titleArticleInformation.setFont(AppAppereance.TITLE_FONT);
		articleForm.add(titleArticleInformation);

		var descriptionFieldContainer = new FieldContainer("Description", this);
		descriptionFieldContainer.bind(
			article.getName(),	// Starting value
			(s)-> article.setName(s),	// On change, update corresponding article property
			(fieldValue)->dao.findOneBy("name", fieldValue) == null); // Only valid if no other article exists with the same property
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
		// Build editable list of all units
		var unitListContainer = new EditableListFieldContainer("Unité:", "name", Unit.class);
		unitListContainer.bindSelection(
			article.getUnit().getName(),
			(s)->article.setUnit(unitDao.findOneBy("name",s)));
		articleForm.add(unitListContainer);

		var packagingDao = new PackagingDao();
		var packagingListContainer = new EditableListFieldContainer("Emballage:", "name", Packaging.class);
		// Build editable list of all packagings
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
		// On tab close, go back to article directory
		this.mainControl.getMainPane().setSelectedComponent(this.mainControl.getArticleDirectory());
	}
}


