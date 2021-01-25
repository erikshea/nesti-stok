package com.nesti.stock_manager.form;



import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class LabelContainer extends BaseFieldContainer{
	protected JLabel infoLabel;
	
	public LabelContainer(String labelText, String infoText) {
		super(labelText);
		this.infoLabel = new JLabel();
		infoLabel.setText(infoText);
		this.add(this.infoLabel);
		this.setBackground(Color.getHSBColor(0f, 0f, 0.9f));
		label.setMaximumSize(new Dimension(label.getPreferredSize().width, Short.MAX_VALUE));
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
	}
	
	public JLabel getInfoLabel() {
		return this.infoLabel;
	}
	
	public void setText(String infoText) {
		this.infoLabel.setText(infoText);
	}
}
		


