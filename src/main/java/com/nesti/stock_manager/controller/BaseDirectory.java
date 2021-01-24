package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.ParameterizedType;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.util.HibernateUtil;

@SuppressWarnings("serial")
public abstract class BaseDirectory<E> extends JPanel implements Tab {

	protected JPanel buttonBar;
	protected DefaultTableModel tableModel;
	protected JTable table;

	protected JButton buttonAdd;
	protected JButton buttonDelete;
	protected JButton buttonModify;
	protected JButton buttonDuplicate;
	protected MainWindowControl mainController;
	
	public BaseDirectory(MainWindowControl c) {
		this.mainController = c;
		createTable();	
		addButtonBar();
		// Title of the article List
		var titleLabel = new JLabel(this.getTitle());
		this.add(titleLabel);


		var tableContainer = new JScrollPane(this.table);
		this.add(tableContainer);
		
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public void addButtonBar() {
		this.buttonAdd = new JButton("Créer");
		
		this.buttonDelete = new JButton("Supprimer");
		this.buttonModify = new JButton("Modifier");
		this.buttonDuplicate = new JButton("Dupliquer");
		this.buttonDelete.setEnabled(false);
		this.buttonModify.setEnabled(false);
		this.buttonDuplicate.setEnabled(false);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Define all the button of the head on the article list
		this.buttonBar = new JPanel();
		this.buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));

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
	
	public String getTitle() {
		return "";
	}

	public void createTable() {
		this.tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableModelColumns());
		this.table = new JTable(tableModel);
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
			
			if ( choice == 1 ) {
				for (var rowIndex : this.table.getSelectedRows()) {
					this.deleteRow(rowIndex);
				}
			}
			HibernateUtil.getSession().getTransaction().commit();
			refreshTable();
		});
		
		this.table.getSelectionModel().addListSelectionListener(e->{
			this.buttonAdd.setEnabled(this.table.getSelectedRowCount() <= 1) ; //TODO: re-enable
			this.buttonDelete.setEnabled(this.table.getSelectedRowCount() > 0) ;
			this.buttonModify.setEnabled(this.table.getSelectedRowCount() == 0 || this.table.getSelectedRowCount() == 1) ;
			this.buttonDuplicate.setEnabled(this.table.getSelectedRowCount() <= 1) ;
		});
	}
	public abstract void deleteRow(int rowIndex) ;
	public abstract void addRow(E entity) ;
	
	public Object[] getTableModelColumns() {
		return new Object[] {};
	}

	public void addRowData(Object[] data) {
		this.tableModel.addRow(data);
	}
	

	public void duplicate() {
		//var dao = BaseDao.getDao(getEntityClass());
		
		//var entity = dao.findOneBy("code", this.table.getValueAt(rowIndex, 1));
	}
	
	//public abstract  Pair<Integer,String> getFindableColumn();
	
	@SuppressWarnings("unchecked")
	public void refreshTable() {
		this.tableModel.getDataVector().removeAllElements();
		// Detail of the article List
		
		var dao = BaseDao.getDao(getEntityClass());
		var entities = dao.findAll(BaseDao.ACTIVE);

		entities.forEach(e-> {
			this.addRow((E) e);
		});
	}
	
	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void refreshTab() {
		refreshTable();
		if (table.getRowCount() > 0) {
			this.table.setRowSelectionInterval(0, 0);
		}
	}
	
	public void closeTab() {}
}
