package com.nesti.stock_manager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the offers database table.
 * 
 */
@Embeddable
public class OfferPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_article")
	private Integer idArticle;

	@Column(name="id_supplier")
	private Integer idSupplier;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	private java.util.Date startDate;
	
	public OfferPK() {
	}
	public Integer getIdArticle() {
		return this.idArticle;
	}
	public void setIdArticle(Integer idArticle) {
		this.idArticle = idArticle;
	}
	public Integer getIdSupplier() {
		return this.idSupplier;
	}
	public void setIdSupplier(Integer idSupplier) {
		this.idSupplier = idSupplier;
	}

	public java.util.Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(java.util.Date d) {
		this.startDate = d;
	}

	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OfferPK)) {
			return false;
		}
		OfferPK castOther = (OfferPK)other;
		return 
			(this.idArticle == castOther.idArticle)
			&& (this.idSupplier == castOther.idSupplier);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idArticle;
		hash = hash * prime + this.idSupplier;
		
		return hash;
	}
}