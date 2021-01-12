package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;

public class MainWindowControl extends JTabbedPane {
	private static final long serialVersionUID = 4705253258936419615L;
	
	public MainWindowControl() {
		this.setPreferredSize(new Dimension(
				(int) (Toolkit.getDefaultToolkit().getScreenSize().width*0.8),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().height*0.8)		
		));
		var infoPane = new ArticleInformation(this);
	    this.addTab("Détail article", infoPane);
	}
}
