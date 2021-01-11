package controller;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ArticleInformation extends JPanel {
	private static final long serialVersionUID = 1775908299271902575L;

	public ArticleInformation() {
		this.setLayout(new BorderLayout());
		var testLabelLeft = new JLabel("Label de test pour la section gauche!");
		var testLabelRight = new JLabel("Label de test pour la section droite!");
		var testLabelBotton = new JLabel("Label de test pour la section du BAS");
		this.add(testLabelLeft, BorderLayout.LINE_START);
		this.add(testLabelRight, BorderLayout.LINE_END);
	

		/**
		 * create Page End
		 */
		var buttonBottomBar = new JPanel();
		var buttonCancel = new JButton();
		var buttonValidate = new JButton();

		// on ajoute un layout
		var buttonBottomBarLayout = new GroupLayout(buttonBottomBar);
		buttonBottomBar.setLayout(buttonBottomBarLayout);

		buttonBottomBarLayout.setHorizontalGroup(buttonBottomBarLayout.createSequentialGroup()
				.addGroup(buttonBottomBarLayout.createParallelGroup().addComponent(buttonCancel))
				.addGroup(buttonBottomBarLayout.createParallelGroup().addComponent(buttonValidate)));

		
		this.add(buttonBottomBar, BorderLayout.PAGE_END);
	}
}
