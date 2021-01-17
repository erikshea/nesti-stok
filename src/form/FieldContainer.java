package form;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import util.ReflectionProperty;

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
	/*
	public void bindField(Object o, String propertyName) {
		field.setText(ReflectionProperty.get(o, propertyName).toString());
		System.out.println();
		field.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
		  	change();
		  }
		  public void removeUpdate(DocumentEvent e) {
			change();
		  }
		  public void insertUpdate(DocumentEvent e) {
			change();
		  }

		  public void change() {
			 var val = ReflectionProperty.get(o, propertyName);
			 
		     ReflectionProperty.set(o, propertyName, field.getText());
		     
		     System.out.println(val.getClass());
		  }
		});
	}*/
}

