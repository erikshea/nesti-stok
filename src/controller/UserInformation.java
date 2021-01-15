package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import form.EditableListFieldContainer;
import form.FieldContainer;
import form.ListFieldContainer;

public class UserInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public UserInformation(MainWindowControl c, Object i) {
		super(c, i);
	
		var userForm = new JPanel();
		userForm.setPreferredSize(new Dimension(500, 0));
		userForm.setLayout(new BoxLayout(userForm, BoxLayout.Y_AXIS));
		
		var titleUserInformation = new JLabel("Utilisateur");
		userForm.add(titleUserInformation);
		
		var nameUserFieldContainer = new FieldContainer("Nom d'utilisateur");
		nameUserFieldContainer.getField().setText("Polo");
		userForm.add(nameUserFieldContainer);
		
		var contactNameFieldContainer = new FieldContainer("Nom de contact");
		contactNameFieldContainer.getField().setText("Paul Legrand");
		userForm.add(contactNameFieldContainer);
		
		var roleFieldContainer = new ListFieldContainer("Rôle:");
		roleFieldContainer.populateList( List.of("Super administrateur","administrateur"));
		userForm.add(roleFieldContainer);
		
		userForm.add(Box.createVerticalGlue());
		
		this.add(userForm, BorderLayout.WEST);
	}
}