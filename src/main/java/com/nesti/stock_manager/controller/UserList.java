package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.model.Supplier;
import com.nesti.stock_manager.model.User;

@SuppressWarnings("serial")
public class UserList extends BaseList<User> {

	public UserList(MainWindowControl c) {
		super(c);

		refresh();
	}

	
	@Override
	public String getTitle() {
		return "Liste des administrateurs";
	}

	
	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Nom d'utilisateur", "Rôle", "Date d'inscription", "Nom du contact" };
	}
	
	@Override
	public void setUpButtonListeners()  {
		super.setUpButtonListeners();
		this.buttonModify.addActionListener( e->{
			var login = this.table.getValueAt(this.table.getSelectedRow(), 0);

			var a = (new UserDao()).findOneBy("login",login);

			this.mainController.addCloseableTab(
					"Utilisateur: " + a.getName(),
					new UserInformation(this.mainController,a)
			);

		});
		
		this.buttonAdd.addActionListener( e->{
			this.mainController.addCloseableTab(
					"Nouvel Utilisateur",
					new UserInformation(this.mainController,null)
			);
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
		dao.delete(entity);
	}
	
	@Override
	public void addRow(User entity) {
		this.addRowData(new Object[] {entity.getLogin(),entity.getRole(), entity.getDateCreation(),entity.getName()});
	}
}
