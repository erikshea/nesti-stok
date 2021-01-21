package com.nesti.stock_manager.controller;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nesti.stock_manager.dao.ArticleDao;

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

        refresh();
    }

    public void refresh() {
        this.tableModel.getDataVector().removeAllElements();
        // Detail of the article List
        var dao = new ArticleDao();
        var articles = dao.findAll();
        articles.forEach(a -> {
            this.addRowData(new Object[]{a.getName(), a.getCode(), "", 0, a.getStock(), 0});
        });
    }

    @Override
    public String getTitle() {
        return "Liste d'article";
    }

    @Override
    public Object[] getTableModelColumns() {
        return new Object[]{"Description", "Code", "Fournisseur par d�faut", "Prix d'achat", "Stock", "PV Conseill�"};
    }

    @Override
    public void setUpButtonListeners() {
        super.setUpButtonListeners();
        this.buttonModify.addActionListener(e -> {
            var code = this.table.getValueAt(this.table.getSelectedRow(), 1);

            var a = (new ArticleDao()).findOneBy("code", code);

            this.mainController.addCloseableTab(
                    "Article: " + a.getName(),
                    new ArticleInformation(this.mainController, a)
            );
        });

        this.buttonAdd.addActionListener(e -> { // TODO
            /*this.mainController.addCloseableTab(
					"Nouvel Article",
					new ArticleInformation(this.mainController,null)
			);*/
        });
        /*
		this.buttonDelete.addActionListener( e->{
			var dao = new ArticleDao();
			
			for ( var rowIndex : this.table.getSelectedRows()) {
				var article = dao.findOneBy("code", this.table.getValueAt(rowIndex, 1));
				dao.delete(article);
			}
			
			refresh();
		});
		
		this.buttonDuplicate.addActionListener( e->{
			var code = this.table.getValueAt(this.table.getSelectedRow(), 1);
			var a = (new ArticleDao()).findOneBy("code",code);
			a.setIdArticle(0);
			a.getProduct().setIdProduct(0);
			a.setCode("");
			a.setName("");
			a.setEan("");
			
			
			this.mainController.addCloseableTab(
					"Nouvel Article",
					new ArticleInformation(this.mainController,a)
			);
		});*/
    }
}
