package com.nesti.stock_manager.util;

import java.awt.Component;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Emmanuelle Gay, Erik Shea
 * Swing-related convenience methods
 */
public class SwingUtil {

	/**
	 * Sets up column sorting for a table when a column a clicked. Only does it once per table .
	 * @param table
	 */
	public static void setUpTableAutoSort(JTable table) {
		var model = (DefaultTableModel) table.getModel();
		// If we did not already add a row sorter, and the first row has been added (will thus only run once per table)
		if (table.getRowSorter() == null && model.getRowCount()>0) {
			
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
			
			if (table.getRowCount()>0) {
				for (var i=0;i<model.getDataVector().elementAt(0).size(); i++) { // Loop through the data (array of Objects) of a single row
					sorter.setComparator(i, (Object v1, Object v2) -> { // Need a comparator between two objects to be able to sort table
							// Start assuming v1 smaller than v2 (we don't have to consider equals case for the sorting algorithm)
							var result = -1; 
		
					    	// Look at column data object's class, and use appropriate comparator function
					    	if ( v1 instanceof String) {
					    		result = v1.toString().compareTo(v2.toString());
					    	} else if ( (v1 instanceof Integer &&  (Integer)v1 > (Integer)v2 )
					    			||  (v1 instanceof Double &&  (Double)v1 > (Double)v2 )
					    			||  (v1 instanceof Date &&  ((Date)v1).after((Date)v2)) ){
						    	result = 1; // if one of these checks out, v1 is considered larger than v2 for row sorting purposes
					    	}
					    	
					    	return result;
					    }
					);
				}
			}
			
			table.setRowSorter(sorter);
		};
	}
	
	/**
	 * move a frame or root application component to the center of the screen
	 * @param c component to center
	 */
	public static void centerFrame (Component c) {
		// location is set from top left corner
		int centeredWindowX = (int) (Toolkit.getDefaultToolkit().getScreenSize().width - c.getPreferredSize().getWidth() )/2;
		int centeredWindowY = (int) (Toolkit.getDefaultToolkit().getScreenSize().height - c.getPreferredSize().getHeight() )/2;
		c.setLocation(centeredWindowX,centeredWindowY);
	}
}
