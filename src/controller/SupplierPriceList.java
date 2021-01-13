package controller;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class SupplierPriceList extends BasePriceList{

	protected ButtonGroup radioGroup;
	
	public SupplierPriceList() {
		super();
		this.radioGroup = new ButtonGroup();

		
		this.table.getColumn("Par défaut").setCellRenderer(new RadioButtonRenderer());
		this.table.getColumn("Par défaut").setCellEditor(new RadioButtonEditor(new JCheckBox()));
		
		this.addRowData(new Object[]{ new JRadioButton("", true), "Tout pour la cuisine", "8", new JButton("-") });
		this.addRowData(new Object[]{ new JRadioButton(""), "JeanBon Grossiste", "69", new JButton("-") });
		this.addRowData(new Object[]{ new JRadioButton(""), "O'sel fin", "42", new JButton("-") });
		this.addRowData(new Object[]{ new JRadioButton(""), "Blabla", "41", new JButton("-") });
		this.addRowData(new Object[]{new JRadioButton(""), "Jean Charles Farine", "49", new JButton("-") });
	}
	
	@Override
	public String getTitle() {
		return "Liste de prix";
	}
	
	
	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Par défaut", "Prix", "Prix d'achat", "Suppression" };
	}
	
	/**
	 * add a row and radio button into a group
	 */
	@Override
	  public void addRowData(Object[] data){
	  super.addRowData(data);
	  radioGroup.add((JRadioButton) data[data.length-1]);
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
