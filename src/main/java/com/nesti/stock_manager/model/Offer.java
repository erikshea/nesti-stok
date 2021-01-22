package com.nesti.stock_manager.model;

import java.io.Serializable;
import javax.persistence.*;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.SupplierDao;

import java.util.Date;

/**
 * The persistent class for the offers database table.
 * 
 */
@Entity
@Table(name = "offers")
@NamedQuery(name = "Offer.findAll", query = "SELECT o FROM Offer o")
public class Offer implements Serializable {
	private static final long serialVersionUID = 1L;
   @PrePersist
   private void prePersist() {
       if (getId() == null) {
    	   var pk = new OfferPK();
           pk.setIdArticle(getArticle().getIdArticle());
           pk.setIdSupplier(getSupplier().getIdSupplier());
           pk.setStartDate(getStartDate());
           setId(pk);
       }
   }
	   
	@EmbeddedId
	private OfferPK id;

	private Double price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", insertable = false, updatable = false)
	private Date startDate;

	// bi-directional many-to-one association to Article
	@ManyToOne
	@JoinColumn(name = "id_article", insertable = false, updatable = false)
	private Article article;

	// bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name = "id_supplier", insertable = false, updatable = false)
	private Supplier supplier;

	public Offer() {
		this.setStartDate(new Date());
	}

	public Offer(Double p) {
		this();
		setPrice(p);
	}

	public OfferPK getId() {
		return this.id;
	}

	public void setId(OfferPK id) {
		this.id = id;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public void setSupplierFromName(String n) {
		var supplierDao = new SupplierDao();
		var supplier = supplierDao.findOneBy("name", n);
		setSupplier(supplier);
	}
	
	public void setArticleFromCode(String c) {
		var articleDao = new ArticleDao();
		var article = articleDao.findOneBy("code", c);
		setArticle(article);
	}

}