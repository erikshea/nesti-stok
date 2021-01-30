package com.nesti.stock_manager.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.ParameterizedType;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.SwingUtil;

/**
 * Shows list all entities (E defined in subclass), and provides buttons to manipulate them 
 * 
 * @param <E> Entity representing a single row of directory
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public abstract class BaseDirectory<E> extends JPanel implements Tab {

	protected JPanel buttonBar;
	protected DefaultTableModel tableModel;
	protected JTable table;
	protected TableSearchBar searchBar;
	protected JButton buttonAdd;
	protected JButton buttonDelete;
	protected JButton buttonModify;
	protected JButton buttonDuplicate;
	protected MainWindowControl mainController;
	
	/**
	 * 
	 * @param c main controller, to talk with other tabs
	 */
	@SuppressWarnings("static-access")
	public BaseDirectory(MainWindowControl c) {
		this.mainController = c;
		createTable();	
		searchBar = new TableSearchBar(table);	// Sea
		this.add(searchBar);
		
		addButtonBar();
		this.setBackground(new AppAppereance().LIGHT_COLOR);

		var tableContainer = new JScrollPane(this.table);
		tableContainer.getViewport().setBackground(new Color(244,225,181));
		this.add(tableContainer);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}

	/**
	 * Adds buttons that allow manipulation of entity list (add, delete, modify, duplicate...)
	 */
	public void addButtonBar() {
		this.buttonAdd = new JButton("Créer");
		this.buttonDelete = new JButton("Supprimer");
		this.buttonModify = new JButton("Modifier");
		this.buttonDuplicate = new JButton("Dupliquer");
		this.buttonDelete.setEnabled(false);
		this.buttonModify.setEnabled(false);
		this.buttonDuplicate.setEnabled(false);

		this.buttonAdd.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonAdd.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonDelete.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonDelete.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonModify.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonModify.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonDuplicate.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonDuplicate.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		

		this.buttonBar = new JPanel();
		this.buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));
		this.buttonBar.setBorder(BorderFactory.createEmptyBorder(30,10,20,10));
		this.buttonBar.setBackground(AppAppereance.LIGHT_COLOR);

		this.buttonBar.add(buttonAdd);
		this.buttonBar.add((Box.createRigidArea(new Dimension(20,0))));
		this.buttonBar.add(buttonDelete);
		this.buttonBar.add((Box.createRigidArea(new Dimension(20,0))));
		this.buttonBar.add(buttonModify);
		this.buttonBar.add((Box.createRigidArea(new Dimension(20,0))));
		this.buttonBar.add(buttonDuplicate);
	

		this.buttonBar.add(Box.createHorizontalGlue());

		this.add(buttonBar);
		
		this.setUpButtonBarListeners();
	}
	
	/**
	 *	Returns the tab title, to be shown in the list of tabs
	 */
	public abstract String getTitle();

	public void createTable() {
		this.tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableModelColumnNames());
		table = new JTable(tableModel);
				
		JTableHeader header = table.getTableHeader();
	    header.setPreferredSize(new Dimension(0,30));

	}

	public void setUpButtonBarListeners() {
		buttonDelete.addActionListener( e->{

			var options = new String[] {"Annuler", "Confirmer"};
			
			int choice = JOptionPane.showOptionDialog(this,
				"êtes-vous certain de vouloir supprimer "
						+ (this.table.getSelectedRowCount()==1?"cet élément?":"ces " + this.table.getSelectedRowCount() + " éléments?")
						+ "\nCeci est irréversible.",
				"Supprimer",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.WARNING_MESSAGE,
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
			    options[0]); //default button title

			if ( choice == 1 ) { // If "Confirm" clicked
				for (var rowIndex : this.table.getSelectedRows()) { // Loop through selected rows
					this.deleteRow(rowIndex);	// delete table method is implemented in all subclassess
					HibernateUtil.getSession().getTransaction().commit(); // Save DB
					refreshTable();
				}
			}

		});
		
		this.table.getSelectionModel().addListSelectionListener(e->{
			// If no row selected, disable delete button
			this.buttonDelete.setEnabled(this.table.getSelectedRowCount() > 0) ;
			
			// Modify and duplicate buttons only enabled if one row selected
			this.buttonModify.setEnabled(this.table.getSelectedRowCount() == 1) ;
			this.buttonDuplicate.setEnabled(this.table.getSelectedRowCount() == 1) ;
		});
	}
	
	
	/**
	 * Performs row deletion logic (includes deleting entity)
	 * @param rowIndex index (in table model) of row to delete
	 */
	public abstract void deleteRow(int rowIndex) ;
	
	
	
	/**
	 * Adds a row to table that corresponds to a given entity
	 * @param entity with which to fill row values
	 */
	public abstract void addRow(E entity) ;
	
	
	/**
	 * Returns an array of all the table column names
	 * @return array of names
	 */
	public abstract Object[] getTableModelColumnNames();

	
	
	/**
	 * 	Adds a row to the table (to be overriden if needed by subclasses)
	 * @param data
	 */
	public void addRowData(Object[] data) {
		this.tableModel.addRow(data);
	}
	
	/**
	 * Refresh the directory table.
	 */
	@SuppressWarnings("unchecked")
	public void refreshTable() {
		this.tableModel.setRowCount(0); // remove all rows
		// Detail of the article List
		
		var dao = BaseDao.getDao(getEntityClass());  // get E's dao
		var entities = dao.findAll(BaseDao.ACTIVE);	// get all flagged active

		entities.forEach(e-> {
			this.addRow((E) e); // Loop throuch them and perform add row logic
		});
		if (table.getRowCount()>0) {
			table.setRowSelectionInterval(0, 0); // Select first row
		}
		SwingUtil.setUpTableAutoSort(table); // enable auto-sorting (logic will only run once)
	}
	
	/**
	 *  Uses a popular trick to get the class of E
	 * @return E's class
	 */
	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * Called whenever the tab needs to be refreshed (at initialization, when re-focused...)
	 */
	public void refreshTab() {
		refreshTable();
		if (table.getRowCount() > 0) { 
			this.table.setRowSelectionInterval(0, 0);
		}
	}
	
	/**
	 * Called whenever tab is closed
	 */
	public void closeTab() {};
}
