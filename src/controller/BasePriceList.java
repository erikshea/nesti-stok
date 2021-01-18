package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
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

public class BasePriceList extends JPanel {

	private static final long serialVersionUID = -1997250030218950222L;
	protected DefaultTableModel tableModel;
	protected JTable table;

	@SuppressWarnings("serial")
	public JTable getPriceTable() {
		
		this.tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableModelColumns());
	
		this.table = new JTable(tableModel) {
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};

		this.table.getColumn("Suppression").setCellRenderer(new JTableButtonRenderer(null));
		//!!!!!!!!!!!!!!!!!!! manque la partie pour éditer quand on clique sur le bouton!!!! a ajouter 
		return this.table;
	}

	public Object[] getTableModelColumns() {
		return new Object[] {};
	}

	public void addRowData(Object[] data) {
		var newData = Arrays.copyOf(data, data.length+1);
		newData [newData.length-1] = new JButton("-");
		this.tableModel.addRow(newData);

	}

	public String getTitle() {
		return "";
	}

	public BasePriceList() {

		// right of the screen, price's and supplier's informations
		this.setPreferredSize(new Dimension(800, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		var titlePriceList = new JLabel(this.getTitle());
		this.add(titlePriceList);

		this.table = this.getPriceTable();

		// entre () on met ce qui doit scroller
		var scrollPriceList = new JScrollPane(table);

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

//display classic button
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
