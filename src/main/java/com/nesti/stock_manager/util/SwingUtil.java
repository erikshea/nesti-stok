package com.nesti.stock_manager.util;

import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SwingUtil {

	public static void setUpTableAutoSort(JTable table) {
		var model = (DefaultTableModel) table.getModel();
		if (table.getRowSorter() == null && model.getRowCount()>0) {
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
			if (table.getRowCount()>0) {
				for (var i=0;i<model.getDataVector().elementAt(0).size(); i++) {
					sorter.setComparator(i, (Object v1, Object v2) -> {
					    	var result = -1;
		
					    	if ( v1 instanceof String) {
					    		result = v1.toString().compareTo(v2.toString());
					    	} else if ( (v1 instanceof Integer &&  (Integer)v1 > (Integer)v2 )
					    			||  (v1 instanceof Double &&  (Double)v1 > (Double)v2 )
					    			||  (v1 instanceof Date &&  ((Date)v1).after((Date)v2)) ){
						    	result = 1;
					    	}
					    	
					    	return result;
					    }
					);
				}
			}
			
			table.setRowSorter(sorter);
		};
	}
}
