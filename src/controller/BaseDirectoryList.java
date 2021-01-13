package controller;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class BaseDirectoryList extends JPanel {

	protected DefaultTableModel tableModel;
	protected JTable table;

	public JTable getPriceTable() {

		this.tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableModelColumns());

		this.table = new JTable(tableModel) {
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};
		return this.table;
	}

	public Object[] getTableModelColumns() {
		return new Object[] {};
	}

	public void addRowData(Object[] data) {
		var newData = Arrays.copyOf(data, data.length + 1);
		newData[newData.length - 1] = new JButton("-");
		this.tableModel.addRow(newData);

	}

	public String getTitle() {
		return "";
	}

	public BaseDirectoryList() {

		this.setPreferredSize(new Dimension(2000, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.table = this.getPriceTable();

		var scrollDirectoryList = new JScrollPane(table);
		this.add(scrollDirectoryList);

		this.add(Box.createVerticalGlue());
	}

}
