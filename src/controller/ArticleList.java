package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import util.HibernateUtil;
import model.*;

@SuppressWarnings("serial")
public class ArticleList extends BaseList {

	public ArticleList() {
		super();

		
		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();

		addToCartField.setMaximumSize(new Dimension(100, 30));
        addToCartField.setPreferredSize(new Dimension(100, 0));

		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);

		this.buttonBar.add(addToCart, 4);
		

		var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        
        List<Article> queryArticle = session.createQuery("from Article").list();
        
        queryArticle.forEach( v->{
        	this.addRowData(new Object[] { v.getName(),"", "", "",v.getStock(),""});
 		});

		transaction.commit();
		
		
	}

	@Override
	public String getTitle() {
		return "Liste d'article";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Description", "Fournisseur par défaut", "Prix d'achat","Type","Stock","PV Conseillé" };
	}
	
}
