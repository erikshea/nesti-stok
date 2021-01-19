package controller;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import dao.ArticleDao;
import dao.OfferDao;

@SuppressWarnings("serial")
public class ArticleSupplierList extends ArticlePriceList {

	protected ButtonGroup radioGroup;

	public ArticleSupplierList(Object listOwner) {
		super();
		this.radioGroup = new ButtonGroup();

		this.table.getColumn("Par défaut").setCellRenderer(new RadioButtonRenderer());
		this.table.getColumn("Par défaut").setCellEditor(new RadioButtonEditor(new JCheckBox()));
		
		// -----------------A CORRIGER POUR AFFICHER QUE LES FOURNISSEURS DE L'ARTICLE CONCERNE--------
		
		var dao = new OfferDao(); 
		var supplierPrice = dao.findAll();
		supplierPrice.forEach(sp->{
			//-----------------A CORRIGER NON FONCTIONNEL AVEC LE TRUE QUI DOIT ETRE PAR DEFAUT-------------
	//		this.addRowData(new Object[] {sp.getSupplier().getName(),sp.getPrice()},true);
			this.addRowData(new Object[] {sp.getSupplier().getName(),sp.getPrice()});
        });
		

	}

	@Override
	public String getTitle() {
		return "Liste de prix";
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

	/**
	 * used to choose a supplier by default
	 * 
	 * @param data
	 * @param isDefault
	 */
	public void addRowData(Object[] data, boolean isDefault) {
		this.addRowData(data);
		var radioButton = (JRadioButton) this.table.getValueAt(this.tableModel.getRowCount() - 1, 0);
		radioButton.setSelected(isDefault);
	}
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
