package controller;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ArticleInformation extends JPanel{
	private static final long serialVersionUID = 1775908299271902575L;
	public ArticleInformation() {
		this.setLayout(new BorderLayout());
		var testLabelLeft = new JLabel("Label de test pour la section gauche!");
		var testLabelRight = new JLabel("Label de test pour la section droite!");
		var testLabelBotton = new JLabel("Label de test pour la section du BAS");
		this.add(testLabelLeft, BorderLayout.LINE_START);
		this.add(testLabelRight, BorderLayout.LINE_END);
		this.add(testLabelBotton, BorderLayout.PAGE_END);
	}
}
