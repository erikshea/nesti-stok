package controller;

import javax.swing.JButton;

import dao.OfferDao;

@SuppressWarnings("serial")
public class ArticlePriceList extends BasePriceList {

	public ArticlePriceList() {
		super();
		
		var dao = new OfferDao(); 
		var articlePrice = dao.findAll();
		articlePrice.forEach(ap->{
			this.addRowData(new Object[] {ap.getArticle().getName(),ap.getPrice()});
        });
	
	}
	@Override
	public String getTitle() {
		return "Liste de prix";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Article", "Prix de vente", "Suppression" };
	}

	/**
	 * add a row in the list
	 */
	@Override
	public void addRowData(Object[] data) {
		super.addRowData(data);
	}

}