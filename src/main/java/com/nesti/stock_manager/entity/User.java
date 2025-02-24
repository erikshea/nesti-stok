package com.nesti.stock_manager.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.dao.UserDao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Version;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;

/**
 * Persistent entity class corresponding to the user table.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends BaseEntity implements Serializable,Flagged {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Integer idUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_creation")
	private Date dateCreation;

	private String login;

	private String name;

	@Column(name = "password_hash")
	private String passwordHash;

	private String role;

	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	
	private String flag;

	private static UserDao dao;

	public User() {
		this.setDateCreation(new Date()); // initialize date in constructor for in-memory operations
		this.setFlag(BaseDao.DEFAULT);
	}

	public User(String l, String n, Date d, String r) {
		this();
		setLogin(l);
		setName(n);
		setDateCreation(d);
		setRole(r);
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

	/**
	 *	Persistent entities need to override equals for consistent behavior. Uses unique field for comparison.
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof User))
            return false;
 
        var other = (User) o;
 
        return  getLogin() != null &&
        		getLogin().equals(other.getLogin());
    }
	 
	/**
	 * Generate hashCode using unique field as base. Used in Hash-based collections.
	 */
	
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getLogin());
	}
	
	/**
	 * Duplicate Supplier into another with the same properties, and unique ones derived from original 
	 * @return
	 */
	public User duplicate() {
		var duplicate = new User();
		duplicate.setLogin(getDuplicatedFieldValue("login"));
		duplicate.setName(this.getName());
		duplicate.setPasswordHash(this.getPasswordHash());
		duplicate.setRole(this.getRole());
		duplicate.setFlag(this.getFlag());
		
		return duplicate;
	}
	
	public boolean isSuperAdmin() {
		return this.getRole().equals("super-administrator");
	}


	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
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

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	

	
	
}