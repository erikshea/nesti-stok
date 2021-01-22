package com.nesti.stock_manager.controller;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nesti.stock_manager.model.*;


@SuppressWarnings("serial")
public class ShoppingCartList extends BaseList<OrdersArticle> {
	protected JTextField subTotalField; 
	

	public ShoppingCartList(MainWindowControl c) {
		super(c);
		
		// add element to display sheeping fees of order
		var sheepingFeesContainer = new JPanel();
		sheepingFeesContainer.setLayout(new BoxLayout(sheepingFeesContainer, BoxLayout.X_AXIS));
		sheepingFeesContainer.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		
		var sheepingFeesLabel = new JLabel("Frais de port");
		sheepingFeesLabel.setPreferredSize(new Dimension(100, 40));
		
		var sheepingFeesField = new JTextField("20");
		sheepingFeesField.setPreferredSize(new Dimension(100, 40));
		sheepingFeesField.setMaximumSize(new Dimension(100,40));
		
		sheepingFeesContainer.add(Box.createHorizontalGlue());
		sheepingFeesContainer.add(sheepingFeesLabel);
		sheepingFeesContainer.add(sheepingFeesField);
		
		
		// add element to display subTotal of order
		var subTotalContainer = new JPanel();
		subTotalContainer.setLayout(new BoxLayout(subTotalContainer, BoxLayout.X_AXIS));
		subTotalContainer.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		
		var subTotalLabel = new JLabel("Total");
		subTotalLabel.setPreferredSize(new Dimension(100, 40));
		
		var subTotalField = new JTextField("200");
		subTotalField.setPreferredSize(new Dimension(100, 40));
		subTotalField.setMaximumSize(new Dimension(100,40));
		
		subTotalContainer.add(Box.createHorizontalGlue());
		subTotalContainer.add(subTotalLabel);
		subTotalContainer.add(subTotalField);
		
		this.add(sheepingFeesContainer);
		this.add(subTotalContainer);
		
		refresh();

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

	@Override
	public void refresh() {
		this.tableModel.getDataVector().removeAllElements();

		var orderArticle = mainController.getShoppingCart().getAllOrdersArticle();
		orderArticle.forEach(oa -> {
			this.addRow(oa);
		});
	}

}


