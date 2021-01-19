package controller;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import model.*;

import dao.OfferDao;
import dao.SupplierDao;

@SuppressWarnings("serial")
public class SupplierArticleList extends BasePriceList {

	public SupplierArticleList(Supplier supplier) {
		super(supplier);
		
		this.table = this.getPriceTable();
	

		var daoOffer = new OfferDao();
		var articlePrice = daoOffer.findAll();
		articlePrice.forEach(ap -> {
			this.addRowData(new Object[] { ap.getArticle().getCode(), ap.getArticle().getName(), ap.getPrice() });
		});

		// FOOTER OF THE SCREEN, ADD A SUPPLIER
		var scrollPriceList = new JScrollPane(table);
		scrollPriceList.setPreferredSize(new Dimension(0, 150));
		scrollPriceList.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
		this.add(scrollPriceList);

		var addPriceContainer = new JPanel();
		addPriceContainer.setPreferredSize(new Dimension(500, 100));
		addPriceContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		addPriceContainer.setLayout(new BoxLayout(addPriceContainer, BoxLayout.X_AXIS));

		var listModel = new DefaultListModel<>();
		var list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		var daoArticle = new SupplierDao();
		var articles = daoArticle.findAll();
		articles.forEach(a -> listModel.addElement(a.getName()));

		var scrollPane = new JScrollPane(list);
		addPriceContainer.add(scrollPane);

		var priceArticle = new JTextField("12");
		addPriceContainer.add(priceArticle);

		addButton = new JButton("+");
		addPriceContainer.add(addButton);

		this.add(addPriceContainer);

		this.add(Box.createVerticalGlue());

	}

	@Override
	public String getTitle() {
		return "Liste d'article";
	}

	@Override
	public Object[] getTableModelColumns() {
		return new Object[] { "Code article", "Désignation", "Prix de vente", "Suppression" };
	}
}