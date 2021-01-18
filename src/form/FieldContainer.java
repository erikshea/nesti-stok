package form;


import java.awt.Color;
import java.util.function.Predicate;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.BaseInformation;
import dao.BaseDao;
import util.ReflectionProperty;

@SuppressWarnings("serial")
public class FieldContainer extends BaseFieldContainer{
	protected JTextField field;
	protected String initialFieldValue;
	protected static final Color COLOR_VALID = Color.WHITE;
	protected static final Color COLOR_INVALID = Color.ORANGE;
	
	public FieldContainer(String labelText, BaseInformation infoPane) {
		super(labelText, infoPane);
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
		this.initialFieldValue = v.get();
		field.setText(initialFieldValue);
		var container = this;
		
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
				 field.setBackground(COLOR_VALID);
				 var isValid = false;
				 
				 try {
					 isValid = 		validator == null							// Always valid if no validator set
							 	||  field.getText().equals(initialFieldValue) 	// always valid if text is same as initial value
							 	||	validator.test(field.getText()); 			// valid if validator passes
					 if (isValid) {
						 s.set(field.getText());
					 }  else {
						 field.setBackground(COLOR_INVALID);
					 }
					 
				 } catch ( Exception e ) {	// exception when setting (ie entering letters for Double property)
					 isValid = false;
					 field.setBackground(COLOR_INVALID);
				 } 
				 
				 infoPane.setValidatedFieldsState(container, isValid);
			  }
		});
	}
}
		


