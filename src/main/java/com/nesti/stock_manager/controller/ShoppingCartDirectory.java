package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.nesti.stock_manager.controller.ArticleSupplierList.RadioButtonEditor;
import com.nesti.stock_manager.controller.ArticleSupplierList.RadioButtonRenderer;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.OrdersArticle;
import com.nesti.stock_manager.model.Supplier;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.SwingUtil;

public class ShoppingCartDirectory extends BaseDirectory<OrdersArticle> {
	private static final long serialVersionUID = 1L;

	protected JLabel totalValue;
	protected JLabel sheepingFeesValue;
	protected JButton orderButton,cancelButton;
	
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

		orderButton = new JButton("Commander");
		cancelButton = new JButton("Annuler");

		buttonBarContainer.add(Box.createHorizontalGlue());
		buttonBarContainer.add(cancelButton);
		buttonBarContainer.add(orderButton);

		this.add(sheepingFeesContainer);
		this.add(totalContainer);
		this.add(buttonBarContainer);
		addButtonListeners();
	}

	@Override
	public String getTitle() {
		return "Panier";
	}

	public Object[] getTableModelColumns() {
		return new Object[] { "Réf", "Description", "Quantité", "Prix d'achat", "Fournisseur" };
	}

	@Override
	public void deleteRow(int rowIndex) {
	}
	
	@Override
	public void addButtonBar() {
	}

	@Override
	public void addRow(OrdersArticle orderLine) {
		var supplierComboBox = new JComboBox<Offer>();
		
		orderLine.getArticle().getCurrentOffers().values().forEach( o->{
			supplierComboBox.addItem(o);
		});
		supplierComboBox.setSelectedItem(orderLine.getOffer());
		
		supplierComboBox.addActionListener( e->{
			var oldOffer = (Offer) supplierComboBox.getSelectedItem();
			var newOffer = orderLine.getArticle().getCurrentOffers().get(oldOffer.getSupplier());

			orderLine.getOrder().removeOrdersArticle(orderLine);
			mainController.getShoppingCart().addOffer(newOffer, orderLine.getQuantity());
			this.refreshTab();
		});
		
		supplierComboBox.setRenderer(new OfferListCellRenderer());
		
		var offer = orderLine.getArticle().getCurrentOffers().get(orderLine.getOrder().getSupplier());
		this.addRowData(new Object[] { orderLine.getArticle().getCode(), orderLine.getArticle().getName(),
				orderLine.getQuantity(), offer.getPrice(), supplierComboBox });
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
	
	public void addButtonListeners() {
		cancelButton.addActionListener( ev->{
			closeTab();
		});
		
		orderButton.addActionListener( e->{
			try{
				mainController.getShoppingCart().saveOrders();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			refreshTab();
		});
	}
	
	
	@Override
	public void refreshTable() {
		this.tableModel.setRowCount(0);
		// Detail of the article List
		var orderItems = mainController.getShoppingCart().getAllOrdersArticle();

		orderItems.forEach(e-> {
			this.addRow( e);
		});
		refreshTotal();
		refreshSheepingFees();
		SwingUtil.setUpTableAutoSort(table);
	}
	
	
	@Override
	public void createTable() {
		super.createTable();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.setRowHeight(20);
		this.table.getColumn("Fournisseur").setCellRenderer(new ComboBoxCellRenderer());
		this.table.getColumn("Fournisseur").setCellEditor(new ComboBoxCellEditor(new JCheckBox()));
	}
}


//display radio button
	class ComboBoxCellRenderer implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value == null)
				return null;
			return (Component) value;
		}
	}

//click radio button

	class ComboBoxCellEditor extends DefaultCellEditor implements ItemListener {
	private static final long serialVersionUID = 1L;
		private JComboBox<Supplier> comboBox;

		public ComboBoxCellEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		@SuppressWarnings("unchecked")
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null)
				return null;
			comboBox = (JComboBox<Supplier>) value;
			comboBox.addItemListener(this);
			return (Component) value;
		}

		public Object getCellEditorValue() {
			comboBox.removeItemListener(this);
			return comboBox;
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
	}

	 class OfferListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(
	                                   JList list,
	                                   Object value,
	                                   int index,
	                                   boolean isSelected,
	                                   boolean cellHasFocus) {
	        if (value instanceof Offer) {
	        	var offer = (Offer)value;
	            value = offer.getSupplier().getName() + " (" + offer.getPrice() + ")";
	        }
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	        return this;
	    }
	}