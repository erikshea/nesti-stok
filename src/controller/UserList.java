package controller;

import java.util.List;

import model.*;
import util.HibernateUtil;

@SuppressWarnings("serial")
public class UserList extends BaseList {

	public UserList() {
		super();

		var session = HibernateUtil.getSessionFactory().openSession();
		var transaction = session.beginTransaction();

		List<User> queryUser = session.createQuery("from User").list();

		queryUser.forEach(v -> {
			this.addRowData(new Object[] { v.getLogin(), v.getRole(), v.getDateCreation(), v.getName() });
		});

		transaction.commit();

	}

	@Override
	public String getTitle() {
		return "Liste des administrateurs";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Nom d'utilisateur", "Rôle", "Date d'inscription", "Nom du contact" };
	}

}
