package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import form.FieldContainer;
import form.ListFieldContainer;

public class SupplierInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	
	/**
	 * constructor supplier information
	 * @param c
	 */
	public SupplierInformation(MainWindowControl c) {
		super(c);

// left of the screen, supplier's information
		
		var supplierForm = new JPanel();
		supplierForm.setPreferredSize(new Dimension(500, 0));
		supplierForm.setLayout(new BoxLayout(supplierForm, BoxLayout.Y_AXIS));
		
		var titleSupplierInformation = new JLabel("Fournisseur");
		supplierForm.add(titleSupplierInformation);
		
		var nameFieldContainer = new FieldContainer("Nom");
		nameFieldContainer.getField().setText("Jean Bon Grossiste");
		supplierForm.add(nameFieldContainer);
		
		var adress1FieldContainer = new FieldContainer("Adresse 1");
		adress1FieldContainer.getField().setText("1 rue des couteaux");
		supplierForm.add(adress1FieldContainer);
		
		var adresse2FieldContainer = new FieldContainer("Adresse 2");
		adresse2FieldContainer.getField().setText("Batiment A");
		supplierForm.add(adresse2FieldContainer);
		
		var zipCodeFieldContainer = new FieldContainer("Code postal");
		zipCodeFieldContainer.getField().setText("34000");
		supplierForm.add(zipCodeFieldContainer);
		
		var cityFieldContainer = new FieldContainer("Ville");
		cityFieldContainer.getField().setText("Montpellier");
		supplierForm.add(cityFieldContainer);

		var countryFieldContainer = new FieldContainer("Pays");
		countryFieldContainer.getField().setText("France");
		supplierForm.add(countryFieldContainer);

		var phoneFieldContainer = new FieldContainer("Téléphone");
		phoneFieldContainer.getField().setText("06 12 13 14 15");
		supplierForm.add(phoneFieldContainer);
		
		var contactNameFieldContainer = new FieldContainer("Nom du contact");
		contactNameFieldContainer.getField().setText("Martine Martin");
		supplierForm.add(contactNameFieldContainer);

		supplierForm.add(Box.createVerticalGlue());
		
		this.add(supplierForm, BorderLayout.WEST);
		

		var articlePriceList = new ArticlePriceList();
		this.add(articlePriceList, BorderLayout.EAST);
		
	}
}