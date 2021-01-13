package controller;

@SuppressWarnings("serial")
public class ArticleDirectoryList extends BaseDirectoryList{

	public ArticleDirectoryList() {
		super();

		this.addRowData(new Object[] {"", "Couteau de cuisine en inox","ACME","12","Ustensil","50","18"});
		this.addRowData(new Object[] {"", "1 boite d'oeuf de poule","Poulen'Herbe","2.5","Ingrédient","18","4"});
		this.addRowData(new Object[] {"", "fouet en plastique","Tout pour la cuisine","3.30","Ustensil","451","11.60"});
	};

	@Override
	public String getTitle() {
		return "Liste d'articles";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"", "Description", "Fournisseur par défaut", "Prix d'achat","Type","Stock","PV Conseillé" };
	}

	/**
	 * add a row in the list
	 */
	@Override
	public void addRowData(Object[] data) {
		super.addRowData(data);
	}

}
