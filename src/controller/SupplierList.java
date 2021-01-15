package controller;

import java.util.List;

import model.Supplier;
import util.HibernateUtil;

@SuppressWarnings("serial")
public class SupplierList extends BaseList {

	public SupplierList(MainWindowControl c) {
		super(c);
		var session = HibernateUtil.getSessionFactory().openSession();
        List<Supplier> querySupplier = session.createQuery("from Supplier").list();
        
        // ex ici on affiche les id des supplier, on pourrait faire un addrowData
        querySupplier.forEach( v->{
        	this.addRowData(new Object[] { v.getName(), v.getContactName(), v.getCity(), v.getPhoneNumber()});
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
	
}
