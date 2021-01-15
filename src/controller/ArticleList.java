package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.ArticleDao;
import model.Article;
import util.HibernateUtil;

@SuppressWarnings("serial")
public class ArticleList extends BaseList {

	public ArticleList(MainWindowControl c) {
		super(c);

		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();

		addToCartField.setMaximumSize(new Dimension(100, 30));
        addToCartField.setPreferredSize(new Dimension(100, 0));

		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);

		this.buttonBar.add(addToCart, 4);

		// Detail of the article List
        var dao = new ArticleDao();
        var articles = dao.findAll();
        articles.forEach( a->{
    		this.addRowData(new Object[] {a.getName(),a.getCode(),"", 0,a.getStock(),0});
        });

	}

	@Override
	public String getTitle() {
		return "Liste d'article";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] {"Description", "Code", "Fournisseur par défaut", "Prix d'achat","Stock","PV Conseillé" };
	}
	
	@Override
	public void setUpButtonListeners()  {
		this.buttonModify.addActionListener( e->{
			var code = this.table.getValueAt(this.table.getSelectedRow(), 1);

			var a = (new ArticleDao()).findOneBy("code",code);

			this.mainController.addCloseableTab(
					"Article: " + a.getName(),
					new ArticleInformation(this.mainController,a)
			);

		});
	}
}
