package controller;

import java.util.List;

import dao.ArticleDao;
import dao.SupplierDao;
import model.Supplier;
import util.HibernateUtil;

@SuppressWarnings("serial")
public class SupplierList extends BaseList {

	public SupplierList(MainWindowControl c) {
		super(c);

		var dao = new SupplierDao();
		var suppliers = dao.findAll();
		suppliers.forEach(s->{
			this.addRowData(new Object[] {s.getName(), s.getContactName(), s.getCity(), s.getPhoneNumber()});
		});
	
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

	@Override
	public void setUpButtonListeners()  {
		this.buttonModify.addActionListener( e->{
			var name = this.table.getValueAt(this.table.getSelectedRow(),0);
			System.out.println(name);
			var a = (new SupplierDao()).findOneBy("name",name);

			this.mainController.addCloseableTab(
					"Fournisseur: " + a.getName(),
					new SupplierInformation(this.mainController,a)
			);

		});
	}

}
