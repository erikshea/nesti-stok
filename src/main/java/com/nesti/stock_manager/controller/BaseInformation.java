
package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.model.BaseEntity;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.HibernateUtil;

public abstract class BaseInformation<E extends BaseEntity> extends JPanel implements Tab {
	private static final long serialVersionUID = -4055453327212347699L;
	protected MainWindowControl mainControl;
	protected E item;
	protected JButton buttonValidate, buttonCancel;
	protected List<FieldContainer> validatedFields;
	
	
	public BaseInformation(MainWindowControl c, E e) {
		this.setLayout(new BorderLayout());
		this.item =  e;
		this.mainControl = c;
		validatedFields = new ArrayList<>();
		this.setBackground(AppAppereance.LIGHT_COLOR);
	}
	
	public void closeTab() {
		this.mainControl.getMainPane().remove(this);
	}

	
	public void preRefreshTab() {
		this.removeAll();
		addBottomButtonBar();
	}
	
	public void postRefreshTab() {;
		HibernateUtil.getSession().evict(item); // Changes to item not reflected in session cache until next saveOrUpdate
		HibernateUtil.getSession().clear();
	}
	
	
	public void refreshTab() {
		preRefreshTab();
		postRefreshTab();
	}
	
	public void addBottomButtonBar() {	
		var buttonBottomBar = new JPanel();
		buttonBottomBar.setLayout(new BoxLayout(buttonBottomBar, BoxLayout.X_AXIS));
		buttonBottomBar.setBackground(AppAppereance.LIGHT_COLOR);
		this.buttonCancel = new JButton("Annuler");
		this.buttonCancel.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonCancel.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		
		this.buttonCancel.setBackground(AppAppereance.DARK);
		this.buttonCancel.setForeground(new Color(255,255,255));
		
		this.buttonValidate = new JButton("Enregistrer");
		this.buttonValidate.setBackground(AppAppereance.HIGHLIGHT);
		this.buttonValidate.setForeground(new Color(255,255,255));
		this.buttonValidate.setPreferredSize(AppAppereance.CLASSIC_BUTTON);
		this.buttonValidate.setMaximumSize(AppAppereance.CLASSIC_BUTTON);
		
		addButtonListeners();
		buttonBottomBar.add(Box.createHorizontalGlue());
		
		buttonBottomBar.add(buttonCancel);
		buttonBottomBar.add((Box.createRigidArea(new Dimension(50,0))));
		buttonBottomBar.add(buttonValidate);
		buttonBottomBar.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.add(buttonBottomBar, BorderLayout.PAGE_END);
	}
	
	
	public void addButtonListeners() {
		this.buttonCancel.addActionListener( ev->{
			closeTab();
		});
		
		this.buttonValidate.addActionListener( e->{
			try{
				HibernateUtil.getSession().saveOrUpdate(item); 			// Item is back in session cache
				HibernateUtil.getSession().getTransaction().commit(); 	// Save to data source
				closeTab();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	public void addValidatedField(FieldContainer fieldContainer) {
		validatedFields.add(fieldContainer);
	}
	
	public void checkValidatedFields() {
		var formValid = true;

		for ( var fieldContainer : validatedFields ) {
			formValid &= fieldContainer.validates();
		}
		this.buttonValidate.setEnabled(formValid);
	}
}