package com.nesti.stock_manager.model;

import java.io.Serializable;
import javax.persistence.*;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.SupplierDao;

import java.util.List;


/**
 * The persistent class for the supplier database table.
 * 
 */
@Entity
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s")
public class Supplier  extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_supplier")
	private int idSupplier;

	private String address1;

	private String address2;

	private String city;

	@Column(name="contact_name")
	private String contactName;

	private String country;

	private String name;

	@Column(name="phone_number")
	private String phoneNumber;

	@Column(name="zip_code")
	private String zipCode;

	//bi-directional many-to-one association to Offer
	@OneToMany(mappedBy="supplier", cascade = CascadeType.REMOVE)
	private List<Offer> offers;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="supplier", cascade = CascadeType.REMOVE)
	private List<Order> orders;

	//bi-directional many-to-one association to Default
	@OneToMany(mappedBy="supplier", cascade = CascadeType.REMOVE)
	private List<IsDefault> isDefault;
	
	private static SupplierDao dao;
	
	public Supplier() {
	}

	public int getIdSupplier() {
		return this.idSupplier;
	}

	public void setIdSupplier(int idSupplier) {
		this.idSupplier = idSupplier;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<Offer> getOffers() {
		return this.offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public Offer addOffer(Offer offer) {
		getOffers().add(offer);
		offer.setSupplier(this);

		return offer;
	}

	public Offer removeOffer(Offer offer) {
		getOffers().remove(offer);
		offer.setSupplier(null);

		return offer;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setSupplier(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setSupplier(null);

		return order;
	}

	public List<IsDefault> getDefaults() {
		return this.isDefault;
	}

	public void setDefaults(List<IsDefault> defaults) {
		this.isDefault = defaults;
	}

	public IsDefault addDefault(IsDefault d) {
		getDefaults().add(d);
		d.setSupplier(this);

		return d;
	}

	public IsDefault removeDefault(IsDefault d) {
		getDefaults().remove(d);
		d.setSupplier(null);

		return d;
	}
	
	public SupplierDao getDao() {
		if (dao == null) {
			dao = new SupplierDao();
		}
		return dao;
	}
}