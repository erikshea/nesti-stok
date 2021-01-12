package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SingleSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import form.ListFieldContainer;

/** 
 * display
 * @author Manu
 *
 */

class RadioButtonRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value == null)
			return null;
		return (Component) value;
	}
}

/**
 * click radion button
 * @author Manu
 *
 */
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

public class ArticlePriceList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997250030218950222L;

	public ArticlePriceList() {

		// right of the screen, price's and supplier's informations

		this.setPreferredSize(new Dimension(500, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		var titlePriceList = new JLabel("Liste de prix");
		this.add(titlePriceList);

		// define button radio
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(
				new Object[][] { { new JRadioButton("",true), "TOUT POUR LA CUISINE", "50", "Snowboarding", 10, false },
						{ new JRadioButton(""), "ACME", "35", "Rowing", 3, true },
						{ new JRadioButton(""), "O'sel fin", "42", "Knitting", 2, false },
						{ new JRadioButton(""), "Blabla", "41", "Speed reading", 20, true },
						{ new JRadioButton(""), "Jean Charles Farine", "49", "Pool", 10, false } },

				new Object[] { "Par défaut", "First Name", "Last Name", "Sport", "# of Years", "Vegetarian"

				});

		// on précise que tous les boutons radio sont dans le gpe radio pour que quand
		// on sélectionne 1 bouton , tous les autres sont déselectionnés
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add((JRadioButton) dm.getValueAt(0, 0));
		radioGroup.add((JRadioButton) dm.getValueAt(1, 0));
		radioGroup.add((JRadioButton) dm.getValueAt(2, 0));
		radioGroup.add((JRadioButton) dm.getValueAt(3, 0));
		radioGroup.add((JRadioButton) dm.getValueAt(4, 0));

		// define name of the column tab
		/*
		 * Object[][] data = { {new Boolean(false), "TOUT POUR LA CUISINE", "25", new
		 * Integer(5)}, {new Boolean(false), "ACME", "29", new Integer(3)}, {new
		 * Boolean(false), "Blabla", "25", new Integer(2)}, {new Boolean(false),
		 * "Le moins cher", "41", new Integer(20)}, };
		 * 
		 * 
		 */

		/**
		 * we have to change all the table when deselectionne
		 * 
		 */

		// fonction anonyme => on redéfini une méthode de table, on réécrit dans la table

		@SuppressWarnings("serial")
		var listPrice = new JTable(dm) {
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};

		listPrice.getColumn("Par défaut").setCellRenderer(new RadioButtonRenderer());
		listPrice.getColumn("Par défaut").setCellEditor(new RadioButtonEditor(new JCheckBox()));

		// var listPrice = new JTable(data,columnNames);
		// listPrice.setSelectionMode(1);

		// listPrice.setPreferredScrollableViewportSize(new Dimension(500, 70));
		// listPrice.setFillsViewportHeight(true);

		// entre () on met ce qui doit scroller
		var scrollPriceList = new JScrollPane(listPrice);
	
		scrollPriceList.setPreferredSize(new Dimension(0, 150));
		scrollPriceList.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));

		this.add(scrollPriceList);
		
			
		/**
		 * define add price design
		 */

		var addPrice = new JPanel();
		addPrice.setPreferredSize(new Dimension(500, 0));
		addPrice.setLayout(new BoxLayout(addPrice, BoxLayout.X_AXIS));
		

		var listModel = new DefaultListModel<>();
		var list = new JList<>(listModel);
		listModel.addElement("coucou");
		listModel.addElement("salut");
		listModel.addElement("ola");
		
		var scrollPane = new JScrollPane(list);
		
	//	var supplierListContainer = new ListFieldContainer("fournisseur");
//		supplierListContainer.populateList(
	//			List.of("ACME", "Tout pour la cuisine", "O'Sel fin", "blaSSDh", "blah3", "blah", "blargh"));

		addPrice.add(scrollPane);
		
		this.add(addPrice);
		
		
		this.add(Box.createVerticalGlue());

	}

}
