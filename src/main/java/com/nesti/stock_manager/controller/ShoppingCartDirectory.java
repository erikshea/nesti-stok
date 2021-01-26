package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.nesti.stock_manager.form.ButtonColumn;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.OrdersArticle;
import com.nesti.stock_manager.model.Supplier;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.MathUtil;
import com.nesti.stock_manager.util.SwingUtil;

public class ShoppingCartDirectory extends BaseDirectory<OrdersArticle> {
	private static final long serialVersionUID = 1L;

	protected JLabel totalValue;
	protected JLabel shippingFeesValue;
	protected JButton orderButton,clearButton;
	
	public ShoppingCartDirectory(MainWindowControl c) {
		super(c);
		this.table.getModel().addTableModelListener(e -> {
			refreshTotals();
		});
		
		
		
		var shippingFeesContainer = new JPanel();
		shippingFeesContainer.setLayout(new BoxLayout(shippingFeesContainer, BoxLayout.X_AXIS));
		shippingFeesContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		var shippingFeesLabel = new JLabel("Frais de port");
		shippingFeesLabel.setFont(AppAppereance.TITLE_FONT);
		shippingFeesLabel.setPreferredSize(new Dimension(100, 40));

		shippingFeesValue = new JLabel("0");
		shippingFeesValue.setFont(AppAppereance.TITLE_FONT);
		shippingFeesValue.setBorder(BorderFactory.createMatteBorder(2,2,2,2, AppAppereance.DARK));
	
		shippingFeesValue.setPreferredSize(new Dimension(100, 40));
		shippingFeesValue.setMaximumSize(new Dimension(100, 40));

		shippingFeesContainer.add(Box.createHorizontalGlue());
		shippingFeesContainer.add(shippingFeesLabel);
		shippingFeesContainer.add((Box.createRigidArea(new Dimension(5,0))));
		shippingFeesContainer.add(shippingFeesValue);

		// add element to display subTotal of order
		var totalContainer = new JPanel();
		totalContainer.setLayout(new BoxLayout(totalContainer, BoxLayout.X_AXIS));
		totalContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		var totalLabel = new JLabel("Total");
		totalLabel.setFont(AppAppereance.TITLE_FONT);
		totalLabel.setPreferredSize(new Dimension(100, 40));

		totalValue = new JLabel("0");
		totalValue.setFont(AppAppereance.TITLE_FONT);
		totalValue.setBorder(BorderFactory.createMatteBorder(2,2,2,2, AppAppereance.DARK));
		
		totalValue.setPreferredSize(new Dimension(100, 40));
		totalValue.setMaximumSize(new Dimension(100, 40));

		totalContainer.add(Box.createHorizontalGlue());
		totalContainer.add(totalLabel);
		totalContainer.add((Box.createRigidArea(new Dimension(5,0))));
		totalContainer.add(totalValue);
		
		// buttons footer
		var buttonBarContainer = new JPanel();

		orderButton = new JButton("Commander");
		clearButton = new JButton("Tout effacer");
		clearButton.setBackground(AppAppereance.DARK);

		buttonBarContainer.add(Box.createHorizontalGlue());
		buttonBarContainer.add(clearButton);
		buttonBarContainer.add(orderButton);

		this.add(shippingFeesContainer);
		this.add(totalContainer);
		this.add(buttonBarContainer);
		addButtonListeners();
	}

	@Override
	public String getTitle() {
		return "Panier";
	}

	public Object[] getTableModelColumns() {
		return new Object[] { "Réf", "Description", "Quantité", "Prix d'achat", "Fournisseur", "Effacer"};
	}

	@Override
	public void deleteRow(int rowIndex) {
	}
	
	@Override
	public void addButtonBar() {
	}

	@Override
	public void addRow(OrdersArticle orderLine) {
		var offer = orderLine.getArticle().getCurrentOffers().get(orderLine.getOrder().getSupplier());
		
		var supplierComboBox = new JComboBox<Offer>();
		
		
		orderLine.getArticle().getCurrentOffers().values().forEach( o->{
			supplierComboBox.addItem(o);
		});
		supplierComboBox.setSelectedItem(orderLine.getOffer());
		
		supplierComboBox.addActionListener( e->{
			var selectedOffer = (Offer) supplierComboBox.getSelectedItem();

			mainController.getShoppingCart().removeOffer(offer);
			mainController.getShoppingCart().addOffer(selectedOffer, orderLine.getQuantity());
			this.refreshTab();
		});
		
		// First declare any component, say a JTextField
		JTextField quantityField = new JTextField();
		quantityField.setText(orderLine.getQuantity().toString());
		quantityField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { update(); }
			
			@Override
			public void removeUpdate(DocumentEvent e) { update(); }
			
			@Override
			public void insertUpdate(DocumentEvent e) { update(); }
			  
			public void update() {
				try {
					orderLine.setQuantity(Integer.parseInt(quantityField.getText()));
					refreshTotals();
				} catch (Exception e) {}
			}
		});
		
		
		supplierComboBox.setRenderer(new OfferListCellRenderer());
		
		this.addRowData(new Object[] {
			orderLine.getArticle().getCode(),
			orderLine.getArticle().getName(),
			quantityField,
			MathUtil.round(offer.getPrice(),2),
			supplierComboBox,
			"-"
		});
	
	}
	

	
	public void refreshTotals() {
		var total = MathUtil.round(mainController.getShoppingCart().getTotal(), 2);
		totalValue.setText(String.valueOf(total));
		
		var shippingFees = MathUtil.round(mainController.getShoppingCart().getShipingFees(), 2);
		shippingFeesValue.setText(String.valueOf(shippingFees));
	}


	
	public void addButtonListeners() {
		clearButton.addActionListener( ev->{
			mainController.getShoppingCart().clearAll();
			refreshTab();
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
		refreshTotals();
		
		
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

		// The code below adds the component to the JTable column
		
		this.table.getColumn("Quantité").setCellRenderer(new TextFieldCellRenderer());
		this.table.getColumn("Quantité").setCellEditor(new TextFieldCellEditor(new JCheckBox()));
		
		Action delete = new AbstractAction() {
		    public void actionPerformed(ActionEvent e)
		    {
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        onRowDelete(modelRow);
		    }
		};
		ButtonColumn buttonColumn = new ButtonColumn(
				table,
				delete,
				getTableModelColumns().length-1
		);
	}
	

	protected void onRowDelete(int modelRow) {
		@SuppressWarnings("unchecked")
		var comboBox = (JComboBox<Supplier>) this.table.getValueAt(modelRow, 4);
		var offer = (Offer) comboBox.getSelectedItem();
		mainController.getShoppingCart().removeOffer(offer);
		refreshTab();
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
	 
		class TextFieldCellEditor extends DefaultCellEditor implements ItemListener {
			private static final long serialVersionUID = 1L;
				private JTextField textField;

				public TextFieldCellEditor(JCheckBox checkBox) {
					super(checkBox);
				}

				@SuppressWarnings("unchecked")
				public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
						int column) {
					if (value == null)
						return null;
					textField = (JTextField) value;
					//textField.getDocument().addDocumentListener((DocumentListener) this);
					return (Component) value;
				}

				public Object getCellEditorValue() {
					//textField.getDocument().removeDocumentListener((DocumentListener) this);
					return textField;
				}

				public void itemStateChanged(ItemEvent e) {
					super.fireEditingStopped();
				}
			}

			 class TextFieldCellRenderer  implements TableCellRenderer {
				private static final long serialVersionUID = 1L;


				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					// TODO Auto-generated method stub
					if (value == null)
						return null;
					return (Component) value;
				}
			}