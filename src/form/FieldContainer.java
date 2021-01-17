package form;


import java.awt.Color;
import java.util.function.Predicate;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.BaseDao;
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
	
	public<E> void bind(ValueGetter v, ValueSetter s) {

		this.bind(v, s, null);
	}
	
	public<E> void bind(ValueGetter v, ValueSetter s, Predicate<String> validator) {

		field.setText(v.get());

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
				 s.set(field.getText());
				  
				 if (validator != null) {
					 var isValid = 	field.getText().equals(v.get()) || validator.test(field.getText());
					 if (!isValid) {
						 field.setBackground(Color.orange);
					 } 
				 }

			  }
		});
	}
}
		


