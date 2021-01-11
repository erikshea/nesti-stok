package controller;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ArticleInformation extends JPanel{
	private static final long serialVersionUID = 1775908299271902575L;
	public ArticleInformation() {
		this.setLayout(new BorderLayout());
		var testLabelRight = new JLabel("Label de test pour la section droite!");
		var testLabelBotton = new JLabel("Label de test pour la section du BAS");
		this.add(testLabelRight, BorderLayout.LINE_END);
	

		/**
		 * create Page_End
		 */
		var buttonBottomBar = new JPanel();
		var buttonCancel = new JButton("test1");
		var buttonValidate = new JButton("test3");

		// add layout to the button group
		var buttonBottomBarLayout = new GroupLayout(buttonBottomBar);
		buttonBottomBarLayout.setHorizontalGroup(
			buttonBottomBarLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, buttonBottomBarLayout.createSequentialGroup()
					.addContainerGap(0, Short.MAX_VALUE)
					.addComponent(buttonCancel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonValidate))
		);
		buttonBottomBarLayout.setVerticalGroup(
			buttonBottomBarLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(buttonBottomBarLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(buttonValidate)
					.addComponent(buttonCancel))
		);
		buttonBottomBar.setLayout(buttonBottomBarLayout);
		buttonBottomBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		this.add(buttonBottomBar, BorderLayout.PAGE_END);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
	}
}
