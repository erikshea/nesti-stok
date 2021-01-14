package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orders_article database table.
 * 
 */
@Entity
@Table(name="orders_article")
@NamedQuery(name="OrdersArticle.findAll", query="SELECT o FROM OrdersArticle o")
public class OrdersArticle implements Serializable {
	private static final long serialVersionUID = 1L;

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

}