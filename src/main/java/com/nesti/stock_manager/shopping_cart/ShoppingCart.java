package com.nesti.stock_manager.shopping_cart;

import java.util.ArrayList;
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
		addOffer(offers.get(supplier), quantity);
		
	//	calculateSubTotal(offers.get(supplier));
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
			mainController.getShoppingCartDirectory().getEntityList().refresh();
		} else {
			var currentOrder = orders.get(supplier);
			var orderLine = new OrdersArticle();
			orderLine.setArticle(offer.getArticle());
			orderLine.setQuantity(quantity);
			currentOrder.addOrdersArticle(orderLine);
			
			mainController.getShoppingCartDirectory().getEntityList().addRow(orderLine);
	

		}

	}


	public ArrayList<OrdersArticle>  getAllOrdersArticle() {
		var orderlines = new ArrayList<OrdersArticle>();
		orders.values().forEach(o -> {
			orderlines.addAll(o.getOrdersArticles());
		});
		return orderlines;
	}
}
