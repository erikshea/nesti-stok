package controller;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ArticlePriceList extends BasePriceList {

	public ArticlePriceList() {
		super();

		this.addRowData(new Object[] {"Couteaux", "8", new JButton("-") });
		this.addRowData(new Object[] {"Fouet", "9", new JButton("-") });
		this.addRowData(new Object[] {"Robot", "6", new JButton("-") });
	};

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