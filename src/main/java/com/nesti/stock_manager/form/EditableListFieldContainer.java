package com.nesti.stock_manager.form;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.model.Flagged;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.ReflectionProperty;

@SuppressWarnings("serial")
public class EditableListFieldContainer extends ListFieldContainer {
	
	/**
	 * allows to add a element into the list from an input
	 */
	public void  addToList() {
//		Object[] possibilities = null;
		String newItemString = (String)JOptionPane.showInputDialog(
		                    this,
		                    "Ajouter : ",
		                    "Ajouter ...",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    null);

		//If a string was returned, say so.
		if ((newItemString != null) && (newItemString.length() > 0)) {
			var dao = BaseDao.getDao(entityClass);
			var existingItem = (Flagged) dao.finddOneBy(fieldName, newItemString);
			
			if ( existingItem == null ) {
				try {
					var newItem = entityClass.getConstructor().newInstance();
					ReflectionProperty.set(newItem, "name", newItemString);
					ReflectionProperty.set(newItem, "flag", BaseDao.ACTIVE);

					HibernateUtil.getSession().saveOrUpdate(newItem);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ( existingItem.getFlag().equals(BaseDao.DELETED)) {
				existingItem.setFlag(BaseDao.ACTIVE);
				HibernateUtil.getSession().saveOrUpdate(existingItem);
			}
			refreshItems(newItemString);
		}
	}
	
	/**
	 * Remove an element from the list
	 */
	public void deleteFromList() {
		var selected = this.list.getSelectedValue();
		var dao = BaseDao.getDao(entityClass);
		var existingItem = (Flagged) dao.finddOneBy(fieldName, selected);
		if ( existingItem != null ) {
			existingItem.setFlag(BaseDao.DELETED);
			HibernateUtil.getSession().saveOrUpdate(existingItem);
		}
		refreshItems(null);
	}
	
	public EditableListFieldContainer(String labelText, String fn, Class<?> ec) {
		super(labelText, fn , ec);
		
		this.setPreferredSize(new Dimension(0, 120));

		var buttonContainer = new JPanel();
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

		var buttonDimension = new Dimension(50, 50);

		var plusButton = new JButton("+");
		plusButton.setPreferredSize(buttonDimension);

		plusButton.addActionListener((e) -> {
			this.addToList();
		});

		var minusButton = new JButton("-");
		minusButton.setPreferredSize(buttonDimension);
		
		minusButton.addActionListener((e) -> {
			this.deleteFromList();
		});

		buttonContainer.add(plusButton);
		buttonContainer.add(Box.createVerticalGlue());
		buttonContainer.add(minusButton);

		this.add(buttonContainer);

	}

}
