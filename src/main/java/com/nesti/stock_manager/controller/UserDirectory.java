package com.nesti.stock_manager.controller;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.model.User;

/**
 * Shows list of all users, and provides buttons to manipulate them
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class UserDirectory extends BaseDirectory<User> {

	public UserDirectory(MainWindowControl c) {
		super(c);
	}

	
	@Override
	public String getTitle() {
		return "Administrateurs";
	}

	
	@Override
	public Object[] getTableModelColumnNames() {
		return new Object[] { "Nom d'utilisateur", "Rôle", "Date d'inscription", "Nom du contact" };
	}
	
	@Override
	public void setUpButtonBarListeners()  {
		super.setUpButtonBarListeners();
		
		// "Modify" button action
		this.buttonModify.addActionListener( e->{
			var login = this.table.getValueAt(this.table.getSelectedRow(), 0);
			var a = (new UserDao()).findOneBy("login",login);

			this.mainController.getMainPane().addCloseableTab(new UserInformation(this.mainController,a));

		});
		
		// New article action
		this.buttonAdd.addActionListener( e->{
			this.mainController.getMainPane().addCloseableTab(new UserInformation(this.mainController,new User()));
		});
		
		// "Duplicate" button action
		this.buttonDuplicate.addActionListener(e -> {
			var login = this.table.getValueAt(this.table.getSelectedRow(), 0);
			var a = (new UserDao()).findOneBy("login",login);
			
			mainController.getMainPane().addCloseableTab(new UserInformation(mainController, a.duplicate()));
		});
	}
	
	@Override
	public void deleteRow(int rowIndex) {
		var dao = new UserDao();
		var entity = dao.findOneBy("login", this.table.getValueAt(rowIndex, 0));
		entity.setFlag(BaseDao.DELETED);
	}
	
	@Override
	public void addRow(User entity) {
		this.addRowData(new Object[] {entity.getLogin(),entity.getRole(), entity.getDateCreation(),entity.getName()});
	}
	
	
	@Override
	public void createTable() {
		super.createTable();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	
	}
}
