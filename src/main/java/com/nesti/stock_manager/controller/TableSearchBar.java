package com.nesti.stock_manager.controller;

import java.awt.Color;
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

public class TableSearchBar extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private Map<Integer, RowFilter<TableModel,Integer>> filters;
	
	public TableSearchBar(JTable t) {
		table = t;
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(2000,80));
		this.setMaximumSize(new Dimension(2000,80));
		this.setBackground(new AppAppereance().LIGHT_COLOR);
		table.getModel().addTableModelListener(e->{
			if (filters == null && table.getModel().getRowCount()>0) {
				buildSearchFields();
			}
		});
	}
	
	public void buildSearchFields() {
		filters = new HashMap<>();
		for ( var i = 0; i < table.getColumnCount(); i++) {
			final var colIndex = i;
			var fieldContainer = new FieldContainer(table.getColumnName(i));
			fieldContainer.setLayout(new BoxLayout(fieldContainer, BoxLayout.Y_AXIS));
			fieldContainer.setMaximumSize( new Dimension(200,Short.MAX_VALUE) );
			fieldContainer.getLabel().setMaximumSize( new Dimension(Short.MAX_VALUE,50) );
			fieldContainer.getField().setMinimumSize( new Dimension(0,30) );
			
			this.add(fieldContainer);
			fieldContainer.setBackground(AppAppereance.LIGHT_COLOR);
			fieldContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			
			fieldContainer.bind("", (s)->updateFilter(s, colIndex), true);
		}
	}

    protected void updateFilter(String s, int colIndex) {
        if (s.length() == 0) {
        	filters.remove(colIndex);
        } else {
        	filters.put(colIndex, RowFilter.regexFilter("(?i)" + s, colIndex));
        }
        
		@SuppressWarnings("unchecked")
		TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();
		if ( sorter != null ) {
			sorter.setRowFilter(RowFilter.andFilter(filters.values()));
		}
    }
}
