package com.nesti.stock_manager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.SupplierDao;


/**
 * Persistent class corresponding to the supplier table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s")
public class Supplier  extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_supplier")
	private Integer idSupplier;

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
	@OneToMany(mappedBy="supplier", cascade = CascadeType.ALL)
	private List<Offer> offers;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="supplier")
	private List<Order> orders;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="supplier")
	private List<Article> articles;
	
	private String flag;
	
	private static SupplierDao dao;
	
	public Supplier() {
		this.setFlag(BaseDao.DEFAULT);
	}

	
	/**
	 * Get most recent offers.
	 * 
	 * @return hashmap of most recent offers with corresponding articles as keys
	 */
	public HashMap<Article,Offer> getLatestOffers(){
		var offersByArticle = new HashMap<Article,Offer>();
		
		this.getOffers().forEach(o->{
			if (	!offersByArticle.containsKey( o.getArticle() )
				||   offersByArticle.get(o.getArticle()).getStartDate().before(o.getStartDate()) ){
				offersByArticle.put(o.getArticle(), o);
			}
		});
		
		return offersByArticle;
	}

	/**
	 * Get currently active offers (price not null).
	 * 
	 * @return hashmap of active offers with corresponding articles as keys
	 */
	public HashMap<Article,Offer> getCurrentOffers() {
		var offersByArticle = new HashMap<Article,Offer>();

		getLatestOffers().forEach((s,o)->{
			if(o.getPrice()!=null) {
				offersByArticle.put(s,o);
			}
		});
		
		return offersByArticle;
	}
	
	public Supplier(String name, String adress1, String adress2, String zipCode, String city, String contactName,
			String country, String phone) {
		this();
		setName(name);
		setAddress1(adress1);
		setAddress2(adress2);
		setZipCode(zipCode);
		setCity(city);
		setContactName(contactName);
		setCountry(country);
		setPhoneNumber(phone);
	}

	public Integer getIdSupplier() {
		return this.idSupplier;
	}

	public void setIdSupplier(Integer idSupplier) {
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
		if (this.offers == null) {
			this.offers = new ArrayList<Offer>();
		}
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

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Article addArticle(Article article) {
		getArticles().add(article);
		article.setDefaultSupplier(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setDefaultSupplier(null);

		return article;
	}
	
	public SupplierDao getDao() {
		if (dao == null) {
			dao = new SupplierDao();
		}
		return dao;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Supplier))
            return false;
 
        var other = (Supplier) o;
 
        return  getName() != null &&
        		getName().equals(other.getName());
    }
	 
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getName());
	}
	
	public Supplier duplicate() {
		var duplicate = new Supplier();
		duplicate.setName(getDuplicatedFieldValue("name"));
		duplicate.setAddress1(this.getAddress1());
		duplicate.setAddress2(this.getAddress2());
		duplicate.setCity(this.getCity());
		duplicate.setContactName(this.getContactName());
		duplicate.setCountry(this.getCountry());
		duplicate.setPhoneNumber(this.getPhoneNumber());
		duplicate.setZipCode(this.getZipCode());
		duplicate.setFlag(this.getFlag());
		
		
		this.getOffers().forEach(o->{
			var newOffer = new Offer();
			newOffer.setArticle(o.getArticle());
			newOffer.setPrice(o.getPrice());
			newOffer.setStartDate(o.getStartDate());
			duplicate.addOffer(newOffer);
		});
		
		return duplicate;
	}
}