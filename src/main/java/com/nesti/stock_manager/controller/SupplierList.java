package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.dao.IngredientDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.model.*;

@SuppressWarnings("serial")
public class SupplierList extends BaseList<Supplier> {

	public SupplierList(MainWindowControl c) {
		super(c);


		refresh();
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
	public void setUpButtonListeners()  {
		super.setUpButtonListeners();
		this.buttonModify.addActionListener( e->{
			var name = this.table.getValueAt(this.table.getSelectedRow(),0);
			var a = (new SupplierDao()).findOneBy("name",name);

			this.mainController.addCloseableTab(
					"Fournisseur: " + a.getName(),
					new SupplierInformation(this.mainController,a)
			);

		});
		
		this.buttonAdd.addActionListener( e->{
			this.mainController.addCloseableTab(
					"Nouveau Fournisseur",
					new SupplierInformation(this.mainController,new Supplier())
			);
		});
		/*
		this.buttonDelete.addActionListener( e->{
			var dao = new SupplierDao();
			
			for ( var rowIndex : this.table.getSelectedRows()) {
				var supplier = dao.findOneBy("name", this.table.getValueAt(rowIndex, 0));
				dao.delete(supplier);
			}
			
			refresh();
		});
		
		this.buttonDuplicate.addActionListener( e->{
			var dao = new SupplierDao();
			var supplier = dao.findOneBy("name", this.table.getValueAt(this.table.getSelectedRow(),0));
			supplier.setIdSupplier(0);
			
			this.mainController.addCloseableTab(
					"Nouveau Fournisseur",
					new SupplierInformation(this.mainController,supplier)
			);
		});*/
	}
	
	@Override
	public void deleteRow(int rowIndex) {
		var dao = new SupplierDao();
		var entity = dao.findOneBy("name", this.table.getValueAt(rowIndex, 0));
		dao.delete(entity);
	}
	
	@Override
	public void addRow(Supplier entity) {
		this.addRowData(new Object[] {entity.getName(), entity.getContactName(), entity.getCity(), entity.getPhoneNumber()});
	}
}
