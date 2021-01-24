package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.model.Supplier;

@SuppressWarnings("serial")
public class SupplierDirectory extends BaseDirectory<Supplier> {

	public SupplierDirectory(MainWindowControl c) {
		super(c);
	}
	
	
	// Title of the article List
	@Override
	public String getTitle() {
		return "Liste des fournisseurs";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Nom", "Nom du contact", "Ville", "Tel"};
	}

	@Override
	public void setUpButtonBarListeners()  {
		super.setUpButtonBarListeners();
		this.buttonModify.addActionListener( e->{
			var name = this.table.getValueAt(this.table.getSelectedRow(),0);
			var a = (new SupplierDao()).findOneBy("name",name);

			this.mainController.getMainPane().addCloseableTab(
					"Fournisseur: " + a.getName(),
					new SupplierInformation(this.mainController,a)
			);

		});
		
		this.buttonAdd.addActionListener( e->{
			this.mainController.getMainPane().addCloseableTab(
					"Nouveau Fournisseur",
					new SupplierInformation(this.mainController,new Supplier())
			);
		});
	}
	
	@Override
	public void deleteRow(int rowIndex) {
		var dao = new SupplierDao();
		var entity = dao.findOneBy("name", this.table.getValueAt(rowIndex, 0));
		entity.setFlag(BaseDao.DELETED);
	}
	
	@Override
	public void addRow(Supplier entity) {
		this.addRowData(new Object[] {entity.getName(), entity.getContactName(), entity.getCity(), entity.getPhoneNumber()});
	}
}
