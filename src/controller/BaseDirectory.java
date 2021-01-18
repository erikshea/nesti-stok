package controller;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BaseDirectory extends JPanel {
	protected MainWindowControl mainControl;

	public BaseDirectory(MainWindowControl c) {
		this.mainControl = c;

		this.setLayout(new BorderLayout());

	}
}
