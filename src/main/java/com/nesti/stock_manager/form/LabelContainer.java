package com.nesti.stock_manager.form;



import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * simple label container (no editable field)
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class LabelContainer extends BaseFieldContainer{
	protected JLabel infoLabel;
	
	public LabelContainer(String labelText, String infoText) {
		super(labelText);
		this.infoLabel = new JLabel();
		infoLabel.setText(infoText);
		this.add(this.infoLabel);
		label.setMaximumSize(new Dimension(label.getPreferredSize().width, Short.MAX_VALUE));
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
	}
	
	public JLabel getInfoLabel() {
		return this.infoLabel;
	}
	
	public void setInfoText(String infoText) {
		this.infoLabel.setText(infoText);
	}
}
		


