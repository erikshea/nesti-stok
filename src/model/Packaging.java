package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the packaging database table.
 * 
 */
@Entity
@NamedQuery(name="Packaging.findAll", query="SELECT p FROM Packaging p")
public class Packaging implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_packaging")
	private int idPackaging;

	private String name;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="packaging")
	private List<Article> articles;

	public Packaging() {
	}

	public int getIdPackaging() {
		return this.idPackaging;
	}

	public void setIdPackaging(int idPackaging) {
		this.idPackaging = idPackaging;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Article addArticle(Article article) {
		getArticles().add(article);
		article.setPackaging(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setPackaging(null);

		return article;
	}

}