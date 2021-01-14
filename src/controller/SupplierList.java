package controller;

@SuppressWarnings("serial")
public class SupplierList extends BaseList {

	public SupplierList() {
		super();
		this.addRowData(new Object[] {"ACME","jean Paul","Marseille","04 68 45 78 96"});
		this.addRowData(new Object[] {"Tout pour la cuisine","Martine Martin","Gap", "0604859785"});
		this.addRowData(new Object[] {"O'Sel Fin","James Bond","Londres","04 87 65 47 96"});
		
	}

	// Title of the article List
	@Override
	public String getTitle() {
		return "Liste des fournisseurs";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Nom", "Nom du contact", "Ville","Tel"};
	}
	
}
