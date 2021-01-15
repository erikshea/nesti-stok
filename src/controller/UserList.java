package controller;

<<<<<<< HEAD
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

=======

@SuppressWarnings("serial")
public class UserList extends BaseList {

	public UserList() {
		super();
		this.addRowData(new Object[] {"007","administrateur","0145879645","James Bond"});
		this.addRowData(new Object[] {"mandy","administrateur","065479896", "Mandy Moore"});
		this.addRowData(new Object[] {"Polo","administrateur","0458796321","Paul Legrand"});
	
	}

	@Override
	public String getTitle() {
		return "Liste des administrateurs";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Nom d'utilisateur", "Rôle", "Date d'inscription","Nom du contact"};
	}
	
>>>>>>> refs/remotes/origin/Manue
}
