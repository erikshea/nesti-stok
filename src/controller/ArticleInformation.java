package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

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
import model.Ingredient;
import util.HibernateUtil;

public class ArticleInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public ArticleInformation(MainWindowControl c, Article article) {
		super(c, article);

		if (article == null) {
			article = new Article();
		}
		final var articleFinal= article;
		var dao = new ArticleDao();
		var ingredientDao = new IngredientDao();
		
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

		var descriptionFieldContainer = new FieldContainer("Description", this);
		descriptionFieldContainer.bind(
			articleFinal.getName(),
			(s)-> articleFinal.setName(s),
			(fieldValue)->dao.findOneBy("name", fieldValue) == null);
		//descriptionFieldContainer.getField().setText(article.getName());
		articleForm.add(descriptionFieldContainer);

		var codeFieldContainer = new FieldContainer("Code Article", this);
		codeFieldContainer.bind(
			articleFinal.getCode(),
			(s)->articleFinal.setCode(s),
			(fieldValue)->dao.findOneBy("code", fieldValue) == null);
		articleForm.add(codeFieldContainer);

		var eanFieldContainer = new FieldContainer("EAN", this);
		eanFieldContainer.bind(
			articleFinal.getEan(),
			(s)->articleFinal.setEan(s),
			(fieldValue)->dao.findOneBy("ean", fieldValue) == null);
		articleForm.add(eanFieldContainer);
		
		if (article.getProduct() instanceof Ingredient) {
			var ingredientListContainer = new ListFieldContainer("Ingrédient:", this);
			var listModel = ingredientListContainer.getListModel();
			ingredientDao.findAll().forEach(ing -> {
				listModel.addElement(ing.getName());				
			});
			ingredientListContainer.bindSelection(
				articleFinal.getProduct().getName(),
				(s)->articleFinal.setProduct(ingredientDao.findOneBy("name",s)));
			articleForm.add(ingredientListContainer);
		}

		var quantityFieldContainer = new FieldContainer("Quantité", this);
		quantityFieldContainer.bind(
			String.valueOf(articleFinal.getQuantity()),
			(s)->articleFinal.setQuantity(Double.parseDouble(s)));
		articleForm.add(quantityFieldContainer);

		var weightFieldContainer = new FieldContainer("Poids", this);
		weightFieldContainer.bind(
			String.valueOf(articleFinal.getWeight()),
			(s)->articleFinal.setWeight(Double.parseDouble(s)));
		articleForm.add(weightFieldContainer);

		articleForm.add(Box.createVerticalGlue());

		this.add(articleForm, BorderLayout.WEST);

		this.buttonValidate.addActionListener( e->{
			try{
				dao.saveOrUpdate(articleFinal);
				(new ProductDao()).saveOrUpdate(articleFinal.getProduct());
				HibernateUtil.getSession().getTransaction().commit();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
				    "Veuillez vérifier les champs en orange.",
				    "Paramètres invalides",
				    JOptionPane.WARNING_MESSAGE);
			}
			this.mainControl.getArticleDirectory().getEntityList().refresh();
			this.mainControl.remove(this);
			this.mainControl.setSelectedComponent(this.mainControl.getArticleDirectory());
		});
		
		this.buttonCancel.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getArticleDirectory());
		});
	}
}