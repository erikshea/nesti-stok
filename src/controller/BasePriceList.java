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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SingleSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import form.ListFieldContainer;

// display radio button
class RadioButtonRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value == null)
			return null;
		return (Component) value;
	}
}

// click radio button

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

// display classic button
class JTableButtonRenderer implements TableCellRenderer {
	private TableCellRenderer defaultRenderer;

	public JTableButtonRenderer(TableCellRenderer renderer) {
		defaultRenderer = renderer;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value instanceof Component)
			return (Component) value;
		return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}

public class BasePriceList extends JPanel {

	private static final long serialVersionUID = -1997250030218950222L;

	public Object[][] getModelData() {
return { { new JRadioButton("", true), "TOUT POUR LA CUISINE", "50", new JButton("-") },
	{ new JRadioButton(""), "ACME", "35", new JButton("-") },
	{ new JRadioButton(""), "O'sel fin", "42", new JButton("-") },
	{ new JRadioButton(""), "Blabla", "41", new JButton("-") },
	{ new JRadioButton(""), "Jean Charles Farine", "49", new JButton("-") } },

new Object[] { "Par défaut", "Fournisseur", "Prix", "Suppression" }
	}

	public JTable getPriceTable() {
		// define button radio
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(
				new Object[][] );

		// add radio button into a group
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add((JRadioButton) dm.getValueAt(0, 0));
		radioGroup.add((JRadioButton) dm.getValueAt(1, 0));
		radioGroup.add((JRadioButton) dm.getValueAt(2, 0));
		radioGroup.add((JRadioButton) dm.getValueAt(3, 0));
		radioGroup.add((JRadioButton) dm.getValueAt(4, 0));

		// change all the table when select one button to another (deselct all the
		// other)

		@SuppressWarnings("serial")
		var listPrice = new JTable(dm) {
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};

		listPrice.getColumn("Par défaut").setCellRenderer(new RadioButtonRenderer());
		listPrice.getColumn("Par défaut").setCellEditor(new RadioButtonEditor(new JCheckBox()));

		listPrice.getColumn("Suppression").setCellRenderer(new JTableButtonRenderer(null));
//				listPrice.getColumn("Suppression").setCellEditor(new JTableButtonRenderer(null));

		return listPrice;
	}

	public BasePriceList() {

		// right of the screen, price's and supplier's informations

		this.setPreferredSize(new Dimension(800, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		var priceTable = this.getPriceTable();

		// entre () on met ce qui doit scroller
		var scrollPriceList = new JScrollPane(priceTable);

		scrollPriceList.setPreferredSize(new Dimension(0, 150));
		scrollPriceList.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));

		this.add(scrollPriceList);

		// define add price design
		var addPriceContainer = new JPanel();
		addPriceContainer.setPreferredSize(new Dimension(500, 0));
		addPriceContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 2000));
		addPriceContainer.setLayout(new BoxLayout(addPriceContainer, BoxLayout.X_AXIS));

		var listModel = new DefaultListModel<>();
		var list = new JList<>(listModel);
		list.setPreferredSize(new Dimension(500, 0));

		// allows to select only one supplier
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listModel.addElement("O'Sel fin");
		listModel.addElement("Tout pour la cuine");
		listModel.addElement("Jean Bon Grossiste");

		var scrollPane = new JScrollPane(list);
		addPriceContainer.add(scrollPane);

		var priceSupplier = new JTextField("25");
		addPriceContainer.add(priceSupplier);

		var buttonAddPrice = new JButton("+");
		addPriceContainer.add(buttonAddPrice);

		this.add(addPriceContainer);

		// allows to reduce the windows
		this.add(Box.createVerticalGlue());

	}

}
