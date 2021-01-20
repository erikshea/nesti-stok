package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.UserDao;

@SuppressWarnings("serial")
public class UserList extends BaseList {

	public UserList(MainWindowControl c) {
		super(c);

		refresh();
	}

	

	public void refresh() {
		this.tableModel.getDataVector().removeAllElements();
		// Detail of the article List
		var dao = new UserDao();
		var user = dao.findAll();
		user.forEach(u-> {
			this.addRowData(new Object[] {u.getLogin(),u.getRole(), u.getDateCreation(),u.getName()});
		});
	}
	
	@Override
	public String getTitle() {
		return "Liste des administrateurs";
	}

	
	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Nom d'utilisateur", "R�le", "Date d'inscription", "Nom du contact" };
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
}
