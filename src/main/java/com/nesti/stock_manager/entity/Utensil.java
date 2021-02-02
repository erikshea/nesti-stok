package com.nesti.stock_manager.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

import com.nesti.stock_manager.dao.UtensilDao;


/**
 * Persistent entity class corresponding to the utensil table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@PrimaryKeyJoinColumn(name = "id_product")
@NamedQuery(name="Utensil.findAll", query="SELECT u FROM Utensil u")
public class Utensil extends Product{
	private static final long serialVersionUID = 1L;
	
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


	public Utensil duplicate() {
		return (Utensil) super.duplicate();
	}
}