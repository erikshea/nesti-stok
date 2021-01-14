package controller;


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
	
}
