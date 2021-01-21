package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.model.User;

@SuppressWarnings("serial")
public class ConnexionForm extends JPanel {

	public ConnexionForm(MainWindowControl c) {
		super();

		this.setPreferredSize(new Dimension(800, 0));
		// this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		var formContainer = new JPanel();
		formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
		formContainer.setPreferredSize(new Dimension(400, 200));
		this.add(formContainer);

		var LoginContainer = new JPanel();
		var labelLogin = new JLabel("Login");
		var loginField = new JTextField("");
		labelLogin.setPreferredSize(new Dimension(100, 35));
		loginField.setPreferredSize(new Dimension(200, 35));
		LoginContainer.add(labelLogin);
		LoginContainer.add(loginField);
		LoginContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

		var passwordContainer = new JPanel();
		var labelPassword = new JLabel("Mot de passe");
		var passwordField = new JPasswordField();
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

			var userDao = new UserDao();
			var user = userDao.findOneBy("login", loginField.getText());
			if (user != null && user.isPassword(passwordField.getText())) {
				
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
