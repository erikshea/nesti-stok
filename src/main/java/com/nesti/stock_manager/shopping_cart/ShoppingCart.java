package com.nesti.stock_manager.shopping_cart;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import com.nesti.stock_manager.controller.MainWindowControl;
import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.entity.Article;
import com.nesti.stock_manager.entity.Offer;
import com.nesti.stock_manager.entity.Order;
import com.nesti.stock_manager.entity.OrdersArticle;
import com.nesti.stock_manager.entity.Supplier;
import com.nesti.stock_manager.entity.User;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.UnavailableArticleException;

/**
 * @author Emmanuelle Gay, Erik Shea
 * Shopping cart class holds current (non-submitted) orders, and order-related logic
 *
 */
/**
 * @author hoops
 *
 */
public class ShoppingCart {

	protected MainWindowControl mainController;
	protected HashMap<Supplier, Order> orders; // Currrent (not submitted orders)
	protected User user; // User with which to associate order


	public ShoppingCart(MainWindowControl c, User u) {
		user = u;
		mainController = c;
		orders = new HashMap<>();
	}
	
	public ShoppingCart() {
		
	}

	/**
	 * Add an article to order list
	 * @param article
	 * @param quantity
	 */
	public void addArticle(Article article, int quantity) {
		var offers = article.getLatestOffers();
		var supplier = article.getDefaultSupplier();
		addOffer(offers.get(supplier), quantity);
	}

	/**
	 * Add an article to order list, with a string as quantity
	 * 
	 * @param article
	 * @param quantityString
	 * @throws InvalidParameterException if quantity string invalid
	 * @throws UnavailableArticleException if article unavailable
	 */
	public void addArticle(Article article, String quantityString) throws InvalidParameterException, UnavailableArticleException{
		if (!isNumeric(quantityString) || Double.parseDouble(quantityString) < 0) { // If string unparseable to double, or to a negative number
			throw new InvalidParameterException();
		} else if (article.getDefaultSupplier() == null) { // If no supplier currently offers article
			throw new UnavailableArticleException();
		} else {
			var quantity = (int) Double.parseDouble(quantityString);
			this.addArticle(article, quantity);
		}
	}

	
	/**
	 * Add offer to order list 
	 * @param offer
	 * @param quantity
	 */
	public void addOffer(Offer offer, int quantity) {
		var supplier = offer.getSupplier();
		if (!orders.containsKey(supplier)) { // If no order exists for offer's supplier
			var order = new Order(); // Create a new one
			order.setSupplier(supplier);
			order.setUser(user);
			orders.put(supplier, order);
		}
		// Try to find an order line that matches added offer
		var existingOrderLine = orders.get(supplier).getOrdersArticleFor(offer.getArticle());
		if (existingOrderLine != null) { // If offer already added
			var newQuantity = quantity + existingOrderLine.getQuantity(); 
			existingOrderLine.setQuantity(newQuantity);// Increment quantity
			mainController.getShoppingCartDirectory().refreshTable();
		} else { // If no corresponding offer added
			var currentOrder = orders.get(supplier); // get order for offer's supplier
			var orderLine = new OrdersArticle();// create new order line
			orderLine.setArticle(offer.getArticle());
			orderLine.setQuantity(quantity);
			currentOrder.addOrdersArticle(orderLine);
		}

	}

	/**
	 * Remove an offer from article list
	 * @param offer to remove
	 */
	public void removeOffer(Offer offer) {
		var order = orders.get(offer.getSupplier()); // Get order that corresponds to offer's supplier
		order.removeOrdersArticleFor(offer.getArticle()); // Remove corresponding order line
		
		// If order is now empty, remove it
		if (order.getOrdersArticles().size() == 0) { 
			orders.remove(offer.getSupplier());
		}
	}
	
	/**
	 * Remove an order line that corresponds to an article, and supplier
	 * @param article
	 * @param supplier
	 */
	public void removeOrdersArticle(Article article, Supplier supplier) {
		if (orders.containsKey(supplier)) { // Check that order exists for supplier
			orders.get(supplier).removeOrdersArticleFor(article); // if so, remove order line that corresponds to article
			
			// If order is now empty, remove it
			if (orders.get(supplier).getOrdersArticles().size() == 0) {
				orders.remove(supplier);
			}
		}
	}
	
	/**
	 * Remove order line from orders
	 * @param oa
	 */
	public void removeOrdersArticle(OrdersArticle oa) {
		var supplier = oa.getSupplier();
		oa.getOrder().removeOrdersArticle(oa); // Remove order line from corresponding order
		
		// If order is now empty, remove it
		if (orders.get(supplier).getOrdersArticles().size() == 0) {
			orders.remove(supplier);
		}
	}
	
	/**
	 * Checks whether a number string is numeric
	 * @param strNum number string
	 * @return true if numeric
	 */
	public static boolean isNumeric(String strNum) {
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	
	/**
	 * Saves all orders to database
	 * @throws Exception
	 */
	public void saveOrders() throws Exception {
		var orderDao = new OrderDao();

		getOrders().values().forEach(o -> { // loop through each current order
			var id = orderDao.save(o);	// Save it to recover ID
			var number = (int) (Math.random() * 90 + 10); // Generate random part of order number
			o.setNumber((int) id + "00" + String.valueOf(number)); // order number generated from id, and random part

			// Loop through each order line
			o.getOrdersArticles().forEach(oa -> {
				// Update corresponding article quantity
				oa.getArticle().setStock(oa.getArticle().getStock() + oa.getQuantity());
			});

			// Cascade saves order, which saves order item, which updates article (stock)
			orderDao.saveOrUpdate(o);
		});

		getOrders().clear();
		HibernateUtil.getSession().getTransaction().commit();
	}

	/**
	 * Get subtotal for all orders (doesn't include shipping)
	 * @return
	 */
	public Double getSubTotal() {
		var result = 0.0;
		for (var o : orders.values()) { 
			result += o.getSubTotal(); // subtotal is the sum of all order subtotals
		}
		return result;
	}

	/**
	 * Show shipping fees
	 * @return
	 */
	public Double getShipingFees() {
		var result = 0.0;
		for (var o : orders.values()) {
			// shipping fees consist of the sum of all order shipping fees
			result += o.getShippingFees(); 
		}
		return result;
	}

	/**
	 * Get total
	 * @return sum of subtotal and shipping fees
	 */
	public Double getTotal() {
		return getSubTotal() + getShipingFees();
	}
	


	/**
	 * Get all order lines as a flat array list
	 * @return array list of all order lines
	 */
	public ArrayList<OrdersArticle> getAllOrdersArticle() {
		var orderlines = new ArrayList<OrdersArticle>();
		orders.values().forEach(o -> {
			orderlines.addAll(o.getOrdersArticles());
		});
		return orderlines;
	}

	
	/**
	 * Clear all orders
	 */
	public void clearAll() {
		orders.clear();
	}
	
	
	public HashMap<Supplier, Order> getOrders() {
		return orders;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
