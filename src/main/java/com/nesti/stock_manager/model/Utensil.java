package com.nesti.stock_manager.model;

import javax.persistence.*;


/**
 * The persistent class for the utensil database table.
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "id_product")
@NamedQuery(name="Utensil.findAll", query="SELECT u FROM Utensil u")
public class Utensil extends Product {
	private static final long serialVersionUID = 1L;

	public Utensil() {
	}

}