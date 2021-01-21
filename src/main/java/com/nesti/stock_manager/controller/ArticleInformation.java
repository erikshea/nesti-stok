package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.form.ListFieldContainer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nesti.stock_manager.dao.*;
import com.nesti.stock_manager.model.*;
import com.nesti.stock_manager.util.HibernateUtil;

public class ArticleInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	// left of the screen, article's information
	
	public ArticleInformation(MainWindowControl c, Article article) {
		super(c, article);

		if (article.getUnit() == null) {
			article.setUnit(new Unit());
		}
		
		if (article.getPackaging() == null) {
			article.setPackaging(new Packaging());
		}

		final var articleFinal= article;
		var articleDao = new ArticleDao();
		var ingredientDao = new IngredientDao();
		
		var supplierPriceList = new ArticleSupplierList(article);
		if (article.getIdArticle()==0) {
			supplierPriceList.getAddButton().setEnabled(false);
		}
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
			articleFinal.getName(),
			(s)-> articleFinal.setName(s),
			(fieldValue)->articleDao.findOneBy("name", fieldValue) == null);
		articleForm.add(descriptionFieldContainer);

		var codeFieldContainer = new FieldContainer("Code Article", this);
		codeFieldContainer.bind(
			articleFinal.getCode(),
			(s)->articleFinal.setCode(s),
			(fieldValue)->articleDao.findOneBy("code", fieldValue) == null);
		articleForm.add(codeFieldContainer);

		var eanFieldContainer = new FieldContainer("EAN", this);
		eanFieldContainer.bind(
			articleFinal.getEan(),
			(s)->articleFinal.setEan(s),
			(fieldValue)->articleDao.findOneBy("ean", fieldValue) == null);
		articleForm.add(eanFieldContainer);
		
		if (!article.containsUtensil()) {
			var ingredientListContainer = new ListFieldContainer("Ingrédient:", this);
			var listModel = ingredientListContainer.getListModel();
			ingredientDao.findAll().forEach(ing -> {
				listModel.addElement(ing.getName());				
			});
			ingredientListContainer.bindSelection(
				articleFinal.getProduct().getName(),
				(s)->articleFinal.setProduct(ingredientDao.findOneBy("name",s)));
			articleForm.add(ingredientListContainer);
		}

		var quantityFieldContainer = new FieldContainer("Quantité", this);
		quantityFieldContainer.bind(
			String.valueOf(articleFinal.getQuantity()),
			(s)->articleFinal.setQuantity(Double.parseDouble(s)));
		articleForm.add(quantityFieldContainer);

		var weightFieldContainer = new FieldContainer("Poids", this);
		weightFieldContainer.bind(
			String.valueOf(articleFinal.getWeight()),
			(s)->articleFinal.setWeight(Double.parseDouble(s)));
		articleForm.add(weightFieldContainer);

		var unitListContainer = new ListFieldContainer("Unité:", this);
		var unitDao = new UnitDao();
		unitDao.findAll().forEach(u -> {
			unitListContainer.getListModel().addElement(u.getName());				
		});
		unitListContainer.bindSelection(
			articleFinal.getUnit().getName(),
			(s)->articleFinal.setUnit(unitDao.findOneBy("name",s)));
		articleForm.add(unitListContainer);
		
		var packagingListContainer = new ListFieldContainer("Emballage:", this);
		var packagingDao = new PackagingDao();
		packagingDao.findAll().forEach(p -> {
			packagingListContainer.getListModel().addElement(p.getName());				
		});
		packagingListContainer.bindSelection(
			articleFinal.getPackaging().getName(),
			(s)->articleFinal.setPackaging(packagingDao.findOneBy("name",s)));
		articleForm.add(packagingListContainer);
		
		articleForm.add(Box.createVerticalGlue());

		
		
		this.add(articleForm, BorderLayout.WEST);

		this.buttonValidate.addActionListener( e->{
			try{
				(new ProductDao()).saveOrUpdate(articleFinal.getProduct());
				articleDao.saveOrUpdate(articleFinal);
				HibernateUtil.getSession().getTransaction().commit();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
				    "Veuillez vérifier les champs en orange.",
				    "Paramétres invalides",
				    JOptionPane.WARNING_MESSAGE);
			}
			this.mainControl.getArticleDirectory().getEntityList().refresh();
			this.mainControl.remove(this);
			this.mainControl.setSelectedComponent(this.mainControl.getArticleDirectory());
		});
		
		this.buttonCancel.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getArticleDirectory());
		});
	}
}