package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the utensil database table.
 * 
 */
@Entity
@NamedQuery(name="Utensil.findAll", query="SELECT u FROM Utensil u")
public class Utensil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_product")
	private int idProduct;

	//bi-directional one-to-one association to Product
	@OneToOne
	@JoinColumn(name="id_product")
	private Product product;

	public Utensil() {
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

}