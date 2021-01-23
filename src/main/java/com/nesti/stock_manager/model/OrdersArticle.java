package com.nesti.stock_manager.model;

import java.io.Serializable;

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
 * The persistent class for the orders_article database table.
 * 
 */
@Entity
@Table(name="orders_article")
@NamedQuery(name="OrdersArticle.findAll", query="SELECT o FROM OrdersArticle o")
public class OrdersArticle extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static OrdersArticleDao dao;
	
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

	private int quantity;

	//bi-directional many-to-one association to Article
	@ManyToOne
	@JoinColumn(name="id_article", insertable=false, updatable=false)
	private Article article;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="id_orders", insertable=false, updatable=false)
	private Order order;

	public OrdersArticle() {
	}

	public OrdersArticle(int q) {
		setQuantity(q);
	}

	public OrdersArticlePK getId() {
		return this.id;
	}

	public void setId(OrdersArticlePK id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	
	@Override
	public OrdersArticleDao getDao() {
		if (dao == null) {
			dao = new OrdersArticleDao();
		}
		return dao;
	}
}