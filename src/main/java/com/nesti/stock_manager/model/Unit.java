package com.nesti.stock_manager.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the unit database table.
 * 
 */
@Entity
@NamedQuery(name="Unit.findAll", query="SELECT u FROM Unit u")
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_unit")
	private int idUnit;

	private String name;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="unit")
	private List<Article> articles;

	//bi-directional many-to-many association to Unit
	@ManyToMany(mappedBy="units")
	private List<Ingredient> ingredients;

	public Unit() {
	}

	public int getIdUnit() {
		return this.idUnit;
	}

	public void setIdUnit(int idUnit) {
		this.idUnit = idUnit;
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
		article.setUnit(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setUnit(null);

		return article;
	}

	public List<Ingredient> getIngredients() {
		return this.ingredients;
	}
/*
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}*/

}