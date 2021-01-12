package controller;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BaseInformation extends JPanel{
	private static final long serialVersionUID = -4055453327212347699L;
	protected MainWindowControl mainControl ;
	
	public BaseInformation(MainWindowControl c) {
		this.mainControl=c;

		this.setLayout(new BorderLayout());
		
		var buttonBottomBar = new JPanel();
		buttonBottomBar.setLayout(new BoxLayout(buttonBottomBar, BoxLayout.X_AXIS));
		
		var buttonCancel = new JButton("Annuler");
		var buttonValidate = new JButton("Envoyer");
		
		buttonBottomBar.add(Box.createHorizontalGlue());
		buttonBottomBar.add(buttonCancel);
		buttonBottomBar.add(buttonValidate);
		this.add(buttonBottomBar, BorderLayout.PAGE_END);
	}
}
