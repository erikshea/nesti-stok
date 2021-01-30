package com.nesti.stock_manager.form;


import java.awt.Color;
import java.util.function.Predicate;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nesti.stock_manager.controller.BaseInformation;

/**
 *Textfield container with validation logic. Base on which other validated fields are built.
 *
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class FieldContainer extends BaseFieldContainer{
	protected JTextField field;
	protected String initialFieldValue; // Field value when first set
	protected static final Color COLOR_VALID = Color.WHITE; // Field background color if valid
	protected static final Color COLOR_INVALID = Color.ORANGE; // Field background color if invalid
	
	/**
	 * Create field container rom label text
	 * @param labelText label text
	 */
	public FieldContainer(String labelText) {
		super(labelText);
		this.field = new JTextField();
		this.add(this.field);
	}
	
	/**
	 * create field container from label text, and form container object
	 * @param labelText label text
	 * @param container form container
	 */
	public FieldContainer(String labelText, BaseInformation<?> container) {
		this(labelText);
		setFormContainer(container);
		formContainer.addValidatedField(this);
	}

	
	/**
	 * Makes any change to field text call a passed setter function
	 * @param initialText initial field text
	 * @param s setter object implements a single set method (to pass lambda)
	 */
	public void bind(String initialText, ValueSetter s) {
		this.bind(initialText, s, null, true);
	}
	
	/**
	 * Makes any change to field text call a passed setter function
	 * @param initialText initial field text
	 * @param s setter object implements a single set method (to pass lambda)
	 * @param emptyAllowed whether field validates if it's empty
	 */
	public void bind(String initialText, ValueSetter s, boolean emptyAllowed) {
		this.bind(initialText, s, null, emptyAllowed);
	}
	
	/**
	 * Makes any change to field text call a passed setter function, and validates the field with a Predicate object
	 * @param initialText initial field text
	 * @param validator Predicate implements a single boolean test method (to pass lambda)
	 */
	public void bind(String initialText, ValueSetter s, Predicate<String> validator) {
		this.bind(initialText, s, validator, false);
	}
	
	/**
	 * Makes any change to field text call a passed setter function, and validates the field with a Predicate object
	 * @param initialText initial field text
	 * @param s setter object implements a single set method (to pass lambda)
	 * @param validator Predicate implements a single boolean test method (to pass lambda)
	 * @param emptyAllowed whether field validates if it's empty
	 */
	public void bind(String initialText, ValueSetter s, Predicate<String> validator, boolean emptyAllowed) {
		initialFieldValue = initialText;
		getField().setText(initialFieldValue); // Field starts with initial value passed
		validateField(s, validator, emptyAllowed);
		
		// run validation method on any change to field, and 
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

	
	/**
	 * Validates a field according to a predicate, and tries to call the setter with the changed field value
	 * @param s setter object implements a single set method (to pass lambda)
	 * @param validator Predicate implements a single boolean test method (to pass lambda)
	 * @param emptyAllowed whether field validates if it's empty
	 */
	public void validateField(ValueSetter s, Predicate<String> validator, boolean emptyAllowed) {
		 getField().setBackground(COLOR_VALID); 
		 valid = true; // starts valid
		 try {
			 // if validator specified
			 if ( validator != null ) {
				 valid =		getField().getText().equals(initialFieldValue) 	// always valid if text is same as initial value
						 	||	validator.test(getField().getText()); 			// valid if validator passes
			 }
			 
			 if ( !emptyAllowed ) {
				 valid &= getField().getText().length()>0;
			 }
			 
			 if (valid) {
				 s.set(getField().getText());
			 }
		 } catch ( Exception e ) {	// exception when setting (ie entering letters for Double property)
			 valid = false;
		 } 


		 if ( !valid) {
			 getField().setBackground(COLOR_INVALID);
		 }
		 
		 if ( formContainer != null ) { // if form container set
			 formContainer.checkValidatedFields(); // Check all fields (for button activation..)
		 }
	}
	
	public JTextField getField() {
		return this.field;
	}
	
}
		


