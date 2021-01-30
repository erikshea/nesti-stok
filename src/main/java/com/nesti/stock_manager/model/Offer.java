package com.nesti.stock_manager.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.SupplierDao;

/**
 * Persistent class corresponding to the offers table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@Table(name = "offers")
@NamedQuery(name = "Offer.findAll", query = "SELECT o FROM Offer o")
public class Offer implements Serializable {
	private static final long serialVersionUID = 1L;
	
   /**
	 * Need to generate a composite PK at initiation of a persisted item to be able to fetch and set associations in memory
	 */
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

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Offer))
            return false;
 
        var other = (Offer) o;
 
        return  getId() != null &&
        		getId().equals(other.getId());
    }
	 
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getId());
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

	public Boolean isValid() {
		return this.getPrice() == null;
	}
	
	
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
	

	


}