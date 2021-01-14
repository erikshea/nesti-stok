package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class BaseList extends JPanel {

	protected JPanel buttonBar;
	protected DefaultTableModel tableModel;
	protected JTable table;

	public BaseList() {

		this.setPreferredSize(new Dimension(1500, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Define all the button of the head on the article list
		this.buttonBar = new JPanel();
		this.buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));

		var buttonAdd = new JButton("Créer");
		var buttonDelete = new JButton("Supprimer");
		var buttonModify = new JButton("Modifier");
		var buttonDuplicate = new JButton("Dupliquer");

		this.buttonBar.add(buttonAdd);
		this.buttonBar.add(buttonDelete);
		this.buttonBar.add(buttonModify);
		this.buttonBar.add(buttonDuplicate);

		this.buttonBar.add(Box.createHorizontalGlue());

		this.add(buttonBar);

		// Title of the article List
		var titleLabel = new JLabel(this.getTitle());
		this.add(titleLabel);

		createTable();	
		var tableContainer = new JScrollPane(this.table);
		this.add(tableContainer);
		
		this.setAlignmentX(Component.CENTER_ALIGNMENT);

	}

	public String getTitle() {
		return "";
	}

	public void createTable() {
		this.tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableModelColumns());
		this.table = new JTable(tableModel);
	}

	public Object[] getTableModelColumns() {
		return new Object[] {};
	}

	public void addRowData(Object[] data) {
		this.tableModel.addRow(data);
	}

}
