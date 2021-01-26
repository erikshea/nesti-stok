package com.nesti.stock_manager.model;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

import com.nesti.stock_manager.dao.UtensilDao;


/**
 * The persistent class for the utensil database table.
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "id_product")
@NamedQuery(name="Utensil.findAll", query="SELECT u FROM Utensil u")
public class Utensil extends Product {
	private static final long serialVersionUID = 1L;
	
	private String flag;
	private static UtensilDao dao;
	
	public Utensil() {
	}
	@Override
	public UtensilDao getDao() {
		if (dao == null) {
			dao = new UtensilDao();
		}
		return dao;
	}
	
	public Utensil(String r, String n) {
		super(r,n);
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Utensil duplicate() {
		return (Utensil) super.duplicate();
	}
}