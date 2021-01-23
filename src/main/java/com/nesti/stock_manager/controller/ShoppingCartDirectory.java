package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.model.OrdersArticle;

public class ShoppingCartDirectory extends BaseDirectory<OrdersArticle> {
	private static final long serialVersionUID = 1L;

	public ShoppingCartDirectory(MainWindowControl c) {
		super(c);
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
}

//var addtoCardContainer = new JPanel();
//addtoCardContainer.setLayout(new BoxLayout(addtoCardContainer, BoxLayout.Y_AXIS));
//
//

// this.add(addtoCardContainer);

//var deleteButton = new JButton("Supprimer");
//addtoCardContainer.add(deleteButton);
//
//var titleLabel = new JLabel("Mes articles à commander");
//addtoCardContainer.add(titleLabel);
//
//createTable();
//addRowData(new Object[] { "AAA1", "couteau", "1", "12", "ACME" });
//var tableContainer = new JScrollPane(this.table);
//addtoCardContainer.add(tableContainer);

//var sheepingFeeFieldContainer = new FieldContainer("Frais de port");
//sheepingFeeFieldContainer.getField().setText("2.50");

//addtoCardContainer.add(sheepingFeeFieldContainer);
//
//var totalOrderContainer = new FieldContainer("Total", this);
//totalOrderContainer.getField().setText("452.63");
//addtoCardContainer.add(totalOrderContainer);

//this.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//public void createTable() {
//	this.tableModel = new DefaultTableModel();
//	tableModel.setColumnIdentifiers(getTableModelColumns());
//	this.table = new JTable(tableModel);
//}
//
//
//}
//
//public void addRowData(Object[] data) {
//	this.tableModel.addRow(data);
//}
