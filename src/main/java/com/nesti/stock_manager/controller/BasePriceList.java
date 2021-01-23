
package com.nesti.stock_manager.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import com.nesti.stock_manager.form.ButtonColumn;
import com.nesti.stock_manager.model.BaseEntity;


public abstract class BasePriceList<E> extends JPanel {
	E entity;
	
	private static final long serialVersionUID = -1997250030218950222L;
	protected JTable table;
	protected JButton addButton;
	protected ButtonGroup radioGroup;
	protected JList<Object> newPriceList;
	protected JTextField newPriceField;
	
	@SuppressWarnings("unchecked")
	public BasePriceList(BaseEntity e) {
		entity = (E) e;
		// right of the screen, price's and supplier's informations
		this.setPreferredSize(new Dimension(800, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addPriceTableContainer();
		addNewPriceContainer();
		this.add(Box.createVerticalGlue());
	}

	public abstract String getTitle();
	public abstract Object[] getTableModelColumns();
	protected abstract void onRowDelete(int modelRow);
	
	public JButton getAddButton() {
		return addButton;
	}

	protected void addNewPriceContainer() {
		var addPriceContainer = new JPanel();
		addPriceContainer.setPreferredSize(new Dimension(500, 100));
		addPriceContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		addPriceContainer.setLayout(new BoxLayout(addPriceContainer, BoxLayout.X_AXIS));

		var listModel = new DefaultListModel<>();
		newPriceList = new JList<>(listModel);
		newPriceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		var scrollPane = new JScrollPane(newPriceList);
		addPriceContainer.add(scrollPane);

		newPriceField = new JTextField();
		addPriceContainer.add(newPriceField);

		addButton = new JButton("+");
		addPriceContainer.add(addButton);
		this.add(addPriceContainer);
	}


	
	protected void refreshList() {
		getPriceListTableModel().getDataVector().removeAllElements();
		// Rest to be implemented in subclasses
	}

	
	@SuppressWarnings("serial")
	public void addPriceTableContainer() {
		var tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableModelColumns());

		this.table = new JTable(tableModel) {
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};

		Action delete = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        onRowDelete(modelRow);
		    }
		};
		 
		ButtonColumn buttonColumn = new ButtonColumn(table, delete, getTableModelColumns().length-1);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		var priceTableContainer = new JScrollPane(table);
		priceTableContainer.setPreferredSize(new Dimension(0, 150));
		priceTableContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
		this.add(priceTableContainer);
	}

	public void addRowData(Object[] data) {
		var tabData = new ArrayList<Object>(Arrays.asList(data));

		tabData.add("-");
		
		getPriceListTableModel().addRow(tabData.toArray());
	}

	public void addRowData(Object[] data, boolean isDefault) {
		this.addRowData(data);
		var radioButton = (JRadioButton) this.table.getValueAt(getPriceListTableModel().getRowCount() - 1, 0);
		radioButton.setSelected(isDefault);
	}

	public DefaultTableModel getPriceListTableModel() {
		return (DefaultTableModel)this.table.getModel();
	}
}
