package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_product")
	private int idProduct;

	private String name;

	private String reference;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="product")
	private List<Article> articles;
	
/*
	//bi-directional one-to-one association to Ingredient
	@OneToOne(mappedBy="product")
	private Ingredient ingredient;
*/
	/*
	//bi-directional one-to-one association to Utensil
	@OneToOne(mappedBy="product")
	private Utensil utensil;
*/
	public Product() {
	}

	public int getIdProduct() {
		return this.idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
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
/*
	public Ingredient getIngredient() {
		return this.ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}*/
/*
	public Utensil getUtensil() {
		return this.utensil;
	}

	public void setUtensil(Utensil utensil) {
		this.utensil = utensil;
	}*/

}