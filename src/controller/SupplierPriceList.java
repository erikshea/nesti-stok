package controller;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class SupplierPriceList extends BasePriceList{

	
	public SupplierPriceList() {
		super();
		
		var titlePriceList = new JLabel("Liste de prix");
		this.add(titlePriceList);
		
	}
}
