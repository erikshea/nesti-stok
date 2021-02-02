package com.nesti.stock_manager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.dao.UserDao;

/**
 * Persistent entity class corresponding to the order table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_orders")
	private Integer idOrders;

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
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // changes to order in data source propagate to order items
	private List<OrdersArticle> ordersArticles;
	
	private static OrderDao dao;
	
	public Order() {
		this.setDateOrder(new Date()); // initialize date parameter in constructor for in-memory operations
	}

	public Order(String n, Date dateO, Date dateD) {
		this();
		setNumber(n);
		setDateOrder(dateO);
		setDateDelivery(dateD);
	}

	public Integer getIdOrders() {
		return this.idOrders;
	}

	public void setIdOrders(Integer idOrders) {
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

	/**
	 * Set supplier association from a supplier name
	 * @param n name of supplier to associate
	 */
	public void setSupplierFromName(String n) {
		var supplierDao = new SupplierDao();
		var supplier = supplierDao.findOneBy("name", n);
		setSupplier(supplier);
	}


	/**
	 * Set user association from a login
	 * @param l login of user to associate
	 */
	public void setUserFromLogin(String l) {
		var userDao = new UserDao();
		var user = userDao.findOneBy("login", l);
		setUser(user);
	}

	
	/**
	 * get subtotal: the sum of all order item prices without shipping
	 * @return
	 */
	public Double getSubTotal() {
		var result = 0.0;

		for (var oa : this.getOrdersArticles()) {
			result += oa.getQuantity() * oa.getOffer().getPrice();
		}
		return result;
	}
	

	/**
	 * calculate shipping fees according to weight of items in order
	 * @return
	 */
	public Double getShippingFees() {
		var result = 0.0;
		
		for (var oa : this.getOrdersArticles()) {
			result += oa.getOffer().getArticle().getWeight()*oa.getQuantity()*0.006;
		}

		return result;
	}
	
	
	/**
	 * get the order item associated with an Article
	 * @param article associated with order item
	 * @return single order item (only one per article in an order)
	 */
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
	
	
	/**
	 * remove the order item associated with an Article (only one per article in an order)
	 * @param article associated with order item
	 */
	public void removeOrdersArticleFor(Article article) {
		removeOrdersArticle(getOrdersArticleFor(article));
	}
	

	@Override
	public OrderDao getDao() {
		if (dao == null) {
			dao = new OrderDao();
		}
		return dao;
	}
	
	/**
	 *	Persistent entities need to override equals for consistent behavior. Uses unique field for comparison.
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Order))
            return false;
 
        var other = (Order) o;
 
        return  getNumber() != null &&
        		getNumber().equals(other.getNumber());
    }
	 
	/**
	 * Generate hashCode using unique field as base. Used in Hash-based collections.
	 */
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getNumber());
	}
	
	public Double getTotal() {
		return getSubTotal() + getShippingFees();
	}


}