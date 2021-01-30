package com.nesti.stock_manager.form;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nesti.stock_manager.controller.BaseInformation;

/**
 *	Base for all field containers. Contains just the label, and basic methods
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class BaseFieldContainer extends JPanel{
	protected JLabel label; // Field label
	protected BaseInformation<?> formContainer;  // Corresponding pane that holds the form

	protected boolean valid; // true if field is valid
	
	public BaseFieldContainer(String labelText) {
		valid=true; // Starts valid
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setAlignmentX(Component.LEFT_ALIGNMENT); 
		
		this.setPreferredSize(new Dimension(0,30));
		this.setMaximumSize(new Dimension(Short.MAX_VALUE,0));
		this.label = new JLabel(labelText);
		this.label.setPreferredSize(new Dimension(120, Short.MAX_VALUE));
		this.add(label);
	}

	public boolean validates() {
		return valid;
	}
	
	public BaseInformation<?> getFormContainer() {
		return formContainer;
	}

	public void setFormContainer(BaseInformation<?> container) {
		this.formContainer = container;
	}

	public JLabel getLabel() {
		return label;
	}

}
