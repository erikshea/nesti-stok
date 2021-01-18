package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.SupplierDao;
import form.FieldContainer;
import form.ListFieldContainer;
import model.Ingredient;
import model.Product;
import model.Supplier;
import util.HibernateUtil;

public class SupplierInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	
	/**
	 * constructor supplier information
	 * @param c
	 */
	public SupplierInformation(MainWindowControl c, Supplier supplier) {
		super(c, supplier);
		if (supplier == null) {
			supplier = new Supplier();
		}
		var dao = new SupplierDao();
		final var supplierFinal = supplier;
// left of the screen, supplier's information
		
		var supplierForm = new JPanel();
		supplierForm.setPreferredSize(new Dimension(500, 0));
		supplierForm.setLayout(new BoxLayout(supplierForm, BoxLayout.Y_AXIS));
		
		var titleSupplierInformation = new JLabel("Fournisseur");
		supplierForm.add(titleSupplierInformation);
		
		var nameFieldContainer = new FieldContainer("Nom", this);
		nameFieldContainer.bind(
				()-> supplierFinal.getName(),
				(s)-> supplierFinal.setName(s),
				(fieldValue)->dao.findOneBy("name", fieldValue) == null);
		supplierForm.add(nameFieldContainer);
		
		var adress1FieldContainer = new FieldContainer("Adresse 1", this);
		adress1FieldContainer.bind(
				()-> supplierFinal.getAddress1(),
				(s)-> supplierFinal.setAddress1(s));
		supplierForm.add(adress1FieldContainer);
		
		var adresse2FieldContainer = new FieldContainer("Adresse 2", this);
		adresse2FieldContainer.bind(
				()-> supplierFinal.getAddress2(),
				(s)-> supplierFinal.setAddress2(s));
		supplierForm.add(adresse2FieldContainer);
		
		var zipCodeFieldContainer = new FieldContainer("Code postal", this);
		zipCodeFieldContainer.bind(
				()-> supplierFinal.getZipCode(),
				(s)-> supplierFinal.setZipCode(s));
		supplierForm.add(zipCodeFieldContainer);
		
		var cityFieldContainer = new FieldContainer("Ville", this);
		cityFieldContainer.bind(
				()-> supplierFinal.getCity(),
				(s)-> supplierFinal.setCity(s));
		supplierForm.add(cityFieldContainer);

		var countryFieldContainer = new FieldContainer("Pays", this);
		countryFieldContainer.bind(
				()-> supplierFinal.getCountry(),
				(s)-> supplierFinal.setCountry(s));
		supplierForm.add(countryFieldContainer);

		var phoneFieldContainer = new FieldContainer("T�l�phone", this);
		phoneFieldContainer.bind(
				()-> supplierFinal.getPhoneNumber(),
				(s)-> supplierFinal.setPhoneNumber(s));
		supplierForm.add(phoneFieldContainer);
		
		var contactNameFieldContainer = new FieldContainer("Nom du contact", this);
		contactNameFieldContainer.bind(
				()-> supplierFinal.getContactName(),
				(s)-> supplierFinal.setContactName(s));
		supplierForm.add(contactNameFieldContainer);

		supplierForm.add(Box.createVerticalGlue());
		
		this.add(supplierForm, BorderLayout.WEST);

		var articlePriceList = new SupplierArticleList(supplier);
		this.add(articlePriceList, BorderLayout.EAST);
		
		this.buttonValidate.addActionListener( e->{
			try{
				dao.saveOrUpdate(supplierFinal);
				HibernateUtil.getSession().getTransaction().commit();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
				    "Veuillez v�rifier les champs en orange.",
				    "Param�tres invalides",
				    JOptionPane.WARNING_MESSAGE);
			}
			this.mainControl.getSupplierDirectory().getEntityList().refresh();
			this.mainControl.remove(this);
			this.mainControl.setSelectedComponent(this.mainControl.getSupplierDirectory());
		});
		
		this.buttonCancel.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getSupplierDirectory());
		});
	}
}