package com.nesti.stock_manager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.dao.UserDao;

/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_orders")
	private int idOrders;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_delivery")
	private Date dateDelivery;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_order")
	private Date dateOrder;

	private String number;

	// bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name = "id_supplier")
	private Supplier supplier;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	// bi-directional many-to-one association to OrdersArticle
	@OneToMany(mappedBy = "order")
	private List<OrdersArticle> ordersArticles;
	
	private static OrderDao dao;
	
	public Order() {
	}

	public Order(String n, Date o, Date d) {
		this();
		setNumber(n);
		setDateOrder(o);
		setDateDelivery(d);
	}

	public int getIdOrders() {
		return this.idOrders;
	}

	public void setIdOrders(int idOrders) {
		this.idOrders = idOrders;
	}

	public Date getDateDelivery() {
		return this.dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public Date getDateOrder() {
		return this.dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrdersArticle> getOrdersArticles() {
		if (this.ordersArticles == null) {
			this.ordersArticles = new ArrayList<OrdersArticle>();
		}
		return this.ordersArticles;
	}

	public void setOrdersArticles(List<OrdersArticle> ordersArticles) {
		this.ordersArticles = ordersArticles;
	}

	public OrdersArticle addOrdersArticle(OrdersArticle ordersArticle) {
		if (getOrdersArticles() == null) {
			ordersArticles = new ArrayList<>();
		}
		getOrdersArticles().add(ordersArticle);
		ordersArticle.setOrder(this);

		return ordersArticle;
	}

	public OrdersArticle removeOrdersArticle(OrdersArticle ordersArticle) {
		getOrdersArticles().remove(ordersArticle);
		ordersArticle.setOrder(null);

		return ordersArticle;
	}

	public void setSupplierFromName(String n) {
		var supplierDao = new SupplierDao();
		var supplier = supplierDao.findOneBy("name", n);
		setSupplier(supplier);
	}

	public void setUserFromLogin(String l) {
		var userDao = new UserDao();
		var user = userDao.findOneBy("login", l);
		setUser(user);
	}

	public Double getSubTotal() {
		return 0.0;
	}

	public Double getSheepingFees() {
		return 0.0;
	}

	public OrdersArticle getOrdersArticleFor(Article article) {
		OrdersArticle result = null;
		for (var oa : getOrdersArticles()) {
			if (oa.getArticle().equals(article)) {
				result = oa;
			}
		}
		;
		return result;
	}
	
	@Override
	public OrderDao getDao() {
		if (dao == null) {
			dao = new OrderDao();
		}
		return dao;
	}
}