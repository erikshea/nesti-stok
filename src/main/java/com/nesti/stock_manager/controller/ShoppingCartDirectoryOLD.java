package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.form.FieldContainer;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ShoppingCartDirectoryOLD extends BaseInformation {
	private static final long serialVersionUID = 1L;
	protected DefaultTableModel tableModel;
	protected JTable table;

	public ShoppingCartDirectoryOLD(MainWindowControl c, Article article) {
		super(c,article);

		var addtoCardContainer = new JPanel();
		addtoCardContainer.setLayout(new BoxLayout(addtoCardContainer, BoxLayout.Y_AXIS));
		this.add(addtoCardContainer);

		var deleteButton = new JButton("Supprimer");
		addtoCardContainer.add(deleteButton);

		var titleLabel = new JLabel("Mes articles à commander");
		addtoCardContainer.add(titleLabel);

		createTable();
		addRowData(new Object[] {"AAA1","couteau","1","12","ACME"});
		var tableContainer = new JScrollPane(this.table);
		addtoCardContainer.add(tableContainer);
		
		var sheepingFeeFieldContainer = new FieldContainer("Frais de port", this);
		sheepingFeeFieldContainer.getField().setText("2.50");
		
		addtoCardContainer.add(sheepingFeeFieldContainer);
		
		var totalOrderContainer = new FieldContainer("Total", this);
		totalOrderContainer.getField().setText("452.63");
		addtoCardContainer.add(totalOrderContainer);

		this.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	public void createTable() {
		this.tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableModelColumns());
		this.table = new JTable(tableModel);
	}

	public Object[] getTableModelColumns() {
		return new Object[] { "Réf", "Description", "Quantité", "Prix d'achat", "Fournisseur" };
	}

	public void addRowData(Object[] data) {
		this.tableModel.addRow(data);
	}

}