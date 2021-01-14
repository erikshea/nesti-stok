package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JTabbedPane;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Article;

public class MainWindowControl extends JTabbedPane {
	private static final long serialVersionUID = 4705253258936419615L;
	
	public MainWindowControl() {
		SessionFactory sessionFactory = new Configuration()
                .configure("/application/hibernate.cfg.xml")
                .buildSessionFactory();
		
		var test = sessionFactory.openSession().createQuery("from Article");

		List<Article> t = test.list();
		
		
		t.forEach( v->{
			System.out.println(v.getProduct().getIngredient().getName());
		});
		
		System.out.println();
		
		this.setPreferredSize(new Dimension(
				(int) (Toolkit.getDefaultToolkit().getScreenSize().width*0.8),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().height*0.8)		
		));
		var infoPane = new ArticleInformation(this);
	    this.addTab("Détail article", infoPane);
	}
}
