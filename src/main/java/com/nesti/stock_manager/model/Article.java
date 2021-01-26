package com.nesti.stock_manager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.PackagingDao;
import com.nesti.stock_manager.dao.ProductDao;
import com.nesti.stock_manager.dao.UnitDao;
import com.nesti.stock_manager.util.HibernateUtil;

/**
 * The persistent class for the article database table.
 * 
 */
@Entity
@NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a")
public class Article extends BaseEntity implements Serializable,Flagged {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_article")
	private Integer idArticle;

	private String code;

	private String ean;

	private String name;

	private double quantity;

	private int stock;

	private double weight;
	
	private String flag;
	
	// bi-directional many-to-one association to Packaging
	@ManyToOne
	@JoinColumn(name = "id_packaging")
	private Packaging packaging;

	// bi-directional many-to-one association to Product
	@ManyToOne(cascade = CascadeType.ALL)
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
		this.setFlag(BaseDao.DEFAULT);
	}

	public Article(String code, String name, String ean, double weight, double quantity, int stock) {
		this();
		setCode(code);
		setName(name);
		setEan(ean);
		setWeight(weight);
		setQuantity(quantity);
		setStock(stock);
	}

	
	public Offer getLowestOffer() {
		Offer result = null;
		var offers = getCurrentOffers().values();
		
		for (var offer:offers) {
			if (result == null || result.getPrice() > offer.getPrice()) {
				result = offer;
			}
		}
		return result;
	}
	
	public Offer getLatestDefaultOffer() {
		Offer latestOffer = null;
		var offers = getCurrentOffers().values();
		
		for (var offer:offers) {
			if (	this.getDefaultSupplier() != null
				&&  offer.getSupplier().equals(this.getDefaultSupplier()) 
				&&  ( latestOffer == null || latestOffer.getStartDate().before(offer.getStartDate()))	) {
				latestOffer = offer;
			}
		}
		return latestOffer;
	}

	
	
	 public Offer getOfferAt(Date date, Supplier s) {
	        var hql = "SELECT o FROM Offer o"
	                + "     WHERE o.article = :p_article "
	                + "        AND o.supplier = :p_supplier"
	                + "        AND o.price IS NOT NULL"
	                + "        AND o.startDate < :date"
	                + "        ORDER BY o.startDate DESC";
	        var query = HibernateUtil.getSession().createQuery(hql);
	        query.setParameter("p_article", this);
	        query.setParameter("p_supplier", s);
	        query.setParameter("date", date);
	        var results = query.list();
	        Offer result = null;
	        if (results.size() > 0) {
	            result = (Offer) results.get(0);
	        }
	        return result;
	    }


	public HashMap<Supplier,Offer> getCurrentOffers2() {
		var hql = "SELECT o FROM Offer o "
				+ "WHERE o.article = :p_article" 
				+ "	AND o.price IS NOT NULL"
				+ "	AND o.startDate = (SELECT MAX(oo.startDate) FROM Offer oo"
				+ "	 					WHERE oo.article = o.article"
				+ "						AND oo.supplier = o.supplier)";
		var query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("p_article", this);
		@SuppressWarnings("unchecked")
		List<Offer> results = query.list();
		var offersBySupplier = new HashMap<Supplier,Offer>();
		results.forEach( o-> offersBySupplier.put(o.getSupplier(), o));
		
		return offersBySupplier;
	}

	
	public HashMap<Supplier,Offer> getLatestOffers(){
		var offersBySupplier = new HashMap<Supplier,Offer>();
		
		this.getOffers().forEach(o->{
			if (	!offersBySupplier.containsKey( o.getSupplier() )
				||   offersBySupplier.get(o.getSupplier()).getStartDate().before(o.getStartDate()) ){
					offersBySupplier.put(o.getSupplier(), o);
				}
		});
		
		return offersBySupplier;
	}

	/*
	 * Used with a detached instance in BasePriceLists, so no HQL
	 */
	public HashMap<Supplier,Offer> getCurrentOffers() {
		var offersBySupplier = new HashMap<Supplier,Offer>();

		getLatestOffers().forEach((s,o)->{
			if(o.getPrice()!=null) {
				offersBySupplier.put(s,o);
			}
		});
		
		return offersBySupplier;
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

	public Integer getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(Integer idArticle) {
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
			var lowestOffer = getLowestOffer();

			if (lowestOffer != null) {
				result = lowestOffer.getSupplier();
			}
		} else {
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
		var newArticle = new Article();
		newArticle.setCode(getDuplicatedFieldValue("code"));
		newArticle.setEan(getDuplicatedFieldValue("ean"));
		newArticle.setName(getDuplicatedFieldValue("name"));
		newArticle.setProduct(this.getProduct());
		newArticle.setQuantity(this.getQuantity());
		newArticle.setWeight(this.getWeight());
		newArticle.setStock(this.getStock());
		newArticle.setUnit(this.getUnit());
		newArticle.setPackaging(this.getPackaging());
		this.getOffers().forEach(o->{
			var newOffer = new Offer();
			newOffer.setSupplier(o.getSupplier());
			newOffer.setPrice(o.getPrice());
			newOffer.setStartDate(o.getStartDate());
			newArticle.addOffer(newOffer);
		});
		newArticle.setSupplier(getSupplier());
		newArticle.setFlag(this.getFlag());
		return newArticle;
	}
	
	
	public static Article createEmpty() {
		var newArticle = new Article();
		newArticle.setUnit( (new UnitDao()).findOneBy("name", "pièce") );
		newArticle.setPackaging( (new PackagingDao()).findOneBy("name", "boîte") );
		
		return newArticle;
	}
	


	public String getFlag() {
		return this.flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Article))
            return false;
 
        var other = (Article) o;
 
        return  getCode() != null &&
        		getCode().equals(other.getCode());
    }
	 
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getCode());
	}
	
	
	protected void setSupplier(Supplier s) {
		this.supplier = s;
	}
	
	protected Supplier getSupplier() {
		return this.supplier;
	}
	
}