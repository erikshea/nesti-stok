package com.nesti.stock_manager.controller;

import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.model.Offer;
import com.nesti.stock_manager.model.Supplier;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import org.hibernate.internal.build.AllowSysOut;

import com.nesti.stock_manager.dao.OfferDao;
import com.nesti.stock_manager.dao.SupplierDao;

//RIGHT OF THE SCREEN, SUPPLIER'S INFORMATION OF THE ARTICLE

@SuppressWarnings("serial")
public class ArticleSupplierList extends BasePriceList {
	HashMap<Supplier,Offer> suppliers;
	Article article;
	// right of the screen, price's and supplier's informations

	public ArticleSupplierList(Article a) {
		super(a);
		article = a;
		// HEAD OF THE SCREEN, SUPPLIERS OF THE ARTICLE
		this.table = this.getPriceTable();

		this.radioGroup = new ButtonGroup();

		this.table.getColumn("Par défaut").setCellRenderer(new RadioButtonRenderer());
		this.table.getColumn("Par défaut").setCellEditor(new RadioButtonEditor(new JCheckBox()));


		refreshSuppliers();
	
		// FOOTER OF THE SCREEN, ADD A SUPPLIER
		var scrollPriceList = new JScrollPane(table);
		scrollPriceList.setPreferredSize(new Dimension(0, 150));
		scrollPriceList.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
		this.add(scrollPriceList);

		var addPriceContainer = new JPanel();
		addPriceContainer.setPreferredSize(new Dimension(500, 100));
		addPriceContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		addPriceContainer.setLayout(new BoxLayout(addPriceContainer, BoxLayout.X_AXIS));

		var listModel = new DefaultListModel<>();
		var list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		var daoSupplier = new SupplierDao();
		var suppliers = daoSupplier.findAll();
		suppliers.forEach(s -> listModel.addElement(s.getName()));

		var scrollPane = new JScrollPane(list);
		addPriceContainer.add(scrollPane);

		var priceSupplier = new JTextField("12");
		addPriceContainer.add(priceSupplier);

		addButton = new JButton("+");
		addButton.addActionListener(e->{
			var supplier = (new SupplierDao()).findOneBy("name", list.getSelectedValue());
			var offer = new Offer();
			offer.setSupplier(supplier);
			offer.setPrice(Double.parseDouble(priceSupplier.getText()));

			Date latestOfferDateForSupplier = null;
			if ( 		article.getLatestOffers() != null
					&&  article.getLatestOffers().get(supplier) != null) {
				latestOfferDateForSupplier = article.getLatestOffers().get(supplier).getStartDate();
			}
	
			if ( latestOfferDateForSupplier == null 
				|| latestOfferDateForSupplier != null && (offer.getStartDate().getTime() - latestOfferDateForSupplier.getTime() >1000)) {
				article.addOffer(offer);
				refreshSuppliers();
			}
	
		});
		addPriceContainer.add(addButton);

		this.add(addPriceContainer);

		this.add(Box.createVerticalGlue());

	}
	
	private void refreshSuppliers() {
		suppliers = new HashMap<>();
		this.tableModel.getDataVector().removeAllElements();
		var latestOffers = article.getLatestOffers().values();
		var defaultSupplier = article.getDefaultSupplier();
		if (defaultSupplier != null){
			System.out.println(defaultSupplier.getName());
			
		}
		
		latestOffers.forEach( o->{
			if (o.getPrice() != -1) {
				this.addRowData(new Object[] { o.getSupplier().getName(), o.getPrice() }, o.getSupplier().equals(defaultSupplier) );
			}
		});
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
		radioButton.addActionListener(e->{
			if (radioButton.isSelected()) {
				var supplier = (new SupplierDao()).findOneBy("name", data[0]);
				article.setDefaultSupplier(supplier);
			}
			
		});
		tabData.add(0, radioButton);
		tabData.add(new JButton("-"));

		this.tableModel.addRow(tabData.toArray());

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
	@SuppressWarnings("serial")
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
