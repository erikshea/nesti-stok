package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.model.Supplier;

public class SupplierInformation extends BaseInformation<Supplier> {
	private static final long serialVersionUID = 1775908299271902575L;
	/**
	 * constructor supplier information
	 * @param c
	 */
	public SupplierInformation(MainWindowControl c, Supplier supplier) {
		super(c, supplier);
	}
	
	@Override
	public void refreshTab() {
		super.refreshTab();
		final var supplier = item;
		var dao = item.getDao();
		
		var articlePriceList = new SupplierArticleList(supplier);
		this.add(articlePriceList, BorderLayout.EAST);
		
		var supplierForm = new JPanel();
		supplierForm.setPreferredSize(new Dimension(500, 0));
		supplierForm.setLayout(new BoxLayout(supplierForm, BoxLayout.Y_AXIS));
		
		var titleSupplierInformation = new JLabel("Fournisseur");
		supplierForm.add(titleSupplierInformation);
		
		var nameFieldContainer = new FieldContainer("Nom", this);
		nameFieldContainer.bind(
				supplier.getName(),
				(s)-> supplier.setName(s),
				(fieldValue)->dao.findOneBy("name", fieldValue) == null);
		supplierForm.add(nameFieldContainer);
		
		var adress1FieldContainer = new FieldContainer("Adresse 1", this);
		adress1FieldContainer.bind(
				supplier.getAddress1(),
				(s)-> supplier.setAddress1(s));
		supplierForm.add(adress1FieldContainer);
		
		var adresse2FieldContainer = new FieldContainer("Adresse 2", this);
		adresse2FieldContainer.bind(
				supplier.getAddress2(),
				(s)-> supplier.setAddress2(s));
		supplierForm.add(adresse2FieldContainer);
		
		var zipCodeFieldContainer = new FieldContainer("Code postal", this);
		zipCodeFieldContainer.bind(
				supplier.getZipCode(),
				(s)-> supplier.setZipCode(s));
		supplierForm.add(zipCodeFieldContainer);
		
		var cityFieldContainer = new FieldContainer("Ville", this);
		cityFieldContainer.bind(
				supplier.getCity(),
				(s)-> supplier.setCity(s));
		supplierForm.add(cityFieldContainer);
	
		var countryFieldContainer = new FieldContainer("Pays", this);
		countryFieldContainer.bind(
				supplier.getCountry(),
				(s)-> supplier.setCountry(s));
		supplierForm.add(countryFieldContainer);
	
		var phoneFieldContainer = new FieldContainer("Téléphone", this);
		phoneFieldContainer.bind(
				supplier.getPhoneNumber(),
				(s)-> supplier.setPhoneNumber(s));
		supplierForm.add(phoneFieldContainer);
		
		var contactNameFieldContainer = new FieldContainer("Nom du contact", this);
		contactNameFieldContainer.bind(
				supplier.getContactName(),
				(s)-> supplier.setContactName(s));
		supplierForm.add(contactNameFieldContainer);
	
		supplierForm.add(Box.createVerticalGlue());
		
		this.add(supplierForm, BorderLayout.WEST);
	}
	
	@Override
	public void addButtonListeners() {
		super.addButtonListeners();
		this.buttonValidate.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getSupplierList());
		});
		
		this.buttonCancel.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getSupplierList());
		});
	}

	@Override
	public void saveItem() {
		final var supplier= (Supplier) item;
		supplier.getDao().saveOrUpdate(supplier);
		this.mainControl.getSupplierList().refreshTab();
	}
}