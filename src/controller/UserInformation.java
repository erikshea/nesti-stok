package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import dao.UserDao;
import form.FieldContainer;
import form.ListFieldContainer;
import model.User;
import util.HibernateUtil;

public class UserInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public UserInformation(MainWindowControl c, User user) {
		super(c, user);
		if (user == null) {
			user = new User();
		}
		final var userFinal= user;
		var dao = new UserDao();
		
		var userForm = new JPanel();
		userForm.setPreferredSize(new Dimension(500, 0));
		userForm.setLayout(new BoxLayout(userForm, BoxLayout.Y_AXIS));
		
		var titleUserInformation = new JLabel("Utilisateur");
		userForm.add(titleUserInformation);
		
		var nameUserFieldContainer = new FieldContainer("Nom d'utilisateur", this);
		nameUserFieldContainer.bind(
				userFinal.getLogin(),
				(s)-> userFinal.setLogin(s),
				(fieldValue)->dao.findOneBy("login", fieldValue) == null);
		userForm.add(nameUserFieldContainer);
		
		var contactNameFieldContainer = new FieldContainer("Nom de contact", this);
		contactNameFieldContainer.bind(
				userFinal.getName(),
				(s)-> userFinal.setName(s));
		userForm.add(contactNameFieldContainer);
		
		var roleFieldContainer = new ListFieldContainer("Rôle:", this);
		roleFieldContainer.populateList( List.of("super-administrator","administrator"));
		roleFieldContainer.bindSelection(
				userFinal.getRole(),
				(s)->userFinal.setRole(s));
	
		
		userForm.add(roleFieldContainer);
		
		userForm.add(Box.createVerticalGlue());
		
		this.add(userForm, BorderLayout.WEST);
		
		this.buttonValidate.addActionListener( e->{
			try{
				userFinal.setPasswordHash("FFF"); // TODO
				dao.saveOrUpdate(userFinal);
				HibernateUtil.getSession().getTransaction().commit();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
				    "Veuillez vérifier les champs en orange.",
				    "Paramètres invalides",
				    JOptionPane.WARNING_MESSAGE);
			}
			
			this.mainControl.getUserDirectory().getEntityList().refresh();
			this.mainControl.remove(this);
			this.mainControl.setSelectedComponent(this.mainControl.getSupplierDirectory());
		});
		this.buttonCancel.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getSupplierDirectory());
		});
	}
}