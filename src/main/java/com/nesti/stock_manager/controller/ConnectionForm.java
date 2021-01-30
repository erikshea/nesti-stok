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

import com.nesti.stock_manager.application.NestiStokMain;
import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.ApplicationSettings;

/**
 * Tries to authenticate user from previous session, otherwise shows a connection form
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class ConnectionForm extends JPanel {

	protected JPasswordField passwordField;
	protected JTextField loginField;
	protected JFrame frame;

	public ConnectionForm() {
		super();
		this.setPreferredSize(new Dimension(800, 400));
		// this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// If no valid user from previous session, show form
		if (!authenticate(ApplicationSettings.get("login"), ApplicationSettings.get("password"))) {
			showForm();
		}
	}

	public boolean authenticate(String login, String plaintextPassword) {
		var result = false;

		var userDao = new UserDao();
		var user = userDao.findOneBy("login", login);

		// if user exists and passwords match
		if (user != null && user.isPassword(plaintextPassword)) {
			// add logged in user's login and password to permanent app settings
			ApplicationSettings.set("login", login);	
			ApplicationSettings.set("password", plaintextPassword);
			
			// Switch to main window controller
			javax.swing.SwingUtilities.invokeLater(() -> NestiStokMain.changeFrameContent(new MainWindowControl()));

			result = true;
		}
		return result;
	}

	@SuppressWarnings("deprecation")
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
		buttonCancel.setBackground(AppAppereance.DARK);
		buttonCancel.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		buttonCancel.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		buttonCancel.addActionListener(e -> { 
			loginField.setText("");		// clear fields on cancel button press
			passwordField.setText("");	//
		});

		var buttonValidate = new JButton("Se connecter");
		buttonValidate.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		buttonValidate.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		buttonValidate.addActionListener(e -> {
			// Try to authenticate with entered login and password
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
