
package com.nesti.stock_manager.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import com.nesti.stock_manager.entity.BaseEntity;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.ButtonColumn;


/**
 * Shows a table containing all offers associated with an entity E, and controls to change those offers
 * 
 * @param <E> Entity associated with offers on which to build the price list
 * @author Emmanuelle Gay, Erik Shea
 */
public abstract class BasePriceList<E> extends JPanel {
	E entity; // Entity that we will base the price list on, E class being specified in subclass
	
	private static final long serialVersionUID = -1997250030218950222L;
	protected JTable table; // Price list as a table
	protected JButton addButton; // Adds a new offer 
	protected JList<Object> newPriceList; // List of corresponding entities (Article or Supplier) to link new offer with
	protected JTextField newPriceField; // Input area for new offer price
	
	@SuppressWarnings("unchecked")
	public BasePriceList(BaseEntity e) {
		entity = (E) e; // Cast passed entity to E (class defined in subclass declaration)
		this.setPreferredSize(new Dimension(700, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addPriceTableContainer();	// Builds price table
		addNewPriceContainer();		// Holds all components needed to add a new offer
	}

	/**
	 * Part of the Tab interface, returns a title to display inside tab
	 */
	public abstract String getTitle();
	
	/**
	 * @return an array representing the column names
	 */
	public abstract Object[] getTableModelColumns();
	
	/**
	 * Called whenever a row is deleted
	 * @param modelRow model index of row
	 */
	protected abstract void onRowDelete(int modelRow);


	/**
	 * Builds and adds all components needed to add a new offer
	 */
	protected void addNewPriceContainer() {
		var addPriceContainer = new JPanel();
		addPriceContainer.setPreferredSize(new Dimension(800, 100));
		addPriceContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		addPriceContainer.setLayout(new BoxLayout(addPriceContainer, BoxLayout.X_AXIS));

		var listModel = new DefaultListModel<>();
		newPriceList = new JList<>(listModel);
		newPriceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		var scrollPane = new JScrollPane(newPriceList);
		scrollPane.setPreferredSize(new Dimension(800,100));
		addPriceContainer.add(scrollPane);

		newPriceField = new JTextField("0.0");
		newPriceField.setPreferredSize(new Dimension(50,30));
		newPriceField.setMaximumSize(new Dimension(50,30));
		
		addPriceContainer.add(Box.createHorizontalGlue());
		addPriceContainer.add(newPriceField);

		addButton = new JButton("+");
		addPriceContainer.add(addButton);
		addPriceContainer.add(Box.createHorizontalGlue());
		this.add(addPriceContainer);

	}


	
	/**
	 *  Called whenever a list needs to be refreshed
	 */
	protected void refreshList() {
		getPriceListTableModel().setRowCount(0);
		// Rest to be implemented in subclasses
	}
	
	/**
	 *	Determines which cells of the directory listing are editable
	 */
	protected  boolean isCellEditable(DefaultTableModel model, int row, int column) {
		var name = model.getColumnName(column);
		return name == "Par défaut"
			|| name == "Suppression";
	}
	
	/**
	 * Builds and adds price list table and its container
	 */
	@SuppressWarnings("serial")
	public void addPriceTableContainer() {
		var me = this;
		
		var tableModel = new DefaultTableModel() {
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	        	return me.isCellEditable(this, row, column);
	        }
		};
		tableModel.setColumnIdentifiers(getTableModelColumns());

		this.table = new JTable(tableModel) {
			/**
			 * Redefine tableChanged to redraw entire table in case we have a radio button
			 * or other element whose activation/editing can also change another row
			 */
			@Override
			public void tableChanged(TableModelEvent e) { 
				super.tableChanged(e);
				repaint();
			}
		};

		// Action: whenever a row is deleted
		Action delete = new AbstractAction() {
		    public void actionPerformed(ActionEvent e)
		    {
		        if (showDeleteConfirmationDialog() == 1 ) {
			        int modelRow = Integer.valueOf( e.getActionCommand() );
		        	onRowDelete(modelRow);
		        }
		    }
		};
		 
		// Uses ButtonColumn class to associate a column with a delete button action
		@SuppressWarnings("unused")
		ButtonColumn buttonColumn = new ButtonColumn(
			table, // target table
			delete, // action on click
			getTableModelColumns().length-1 // column in which to show delete buttons
		);
		
		var priceTableContainer = new JScrollPane(table);
		priceTableContainer.setPreferredSize(new Dimension(0, 150));
		priceTableContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
		
		this.table.setSelectionBackground(AppAppereance.LIGHT_COLOR);
		this.table.setSelectionForeground(AppAppereance.DARK);	
		this.add(priceTableContainer);
	}

	
	/**
	 * Shows deletion confirmation dialog
	 * @return number corresponding to position of chosen button in array
	 */
	public Integer showDeleteConfirmationDialog() {

		var options = new String[] {"Annuler", "Confirmer"};
		
		int choice = JOptionPane.showOptionDialog(this,
			"êtes-vous certain de vouloir supprimer cette offre?",
			"Supprimer",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.WARNING_MESSAGE,
		    null,     //do not use a custom Icon
		    options,  //the titles of buttons
		    options[0]); //default button title
		return choice;
	}
	
	
	
	/**
	 * Add a row to the price list table
	 * @param data array of objects representing row data.
	 */
	public void addRowData(Object[] data) {
		var tabData = new ArrayList<Object>(Arrays.asList(data));

		tabData.add("-"); // Text that will be inside delete button 
		
		getPriceListTableModel().addRow(tabData.toArray());
	}


	/**
	 * @return model of price list table
	 */
	public DefaultTableModel getPriceListTableModel() {
		return (DefaultTableModel)this.table.getModel();
	}
}
