package form;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FieldContainer extends BaseFieldContainer{
	protected JTextField field;
	
	public FieldContainer(String labelText) {
		super(labelText);
		this.field = new JTextField();
		this.add(this.field);
	}
	
	public JTextField getField() {
		return this.field;
	}
}
