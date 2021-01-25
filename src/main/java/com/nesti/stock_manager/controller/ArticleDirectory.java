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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

//import org.graalvm.compiler.core.common.spi.JavaConstantFieldProvider_OptionDescriptors;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Ingredient;
import com.nesti.stock_manager.model.Utensil;

@SuppressWarnings("serial")
public class ArticleDirectory extends BaseDirectory<Article> {

	protected JButton addToCartButton;
	protected JTextField addToCartQuantity;
	
	public ArticleDirectory(MainWindowControl c) {
		super(c);
		
		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		addToCartQuantity = new JTextField();

		addToCartQuantity.setMaximumSize(new Dimension(100, 30));
		addToCartQuantity.setPreferredSize(new Dimension(100, 0));

		addToCart.add(addToCartQuantity);
		addToCartButton = new JButton("Ajouter au panier");
		addToCartButton.setEnabled(false);
		addToCart.add(addToCartButton);
		this.buttonBar.add(addToCart, 8);
		
		addToCartButton.addActionListener(e -> {
			if (!isNumeric(addToCartQuantity.getText()) || Double.parseDouble(addToCartQuantity.getText()) < 0) {
				JOptionPane.showMessageDialog(this, "Vous devez saisir une quantité à ajouter au panier");
			} else if (defaultSupplierExistsForAll(this.table.getSelectedRows())) {
				for (var rowIndex : this.table.getSelectedRows()) {
					var code = this.table.getValueAt(rowIndex, 1);
					var article = (new ArticleDao()).findOneBy("code", code);

					var quantity = (int) Double.parseDouble(addToCartQuantity.getText());

					this.mainController.getShoppingCart().addArticle(article, quantity);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Un article n'est pas disponible, merci de vérifier la sélection");
			}
		});
	}

	@Override
	public String getTitle() {
		return "Articles";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Description", "Code", "Fournisseur par défaut", "Prix d'achat", "Stock",
				"PV Conseillé" };
	}

	@Override
	public void setUpButtonBarListeners() {
		super.setUpButtonBarListeners();
		this.buttonModify.addActionListener(e -> {
			var code = this.table.getValueAt(this.table.getSelectedRow(), 1);

			var a = (new ArticleDao()).findOneBy("code", code);

			this.mainController.getMainPane().addCloseableTab(new ArticleInformation(this.mainController, a));
		});

		final JPopupMenu popup = new JPopupMenu();
		var addIngredient = new JMenuItem(new AbstractAction("Ingrédient") {
			public void actionPerformed(ActionEvent e) {
				Article article = Article.createEmpty();
				article.setProduct(new Ingredient());
				mainController.getMainPane().addCloseableTab(new ArticleInformation(mainController, article));
			}
		});

		var addUtensil = new JMenuItem(new AbstractAction("Ustensile") {
			public void actionPerformed(ActionEvent e) {
				Article article = Article.createEmpty();
				article.setProduct(new Utensil());
				mainController.getMainPane().addCloseableTab(new ArticleInformation(mainController, article));
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
			Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	public boolean defaultSupplierExistsForAll(int[] rowIndexes) {
		var selectionIsValid = true;
		for (var rowIndex : rowIndexes) {
			var defaultSupplierName = this.table.getValueAt(rowIndex, 2);
			if (defaultSupplierName.equals("")) {
				selectionIsValid = false;
			}
		}
		return selectionIsValid;
	}

	@Override
	public void createTable() {
		super.createTable();
		this.table.getSelectionModel().addListSelectionListener(e -> {
			addToCartButton.setEnabled(this.table.getSelectedRowCount() > 0);
		});
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());



//	    var numberComparator = new Comparator<String>() {
//			@Override
//			public int compare(String o1, String o2) {
//				try {
//					var v1 = Double.parseDouble(o1) ;
//					var v2 = Double.parseDouble(o2) ;
//
//					
//				}catch (Exception e) {
//					
//				}
//				// TODO Auto-generated method stub
//				return 0;
//			}
//	    };
//	    sorter.setComparator(1, numberComparator);
//	    sorter.setComparator(2, numberComparator);
//	    sorter.setComparator(3, numberComparator);
//	    sorter.setComparator(4, numberComparator);
//	    sorter.setComparator(5, numberComparator);
//	    table.setRowSorter(sorter);
		
	}

	
	@SuppressWarnings("unchecked")
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
		if (entity.getDefaultSupplier() != null) {
			defaultSupplierName = entity.getDefaultSupplier().getName();
		}

		Double purchasePrice = null;
		Double sellingPrice = 0.0;
		if (entity.getCurrentOffers().get(entity.getDefaultSupplier()) != null) {
			var offer = entity.getCurrentOffers().get(entity.getDefaultSupplier());
			purchasePrice = offer.getPrice();
			sellingPrice = (double) Math.round((offer.getPrice() * 1.2) * 100) / 100;

		}
		this.addRowData(new Object[] { entity.getName(), entity.getCode(), defaultSupplierName, purchasePrice,
				entity.getStock(), sellingPrice });

	}
}
