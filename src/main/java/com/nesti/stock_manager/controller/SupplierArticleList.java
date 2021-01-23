package com.nesti.stock_manager.controller;

import javax.swing.DefaultListModel;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.Supplier;

@SuppressWarnings("serial")
public class SupplierArticleList extends BasePriceList<Supplier> {
	
	public SupplierArticleList(Supplier s) {
		super(s);

		refreshList();
	}
	
	@Override
	protected void addNewPriceContainer(){
		super.addNewPriceContainer();
		addButton.addActionListener(e->{
			var article = (new ArticleDao()).findOneBy("name", newPriceList.getSelectedValue());
			var offer = new Offer();
			offer.setArticle(article);
			try {
				offer.setPrice(Double.parseDouble(newPriceField.getText()));
				var currentOffers = entity.getCurrentOffers();
				
				long offerTimeForSupplier = 0;
				if ( currentOffers.containsKey(article) ) {
					offerTimeForSupplier = currentOffers.get(article).getStartDate().getTime();
				}
		
				if ( offer.getStartDate().getTime() - offerTimeForSupplier > 1000) {
					entity.addOffer(offer);
					refreshList();
				}
			} catch (Exception ex) {}
		});
		var articles = (new ArticleDao()).findAll(BaseDao.ACTIVE);
		var listModel = (DefaultListModel<Object>)newPriceList.getModel();
		articles.forEach(a -> listModel.addElement(a.getName()));
		newPriceList.setSelectedIndex(0);
	}
	
	@Override
	protected void onRowDelete(int modelRow) {
		var article = (new ArticleDao()).findOneBy("name", this.table.getValueAt(modelRow, 1));
		var offer = entity.getCurrentOffers().get(article);
		offer.setPrice(null);
		if (	article.getDefaultSupplier() != null
			&&  article.getDefaultSupplier().equals(entity)) {
			article.setDefaultSupplier(null);
		}
		
		refreshList();
	}

	@Override
	protected void refreshList() {
		super.refreshList();
		
		entity.getCurrentOffers().values().forEach( o->{
			this.addRowData(new Object[] { o.getArticle().getCode(), o.getArticle().getName(), o.getPrice()} );
		});
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