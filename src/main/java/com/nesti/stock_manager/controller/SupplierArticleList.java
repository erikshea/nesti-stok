package com.nesti.stock_manager.controller;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.Supplier;
import com.nesti.stock_manager.util.SwingUtil;

/**
 * Shows a table containing all articles offered by a supplier, and controls to change those articles and offers
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class SupplierArticleList extends BasePriceList<Supplier> {
	
	public SupplierArticleList(Supplier s) {
		super(s);

		refreshList();
	}
	
	
	
	@Override
	public void addPriceTableContainer() {
		super.addPriceTableContainer();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);


	}
	
	
	@Override
	protected void addNewPriceContainer(){
		super.addNewPriceContainer();
		addButton.addActionListener(e->{
			// Get article entity from selected value in list
			var article = (new ArticleDao()).findOneBy("name", newPriceList.getSelectedValue());
			// Create offer for selected article
			var offer = new Offer();
			offer.setArticle(article);
			try {
				// Set price as entered in text field
				offer.setPrice(Double.parseDouble(newPriceField.getText()));
				var currentOffers = entity.getCurrentOffers();
				
				long offerTimeForSupplier = 0;
				// Get last offer time for selected article (if exists) 
				if ( currentOffers.containsKey(article) ) {
					offerTimeForSupplier = currentOffers.get(article).getStartDate().getTime();
				}
				// Don't add a new price to supplier if last one added for article was less than 1 second ago 
				if ( offer.getStartDate().getTime() - offerTimeForSupplier > 1000) {
					entity.addOffer(offer);
					refreshList();
				}
			} catch (Exception ex) {}
		});
		var articles = (new ArticleDao()).findAll(BaseDao.ACTIVE);
		var listModel = (DefaultListModel<Object>)newPriceList.getModel();
		// populate list with all articles
		articles.forEach(a -> listModel.addElement(a.getName()));
		newPriceList.setSelectedIndex(0); // first item selected by default
	}
	


	@Override
	protected void refreshList() {
		super.refreshList();
		// Fill price list table with all current offers for that supplier
		entity.getCurrentOffers().values().forEach( o->{
			this.addRowData(new Object[] { o.getArticle().getCode(), o.getArticle().getName(), o.getPrice()} );
		});
		// Set up column sorting (logic will only run once)
		SwingUtil.setUpTableAutoSort(table);
	}
	
	@Override
	protected void onRowDelete(int modelRow) {
		// Get article from its name in table row
		var article = (new ArticleDao()).findOneBy("name", this.table.getValueAt(modelRow, 1));
		// find corresponding offer
		var offer = entity.getCurrentOffers().get(article);
		
		// null price signals offer is no longer valid
		offer.setPrice(null); // TODO: create new null offer instead?
		
		// if supplier was default, unset
		if (	article.getDefaultSupplier() != null
			&&  article.getDefaultSupplier().equals(entity)) {
			article.setDefaultSupplier(null);
		}
		
		refreshList();
	}
	
	@Override
	public String getTitle() {
		return "Liste d'articles";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Code article", "Désignation", "Prix de vente", "Suppression" };
	}
}