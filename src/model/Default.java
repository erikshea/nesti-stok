package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the default database table.
 * 
 */
@Entity
@NamedQuery(name="Default.findAll", query="SELECT d FROM Default d")
public class Default implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DefaultPK id;

	@Column(name="is_default")
	private byte isDefault;

	//bi-directional many-to-one association to Article
	@ManyToOne
	@JoinColumn(name="id_article", insertable=false, updatable=false)
	private Article article;

	//bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name="id_supplier", insertable=false, updatable=false)
	private Supplier supplier;

	public Default() {
	}

	public DefaultPK getId() {
		return this.id;
	}

	public void setId(DefaultPK id) {
		this.id = id;
	}

	public byte getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(byte isDefault) {
		this.isDefault = isDefault;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}