package controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import dao.OfferDao;
import dao.SupplierDao;
import model.Article;

//RIGHT OF THE SCREEN, SUPPLIER'S INFORMATION OF THE ARTICLE

@SuppressWarnings("serial")
public class ArticleSupplierList extends BasePriceList {

	// right of the screen, price's and supplier's informations
	
	public ArticleSupplierList(Article article) {
		super(article);

		// HEAD OF THE SCREEN, SUPPLIERS OF THE ARTICLE
		this.table = this.getPriceTable();

		this.radioGroup = new ButtonGroup();

		this.table.getColumn("Par défaut").setCellRenderer(new RadioButtonRenderer());
		this.table.getColumn("Par défaut").setCellEditor(new RadioButtonEditor(new JCheckBox()));

		var daoOffer = new OfferDao();
		var supplierPrice = daoOffer.findAll();
		
		supplierPrice.forEach(sp -> {
			
			// A CORRIGER NON FONCTIONNEL AVEC LE TRUE QUI DOIT ETRE PAR DEFAUT
			// {sp.getSupplier().getName(),sp.getPrice()},true);
			this.addRowData(new Object[] { sp.getSupplier().getName(), sp.getPrice() });
		});

		
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

		var daoSupplier = new SupplierDao();
		var suppliers = daoSupplier.findAll();
		suppliers.forEach(s -> listModel.addElement(s.getName()));

		var scrollPane = new JScrollPane(list);
		addPriceContainer.add(scrollPane);

		var priceSupplier = new JTextField("12");
		addPriceContainer.add(priceSupplier);

		addButton = new JButton("+");
		addPriceContainer.add(addButton);

		this.add(addPriceContainer);

		this.add(Box.createVerticalGlue());

	}

	@Override
	public String getTitle() {
		return "Liste de fournisseur";
	}
	
	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Par défaut", "Fournisseur", "Prix d'achat", "Suppression" };
	}
	
@Override
public void addRowData(Object[] data) {
	var tabData = new ArrayList<Object>(Arrays.asList(data));
	tabData.add(0, new JRadioButton(""));
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

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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
