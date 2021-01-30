package com.nesti.stock_manager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The composite primary key class for the orders_article database table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Embeddable
public class OrdersArticlePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_article", insertable=false, updatable=false)
	private Integer idArticle;

	@Column(name="id_orders", insertable=false, updatable=false)
	private Integer idOrders;

	/**
	 *	Persistent entities need to override equals for consistent behavior. Uses unique field for comparison.
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrdersArticlePK)) {
			return false;
		}
		OrdersArticlePK castOther = (OrdersArticlePK)other;
		return 
			(this.idArticle == castOther.idArticle)
			&& (this.idOrders == castOther.idOrders);
	}

	/**
	 * Generate hashCode using unique field as base. Used in Hash-based collections.
	 */
	@Override
	public int hashCode() {
		final int prime = 31; 	// prime number to multiply by (no common denominators ensures unique hash)
		int hash = 17; 			// prime seed
		hash = hash * prime + this.idArticle;
		hash = hash * prime + this.idOrders;
		
		return hash;
	}
	
	
	public OrdersArticlePK() {
	}
	public Integer getIdArticle() {
		return this.idArticle;
	}
	public void setIdArticle(Integer idArticle) {
		this.idArticle = idArticle;
	}
	public Integer getIdOrders() {
		return this.idOrders;
	}
	public void setIdOrders(Integer idOrders) {
		this.idOrders = idOrders;
	}

}