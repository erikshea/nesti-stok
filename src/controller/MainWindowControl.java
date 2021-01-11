package controller;

import javax.swing.JTabbedPane;

public class MainWindowControl extends JTabbedPane {
	private static final long serialVersionUID = 4705253258936419615L;

	public MainWindowControl() {
		var infoPane = new ArticleInformation();
	    this.addTab("Détail article", infoPane);
	}
}
