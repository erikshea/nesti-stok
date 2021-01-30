package com.nesti.stock_manager.cells;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *	Textfield cell editor
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class TextFieldCellEditor extends DefaultCellEditor implements ItemListener {
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public TextFieldCellEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	@SuppressWarnings("unchecked")
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
			int column) {
		if (value == null) {
			return null;
		}
		textField = (JTextField) value;
		return (Component) value;
	}

	public Object getCellEditorValue() {
		return textField;
	}

	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}