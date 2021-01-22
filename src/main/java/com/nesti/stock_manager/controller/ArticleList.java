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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//import org.graalvm.compiler.core.common.spi.JavaConstantFieldProvider_OptionDescriptors;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.ProductDao;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Utensil;

@SuppressWarnings("serial")
public class ArticleList extends BaseList<Article> {

	protected JButton addToCartButton;

	public ArticleList(MainWindowControl c) {
		super(c);

		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();

		addToCartField.setMaximumSize(new Dimension(100, 30));
		addToCartField.setPreferredSize(new Dimension(100, 0));

		addToCart.add(addToCartField);
		addToCartButton = new JButton("Ajouter au panier");
		addToCartButton.setEnabled(false);
		addToCartButton.addActionListener(e -> {

			if (!isNumeric(addToCartField.getText()) || Double.parseDouble(addToCartField.getText()) < 0) {
				JOptionPane.showMessageDialog(this, "Vous devez saisir une quantité à ajouter au panier");
			} else {
				for (var rowIndex : this.table.getSelectedRows()) {
					var code = this.table.getValueAt(rowIndex, 1);
					var article = (new ArticleDao()).findOneBy("code", code);

					var quantity = (int)Double.parseDouble(addToCartField.getText());

					System.out.println(article.getCode() + " qte : " + quantity);
					this.mainController.getShoppingCart().addArticle(article, quantity);
				}
			}

		});

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

	public static boolean isNumeric(String strNum) {
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	@Override
	public void createTable() {
		super.createTable();
		this.table.getSelectionModel().addListSelectionListener(e -> {
			addToCartButton.setEnabled(this.table.getSelectedRowCount() > 0);
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
		var defaultSupplierName = "";
		if (entity.getDefaultSupplier() != null) {
			defaultSupplierName = entity.getDefaultSupplier().getName();
		}

		var purchasePrice = "hors stock";
		Double sellingPrice = 0.0;
		if (entity.getCurrentOffers().get(entity.getDefaultSupplier()) != null) {
			var offer = entity.getCurrentOffers().get(entity.getDefaultSupplier());
			purchasePrice = String.valueOf(offer.getPrice());
			sellingPrice = offer.getPrice() * 1.2;
		}
		this.addRowData(new Object[] { entity.getName(), entity.getCode(), defaultSupplierName, purchasePrice,
				entity.getStock(), sellingPrice });

	}
}
