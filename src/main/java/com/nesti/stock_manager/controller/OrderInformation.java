package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.nesti.stock_manager.form.LabelContainer;
import com.nesti.stock_manager.model.Order;
import com.nesti.stock_manager.util.AppAppereance;

public class OrderInformation extends BaseInformation<Order> {
	private static final long serialVersionUID = 1775908299271902575L;
	/**
	 * constructor supplier information
	 * @param c
	 */
	public OrderInformation(MainWindowControl c, Order order) {
		super(c, order);
	}

	public String getTitle() {
		return "Commande N°" + item.getNumber();
	}
	
	@Override
	public void preRefreshTab() {
		super.preRefreshTab();
		final var order = item;
		
		var orderDetails = new JPanel();
		orderDetails.setPreferredSize(new Dimension(500, 0));
		orderDetails.setLayout(new BoxLayout(orderDetails, BoxLayout.Y_AXIS));
		
		var titleSupplierInformation = new JLabel("Commande :");
		titleSupplierInformation.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		titleSupplierInformation.setFont(AppAppereance.TITLE_FONT);
		orderDetails.add(titleSupplierInformation);

		orderDetails.add(
			new LabelContainer("Numéro :", order.getNumber())
		);
		
		orderDetails.add(
			new LabelContainer("Fournisseur :", order.getSupplier().getName())
		);
		
		orderDetails.add(
			new LabelContainer("Auteur :", order.getUser().getName())
		);
		
		orderDetails.add(
			new LabelContainer("Date :", String.valueOf(order.getDateOrder()))
		);
		
		orderDetails.add(
			new LabelContainer("Livrée le :", String.valueOf(order.getDateDelivery()))
		);

		orderDetails.add(Box.createVerticalGlue());
		
		
		addOrderItemTable();
		
		this.add(orderDetails, BorderLayout.WEST);
	}

	@Override
	public void closeTab() {
		super.closeTab();
		this.mainControl.getMainPane().setSelectedComponent(this.mainControl.getOrderDirectory());
	}

	public void addOrderItemTable() {
		var tableModel = new DefaultTableModel();
		var table = new JTable(tableModel);
		table.setFillsViewportHeight(true);


		tableModel.setColumnIdentifiers(
			new Object[] { "Code d'Article", "Nom d'Article", "Prix d'achat", "Quantité" }
		);

		
		item.getOrdersArticles().forEach(oa->{
			tableModel.addRow(new Object[] {
				oa.getArticle().getCode(),
				oa.getArticle().getName(),
				oa.getOffer().getPrice(),
				oa.getQuantity()
			 });
		});
		
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		
		var tableContainer = new JScrollPane(table);
		tableContainer.setPreferredSize(new Dimension(1000, 300));
		tableContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 300));
		
		this.add(tableContainer, BorderLayout.EAST);
	}
	

	
	
	
	@Override
	public void addBottomButtonBar() {}
}