package com.nesti.stock_manager.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.security.InvalidParameterException;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

//import org.graalvm.compiler.core.common.spi.JavaConstantFieldProvider_OptionDescriptors;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Utensil;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.UnavailableArticleException;

/**
 * Shows all articles, and provides buttons to manipulate them and add them to the cart
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class ArticleDirectory extends BaseDirectory<Article> {

	public ArticleDirectory(MainWindowControl c) {
		super(c);

		
		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		
		
		var addToCartQuantity = new JTextField();

		addToCartQuantity.setMaximumSize(new Dimension(100, 30));
		addToCartQuantity.setPreferredSize(new Dimension(100, 0));
		addToCart.add((Box.createRigidArea(new Dimension(50, 0))));
	//	addToCart.setBackground(AppAppereance.LIGHT_COLOR);
		addToCart.add(addToCartQuantity);
		addToCart.add((Box.createRigidArea(new Dimension(5,0)))); 
		
		var addToCartButton = new JButton("Ajouter au panier");
	//	addToCartButton.setBackground(AppAppereance.HIGHLIGHT);
		addToCartButton.setForeground(new Color(255, 255, 255));
		addToCartButton.setPreferredSize(AppAppereance.LARGE_BUTTON);
		addToCartButton.setMaximumSize(AppAppereance.LARGE_BUTTON);

		addToCartButton.setEnabled(false);
		addToCart.add(addToCartButton);
		this.buttonBar.add(addToCart, 7);

		addToCartButton.addActionListener(e -> {
			for (var rowIndex : this.table.getSelectedRows()) { // loop through each selected row of table
				var code = this.table.getValueAt(rowIndex, 1);	
				var article = (new ArticleDao()).findOneBy("code", code); // get article code, find corresponding article
				try { // try to add to cart
					this.mainController.getShoppingCart().addArticle(article, addToCartQuantity.getText());
				} catch (InvalidParameterException ex) {
					JOptionPane.showMessageDialog(this,
							"Vous devez saisir un chiffre correspondant à la quantité souhaitée");
				} catch (UnavailableArticleException ec) {
					JOptionPane.showMessageDialog(this,
							"Un ou plusieurs article(s) n'a pas été ajouté au panier car il est indisponible.");
				}
			}

		});
		
		// Can only add to cart if a row is selected
		this.table.getSelectionModel().addListSelectionListener(e -> {
			addToCartButton.setEnabled(this.table.getSelectedRowCount() > 0);
		});
	}

	
	@Override
	public String getTitle() {
		return "Articles";
	}

	@Override
	public Object[] getTableModelColumnNames() {
		return new Object[] { "Description", "Code", "Fournisseur par défaut", "Prix d'achat", "Stock",
				"PV Conseillé" };
	}

	@Override
	public void setUpButtonBarListeners() {
		super.setUpButtonBarListeners();
		// "Modify" button action
		this.buttonModify.addActionListener(e -> {
			var code = this.table.getValueAt(this.table.getSelectedRow(), 1);
			// find article from code
			var a = (new ArticleDao()).findOneBy("code", code);
			// create new information tab, passing it the selected article
			this.mainController.getMainPane().addCloseableTab(new ArticleInformation(this.mainController, a));
		});

		final JPopupMenu popup = new JPopupMenu(); // select ingredient or utensil product on new article creation
		// New ingredient popup menu action
		var addIngredient = new JMenuItem(new AbstractAction("Ingrédient") {
			public void actionPerformed(ActionEvent e) {
				Article article = Article.createEmpty(); // empty has default packaging and unit
				article.setProduct(new Ingredient()); // TODO: just set default ingredient?
				mainController.getMainPane().addCloseableTab(new ArticleInformation(mainController, article));
			}
		});
		// New ustensil popup menu action
		var addUtensil = new JMenuItem(new AbstractAction("Ustensile") {
			public void actionPerformed(ActionEvent e) {
				Article article = Article.createEmpty();
				article.setProduct(new Utensil()); // create new utensil to associate with article
				mainController.getMainPane().addCloseableTab(new ArticleInformation(mainController, article));
			}
		});

		popup.add(addIngredient);
		popup.add(addUtensil);
		// "New" button action
		this.buttonAdd.addActionListener(e -> {
			popup.show((Component) e.getSource(), 0, 0); // Show popup above button on click
		});
		
		// "Duplicate" button action
		this.buttonDuplicate.addActionListener(e -> {
			var code = this.table.getValueAt(this.table.getSelectedRow(), 1);
			var a = (new ArticleDao()).findOneBy("code", code);
			mainController.getMainPane().addCloseableTab(new ArticleInformation(mainController, a.duplicate()));
		});

	}


	@Override
	public void createTable() {
		super.createTable();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
	}


	@Override
	public void refreshTab() {
		super.refreshTab();
	}

	@Override
	public void deleteRow(int rowIndex) {
		var articleDao = new ArticleDao();
		var article = articleDao.findOneBy("code", this.table.getValueAt(rowIndex, 1));
		article.setFlag(BaseDao.DELETED);
	}

	@Override
	public void addRow(Article entity) {
		var defaultSupplierName = "";
		// Get default supplier name
		if (entity.getDefaultSupplier() != null) {
			defaultSupplierName = entity.getDefaultSupplier().getName();
		}

		Double purchasePrice = null;
		Double sellingPrice = 0.0;
		if (entity.getCurrentOffers().get(entity.getDefaultSupplier()) != null) {
			var offer = entity.getCurrentOffers().get(entity.getDefaultSupplier()); // TODO: change to last valid
			purchasePrice = offer.getPrice();
			sellingPrice = (double) Math.round((offer.getPrice() * 1.2) * 100) / 100;

		}
		this.addRowData(new Object[] { entity.getName(), entity.getCode(), defaultSupplierName, purchasePrice,
				entity.getStock(), sellingPrice });

	}
}
