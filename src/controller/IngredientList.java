package controller;


@SuppressWarnings("serial")
public class IngredientList extends BaseList {

	public IngredientList() {
		super();

		this.addRowData(new Object[] {"I452","sucre","gramme"});
		this.addRowData(new Object[] {"PO984P","oeuf","pièce"});
		this.addRowData(new Object[] {"AZE7","chocolat","gramme"});
	}

	// Title of the article List
	@Override
	public String getTitle() {
		return "Liste des ingrédients";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Réf","Nom","Unité"};
	}
	
}
