package controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalIconFactory;

import model.*;
import util.HibernateUtil;

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
	    
		
	    this.addTab("Article", new ArticleInformation(this,null));
	}

    public void addCloseableTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        int count = this.getTabCount() - 1;
        this.setTabComponentAt(count, new CloseButtonTab(component, title, icon));
    }


    public void addCloseableTab(String title, Icon icon, Component component) {
    	this.addCloseableTab(title, icon, component, null);
    }


    public void addCloseableTab(String title, Component component) {
    	this.addCloseableTab(title, null, component);
    }

    
    /* Button */
    public class CloseButtonTab extends JPanel {
		private static final long serialVersionUID = 1L;
		private Component tab;

        public CloseButtonTab(final Component tab, String title, Icon icon) {
            this.tab = tab;
            setOpaque(false);
            FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
            setLayout(flowLayout);
            JLabel jLabel = new JLabel(title);
            jLabel.setIcon(icon);
            add(jLabel);
            JButton button = new JButton(MetalIconFactory.getInternalFrameCloseIcon(8));
            button.setMargin(new Insets(0, 0, 0, 0));
            button.addMouseListener(new CloseableTabListener(tab));
            add(button);
        }
    }

    public class CloseableTabListener implements MouseListener
    {
        private Component tab;

        public CloseableTabListener(Component tab){
            this.tab=tab;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton closeButton = (JButton) e.getSource();
                JTabbedPane tabbedPane = (JTabbedPane) closeButton.getParent().getParent().getParent();
                tabbedPane.remove(tab);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
