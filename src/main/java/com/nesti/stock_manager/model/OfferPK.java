package com.nesti.stock_manager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The composite primary key class for the offers database table.
 * 
 * @author Emmanuelle Gay, Erik Shea
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

	/**
	 *	Persistent entities need to override equals for consistent behavior. Uses unique field for comparison.
	 */
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
			&& (this.idSupplier == castOther.idSupplier)
			&& (this.startDate.getTime() == castOther.startDate.getTime());
	}
	
	/**
	 * Generate hashCode using a combination of composite key members. Used in Hash-based collections.
	 */
	@Override
	public int hashCode() {
		final int prime = 31; 	// prime number to multiply by (no common denominators ensures unique hash)
		int hash = 17; 			// prime seed
		hash = hash * prime + this.idArticle;
		hash = hash * prime + this.idSupplier;
		hash = hash * prime + (int) startDate.getTime();
		
		return hash;
	}
}