package com.nesti.stock_manager.model;

import java.io.Serializable;
import com.nesti.stock_manager.dao.*;
import com.nesti.stock_manager.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the article database table.
 * 
 */
@Entity
@NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a")
public class Article extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_article")
	private int idArticle;

	private String code;

	private String ean;

	private String name;

	private double quantity;

	private int stock;

	private double weight;

	// bi-directional many-to-one association to Packaging
	@ManyToOne
	@JoinColumn(name = "id_packaging")
	private Packaging packaging;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;

	// bi-directional many-to-one association to Unit
	@ManyToOne
	@JoinColumn(name = "id_unit")
	private Unit unit;

	// bi-directional many-to-one association to Offer
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	private List<Offer> offers;

	// bi-directional many-to-one association to OrdersArticle
	@OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
	private List<OrdersArticle> ordersArticles;

	//bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name="id_default_supplier")
	private Supplier supplier;

	
	private static ArticleDao dao;
	
	public Article() {
	}

	public Article(String code, String name, String ean, double weight, double quantity, int stock) {
		setCode(code);
		setName(name);
		setEan(ean);
		setWeight(weight);
		setQuantity(quantity);
		setStock(stock);
	}

	
	public HashMap<Supplier,Offer> getLatestOffers(){
		HashMap<Supplier,Offer> offersBySupplier = new HashMap<>();

		if ( getOffers() != null && getOffers().size()> 0) {
			getOffers().forEach(oa->{
				var offerInMap = offersBySupplier.get(oa.getSupplier());

				if ( 	offerInMap == null
					||  offerInMap.getStartDate() == null
					||  oa.getStartDate() == null 
					||  offerInMap.getStartDate().compareTo(oa.getStartDate()) < 0  ) {
					offersBySupplier.put(oa.getSupplier(), oa);
				}
					
				offersBySupplier.put(oa.getSupplier(), oa);
			});
		}

		return offersBySupplier;
	}

	public Offer getLowestOffer() {
		Offer result = null;
		var offers = getLatestOffers().values();
		if (offers != null && offers.size() > 0) {
			for (var offer:offers) {
				if (offer.getPrice() != -1 && (result == null || result.getPrice() > offer.getPrice()) ) {
					result = offer;
				}
			}
		}
		return result;
	}


	
	
	public Offer getLowestOfferHQL() {
		var hql = "Select o from Offer o "
				+ "WHERE o.price = (SELECT MIN(oo.price) FROM Offer oo WHERE oo.id.idArticle = :id_article) ";
		var query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("id_article", this.getIdArticle());
		var results = query.list();
		Offer result = null;
		if (results.size() > 0) {
			result = (Offer) results.get(0);
		}
		return result;
	}
	
	
	public Offer getHighestOffer() {
		var hql = "Select o from Offer o "
				+ "WHERE o.price = (SELECT MAX(oo.price) FROM Offer oo WHERE oo.id.idArticle = :id_article) ";
		var query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("id_article", this.getIdArticle());
		var results = query.list();
		Offer result = null;
		if (results.size() > 0) {
			result = (Offer) results.get(0);
		}
		return result;
	}

	public int getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEan() {
		return this.ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Packaging getPackaging() {
		return this.packaging;
	}

	public void setPackaging(Packaging packaging) {
		this.packaging = packaging;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Unit getUnit() {
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public void setUnitFromName(String n) {
		var unitDao = new UnitDao();
		var unit = unitDao.findOneBy("name", n);
		setUnit(unit);
	}

	public void setPackagingFromName(String n) {
		var packagingDao = new PackagingDao();
		var packaging = packagingDao.findOneBy("name", n);
		setPackaging(packaging);
	}
	
	public void setProductFromReference(String r) {
		var productDao = new ProductDao();
		var product = productDao.findOneBy("reference", r);
		setProduct(product);
	}

	public List<Offer> getOffers() {
		return this.offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public Offer addOffer(Offer offer) {
		getOffers().add(offer);
		offer.setArticle(this);

		return offer;
	}

	public Offer removeOffer(Offer offer) {
		getOffers().remove(offer);
		offer.setArticle(null);

		return offer;
	}

	public List<OrdersArticle> getOrdersArticles() {
		return this.ordersArticles;
	}

	public void setOrdersArticles(List<OrdersArticle> ordersArticles) {
		this.ordersArticles = ordersArticles;
	}

	public OrdersArticle addOrdersArticle(OrdersArticle ordersArticle) {
		getOrdersArticles().add(ordersArticle);
		ordersArticle.setArticle(this);

		return ordersArticle;
	}

	public OrdersArticle removeOrdersArticle(OrdersArticle ordersArticle) {
		getOrdersArticles().remove(ordersArticle);
		ordersArticle.setArticle(null);

		return ordersArticle;
	}

	public String getProductName() {
		return this.getProduct().getName();
	}
	
	public Supplier getDefaultSupplier() {
		Supplier result = null;
		
		if (this.supplier == null) {
			var lowestOffer = this.getLowestOffer();

			if (lowestOffer != null) {
				result = lowestOffer.getSupplier();
			}
		} else {
			System.out.println("test:");
			result = this.supplier;
		}
		
		return result;
	}

	public void setDefaultSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public boolean containsUtensil(){
		return this.getProduct() instanceof Utensil;
	}
	
	@Override
	public ArticleDao getDao() {
		if (dao == null) {
			dao = new ArticleDao();
		}
		return dao;
	}
	
	
	public Article duplicate() {
		final String suffix = "_NEW";
		
		var newArticle = new Article();
		newArticle.setCode(this.getCode()+suffix);
		newArticle.setEan(this.getEan()+suffix);
		newArticle.setName(this.getName()+suffix);
		newArticle.setProduct(this.getProduct());
		newArticle.setQuantity(this.getQuantity());
		newArticle.setWeight(this.getWeight());
		newArticle.setStock(this.getStock());

		return newArticle;
	}
	
	
	public static Article createEmpty() {
		var newArticle = new Article();
		newArticle.setUnit( (new UnitDao()).findOneBy("name", "pièce") );
		newArticle.setPackaging( (new PackagingDao()).findOneBy("name", "boîte") );
		
		return newArticle;
	}
}