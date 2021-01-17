
package controller;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BaseInformation extends JPanel {
	private static final long serialVersionUID = -4055453327212347699L;
	protected MainWindowControl mainControl;
	protected Object item;
	protected JButton buttonValidate, buttonCancel;
	
	public BaseInformation(MainWindowControl c, Object o) {
		this.item = o;
		
		this.mainControl = c;

		this.setLayout(new BorderLayout());

		var buttonBottomBar = new JPanel();
		buttonBottomBar.setLayout(new BoxLayout(buttonBottomBar, BoxLayout.X_AXIS));

		this.buttonCancel = new JButton("Annuler");
		this.buttonValidate = new JButton("Enregistrer");

		buttonBottomBar.add(Box.createHorizontalGlue());
		buttonBottomBar.add(buttonCancel);
		buttonBottomBar.add(buttonValidate);
		this.add(buttonBottomBar, BorderLayout.PAGE_END);
		
		
		this.buttonCancel.addActionListener( e->{
			this.mainControl.remove(this);
		});
	}
}