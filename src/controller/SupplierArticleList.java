package controller;

import dao.OfferDao;

@SuppressWarnings("serial")
public class SupplierArticleList extends ArticlePriceList {

	public SupplierArticleList(Object listOwner) {
		super();
		
		var dao = new OfferDao(); 
		var articlePrice = dao.findAll();
		articlePrice.forEach(ap->{
			this.addRowData(new Object[] {ap.getArticle().getCode(), ap.getArticle().getName(),ap.getPrice()});
        });
	
	}
	@Override
	public String getTitle() {
		return "Liste d'article";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Code article", "Désignation", "Prix de vente", "Suppression" };
	}

	/**
	 * add a row in the list
	 */
	@Override
	public void addRowData(Object[] data) {
		super.addRowData(data);
	}

}