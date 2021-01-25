package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.SwingUtil;

//RIGHT OF THE SCREEN, SUPPLIER'S INFORMATION OF THE ARTICLE

@SuppressWarnings("serial")
public class ArticleSupplierList extends BasePriceList<Article> {


	public ArticleSupplierList(Article a) {
		super(a);
		refreshList();
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
		addButton.addActionListener(e->{
			var supplier = (new SupplierDao()).findOneBy("name", newPriceList.getSelectedValue());
			var offer = new Offer();
			offer.setSupplier(supplier);
			try {
				offer.setPrice(Double.parseDouble(newPriceField.getText()));
	
				var currentOffers = entity.getCurrentOffers();
				
				long offerTimeForSupplier = 0;
				if ( currentOffers.containsKey(supplier) ) {
					offerTimeForSupplier = currentOffers.get(supplier).getStartDate().getTime();
				}
		
				if ( offer.getStartDate().getTime() - offerTimeForSupplier > 1000) {
					entity.addOffer(offer);
					refreshList();
				}
			} catch (Exception ex) {}
		});
		
		var suppliers = (new SupplierDao()).findAll(BaseDao.ACTIVE);
		var listModel = (DefaultListModel<Object>)newPriceList.getModel();
		suppliers.forEach(s -> listModel.addElement(s.getName()));
		newPriceList.setSelectedIndex(0);
	}
	
	@Override
	protected void refreshList() {
		super.refreshList();
		var defaultSupplier = entity.getDefaultSupplier();
		
		entity.getCurrentOffers().values().forEach( o->{
			this.addRowData(new Object[] { o.getSupplier().getName(), o.getPrice() }, o.getSupplier().equals(defaultSupplier) );
		});


		SwingUtil.setUpTableAutoSort(table);
	}

	@Override
	protected void onRowDelete(int modelRow) {
		var supplier = (new SupplierDao()).findOneBy("name", this.table.getValueAt(modelRow, 1));
		var offer = entity.getCurrentOffers().get(supplier);
		offer.setPrice(null);
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

	@Override
	public void addRowData(Object[] data) {
		var tabData = new ArrayList<Object>(Arrays.asList(data));
		var radioButton = new JRadioButton("");
		var supplier = (new SupplierDao()).findOneBy("name", data[0]);
		
		radioButton.addActionListener(e->{
			if (radioButton.isSelected()) {
				entity.setDefaultSupplier(supplier);
			}
			
		});
		tabData.add(0, radioButton);
		
		tabData.add("-");

		
		getPriceListTableModel().addRow(tabData.toArray());

		radioGroup.add((JRadioButton) tabData.get(0));
	}

//display radio button
	class RadioButtonRenderer implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value == null)
				return null;
			return (Component) value;
		}
	}

//click radio button

	class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
		private JRadioButton button;

		public RadioButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null)
				return null;
			button = (JRadioButton) value;
			button.addItemListener(this);
			return (Component) value;
		}

		public Object getCellEditorValue() {
			button.removeItemListener(this);
			return button;
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
	}

}
