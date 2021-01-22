package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.ProductDao;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Utensil;
import org.apache.commons.lang3.tuple.Pair;
@SuppressWarnings("serial")
public class ArticleList extends BaseList<Article> {

	public ArticleList(MainWindowControl c) {
		super(c);

		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();

		addToCartField.setMaximumSize(new Dimension(100, 30));
		addToCartField.setPreferredSize(new Dimension(100, 0));

		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);

		this.buttonBar.add(addToCart, 4);

		refresh();
	}
	
	@Override
	public String getTitle() {
		return "Liste d'article";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Description", "Code", "Fournisseur par défaut", "Prix d'achat", "Stock",
				"PV Conseillé" };
	}

	@Override
	public void setUpButtonListeners() {
		super.setUpButtonListeners();
		this.buttonModify.addActionListener(e -> {
			var code = this.table.getValueAt(this.table.getSelectedRow(), 1);

			var a = (new ArticleDao()).findOneBy("code", code);

			this.mainController.addCloseableTab("Article: " + a.getName(),
					new ArticleInformation(this.mainController, a));
		});

		final JPopupMenu popup = new JPopupMenu();
		var addIngredient = new JMenuItem(new AbstractAction("Ingrédient") {
			public void actionPerformed(ActionEvent e) {
				Article article = Article.createEmpty();
				article.setProduct(new Ingredient());
				mainController.addCloseableTab("Nouvel Article", new ArticleInformation(mainController, article));
			}
		});

		var addUtensil = new JMenuItem(new AbstractAction("Ustensile") {
			public void actionPerformed(ActionEvent e) {
				Article article = Article.createEmpty();
				article.setProduct(new Utensil());
				mainController.addCloseableTab("Nouvel Article", new ArticleInformation(mainController, article));
			}
		});

		popup.add(addIngredient);
		popup.add(addUtensil);

		this.buttonAdd.addActionListener(e -> { // TODO
			popup.show((Component) e.getSource(), 0, 0);
	
		});

	}

	@Override
	public void deleteRow(int rowIndex) {
		var articleDao = new ArticleDao();
		var article = articleDao.findOneBy("code", this.table.getValueAt(rowIndex, 1));
		
		articleDao.delete(article);
	}

	@Override
	public void addRow(Article entity) {
		this.addRowData(new Object[] { entity.getName(), entity.getCode(), "", 0, entity.getStock(), 0 });
	}
}