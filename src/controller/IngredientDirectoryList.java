package controller;

public class IngredientDirectoryList extends BaseDirectoryList{

	public IngredientDirectoryList() {
		super();

		this.addRowData(new Object[] {"I452","sucre","gramme"});
		this.addRowData(new Object[] {"PO984P","oeuf","pi�ce"});
		this.addRowData(new Object[] {"AZE7","chocolat","gramme"});
	};

	@Override
	public String getTitle() {
		return "Liste d'ingr�dient";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"R�f","Nom","Unit�"};
	}

	@Override
	public void addRowData(Object[] data) {
		super.addRowData(data);
	}

}
