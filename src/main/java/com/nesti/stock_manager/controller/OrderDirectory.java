package com.nesti.stock_manager.controller;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.model.Order;

@SuppressWarnings("serial")
public class OrderDirectory extends BaseDirectory<Order> {

	public OrderDirectory(MainWindowControl c) {
		super(c);
	}
	@Override
	public void addButtonBar() {
		var buttonInfo = new JButton("Informations");
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Define all the button of the head on the article list
		this.buttonBar = new JPanel();
		this.buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));
		this.buttonBar.add(buttonInfo);
		this.buttonBar.add(Box.createHorizontalGlue());

		this.add(buttonBar);
		
		buttonInfo.addActionListener(e->{
			var number = this.table.getValueAt(this.table.getSelectedRow(),0);
			var o = (new OrderDao()).findOneBy("number",number);

			this.mainController.getMainPane().addCloseableTab(
					"Commande N° " + o.getNumber(),
					new OrderInformation(this.mainController,o)
			);
		});
			
		this.table.getSelectionModel().addListSelectionListener(e->{
			buttonInfo.setEnabled(this.table.getSelectedRowCount() == 1) ; //TODO: re-enable
		});
	}
	
	
	// Title of the article List
	@Override
	public String getTitle() {
		return "Liste des commandes";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Numéro", "Fournisseur", "Auteur", "Date", "Total"};
	}

	@Override
	public void setUpButtonBarListeners()  {
	}
	
	@Override
	public void deleteRow(int rowIndex) {}


	@Override
	public void addRow(Order entity) {
		addRowData(
			new Object[] {
				entity.getNumber(),
				entity.getSupplier().getName(),
				entity.getUser().getName(),
				entity.getDateOrder(),
				entity.getSubTotal()
			}
		);
	}
}
