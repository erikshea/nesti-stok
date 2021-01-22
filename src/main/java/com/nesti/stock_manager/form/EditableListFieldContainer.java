package com.nesti.stock_manager.form;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.nesti.stock_manager.controller.BaseInformation;

@SuppressWarnings("serial")
public class EditableListFieldContainer extends ListFieldContainer {

	
	/**
	 * allows to add a element into the list from an input
	 */
	public void  addToList() {
//		Object[] possibilities = null;
		String s = (String)JOptionPane.showInputDialog(
		                    this,
		                    "Ajouter : ",
		                    "Ajouter ...",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    null);

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
		    System.out.println( s );
		    return;
		}
	}
	
	/**
	 * Remove an element from the list
	 */
	public void deleteFromList() {
		var selected = this.list.getSelectedValue();
		getListModel().removeElement(selected);
	}
	
	public EditableListFieldContainer(String labelText, BaseInformation infoPane) {
		super(labelText, infoPane);

		this.setPreferredSize(new Dimension(0, 120));

		var buttonContainer = new JPanel();
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

		var buttonDimension = new Dimension(50, 50);

		var plusButton = new JButton("+");
		plusButton.setPreferredSize(buttonDimension);

		plusButton.addActionListener((e) -> {
			this.addToList();
		});

		var minusButton = new JButton("-");
		minusButton.setPreferredSize(buttonDimension);
		
		minusButton.addActionListener((e) -> {
			this.deleteFromList();
		});

		buttonContainer.add(plusButton);
		buttonContainer.add(Box.createVerticalGlue());
		buttonContainer.add(minusButton);

		this.add(buttonContainer);

	}

}
