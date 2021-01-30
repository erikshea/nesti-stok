package com.nesti.stock_manager.cells;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;

import com.nesti.stock_manager.model.Supplier;

/**
 * Cell editor for a Combo Box
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class ComboBoxCellEditor extends DefaultCellEditor implements ItemListener {
private static final long serialVersionUID = 1L;
	private JComboBox<Supplier> comboBox;

	public ComboBoxCellEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	@SuppressWarnings("unchecked")
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
			int column) {
		if (value == null)
			return null;
		comboBox = (JComboBox<Supplier>) value;
		comboBox.addItemListener(this);
		return (Component) value;
	}

	public Object getCellEditorValue() {
		comboBox.removeItemListener(this);
		return comboBox;
	}

	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}