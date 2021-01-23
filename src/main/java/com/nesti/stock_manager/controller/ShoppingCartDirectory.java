package com.nesti.stock_manager.controller;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nesti.stock_manager.model.OrdersArticle;

public class ShoppingCartDirectory extends BaseDirectory<OrdersArticle> {
	private static final long serialVersionUID = 1L;

	protected JLabel totalValue;
	protected JLabel sheepingFeesValue;
	
	
	public ShoppingCartDirectory(MainWindowControl c) {
		super(c);
		this.table.getModel().addTableModelListener(e -> {
			refreshTotal();
			refreshSheepingFees();
		});
		
		var sheepingFeesContainer = new JPanel();
		sheepingFeesContainer.setLayout(new BoxLayout(sheepingFeesContainer, BoxLayout.X_AXIS));
		sheepingFeesContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		var sheepingFeesLabel = new JLabel("Frais de port");
		sheepingFeesLabel.setPreferredSize(new Dimension(100, 40));

		sheepingFeesValue = new JLabel("0");
		sheepingFeesValue.setPreferredSize(new Dimension(100, 40));
		sheepingFeesValue.setMaximumSize(new Dimension(100, 40));

		sheepingFeesContainer.add(Box.createHorizontalGlue());
		sheepingFeesContainer.add(sheepingFeesLabel);
		sheepingFeesContainer.add(sheepingFeesValue);

		// add element to display subTotal of order
		var totalContainer = new JPanel();
		totalContainer.setLayout(new BoxLayout(totalContainer, BoxLayout.X_AXIS));
		totalContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		var totalLabel = new JLabel("Total");
		totalLabel.setPreferredSize(new Dimension(100, 40));

		totalValue = new JLabel("0");
		totalValue.setPreferredSize(new Dimension(100, 40));
		totalValue.setMaximumSize(new Dimension(100, 40));

		totalContainer.add(Box.createHorizontalGlue());
		totalContainer.add(totalLabel);
		totalContainer.add(totalValue);
		
		// buttons footer
		var buttonBarContainer = new JPanel();

		var orderButton = new JButton("Commander");
		var cancelButton = new JButton("Annuler");

		buttonBarContainer.add(Box.createHorizontalGlue());
		buttonBarContainer.add(cancelButton);
		buttonBarContainer.add(orderButton);

		this.add(sheepingFeesContainer);
		this.add(totalContainer);
		this.add(buttonBarContainer);
	}

	@Override
	public String getTitle() {
		return "Liste d'articles à commander";
	}

	public Object[] getTableModelColumns() {
		return new Object[] { "Réf", "Description", "Quantité", "Prix d'achat", "Fournisseur" };
	}

	@Override
	public void deleteRow(int rowIndex) {
	}

	@Override
	public void addRow(OrdersArticle orderLine) {
		var offer = orderLine.getArticle().getCurrentOffers().get(orderLine.getOrder().getSupplier());
		this.addRowData(new Object[] { orderLine.getArticle().getCode(), orderLine.getArticle().getName(),
				orderLine.getQuantity(), offer.getPrice(), orderLine.getOrder().getSupplier().getName() });
	}
	
	public void refreshTotal() {
		var total = String.valueOf(Math.round(
				(mainController.getShoppingCart().getTotal() + mainController.getShoppingCart().getSheepingFees()) * 100.0)
				/ 100.0);
		totalValue.setText(total);
	}

	public void refreshSheepingFees() {
		var sheepingFees = String.valueOf(mainController.getShoppingCart().getSheepingFees());
		sheepingFeesValue.setText(sheepingFees);
	}
	
	@Override
	public void refreshTable() {
		this.tableModel.getDataVector().removeAllElements();
		// Detail of the article List
		
		var orderItems = mainController.getShoppingCart().getAllOrdersArticle();

		orderItems.forEach(e-> {
			this.addRow( e);
		});
		refreshTotal();
		refreshSheepingFees();
	}
}
