package com.nesti.stock_manager.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.nesti.stock_manager.cells.ComboBoxCellEditor;
import com.nesti.stock_manager.cells.ComboBoxCellRenderer;
import com.nesti.stock_manager.cells.OfferListCellRenderer;
import com.nesti.stock_manager.cells.TextFieldCellEditor;
import com.nesti.stock_manager.cells.TextFieldCellRenderer;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.OrdersArticle;
import com.nesti.stock_manager.model.Supplier;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.ButtonColumn;
import com.nesti.stock_manager.util.FormatUtil;
import com.nesti.stock_manager.util.SwingUtil;

/**
 * Shows all items inside all current (non-completed) orders, as well as totals and buttons to change those items (quantity, suppliers, delete...)
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class ShoppingCartDirectory extends BaseDirectory<OrdersArticle> {
	private static final long serialVersionUID = 1L;

	protected JLabel totalValue;
	protected JLabel shippingFeesValue;
	protected JButton orderButton,clearButton;
	
	public ShoppingCartDirectory(MainWindowControl c) {
		super(c);
		// When table content changes, refresh all totals
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

	public Object[] getTableModelColumnNames() {
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
		// Combobox holds a list of all suppliers that offer this article
		var supplierComboBox = new JComboBox<Offer>();
		
		// Custom renderer for combobox (shows offer's supplier and price)
		supplierComboBox.setRenderer(new OfferListCellRenderer());
		
		orderLine.getArticle().getCurrentOffers().values().forEach( o->{
			supplierComboBox.addItem(o);
		});
		
		// Selected offer is order lines'
		supplierComboBox.setSelectedItem(orderLine.getOffer());
		
		// Change combo box selection action
		supplierComboBox.addActionListener( e->{
			var selectedOffer = (Offer) supplierComboBox.getSelectedItem(); // TODO: check 
			// Remove order line from shopping cart order list
			mainController.getShoppingCart().removeOrdersArticle(orderLine);
			
			// Add the one selected in the combo box
			mainController.getShoppingCart().addOffer(selectedOffer, orderLine.getQuantity());
			this.refreshTab();
		});
		

		// Quantity is an editable text field
		JTextField quantityField = new JTextField();
		
		// initial text is orderline quantity
		quantityField.setText(orderLine.getQuantity().toString());
		
		// Quantity change listener
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
		
		
		this.addRowData(new Object[] {
			orderLine.getArticle().getCode(),
			orderLine.getArticle().getName(),
			quantityField,
			FormatUtil.round(orderLine.getOffer().getPrice(),2),
			supplierComboBox,
			"-" // Will be turned into a button by the ButtonColumn constructor
		});
	}
	

	
	/**
	 * Refresg total and shipping fees
	 */
	public void refreshTotals() {
		var total = FormatUtil.round(mainController.getShoppingCart().getTotal(), 2);
		totalValue.setText(String.valueOf(total));
		
		var shippingFees = FormatUtil.round(mainController.getShoppingCart().getShipingFees(), 2);
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
				System.out.println("Couldn't save orders:");
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
		
		table.setRowHeight(20); // Rows need to be taller because of combo box
		this.table.getColumn("Fournisseur").setCellRenderer(new ComboBoxCellRenderer());
		this.table.getColumn("Fournisseur").setCellEditor(new ComboBoxCellEditor(new JCheckBox()));

		// Quantity is a text field and needs its own renderer and editor
		this.table.getColumn("Quantité").setCellRenderer(new TextFieldCellRenderer());
		this.table.getColumn("Quantité").setCellEditor(new TextFieldCellEditor(new JCheckBox()));
		
		// Delete row button action
		Action delete = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
		        int modelRow = Integer.valueOf( e.getActionCommand() );
				@SuppressWarnings("unchecked")
				// Combo box holds current offer
				var comboBox = (JComboBox<Supplier>) table.getValueAt(modelRow, 4);
				var offer = (Offer) comboBox.getSelectedItem();
				mainController.getShoppingCart().removeOffer(offer);
				refreshTab();
		    }
		};
		
		// turns the last column cells into a button (for delete action)
		new ButtonColumn(
			table,
			delete,
			getTableModelColumnNames().length-1
		);
	}
	

}












	 



