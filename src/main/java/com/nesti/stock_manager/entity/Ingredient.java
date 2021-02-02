package com.nesti.stock_manager.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

import com.nesti.stock_manager.dao.IngredientDao;
import com.nesti.stock_manager.dao.UnitDao;


/**
 * Persistent entity class corresponding to the ingredient table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@PrimaryKeyJoinColumn(name = "id_product")
@NamedQuery(name="Ingredient.findAll", query="SELECT i FROM Ingredient i")
public class Ingredient extends Product  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_product")
	private Integer idProduct;

	// bi-directional many-to-one association to Product
//	@OneToOne
//	@JoinColumn(name = "id_product")
//	public Product product;
	
	
	
	//bi-directional many-to-many association to Unit
	@ManyToMany
	@JoinTable(
	name="allows"
	, joinColumns={
		@JoinColumn(name="id_product")
		}
	, inverseJoinColumns={
		@JoinColumn(name="id_unit")
		}
	
	)
	private List<Unit> units;

	private static IngredientDao dao;
	
	public Ingredient() {
	}
	
	public Ingredient(String r, String n) {
		super(r,n);
	}

	public List<Unit> getUnits() {
		return this.units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	/**
	 * Get a list of names of all units associated with ingredient
	 * @return list of String names, or empty array if no units associated with Ingredient
	 */
	public List<String> getUnitsNames() {
		List<String>  names = new ArrayList<>();
		if (this.units != null) {
			this.units.forEach(u->names.add(u.getName()));
		}
		return names;
	}
	
	/**
	 * Set unit associations from a list of String names
	 * @param ingredientNames  list of String names of units to associate with Ingredient
	 */
	public void setUnitsFromNames(List<String> ingredientNames) {
		var unitDao = new UnitDao();
		setUnits(new ArrayList<>());
		ingredientNames.forEach( n -> addUnit( unitDao.findOneBy("name", n)));
	}
	
	
	public Unit addUnit(Unit unit) {
		getUnits().add(unit);

		return unit;
	}

	public Unit removeUnit(Unit unit) {
		getUnits().remove(unit);

		return unit;
	}
	
	/**
	 * Duplicate Ingredient into another with the same units, and unique fields derived from original 
	 * @return
	 */
	public Ingredient duplicate() {
		var duplicate = (Ingredient) super.duplicate();
		duplicate.setUnits(this.getUnits());
		
		return duplicate;
	}
	
	
	@Override
	public IngredientDao getDao() {
		if (dao == null) {
			dao = new IngredientDao();
		}
		return dao;
	}
}