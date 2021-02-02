package com.nesti.stock_manager.form;

import javax.swing.JPasswordField;

import com.nesti.stock_manager.controller.BaseInformation;

/**
 * Just like field container, but with a PasswordField instead of a TextField
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class PasswordFieldContainer extends FieldContainer {
	public PasswordFieldContainer(String labelText, BaseInformation<?> infoPane) {
		super(labelText, infoPane);
		
		this.remove(this.field); // Remove Textfield
		this.field = new JPasswordField();
		this.add(this.field); // Add passwordfield in its place
	}
	
	@Override
	public JPasswordField getField() {
		return (JPasswordField) this.field;
	}
}
