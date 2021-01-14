package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the supplier database table.
 * 
 */
@Entity
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s")
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_supplier")
	private int idSupplier;

	private String adress1;

	private String adress2;

	private String city;

	@Column(name="contact_name")
	private String contactName;

	private String country;

	private String name;

	@Column(name="zip_code")
	private String zipCode;

	//bi-directional many-to-one association to Offer
	@OneToMany(mappedBy="supplier")
	private List<Offer> offers;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="supplier")
	private List<Order> orders;

	public Supplier() {
	}

	public int getIdSupplier() {
		return this.idSupplier;
	}

	public void setIdSupplier(int idSupplier) {
		this.idSupplier = idSupplier;
	}

	public String getAdress1() {
		return this.adress1;
	}

	public void setAdress1(String adress1) {
		this.adress1 = adress1;
	}

	public String getAdress2() {
		return this.adress2;
	}

	public void setAdress2(String adress2) {
		this.adress2 = adress2;
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

}