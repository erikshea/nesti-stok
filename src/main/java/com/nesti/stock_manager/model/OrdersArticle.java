package com.nesti.stock_manager.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.dao.OrdersArticleDao;


/**
 * Persistent class corresponding to the orders_article table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@Table(name="orders_article")
@NamedQuery(name="OrdersArticle.findAll", query="SELECT o FROM OrdersArticle o")
public class OrdersArticle extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static OrdersArticleDao dao;
	
	/**
	 * Need to generate a composite PK at initiation of a persisted item to be able to fetch and set associations in memory
	 */
	@PrePersist
	private void prePersist() {
		if (getId() == null) {
			var pk = new OrdersArticlePK();
			pk.setIdArticle(getArticle().getIdArticle());
			pk.setIdOrders(getOrder().getIdOrders());
			setId(pk);
		}
	}
	
	@EmbeddedId
	private OrdersArticlePK id;

	private Integer quantity;

	//bi-directional many-to-one association to Article
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_article", insertable=false, updatable=false)
	private Article article;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="id_orders", insertable=false, updatable=false)
	private Order order;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof OrdersArticle))
            return false;
 
        var other = (OrdersArticle) o;
 
        return  getId() != null &&
        		getId().equals(other.getId());
    }
	 
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getId());
	}
	
	
	public OrdersArticle() {
	}

	public OrdersArticle(Integer q) {
		this();
		setQuantity(q);
	}

	public OrdersArticlePK getId() {
		return this.id;
	}

	public void setId(OrdersArticlePK id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setQuantity(String quantityString) {
		try {
			this.quantity = Integer.parseInt(quantityString);
		}catch(Exception e) {}
	}
	
	
	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public void setOrderFromNumber(String n) {
		var orderDao = new OrderDao();
		var order = orderDao.findOneBy("number", n);
		setOrder(order);
	}
	
	public void setArticleFromCode(String c) {
		var articleDao = new ArticleDao();
		var article = articleDao.findOneBy("code", c);
		setArticle(article);
	}
	
	public Offer getOfferAt(Date date) {
		return getArticle().getOfferAt(date, getSupplier());
	}
	
	public Supplier getSupplier() {
		return this.getOrder().getSupplier();
	}
	
	@Override
	public OrdersArticleDao getDao() {
		if (dao == null) {
			dao = new OrdersArticleDao();
		}
		return dao;
	}

	
	public Offer getOffer() {
		var article = this.getArticle();
		var supplier = getOrder().getSupplier();
		
		var offer = article.getOfferAt(getOrder().getDateOrder(), supplier);
		return offer;
	}

}