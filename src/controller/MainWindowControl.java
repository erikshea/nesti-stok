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
		
	    var listPaneArticle = new ArticleDirectory(this);
	    this.addTab("Article", listPaneArticle);
	    
	    var listPaneSupplier = new SupplierDirectory(this);
	    this.addTab("Fournisseur", listPaneSupplier);
	    
	    var listPaneIngredient = new IngredientDirectory(this);
	    this.addTab("Ingrédient", listPaneIngredient);
	    
	    var listPaneAdministrator = new UserDirectory(this);
	    this.addTab("Utilisateur", listPaneAdministrator);
	    
		var infoPaneArticle = new ArticleInformation(this);
	    this.addTab("Détail article", infoPaneArticle);
	    
	    var infoPaneSupplier = new SupplierInformation(this);
	    this.addTab("Détail fournisseur", infoPaneSupplier);
	    
	    var infoPaneIngredient = new IngredientInformation(this);
	    this.addTab("Détail ingrédient", infoPaneIngredient);
	    
	    var infoPaneUser = new UserInformation(this);
	    this.addTab("Détail utilisateur", infoPaneUser);

	}
}
