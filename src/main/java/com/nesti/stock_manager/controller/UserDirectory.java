package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.model.User;

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
	public Object[] getTableModelColumns() {
		return new Object[] { "Nom d'utilisateur", "Rôle", "Date d'inscription", "Nom du contact" };
	}
	
	@Override
	public void setUpButtonBarListeners()  {
		super.setUpButtonBarListeners();
		this.buttonModify.addActionListener( e->{
			var login = this.table.getValueAt(this.table.getSelectedRow(), 0);

			var a = (new UserDao()).findOneBy("login",login);

			this.mainController.getMainPane().addCloseableTab(new UserInformation(this.mainController,a));

		});
		
		this.buttonAdd.addActionListener( e->{
			this.mainController.getMainPane().addCloseableTab(new UserInformation(this.mainController,new User()));
		});
		/*
		this.buttonDelete.addActionListener( e->{
			var dao = new UserDao();
			
			for ( var rowIndex : this.table.getSelectedRows()) {
				var user = dao.findOneBy("login", this.table.getValueAt(rowIndex, 0));
				dao.delete(user);
			}
			
			refresh();
		});
		
		this.buttonDuplicate.addActionListener( e->{
			var dao = new UserDao();
			var user = dao.findOneBy("login", this.table.getValueAt(this.table.getSelectedRow(), 0));
			user.setIdUser(0);
			user.setLogin(null);
			
			this.mainController.addCloseableTab(
					"Nouvel Utilisateur",
					new UserInformation(this.mainController,user)
			);
		});*/
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
}
