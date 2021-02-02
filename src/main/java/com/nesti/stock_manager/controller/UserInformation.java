package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.form.ListFieldContainer;
import com.nesti.stock_manager.form.PasswordFieldContainer;
import com.nesti.stock_manager.model.User;
import com.nesti.stock_manager.util.HibernateUtil;

/**
 * See and edit a User's information and role.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class UserInformation extends BaseInformation<User> {
	private static final long serialVersionUID = 1775908299271902575L;

	public UserInformation(MainWindowControl c, User user) {
		super(c, user);
	}
	
	public String getTitle() {
		var result = "";
		if (item.getLogin() == null) { // If newly created user
			result = "Nouvel Utilisateur";
		} else {  // Else, show login
			result = "Utilisateur : " + item.getLogin();
		}
		
		return result;
	}
	
	/**
	 * Called first at tab refresh. Build and add swing elements.
	 */
	@Override
	public void preRefreshTab() {
		super.preRefreshTab();
		final var user= item;
		var dao = item.getDao();
	
		var userForm = new JPanel();
		userForm.setPreferredSize(new Dimension(500, 0));
		userForm.setLayout(new BoxLayout(userForm, BoxLayout.Y_AXIS));
		
		var titleUserInformation = new JLabel(getTitle());
		userForm.add(titleUserInformation);
		
		var nameUserFieldContainer = new FieldContainer("Nom d'utilisateur", this);
		nameUserFieldContainer.bind(
				user.getLogin(), // Starting value
				(s)-> user.setLogin(s), // On change, update corresponding user property
				(fieldValue)->dao.findOneBy("login", fieldValue) == null); // Only valid if no other user exists with the same property
		userForm.add(nameUserFieldContainer);
		
		var contactNameFieldContainer = new FieldContainer("Nom de contact", this);
		contactNameFieldContainer.bind(
				user.getName(),
				(s)-> user.setName(s));
		userForm.add(contactNameFieldContainer);
		
		var roleFieldContainer = new ListFieldContainer("Rôle:");
		roleFieldContainer.populateList( List.of("super-administrator","administrator"));
		roleFieldContainer.bindSelection(
				user.getRole(),
				(s)->user.setRole(s));
	
		var passwordFieldContainer = new PasswordFieldContainer("Mot de passe", this);
		passwordFieldContainer.bind(
				"",
				(s)-> user.setPasswordHashFromPlainText(s));
		userForm.add(passwordFieldContainer);
		
		userForm.add(roleFieldContainer);
		
		userForm.add(Box.createVerticalGlue());
		
		this.add(userForm, BorderLayout.WEST);
		HibernateUtil.getSession().evict(item);
	}

	@Override
	public void closeTab() {
		super.closeTab();
		this.mainControl.getMainPane().setSelectedComponent(this.mainControl.getUserDirectory());
	}
}