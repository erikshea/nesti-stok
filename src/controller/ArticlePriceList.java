package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class ArticlePriceList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997250030218950222L;

	public ArticlePriceList() {
		
		// right of the screen, price's and supplier's informations
		
		this.setPreferredSize(new Dimension(500, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		var titlePriceList = new JLabel("Liste de prix");
		this.add(titlePriceList);
		
		
		
		
		  String[] columnNames = {"First Name",
                  "Last Name",
                  "Sport",
                  "# of Years",
                  "Vegetarian"};
		
		  
		  Object[][] data = {
				    {"Kathy", "Smith",
				     "Snowboarding", new Integer(5), new Boolean(false)},
				    {"John", "Doe",
				     "Rowing", new Integer(3), new Boolean(true)},
				    {"Sue", "Black",
				     "Knitting", new Integer(2), new Boolean(false)},
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)},
				    {"Joe", "Brown",
				     "Pool", new Integer(10), new Boolean(false)}
				};
		
		  
		  var listPrice = new JTable(data,columnNames);
		  
		  listPrice.setPreferredScrollableViewportSize(new Dimension(500, 70));
		  listPrice.setFillsViewportHeight(true);
		  
		// entre () on met ce qui doit scroller
		// var scrollPriceList = new JScrollPane();
	
		  
		  
		this.add(listPrice);
				
	}
	
}
