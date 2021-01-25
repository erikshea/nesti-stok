package com.nesti.stock_manager.form;


import java.awt.Color;
import java.util.function.Predicate;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nesti.stock_manager.controller.BaseInformation;

@SuppressWarnings("serial")
public class FieldContainer extends BaseFieldContainer{
	protected JTextField field;
	protected String initialFieldValue;
	protected static final Color COLOR_VALID = Color.WHITE;
	protected static final Color COLOR_INVALID = Color.ORANGE;
	
	public FieldContainer(String labelText) {
		super(labelText);
		this.field = new JTextField();
		this.add(this.field);
	}
	
	public FieldContainer(String labelText, BaseInformation<?> i) {
		this(labelText);
		setInfoPane(i);
		infoPane.addValidatedField(this);
	}
	
	public JTextField getField() {
		return this.field;
	}
	
	public void bind(String initialText, ValueSetter s) {
		this.bind(initialText, s, null, false);
	}
	
	public void bind(String initialText, ValueSetter s, boolean emptyAllowed) {
		this.bind(initialText, s, null, emptyAllowed);
	}
	
	public void bind(String initialText, ValueSetter s, Predicate<String> validator) {
		this.bind(initialText, s, validator, false);
	}
	
	public void bind(String initialText, ValueSetter s, Predicate<String> validator, boolean emptyAllowed) {
		initialFieldValue = initialText;
		getField().setText(initialFieldValue);
		validateField(s, validator, emptyAllowed);
		
		getField().getDocument().addDocumentListener(new DocumentListener() {
			  @Override
			public void changedUpdate(DocumentEvent e) {
				  validateField(s, validator, emptyAllowed);
			  }
			  @Override
			public void removeUpdate(DocumentEvent e) {
				  validateField(s, validator, emptyAllowed);
			  }
			  @Override
			public void insertUpdate(DocumentEvent e) {
				  validateField(s, validator, emptyAllowed);
			}
		});
	}

	
	public void validateField(ValueSetter s, Predicate<String> validator, boolean emptyAllowed) {
		 getField().setBackground(COLOR_VALID);
		 valid = true;
		 try {
			 if ( validator != null ) {
				 valid =		getField().getText().equals(initialFieldValue) 	// always valid if text is same as initial value
						 	||	validator.test(getField().getText()); 			// valid if validator passes
			 }
			 
			 if ( !emptyAllowed ) {
				 valid &= getField().getText().length()>0;
			 }
		 } catch ( Exception e ) {	// exception when setting (ie entering letters for Double property)
			 valid = false;
		 } 


		 if (valid) {
			 s.set(getField().getText());
		 }  else {
			 getField().setBackground(COLOR_INVALID);
		 }
		 
		 if ( infoPane != null ) {
			 infoPane.checkValidatedFields();
		 }
	}
	
}
		


