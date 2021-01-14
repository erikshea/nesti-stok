package controller;

@SuppressWarnings("serial")
public class SupplierDirectoryList extends BaseDirectoryList{

	public SupplierDirectoryList() {
		super();

		this.addRowData(new Object[] {"ACME","jean Paul","Marseille","04 68 45 78 96"});
		this.addRowData(new Object[] {"Tout pour la cuisine","Martine Martin","Gap", "0604859785"});
		this.addRowData(new Object[] {"O'Sel Fin","James Bond","Londres","04 87 65 47 96"});
	};

	@Override
	public String getTitle() {
		return "Liste de founisseur";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Nom", "Nom du contact", "Ville","Tel"};
	}

	/**
	 * add a row in the list
	 */
	@Override
	public void addRowData(Object[] data) {
		super.addRowData(data);
	}

}
