package com.nesti.stock_manager.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the default database table.
 * 
 */
@Entity
@Table(name="is_default")
@NamedQuery(name="IsDefault.findAll", query="SELECT d FROM IsDefault d")
public class IsDefault implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@PrePersist
	private void prePersist() {
		if (getId() == null) {
			var pk = new IsDefaultPK();
			pk.setIdArticle(getArticle().getIdArticle());
			pk.setIdSupplier(getSupplier().getIdSupplier());
			setId(pk);
		}
	}
	
	@EmbeddedId
	private IsDefaultPK id;

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

	public IsDefault() {
	}

	public IsDefaultPK getId() {
		return this.id;
	}

	public void setId(IsDefaultPK id) {
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