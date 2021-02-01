package com.nesti.stock_manager.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableCellRenderer;

import com.nesti.stock_manager.cells.RadioButtonEditor;
import com.nesti.stock_manager.cells.RadioButtonRenderer;
import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.util.SwingUtil;

/**
 * Shows a table containing all suppliers offering an article, and controls to change those suppliers and offers
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class ArticleSupplierList extends BasePriceList<Article> {
	// group for  default supplier selection radio buttons. determines which radio button de-activates when another is clicked
	protected ButtonGroup radioGroup; 

	public ArticleSupplierList(Article a) {
		super(a);
		refreshList(); // refresh article at end of constructor
	}
	
	@Override
	public void addPriceTableContainer() {
		super.addPriceTableContainer();
		this.radioGroup = new ButtonGroup();
		
		this.table.getColumn("Par défaut").setCellRenderer(new RadioButtonRenderer());
		this.table.getColumn("Par défaut").setCellEditor(new RadioButtonEditor(new JCheckBox()));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);
	}
	
	@Override
	protected void addNewPriceContainer(){
		super.addNewPriceContainer();
		// Add new price action
		addButton.addActionListener(e->{
			// Get supplier entity from selected value in list
			var supplier = (new SupplierDao()).findOneBy("name", newPriceList.getSelectedValue());
			// Create offer for selected supplier
			var offer = new Offer();
			offer.setSupplier(supplier);
			try {
				// Set price as entered in text field
				offer.setPrice(Double.parseDouble(newPriceField.getText()));
				var currentOffers = entity.getCurrentOffers();
				
				long offerTimeForSupplier = 0;
				// Get last offer time for selected supplier (if exists) 
				if ( currentOffers.containsKey(supplier) ) {
					offerTimeForSupplier = currentOffers.get(supplier).getStartDate().getTime();
				}
				// Don't add a new price to article if last one added for supplier was less than 1 second ago  
				if ( offer.getStartDate().getTime() - offerTimeForSupplier > 1000) {
					entity.addOffer(offer);
					refreshList();
				}
			} catch (Exception ex) {ex.printStackTrace();}
		});
		
		var suppliers = (new SupplierDao()).findAll(BaseDao.ACTIVE);
		var listModel = (DefaultListModel<Object>)newPriceList.getModel();
		// populate list with all suppliers
		suppliers.forEach(s -> listModel.addElement(s.getName()));
		newPriceList.setSelectedIndex(0); // first item selected by default
	}
	
	@Override
	protected void refreshList() {
		super.refreshList();
		var defaultSupplier = entity.getDefaultSupplier();
		// Fill price list table with all current offers for that article
		entity.getCurrentOffers().values().forEach( o->{
			this.addRowData(new Object[] { o.getSupplier().getName(), o.getPrice() }, o.getSupplier().equals(defaultSupplier) );
		});
		// Set up column sorting (logic will only run once)
		SwingUtil.setUpTableAutoSort(table);
	}

	@Override
	protected void onRowDelete(int modelRow) {
		// Get supplier from its name in table row
		var supplier = (new SupplierDao()).findOneBy("name", this.table.getValueAt(modelRow, 1));
		// find corresponding offer
		var offer = entity.getCurrentOffers().get(supplier);
		
		// null price signals offer is no longer valid
		offer.setPrice(null); // TODO: create new null offer instead?
		
		// if supplier was default, unset
		if (	entity.getDefaultSupplier() != null
			&&  entity.getDefaultSupplier().equals(supplier)) {
			entity.setDefaultSupplier(null);
		}
		refreshList();
	}
	
	@Override
	public String getTitle() {
		return "Liste de fournisseurs";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Par défaut", "Fournisseur", "Prix d'achat", "Suppression" };
	}

	public void addRowData(Object[] data, boolean isDefault) {
		this.addRowData(data);
		// Default supplier datio button
		var radioButton = (JRadioButton) this.table.getValueAt(getPriceListTableModel().getRowCount() - 1, 0);
		radioButton.setSelected(isDefault);
	}
	
	@Override
	public void addRowData(Object[] data) {
		// Convert data array to list
		var tabData = new ArrayList<Object>(Arrays.asList(data));
		
		// Create default supplier radio button
		var radioButton = new JRadioButton("");
		
		// Get supplier from data (to see if it's the default one)
		var supplier = (new SupplierDao()).findOneBy("name", data[0]);
		
		radioButton.addActionListener(e->{
			if (radioButton.isSelected()) {
				entity.setDefaultSupplier(supplier); // If default supplier, radio button is selected
			}
			
		});
		tabData.add(0, radioButton); // Radio button is inserted at start of list
		
		tabData.add("-");	// Text that will be inside delete button 

		
		getPriceListTableModel().addRow(tabData.toArray());

		 // Add radio button to radio group (determines which gets unselected when another gets selected).
		radioGroup.add((JRadioButton) tabData.get(0));
	}
}
