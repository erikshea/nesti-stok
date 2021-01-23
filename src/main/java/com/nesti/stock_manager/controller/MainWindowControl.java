package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalIconFactory;

import com.nesti.stock_manager.dao.UserDao;
import com.nesti.stock_manager.model.User;
import com.nesti.stock_manager.shopping_cart.ShoppingCart;
import com.nesti.stock_manager.util.ApplicationSettings;
import com.nesti.stock_manager.util.HibernateUtil;

public class MainWindowControl extends JTabbedPane {
	private static final long serialVersionUID = 4705253258936419615L;
	protected ArticleDirectory articleList;
	protected SupplierDirectory supplierList;
	protected IngredientDirectory ingredientList;
	protected UserDirectory userList;
	protected ConnectionForm connexionForm;
	protected ShoppingCart shoppingCart;
	private ShoppingCartDirectory shoppingCartList;


	
	public MainWindowControl() {
		shoppingCart= new ShoppingCart(this); 
		
		Integer width = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.8);
		if (width > 1600) {
			width = 1600;
		}
		
		Integer height = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8);
		if (height > 900) {
			height = 900;
		}

		this.setPreferredSize(new Dimension(width, height));

		this.setMaximumSize(new Dimension(600, 900));

		this.articleList = new ArticleDirectory(this);
		this.addTab("Article", this.articleList);

		this.supplierList = new SupplierDirectory(this);
		this.addTab("Fournisseur", this.supplierList);

		this.ingredientList = new IngredientDirectory(this);
		this.addTab("Ingrédient", this.ingredientList);
		
		this.shoppingCartList = new ShoppingCartDirectory(this);
		this.addTab("Panier", this.shoppingCartList);
		
		var user = getConnectedUser();
		if (user.isSuperAdmin()) {
			this.userList = new UserDirectory(this);
			this.addTab("Utilisateur", this.userList);
		}
		
		((Tab) this.getComponent(0)).refreshTab(); // Must refresh first tab
		this.addChangeListener(e->{
			((Tab) this.getSelectedComponent()).refreshTab();
		});
	}

	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		// TODO Auto-generated method stub
		super.addTab(title, icon, component, tip);
		HibernateUtil.getSession().clear();
	}

	public void addCloseableTab(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
		int count = this.getTabCount() - 1;
		this.setTabComponentAt(count, new CloseButtonTab(component, title, icon));
		this.setSelectedIndex(count);
	}

	public void addCloseableTab(String title, Icon icon, Component component) {
		this.addCloseableTab(title, icon, component, null);
	}

	public void addCloseableTab(String title, Component component) {
		this.addCloseableTab(title, null, component);
	}

	/* Button */
	public class CloseButtonTab extends JPanel {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		private Component tab;

		public CloseButtonTab(final Component tab, String title, Icon icon) {
			this.tab = tab;
			setOpaque(false);
			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
			setLayout(flowLayout);
			JLabel jLabel = new JLabel(title);
			jLabel.setIcon(icon);
			add(jLabel);
			JButton button = new JButton(MetalIconFactory.getInternalFrameCloseIcon(8));
			button.setMargin(new Insets(0, 0, 0, 0));
			button.addMouseListener(new CloseableTabListener(tab));
			add(button);
		}
	}

	public class CloseableTabListener implements MouseListener {
		private Tab tab;

		public CloseableTabListener(Component tab) {
			this.tab = (Tab) tab;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JButton) {
				tab.closeTab();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	public ArticleDirectory getArticleList() {
		return articleList;
	}

	public SupplierDirectory getSupplierList() {
		return supplierList;
	}

	public IngredientDirectory getIngredientList() {
		return ingredientList;
	}

	public UserDirectory getUserList() {
		return userList;
	}

	public User getConnectedUser() {
		var userDao = new UserDao();
		return userDao.findOneBy("login", ApplicationSettings.get("login"));

	}
	
	/**
	 * @return the shoppingCart
	 */
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public ShoppingCartDirectory getShoppingCartList() {
		return shoppingCartList;
	}
}
