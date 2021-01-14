package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_product")
	private int idProduct;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="product")
	private List<Article> articles;

	//bi-directional one-to-one association to Ingredient
	@OneToOne(mappedBy="product")
	private Ingredient ingredient;

	//bi-directional one-to-one association to Utensil
	@OneToOne(mappedBy="product")
	private Utensil utensil;

	public Product() {
	}

	public int getIdProduct() {
		return this.idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Article addArticle(Article article) {
		getArticles().add(article);
		article.setProduct(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setProduct(null);

		return article;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Utensil getUtensil() {
		return this.utensil;
	}

	public void setUtensil(Utensil utensil) {
		this.utensil = utensil;
	}

}