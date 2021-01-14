package controller;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BaseDirectory extends JPanel {

	protected MainWindowControl mainControl;

	public BaseDirectory(MainWindowControl c) {
		this.mainControl = c;

		this.setLayout(new BorderLayout());
		
		var displayListPanel = new JPanel();
		displayListPanel.setLayout(new BoxLayout(displayListPanel,BoxLayout.X_AXIS));
		
		
		
		// Define all the button of the head on the article list
		var barHeadButton = new JPanel();
		barHeadButton.setLayout(new BoxLayout(barHeadButton, BoxLayout.X_AXIS));

		var buttonAdd = new JButton("Créer");
		var buttonDelete = new JButton("Supprimer");
		var buttonModify = new JButton("Modifier");
		var buttonDuplicate = new JButton("Dupliquer");

		barHeadButton.add(buttonAdd);
		barHeadButton.add(buttonDelete);
		barHeadButton.add(buttonModify);
		barHeadButton.add(buttonDuplicate);
		
		
		this.add(barHeadButton);
		
		
	}
}
