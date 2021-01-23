
package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.nesti.stock_manager.form.FieldContainer;
import com.nesti.stock_manager.model.BaseEntity;
import com.nesti.stock_manager.util.HibernateUtil;

public abstract class BaseInformation<E extends BaseEntity> extends JPanel implements Tab {
	private static final long serialVersionUID = -4055453327212347699L;
	protected MainWindowControl mainControl;
	protected E item;
	protected JButton buttonValidate, buttonCancel;
	protected List<FieldContainer> validatedFields;
	
	
	public BaseInformation(MainWindowControl c, E e) {
		this.item =  e;
		this.mainControl = c;
		validatedFields = new ArrayList<>();
	}
	
	public void closeTab() {
		HibernateUtil.getSession().getTransaction().rollback();
		this.mainControl.remove(this);
	}
	
	public void refreshTab() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		addBottomButtonBar();
	}
	
	public void addBottomButtonBar() {
		var buttonBottomBar = new JPanel();
		buttonBottomBar.setLayout(new BoxLayout(buttonBottomBar, BoxLayout.X_AXIS));
		this.buttonCancel = new JButton("Annuler");
		this.buttonValidate = new JButton("Enregistrer");
		
		this.buttonValidate.setBackground(new Color(91,148,4));
		this.buttonCancel.setBackground(new Color(179,133,4));
		addButtonListeners();
		buttonBottomBar.add(Box.createHorizontalGlue());
		buttonBottomBar.add(buttonCancel);
		buttonBottomBar.add(buttonValidate);
		this.add(buttonBottomBar, BorderLayout.PAGE_END);
	}
	
	
	public void addButtonListeners() {
		this.buttonCancel.addActionListener( ev->{
			closeTab();
		});
		
		this.buttonValidate.addActionListener( e->{
			try{
				saveItem();
				HibernateUtil.getSession().getTransaction().commit();
				closeTab();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
				    "Veuillez vérifier les champs en orange.",
				    "Paramétres invalides",
				    JOptionPane.WARNING_MESSAGE);
			}
		});
	}
	
	public abstract void saveItem();
	
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