package com.nesti.stock_manager.form;


import java.awt.Color;
import java.util.function.Predicate;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nesti.stock_manager.controller.BaseInformation;
import com.nesti.stock_manager.util.HibernateUtil;

@SuppressWarnings("serial")
public class FieldContainer extends BaseFieldContainer{
	protected JTextField field;
	protected String initialFieldValue;
	protected static final Color COLOR_VALID = Color.WHITE;
	protected static final Color COLOR_INVALID = Color.ORANGE;
	
	public FieldContainer(String labelText, BaseInformation infoPane) {
		super(labelText, infoPane);
		infoPane.addValidatedField(this);
		this.field = new JTextField();
		this.add(this.field);
	}
	
	public JTextField getField() {
		return this.field;
	}
	
	public<E> void bind(String initialFieldValue, ValueSetter s) {

		this.bind(initialFieldValue, s, null);
	}
	
	public<E> void bind(String initialFieldValue, ValueSetter s, Predicate<String> validator) {
		getField().setText(initialFieldValue);
		validateField(s, validator);
		
		getField().getDocument().addDocumentListener(new DocumentListener() {
			  @Override
			public void changedUpdate(DocumentEvent e) {
				  validateField(s, validator);
			  }
			  @Override
			public void removeUpdate(DocumentEvent e) {
				  validateField(s, validator);
			  }
			  @Override
			public void insertUpdate(DocumentEvent e) {
				  validateField(s, validator);
			}
		});
	}
	
	public void validateField(ValueSetter s, Predicate<String> validator) {
		 getField().setBackground(COLOR_VALID);
		 valid = true;
		 try {
			 if ( validator != null ) {
				 valid =		getField().getText().equals(initialFieldValue) 	// always valid if text is same as initial value
						 	||	validator.test(getField().getText()); 			// valid if validator passes

				 valid &= getField().getText().length()>0;
			 }
			 
			 if (valid) {
				 s.set(getField().getText());
			 }  else {
				 getField().setBackground(COLOR_INVALID);
			 }
			 
		 } catch ( Exception e ) {	// exception when setting (ie entering letters for Double property)
			 valid = false;
			 getField().setBackground(COLOR_INVALID);
		 } 

		 infoPane.checkValidatedFields();
	}
}
		


