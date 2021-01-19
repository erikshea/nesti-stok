package model;

import javax.persistence.*;

import dao.UnitDao;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the ingredient database table.
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "id_product")
@NamedQuery(name="Ingredient.findAll", query="SELECT i FROM Ingredient i")
public class Ingredient extends Product  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_product")
	private int idProduct;

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

	public List<Unit> getUnits() {
		return this.units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public List<String> getUnitsNames() {
		List<String>  names = new ArrayList<>();
		if (this.units != null) {
			this.units.forEach(u->names.add(u.getName()));
		}
		return names;
	}
	
	public void setUnitsFromNames(List<String> ingredientNames) {
		var unitDao = new UnitDao();
		this.units = new ArrayList<>();
		ingredientNames.forEach( n -> this.units.add( unitDao.findOneBy("name", n)));
	}
	
	
	public Unit addUnit(Unit unit) {
		getUnits().add(unit);

		return unit;
	}

	public Unit removeUnit(Unit unit) {
		getUnits().remove(unit);

		return unit;
	}
}