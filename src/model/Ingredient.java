package model;

import java.io.Serializable;
import javax.persistence.*;
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_product")
	private int idProduct;

	private String name;

	private int reference;

	//bi-directional one-to-one association to Product
	@OneToOne
	@JoinColumn(name="id_product")
	private Product product;

	//bi-directional many-to-many association to Unit
	@ManyToMany(mappedBy="ingredients")
	private List<Unit> units;

	public Ingredient() {
	}

	public int getIdProduct() {
		return this.idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReference() {
		return this.reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
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

}