package form;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
		listModel.removeElement(selected);
	}
	
	public EditableListFieldContainer(String labelText) {
		super(labelText);

		this.setPreferredSize(new Dimension(0, 120));

		var buttonContainer = new JPanel();
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

		var buttonDimension = new Dimension(50, 50);

		var plusButton = new JButton("+");
		plusButton.setPreferredSize(buttonDimension);

		// c'est comme un add action listener mais on supprime les trucs en trop, ca
		// s'appelle lambda
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
		// on met les deux botons dans un panel ave cbox layout vertyical
		// c'est ce conteneur qu'on ajoutera a ingredient information
	}

}
