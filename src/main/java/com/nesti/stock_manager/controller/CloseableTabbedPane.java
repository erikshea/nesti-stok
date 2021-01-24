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

import com.nesti.stock_manager.util.HibernateUtil;

public class CloseableTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	public CloseableTabbedPane() {
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
	

	
	@Override
	public void setSelectedComponent(Component c) {
		// TODO Auto-generated method stub
		super.setSelectedComponent(c);
		((Tab) c).refreshTab();
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

}
