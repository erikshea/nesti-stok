package com.nesti.stock_manager.entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
 * Persistent entity class corresponding to the product table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product extends BaseEntity implements Serializable, Flagged {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_product")
	private Integer idProduct;

	private String name;

	private String reference;
	
	private String flag;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="product")
	private List<Article> articles;
	
	public Product() {
		this.setFlag(BaseDao.DEFAULT);
	}
	
	public Product(String r, String n) {
		this();
		setReference(r);
		setName(n);
	}

	/**
	 *	Persistent entities need to override equals for consistent behavior. Uses unique field for comparison.
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Product))
            return false;
 
        var other = (Product) o;
 
        return  getReference() != null &&
        		getReference().equals(other.getReference());
    }
	 
	/**
	 * Generate hashCode using unique field as base. Used in Hash-based collections.
	 */
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getReference());
	}
	
	/**
	 * Duplicate Product subclass into another with unique properties derived from original 
	 * @return
	 */
	public Product duplicate() {
		try {
			// get class constructor to generate instance of correct subclass, not product superclass
			var duplicate = this.getClass().getConstructor().newInstance();
			duplicate.setReference(getDuplicatedFieldValue("reference"));
			duplicate.setName(getDuplicatedFieldValue("name"));
			duplicate.setFlag(this.getFlag());
			return duplicate;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public BaseDao<?> getDao() {
		return null;
	}
	
	
	public Integer getIdProduct() {
		return this.idProduct;
	}

	public void setIdProduct(Integer idProduct) {
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