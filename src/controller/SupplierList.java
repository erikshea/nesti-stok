package controller;

<<<<<<< HEAD
import util.HibernateUtil;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import model.*;

@SuppressWarnings("serial")
public class SupplierList extends BaseList {

	public SupplierList() {
		super();
	
		var session = HibernateUtil.getSessionFactory().openSession();

        // on fit ou commence la transaction
        var transaction = session.beginTransaction();
        
        //on affiche tous les suppliers depuis la base
        List<Supplier> querySupplier = session.createQuery("from Supplier").list();
        
        // ex ici on affiche les id des supplier, on pourrait faire un addrowData
        querySupplier.forEach( v->{
        	this.addRowData(new Object[] { v.getName(), v.getContactName(), v.getCity(), v.getPhoneNumber()});
 		});
        // on envoie les modifs en bd
		transaction.commit();
		
		// pr récupérer le nom d'un ingrédient(car 
		// myIngredient.getProduct().getName
		
	}

	// Title of the article List
	@Override
	public String getTitle() {
		return "Liste des fournisseurs";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Nom", "Nom du contact", "Ville", "Tel" };
	}

=======
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
	
>>>>>>> refs/remotes/origin/Manue
}
