package com.nesti.stock_manager.shopping_cart;

import java.util.ArrayList;
import java.util.HashMap;

import com.nesti.stock_manager.controller.MainWindowControl;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.Order;
import com.nesti.stock_manager.model.OrdersArticle;
import com.nesti.stock_manager.model.Supplier;

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


	public Double getTotal() {
		var result = 0.0;
		for(var o : orders.values()){
			result += o.getSubTotal();
		}
		return result;
	}

	
	public Double getSheepingFees() {
		var result = 0.0;
		for(var o :orders.values()){
			result += o.getSheepingFees();
		}
		return result; 
	}

	public ArrayList<OrdersArticle>  getAllOrdersArticle() {
		var orderlines = new ArrayList<OrdersArticle>();
		orders.values().forEach(o -> {
			orderlines.addAll(o.getOrdersArticles());
		});
		return orderlines;
	}
}
