package com.nesti.stock_manager.controller;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.util.AppAppereance;

/**
 * Sets up a search bar to search all fields of a table
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class TableSearchBar extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private Map<Integer, RowFilter<TableModel,Integer>> filters; 
	
	public TableSearchBar(JTable t) {
		table = t;
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(1600,80));
		this.setMaximumSize(new Dimension(1600,80));
		this.setBackground(AppAppereance.LIGHT_COLOR);
		
		// When a row is added to table, build search bar
		table.getModel().addTableModelListener(e->{
			// Only build it once (before filters get set) when after the first row appears
			if (filters == null && table.getModel().getRowCount()>0) { // Todo: filters can be moved to var inside method
				buildSearchFields();
			}
		});
	}
	
	public void buildSearchFields() {
		filters = new HashMap<>(); // Each field has its individual table filter
		for ( var i = 0; i < table.getColumnCount(); i++) { // Loop through each column
			if ( table.getColumnName(i) != "Suppression") {
				final var colIndex = i;
				// create new field container with column name as label text
				var fieldContainer = new FieldContainer(table.getColumnName(i));
				
				fieldContainer.setLayout(new BoxLayout(fieldContainer, BoxLayout.Y_AXIS));
				fieldContainer.setMaximumSize( new Dimension(200,Short.MAX_VALUE) );
				fieldContainer.getLabel().setMaximumSize( new Dimension(Short.MAX_VALUE,50) );
				fieldContainer.getField().setMinimumSize( new Dimension(0,30) );
				
				this.add(fieldContainer);
				fieldContainer.setBackground(AppAppereance.LIGHT_COLOR);
				fieldContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				
				fieldContainer.bind(
					"", // Field empty initially
					(s)->updateFilter(s, colIndex), // When field changes, update table filters to reflect it
					true // allow empty field (no filter)
				); 
			}
		}
	}

    /**
     * Add a row filter to table
     * @param s text to filter rows with
     * @param colIndex column index for which filter applies
     */
    protected void updateFilter(String s, int colIndex) {
        if (s.length() == 0) { // If field empty
        	filters.remove(colIndex); // remove filters from list of table filters
        } else {
        	// new filter: row corresponding to field column must contain field text
        	filters.put(colIndex, RowFilter.regexFilter("(?i)" + s, colIndex));
        }
        
		@SuppressWarnings("unchecked")
		TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();
		if ( sorter != null ) {
			// associate the "and" combination of all filters with table 
			sorter.setRowFilter(RowFilter.andFilter(filters.values()));
		}
    }
}
