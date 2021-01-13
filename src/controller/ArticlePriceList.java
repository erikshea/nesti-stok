package controller;

import javax.swing.JButton;
import javax.swing.JRadioButton;

public class ArticlePriceList extends BasePriceList {

	public ArticlePriceList() {
		super();

	}

	@Override
	public String getTitle() {
		return "Articles proposés";
	}

	@Override
	public Object[][] getTableModelData() {
		return new Object[][] { { "Couteaux", "8", new JButton("-")},
				{ "Fouet", "9", new JButton("-")}, 
				{ "Robot", "6", new JButton("-")}, 
				{ "Blabla", "9", new JButton("-")}, 
				{ "Article test", "29", new JButton("-")}, 
				{ "Article bis", "42", new JButton("-")} };
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Article", "Prix de vente", ""};
	}

}
