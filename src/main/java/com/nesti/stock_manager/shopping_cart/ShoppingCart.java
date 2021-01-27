package com.nesti.stock_manager.shopping_cart;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import com.nesti.stock_manager.controller.MainWindowControl;
import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.Order;
import com.nesti.stock_manager.model.OrdersArticle;
import com.nesti.stock_manager.model.Supplier;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.UnavailableArticleException;

public class ShoppingCart {

	protected MainWindowControl mainController;
	protected HashMap<Supplier, Order> orders;

	public ShoppingCart(MainWindowControl c) {
		mainController = c;
		orders = new HashMap<>();
	}
	
	public ShoppingCart() {
		
	}

	public void addArticle(Article article, int quantity) {
		var offers = article.getLatestOffers();
		var supplier = article.getDefaultSupplier();
		addOffer(offers.get(supplier), quantity);
	}

	public void addArticle(Article article, String quantityString) throws InvalidParameterException, UnavailableArticleException{
		if (!isNumeric(quantityString) || Double.parseDouble(quantityString) < 0) {
			throw new InvalidParameterException();
		} else if (article.getDefaultSupplier() == null) {
			throw new UnavailableArticleException();
		} else {
			var quantity = (int) Double.parseDouble(quantityString);
			this.mainController.getShoppingCart().addArticle(article, quantity);
		}

	}

	public void addOffer(Offer offer, int quantity) {
		var supplier = offer.getSupplier();
		if (!orders.containsKey(supplier)) {
			var order = new Order();
			order.setSupplier(supplier);
			var loggedInUser = mainController.getConnectedUser();
			order.setUser(loggedInUser);
			orders.put(supplier, order);
		}
		var existingOrderLine = orders.get(supplier).getOrdersArticleFor(offer.getArticle());
		if (existingOrderLine != null) {
			var newQuantity = quantity + existingOrderLine.getQuantity();
			existingOrderLine.setQuantity(newQuantity);
			mainController.getShoppingCartDirectory().refreshTable();
		} else {
			var currentOrder = orders.get(supplier);
			var orderLine = new OrdersArticle();
			orderLine.setArticle(offer.getArticle());
			orderLine.setQuantity(quantity);
			currentOrder.addOrdersArticle(orderLine);

			mainController.getShoppingCartDirectory().addRow(orderLine);
		}

	}

	public void removeOffer(Offer offer) {
		var order = orders.get(offer.getSupplier());
		order.removeOrdersArticleFor(offer.getArticle());
		
		if (order.getOrdersArticles().size() == 0) {
			orders.remove(offer.getSupplier());
		}
	}
	
	public void removeOrdersArticle(Article article, Supplier supplier) {
		if (orders.containsKey(supplier)) {
			orders.get(supplier).removeOrdersArticleFor(article);
			if (orders.get(supplier).getOrdersArticles().size() == 0) {
				orders.remove(supplier);
			}
		}
	}
	
	public static boolean isNumeric(String strNum) {
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	public void saveOrders() throws Exception {
		var orderDao = new OrderDao();

		getOrders().values().forEach(o -> {
			var id = orderDao.save(o);
			var number = (int) (Math.random() * 90 + 10);
			o.setNumber((int) id + "00" + String.valueOf(number));

			o.getOrdersArticles().forEach(oa -> {
				oa.getArticle().setStock(oa.getArticle().getStock() + oa.getQuantity());
			});

			// Cascade saves order, which saves order item, which updates article (stock)
			orderDao.saveOrUpdate(o);
		});

		getOrders().clear();
		HibernateUtil.getSession().getTransaction().commit();
	}

	public Double getSubTotal() {
		var result = 0.0;
		for (var o : orders.values()) {
			result += o.getSubTotal();
		}
		return result;
	}

	public Double getShipingFees() {
		var result = 0.0;
		for (var o : orders.values()) {
			result += o.getShippingFees();
		}
		return result;
	}

	public Double getTotal() {
		return getSubTotal() + getShipingFees();
	}
	
	public HashMap<Supplier, Order> getOrders() {
		return orders;
	}

	public ArrayList<OrdersArticle> getAllOrdersArticle() {
		var orderlines = new ArrayList<OrdersArticle>();
		orders.values().forEach(o -> {
			orderlines.addAll(o.getOrdersArticles());
		});
		return orderlines;
	}

	
	public void clearAll() {
		orders.clear();
	}
}
