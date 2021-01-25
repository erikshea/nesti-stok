package com.nesti.stock_manager.controller;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.nesti.stock_manager.form.FieldContainer;

public class TableSearchBar extends JPanel{
	private static final long serialVersionUID = 1L;
	private Object[] rowData;
	private JTable table;
	
	public TableSearchBar(JTable t) {
		table = t;
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
//		var tableModel = (DefaultTableModel)table.getModel();
//
//		this.setPreferredSize(new Dimension(1000,80));
//		this.setMaximumSize(new Dimension(1000,80));
//		table.getModel().addTableModelListener(e->{
//			if (rowData == null && tableModel.getRowCount()>0) {
//				rowData = tableModel.getDataVector().elementAt(0).toArray();
//				buildSearchFields();
//			}
//		});
	}
	
	public void buildSearchFields() {
		for ( var i = 0; i < table.getColumnCount(); i++) {
			System.out.println(table.getColumnName(i));
			var field = new FieldContainer(table.getColumnName(i));
			field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
			field.setMaximumSize( new Dimension(200,Short.MAX_VALUE) );
			field.getLabel().setMaximumSize( new Dimension(Short.MAX_VALUE,15) );
			field.getField().setMinimumSize( new Dimension(0,30) );
			this.add(field);
		}
	}
	
}
