package com.nesti.stock_manager.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.nesti.stock_manager.dao.BaseDao;


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
	
	private String flag;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="packaging")
	private List<Article> articles;

	public Packaging() {
		this.setFlag(BaseDao.DEFAULT);
	}
	
	public Packaging(String n) {
		setName(n);
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
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}