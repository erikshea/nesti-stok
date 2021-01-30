package com.nesti.stock_manager.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.UnitDao;


/**
 * Persistent entity class corresponding to the unit table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@NamedQuery(name="Unit.findAll", query="SELECT u FROM Unit u")
public class Unit extends BaseEntity implements Serializable,Flagged {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_unit")
	private Integer idUnit;

	private String name;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="unit")
	private List<Article> articles;

	//bi-directional many-to-many association to Unit
	@ManyToMany(mappedBy="units")
	private List<Ingredient> ingredients;
	
	private String flag;

	private static UnitDao dao;
	
	public Unit() {
		this.setFlag(BaseDao.DEFAULT);
	}
	
	public Unit(String n) {
		this();
		setName(n);
	}


	@Override
	public UnitDao getDao() {
		if (dao == null) {
			dao = new UnitDao();
		}
		return dao;
	}
	
	/**
	 *	Persistent entities need to override equals for consistent behavior. Uses unique field for comparison.
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Unit))
            return false;
 
        var other = (Unit) o;
 
        return  getName() != null &&
        		getName().equals(other.getName());
    }
	 
	/**
	 * Generate hashCode using unique field as base. Used in Hash-based collections.
	 */
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getName());
	}
	
	public Integer getIdUnit() {
		return this.idUnit;
	}

	public void setIdUnit(Integer idUnit) {
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



	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}