package com.nesti.stock_manager.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the default database table.
 * 
 */
@Embeddable
public class IsDefaultPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	   
	@Column(name="id_article")
	private int idArticle;

	@Column(name="id_supplier")
	private int idSupplier;

	public IsDefaultPK() {
	}
	public int getIdArticle() {
		return this.idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public int getIdSupplier() {
		return this.idSupplier;
	}
	public void setIdSupplier(int idSupplier) {
		this.idSupplier = idSupplier;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IsDefaultPK)) {
			return false;
		}
		IsDefaultPK castOther = (IsDefaultPK)other;
		return 
			(this.idArticle == castOther.idArticle)
			&& (this.idSupplier == castOther.idSupplier);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idArticle;
		hash = hash * prime + this.idSupplier;
		
		return hash;
	}
}