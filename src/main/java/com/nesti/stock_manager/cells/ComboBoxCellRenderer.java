package com.nesti.stock_manager.cells;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Cell renderer for a Combo Box
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class ComboBoxCellRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value == null)
			return null;
		return (Component) value;
	}
}