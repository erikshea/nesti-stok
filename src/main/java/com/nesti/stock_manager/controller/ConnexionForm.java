package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.model.User;
import com.nesti.stock_manager.util.ApplicationSettings;

@SuppressWarnings("serial")
public class ConnexionForm extends JPanel {

	protected JPasswordField passwordField;
	protected JTextField loginField;
	protected JFrame frame;
	protected MainWindowControl mainController;

	@SuppressWarnings("deprecation")
	public ConnexionForm(MainWindowControl c, JFrame f) {
		super();
		this.mainController = c;
		this.frame = f;
		this.setPreferredSize(new Dimension(800, 400));
		// this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		if(authenticate(
				ApplicationSettings.get("login"),
				ApplicationSettings.get("password")
				)) {
			System.out.println("brzvo");
			  javax.swing.SwingUtilities.invokeLater( () -> changePanel(mainController) );
		}
		else {
			showForm();
		}
	}

	public boolean authenticate(String login, String plaintextPassword) {
		var result = false;
		
		var userDao = new UserDao();
		var user = userDao.findOneBy("login", login);

		if (user != null && user.isPassword(plaintextPassword)) {
			ApplicationSettings.set("login", login);
			ApplicationSettings.set("password", plaintextPassword);
			changePanel(mainController);
		
			result=true;
		}
		return result;
	}
	
	  private void changePanel(MainWindowControl c) {
		   frame.getContentPane().removeAll();
		   frame.getContentPane().add(c);
		   frame.getContentPane().doLayout();
		   frame.update(frame.getGraphics());
		}
	  
	  public void showForm() {
			var formContainer = new JPanel();
			formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
			formContainer.setPreferredSize(new Dimension(400, 200));
			this.add(formContainer);
		

			var LoginContainer = new JPanel();
			var labelLogin = new JLabel("Login");
			loginField = new JTextField("");
			labelLogin.setPreferredSize(new Dimension(100, 35));
			loginField.setPreferredSize(new Dimension(200, 35));
			LoginContainer.add(labelLogin);
			LoginContainer.add(loginField);
			LoginContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

			var passwordContainer = new JPanel();
			var labelPassword = new JLabel("Mot de passe");
			passwordField = new JPasswordField();
			labelPassword.setPreferredSize(new Dimension(100, 35));
			passwordField.setPreferredSize(new Dimension(200, 35));
			passwordContainer.add(labelPassword);
			passwordContainer.add(passwordField);
			passwordContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

			var buttonBottomBar = new JPanel();
			buttonBottomBar.setLayout(new BoxLayout(buttonBottomBar, BoxLayout.X_AXIS));
			var buttonCancel = new JButton("Annuler");
			buttonCancel.addActionListener(e -> {
				loginField.setText("");
				passwordField.setText("");
			});

			var buttonValidate = new JButton("Se connecter");
			buttonValidate.addActionListener(e -> {

				
				if (!authenticate(loginField.getText(), passwordField.getText())) {
					JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect");
				}
				
			});

			buttonBottomBar.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonBottomBar.add(Box.createHorizontalGlue());
			buttonBottomBar.add(buttonCancel);
			buttonBottomBar.add(Box.createHorizontalGlue());
			buttonBottomBar.add(buttonValidate);
			buttonBottomBar.add(Box.createHorizontalGlue());

			formContainer.add(LoginContainer);
			formContainer.add(passwordContainer);
			formContainer.add(buttonBottomBar);
	  }
}
