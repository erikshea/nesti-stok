package controller;

import dao.*;

@SuppressWarnings("serial")
public class UserList extends BaseList {

	public UserList(MainWindowControl c) {
		super(c);

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
		return new Object[] { "Nom d'utilisateur", "Rôle", "Date d'inscription", "Nom du contact" };
	}
	
	@Override
	public void setUpButtonListeners()  {
		this.buttonModify.addActionListener( e->{
			var login = this.table.getValueAt(this.table.getSelectedRow(), 0);

			var a = (new UserDao()).findOneBy("login",login);

			this.mainController.addCloseableTab(
					"Utilisateur: " + a.getName(),
					new UserInformation(this.mainController,a)
			);

		});
	}
}
