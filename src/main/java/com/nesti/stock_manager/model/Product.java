package com.nesti.stock_manager.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.nesti.stock_manager.dao.BaseDao;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public abstract class Product extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_product")
	private int idProduct;

	private String name;

	private String reference;
	
	private String flag;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="product", cascade = CascadeType.REMOVE)
	private List<Article> articles;
	
	public Product() {
		this.setFlag(BaseDao.DEFAULT);
	}
	
	public Product(String r, String n) {
		this();
		setReference(r);
		setName(n);
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

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}