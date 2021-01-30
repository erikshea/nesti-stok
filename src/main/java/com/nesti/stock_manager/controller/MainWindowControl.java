package com.nesti.stock_manager.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.nesti.stock_manager.application.NestiStokMain;
import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.model.User;
import com.nesti.stock_manager.shopping_cart.ShoppingCart;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.ApplicationSettings;
import com.nesti.stock_manager.util.HibernateUtil;

/**
 *	Main window controller, through which all other controller communicate. builds tab and adds them to main tab panel, and provides accessors for all main components
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class MainWindowControl extends JPanel {
	private static final long serialVersionUID = 4705253258936419615L;
	protected ArticleDirectory articleDirectory;
	protected SupplierDirectory supplierDirectory;
	protected IngredientDirectory ingredientDirectory;
	protected UserDirectory userDirectory;
	protected ConnectionForm connexionForm;
	protected ShoppingCart shoppingCart;
	private OrderDirectory orderDirectory;
	private ShoppingCartDirectory shoppingCartDirectory;
	private CloseableTabbedPane mainPane;

	public MainWindowControl() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(AppAppereance.LIGHT_COLOR);
		
		// Shows currently connected user, and a disconnect button
		var connectedUserPane = new JPanel();
		
		connectedUserPane.setLayout(new BoxLayout(connectedUserPane, BoxLayout.X_AXIS));
		connectedUserPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		connectedUserPane.setBackground(AppAppereance.LIGHT_COLOR);
		
		var connectedUserLogin = new JLabel(getConnectedUser().getLogin());
		
		var disconnectButton = new JButton("Déconnecter");
		disconnectButton.setBackground(AppAppereance.DARK);
		disconnectButton.setForeground(new Color(255,255,255));
		disconnectButton.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		disconnectButton.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		// Disconnect button action
		disconnectButton.addActionListener(e->{
			 // Unset application settings for login and pass
			ApplicationSettings.set("login",null);
			ApplicationSettings.set("password",null);
			// Show new connection form
			javax.swing.SwingUtilities.invokeLater(() -> NestiStokMain.changeFrameContent(new ConnectionForm()));
		});
		
		connectedUserPane.add(Box.createHorizontalGlue());
		connectedUserPane.add(connectedUserLogin);
		connectedUserPane.add((Box.createRigidArea(new Dimension(30,0))));
		connectedUserPane.add(disconnectButton);
		this.add(connectedUserPane);
		
		// Main panel that holds all tabs
		mainPane = new CloseableTabbedPane();
		
		// shopping cart item keeps track of current orders
		shoppingCart= new ShoppingCart(this, getConnectedUser()); 
		
		// Window starts at 80% of screen width
		var width = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.8);
		if (width > 1600) { 
			width = 1600;
		}
		
		// Window starts at 80% of screen height
		var height = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8);
		if (height > 900) {
			height = 900;
		}

		mainPane.setPreferredSize(new Dimension(width, height));

		mainPane.setMinimumSize(new Dimension(600, 900));
		mainPane.setBackground(AppAppereance.MEDIUM_COLOR);

		// Directories of all entities
		
		this.articleDirectory = new ArticleDirectory(this);
		mainPane.addTab(this.articleDirectory);
		this.supplierDirectory = new SupplierDirectory(this);
		mainPane.addTab(this.supplierDirectory);
		this.ingredientDirectory = new IngredientDirectory(this);
		mainPane.addTab(this.ingredientDirectory);
		this.orderDirectory = new OrderDirectory(this);
		mainPane.addTab(this.orderDirectory);
		this.shoppingCartDirectory = new ShoppingCartDirectory(this);
		mainPane.addTab(this.shoppingCartDirectory);
		
		// Only show user directory if logged in user is super-admin
		var user = getConnectedUser();
		if (user.isSuperAdmin()) {
			this.userDirectory = new UserDirectory(this);
			mainPane.addTab(this.userDirectory);
		}
		
		this.add(mainPane);
		
		HibernateUtil.getSession().clear();
	}
	
	/**
	 * @return currently connected user
	 */
	public User getConnectedUser() {
		var userDao = new UserDao();
		// User has already been authenticated so no need to check password
		return userDao.findOneBy("login", ApplicationSettings.get("login"));
	}

	public ArticleDirectory getArticleDirectory() {
		return articleDirectory;
	}

	public SupplierDirectory getSupplierDirectory() {
		return supplierDirectory;
	}

	public IngredientDirectory getIngredientDirectory() {
		return ingredientDirectory;
	}

	public OrderDirectory getOrderDirectory() {
		return orderDirectory;
	}
	
	public UserDirectory getUserDirectory() {
		return userDirectory;
	}
	
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public ShoppingCartDirectory getShoppingCartDirectory() {
		return shoppingCartDirectory;
	}
	
	public CloseableTabbedPane getMainPane() {
		return this.mainPane;
	}
}
