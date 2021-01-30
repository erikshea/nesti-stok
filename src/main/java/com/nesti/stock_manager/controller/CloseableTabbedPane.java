package com.nesti.stock_manager.controller;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 * Tabbed pane with closeable tabs
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class CloseableTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	public CloseableTabbedPane() {
		this.addChangeListener(e->{
			// When a tab gets clicked on, call Tab interface refresh method
			((Tab) this.getSelectedComponent()).refreshTab();
		});
	}


	/**
	 * Add a component that implements Tab interface
	 * @param tab Tab component to add
	 */
	public void addTab(Tab tab) {
		// set tab title using Tab component's "getTitle()" interface method
		super.addTab(tab.getTitle(), null, (Component) tab);
	}
	
	/**
	 * In case selected component must be explicitly set, refresh tab
	 */
	@Override
	public void setSelectedComponent(Component c) {
		// TODO Auto-generated method stub
		super.setSelectedComponent(c);
		((Tab) c).refreshTab();
	}

	
	/**
	 * add a closeable tab
	 */
	public void addCloseableTab(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
		int count = this.getTabCount() - 1;
		// Add close button
		this.setTabComponentAt(count, new CloseableTab(component, title, icon));
		// Last added tab is selected
		this.setSelectedIndex(count);
	}
	
	
	public void addCloseableTab(String title, Icon icon, Component component) {
		this.addCloseableTab(title, icon, component, null);
	}

	public void addCloseableTab(String title, Component component) {
		this.addCloseableTab(title, null, component);
	}

	public void addCloseableTab(Tab tab) {
		this.addCloseableTab(tab.getTitle(), null, (Component) tab);
	}
	

	/**
	 * Represents a single tab in the closeable tab pane
	 */
	public class CloseableTab extends JPanel {
		private static final long serialVersionUID = 1L;
		public CloseableTab(Component component, String title, Icon icon) {
			setOpaque(false);
			setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3)); // center items with 3px gaps in both dimensions
			JLabel jLabel = new JLabel(title);
			jLabel.setIcon(icon); // if icon was specified
			add(jLabel);
			JButton button = new JButton(MetalIconFactory.getInternalFrameCloseIcon(8));
			button.setMargin(new Insets(0, 0, 0, 0)); // remove button margins
			button.addMouseListener(new CloseTabButtonListener(component)); // Bind a listener to 
			add(button);
		}
	}

	/**
	 * Listener for the close button inside tab
	 */
	public class CloseTabButtonListener implements MouseListener {
		private Tab tab; // component implementing Tab interface

		public CloseTabButtonListener(Component tab) {
			this.tab = (Tab) tab;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JButton) {
				tab.closeTab(); // On click, call closeTab interface method
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	}

}
