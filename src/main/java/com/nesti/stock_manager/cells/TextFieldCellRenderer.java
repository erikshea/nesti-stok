package com.nesti.stock_manager.cells;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Textfield cell renderer
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class TextFieldCellRenderer  implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		if (value == null)
			return null;
		return (Component) value;
	}
}