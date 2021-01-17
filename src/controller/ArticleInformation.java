package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.ArticleDao;
import dao.IngredientDao;
import dao.ProductDao;
import form.*;
import model.Article;
import model.Product;

public class ArticleInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public ArticleInformation(MainWindowControl c, Article article) {
		super(c, article);

		if (article == null) {
			article = new Article();
			article.setProduct(new Product());
		}
		final var articleFinal= article;
		var dao = new ArticleDao();
		
		var supplierPriceList = new ArticleSupplierList(article);
		if (article.getIdArticle()==0) {
			supplierPriceList.getAddButton().setEnabled(false);
		}
		this.add(supplierPriceList, BorderLayout.EAST);
// left of the screen, article's information
		var articleForm = new JPanel();
		articleForm.setPreferredSize(new Dimension(500, 0));
		// on spécifie l'axe d'empillement
		articleForm.setLayout(new BoxLayout(articleForm, BoxLayout.Y_AXIS));

		var addToCart = new JPanel();
		addToCart.setLayout(new BoxLayout(addToCart, BoxLayout.X_AXIS));
		var addToCartField = new JTextField();
		addToCartField.setMaximumSize(new Dimension(40, 30));
		addToCart.add(addToCartField);
		var addToCartButton = new JButton("Ajouter au panier");
		addToCart.add(addToCartButton);

		addToCart.setAlignmentX(Component.LEFT_ALIGNMENT);
		articleForm.add(addToCart);

		var titleArticleInformation = new JLabel("Article");
		articleForm.add(titleArticleInformation);

		var descriptionFieldContainer = new FieldContainer("Description");
		descriptionFieldContainer.bind(
			()-> articleFinal.getName(),
			(s)-> articleFinal.setName(s),
			(fieldValue)->dao.findOneBy("name", fieldValue) == null);
		//descriptionFieldContainer.getField().setText(article.getName());
		articleForm.add(descriptionFieldContainer);

		var codeFieldContainer = new FieldContainer("Code Article");
		codeFieldContainer.bind(
			()->articleFinal.getCode(),
			(s)->articleFinal.setCode(s),
			(fieldValue)->dao.findOneBy("code", fieldValue) == null);
		articleForm.add(codeFieldContainer);

		var eanFieldContainer = new FieldContainer("EAN");
		eanFieldContainer.bind(
			()->articleFinal.getEan(),
			(s)->articleFinal.setEan(s),
			(fieldValue)->dao.findOneBy("ean", fieldValue) == null);
		articleForm.add(eanFieldContainer);

		var i = (new IngredientDao()).findOneBy("idProduct", article.getProduct().getIdProduct());
		if (i != null) {
			var ingredientListContainer = new ListFieldContainer("Ingrédient:");
			var listModel = ingredientListContainer.getListModel();
			var idao = new IngredientDao();
			idao.findAll().forEach(ing -> {
				listModel.addElement(ing.getProduct().getName());				
			});
			ingredientListContainer.bind(
				()->articleFinal.getProduct().getName(),
				(s)->articleFinal.setProduct((new ProductDao()).findOneBy("name",s)));
			articleForm.add(ingredientListContainer);
		}

		var quantityFieldContainer = new FieldContainer("Quantité");
		quantityFieldContainer.bind(
			()->String.valueOf(articleFinal.getQuantity()),
			(s)->articleFinal.setQuantity(Double.parseDouble(s)));
		articleForm.add(quantityFieldContainer);

		var weightFieldContainer = new FieldContainer("Poids");
		weightFieldContainer.bind(
			()->String.valueOf(articleFinal.getWeight()),
			(s)->articleFinal.setWeight(Double.parseDouble(s)));
		articleForm.add(weightFieldContainer);

		articleForm.add(Box.createVerticalGlue());

		this.add(articleForm, BorderLayout.WEST);

		this.buttonValidate.addActionListener( e->{
			try{
				dao.saveOrUpdate(articleFinal);
				(new ProductDao()).saveOrUpdate(articleFinal.getProduct());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
				    "Veuillez vérifier les champs en orange.",
				    "Paramètres invalides",
				    JOptionPane.WARNING_MESSAGE);
			}
			this.mainControl.getArticleDirectory().getEntityList().refresh();
			this.mainControl.remove(this);
		});
	}
}