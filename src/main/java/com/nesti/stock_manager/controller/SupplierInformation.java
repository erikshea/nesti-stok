package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nesti.stock_manager.entity.Supplier;
import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.util.AppAppereance;

/**
 * See and edit a supplier's information, and a list of articles offered by that supplier.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class SupplierInformation extends BaseInformation<Supplier> {
	private static final long serialVersionUID = 1775908299271902575L;
	/**
	 * constructor supplier information
	 * @param c
	 */
	public SupplierInformation(MainWindowControl c, Supplier supplier) {
		super(c, supplier);
	}
	
	public String getTitle() {
		var result = "";
		if (item.getName() == null) { // If newly created
			result = "Nouveau Fournisseur";
		} else { // Else, show name
			result = "Fournisseur : " + item.getName();
		}
		
		return result;
	}
	
	/**
	 * Called first at tab refresh. Build and add swing elements.
	 */
	@Override
	public void preRefreshTab() {
		super.preRefreshTab();
		final var supplier = item;
		var dao = item.getDao();
		
		// Create price list, add to the right of border pane
		var articlePriceList = new SupplierArticleList(supplier);
		
		articlePriceList.setBackground(AppAppereance.LIGHT_COLOR);
		this.add(articlePriceList, BorderLayout.EAST);
		
		var supplierForm = new JPanel();
		supplierForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		supplierForm.setPreferredSize(new Dimension(500, 0));
		supplierForm.setLayout(new BoxLayout(supplierForm, BoxLayout.Y_AXIS));
		supplierForm.setBackground(AppAppereance.LIGHT_COLOR);
		
		var titleSupplierInformation = new JLabel(getTitle());
		titleSupplierInformation.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		titleSupplierInformation.setFont(AppAppereance.TITLE_FONT);
		supplierForm.add(titleSupplierInformation);
		
		var nameFieldContainer = new FieldContainer("Nom", this);
		nameFieldContainer.bind(
				supplier.getName(), // Starting value
				(s)-> supplier.setName(s), // On change, update corresponding supplier property
				(fieldValue)->dao.findOneBy("name", fieldValue) == null); // Only valid if no other supplier exists with the same property
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
				(s)-> supplier.setZipCode(s),
				(fieldValue)->fieldValue.length()<=5);
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
		phoneFieldContainer.setBackground(AppAppereance.LIGHT_COLOR);
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
	public void closeTab() {
		super.closeTab();
		// On tab close, go back to supplier directory
		this.mainControl.getMainPane().setSelectedComponent(this.mainControl.getSupplierDirectory());
	}
}