package com.nesti.stock_manager.model;

import java.io.Serializable;
import javax.persistence.*;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.UnitDao;
import com.nesti.stock_manager.dao.UserDao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Version;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private int idUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_creation")
	private Date dateCreation;

	private String login;

	private String name;

	@Column(name = "password_hash")
	private String passwordHash;

	private String role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Order> orders;

	private static UserDao dao;

	public User() {
	}

	public User(String l, String n, Date d, String r) {
		setLogin(l);
		setName(n);
		setDateCreation(d);
		setRole(r);
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setUser(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setUser(null);

		return order;
	}

	@Override
	public UserDao getDao() {
		if (dao == null) {
			dao = new UserDao();
		}
		return dao;
	}

	/**
	 * Sets hash from plaintext password, using long password strategy described
	 * here: https://github.com/patrickfav/bcrypt
	 * 
	 * @param plaintextPassword to generate hash from
	 */
	public void setPasswordHashFromPlainText(String plaintextPassword) {
		var hash = BCrypt.with(LongPasswordStrategies.truncate(Version.VERSION_2A)).hashToString(6,
				plaintextPassword.toCharArray());

		this.setPasswordHash(hash);
	}

	/**
	 * Checks plaintext password against bcrypt hash.
	 * 
	 * @param plaintextPassword to generate hash from
	 */
	public boolean isPassword(String plaintextPassword) {
		var verifyer = BCrypt.verifyer(Version.VERSION_2A, LongPasswordStrategies.truncate(Version.VERSION_2A));
		return this.getPasswordHash() != null
				&& verifyer.verify(plaintextPassword.toCharArray(), this.getPasswordHash()).verified;
	}

	public boolean isSuperAdmin() {
		return this.getRole().equals("super-administrator");
	}
}