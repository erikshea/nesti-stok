package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ArticlePriceList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997250030218950222L;

	public ArticlePriceList() {
		
		
		// right of the screen, price's and supplier's informations
		
		var priceList = new JPanel();
		priceList.setPreferredSize(new Dimension(500, 0));
		priceList.setLayout(new BoxLayout(priceList, BoxLayout.Y_AXIS));
		
		var titlePriceList = new JLabel("Liste de prix");
		priceList.add(titlePriceList);
		
		this.add(priceList);
		
	}
	
	

	
	
	
	
}
