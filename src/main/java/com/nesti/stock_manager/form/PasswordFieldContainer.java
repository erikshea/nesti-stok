package com.nesti.stock_manager.form;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nesti.stock_manager.controller.BaseInformation;

@SuppressWarnings("serial")
public class PasswordFieldContainer extends FieldContainer {
	public PasswordFieldContainer(String labelText, BaseInformation infoPane) {
		super(labelText, infoPane);
		
		this.remove(this.field);
		this.field = new JPasswordField();
	}
	
	@Override
	public JPasswordField getField() {
		return (JPasswordField) this.field;
	}
}
