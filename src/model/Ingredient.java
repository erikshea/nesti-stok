package model;

import java.io.Serializable;
import javax.persistence.*;

import dao.UnitDao;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the ingredient database table.
 * 
 */
@Entity
@NamedQuery(name="Ingredient.findAll", query="SELECT i FROM Ingredient i")
public class Ingredient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_product")
	private int idProduct;

	//bi-directional one-to-one association to Product
	@OneToOne
	@JoinColumn(name="id_product")
	private Product product;


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

	public Ingredient() {
	}

	public int getIdProduct() {
		return this.idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Unit> getUnits() {
		return this.units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public List<String> getUnitsNames() {
		List<String>  names = new ArrayList<>();
		this.units.forEach(u->names.add(u.getName()));
		return names;
	}
	
	public void setUnitsFromNames(List<String> ingredientNames) {
		var unitDao = new UnitDao();
		this.units = new ArrayList<>();
		ingredientNames.forEach( n -> this.units.add( unitDao.findOneBy("name", n)));
	}
}