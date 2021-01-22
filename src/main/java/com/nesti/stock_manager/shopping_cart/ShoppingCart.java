package com.nesti.stock_manager.shopping_cart;

import java.util.HashMap;

import com.nesti.stock_manager.controller.MainWindowControl;
import com.nesti.stock_manager.model.*;

public class ShoppingCart {

	protected MainWindowControl mainController;
	protected HashMap<Supplier, Order> orders;

	public ShoppingCart(MainWindowControl c) {
		mainController = c;
		orders = new HashMap<>();
	}

	public void addArticle(Article article, int quantity) {
		var offers = article.getLatestOffers();
		var supplier = article.getDefaultSupplier();
		addOffer(offers.get(supplier),quantity);
	}

	public void addOffer(Offer offer, int quantity) {
		var supplier = offer.getSupplier();
		if (!orders.containsKey(supplier)) {
			var order = new Order();
			var loggedInUser = mainController.getConnectedUser();
			order.setUser(loggedInUser);
			orders.put(supplier, order);
		}
		var currentOrder = orders.get(supplier);
		var orderLine = new OrdersArticle();
		orderLine.setArticle(offer.getArticle());
		orderLine.setQuantity(quantity);
		currentOrder.addOrdersArticle(orderLine);

	}
}
