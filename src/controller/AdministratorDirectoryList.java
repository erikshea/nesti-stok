package controller;

public class AdministratorDirectoryList extends BaseDirectoryList{

	public AdministratorDirectoryList() {
		super();

		this.addRowData(new Object[] {"007","administrateur","0145879645","James Bond"});
		this.addRowData(new Object[] {"mandy","administrateur","065479896", "Mandy Moore"});
		this.addRowData(new Object[] {"Polo","administrateur","0458796321","Paul Legrand"});
	};

	@Override
	public String getTitle() {
		return "Liste d'administrateur";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Nom d'utilisateur", "Rôle", "Date d'inscription","Nom du contact"};
	}

	/**
	 * add a row in the list
	 */
	@Override
	public void addRowData(Object[] data) {
		super.addRowData(data);
	}

}
