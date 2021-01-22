
package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.nesti.stock_manager.controller.ArticleSupplierList.RadioButtonEditor;


public class BasePriceList extends JPanel {

	private static final long serialVersionUID = -1997250030218950222L;
	protected DefaultTableModel tableModel;
	protected JTable table;
	protected JButton addButton;
	protected ButtonGroup radioGroup;

	public BasePriceList(Object o) {

		// right of the screen, price's and supplier's informations
		this.setPreferredSize(new Dimension(800, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	}

	public String getTitle() {
		return "";
	}

	public Object[] getTableModelColumns() {
		return new Object[] {};
	}

	public JButton getAddButton() {
		return addButton;
	}

	@SuppressWarnings("serial")
	public JTable getPriceTable() {

		this.tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableModelColumns());

		this.table = new JTable(tableModel) {
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};

		this.table.getColumn("Suppression").setCellRenderer(new JTableButtonRenderer());	
		this.table.getColumn("Suppression").setCellEditor(new JButtonEditor(new JCheckBox()));	
		// manque la partie pour �diter quand on clique sur le
		// bouton!!!! a ajouter
		return this.table;
	}

	public void addRowData(Object[] data) {
		var tabData = new ArrayList<Object>(Arrays.asList(data));
		tabData.add(new JButton("-"));
		this.tableModel.addRow(tabData.toArray());
	}

	public void addRowData(Object[] data, boolean isDefault) {
		this.addRowData(data);
		var radioButton = (JRadioButton) this.table.getValueAt(this.tableModel.getRowCount() - 1, 0);
		radioButton.setSelected(isDefault);
	}

}

	class JTableButtonRenderer implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value == null) {
				return null;
			}
				
			return (Component) value;
		}
	}

//click radio button
	@SuppressWarnings("serial")
	class JButtonEditor extends DefaultCellEditor implements ItemListener {
		private JButton button;

		public JButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null) {
				return null;
			}
			button = (JButton) value;
			button.addItemListener(this);
			return (Component) value;
		}

		public Object getCellEditorValue() {
			button.removeItemListener(this);
			return button;
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
	}
