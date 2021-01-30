package com.nesti.stock_manager.form;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.model.Flagged;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.ReflectionProperty;

/**
 * List field with add and remove item buttons and corresponding logic
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class EditableListFieldContainer extends ListFieldContainer {
	
	public EditableListFieldContainer(String labelText, String fn, Class<?> ec) {
		super(labelText, fn , ec);
		
		this.setPreferredSize(new Dimension(0, 120));

		// Holds plus and minus buttons at right of field container
		var buttonContainer = new JPanel();
		buttonContainer.setBackground(AppAppereance.LIGHT_COLOR);
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

		var buttonDimension = new Dimension(30, 30);

		var plusButton = new JButton("+");
		plusButton.setPreferredSize(buttonDimension);
		plusButton.setMaximumSize(buttonDimension);
		plusButton.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));		

		// Plus button action
		plusButton.addActionListener((e) -> {
			this.addToList();
		});

		var minusButton = new JButton("-");
		
		minusButton.setBackground(AppAppereance.DARK);
		minusButton.setForeground(AppAppereance.VERY_LIGHT_COLOR);
		minusButton.setPreferredSize(buttonDimension);
		minusButton.setMaximumSize(buttonDimension);
		minusButton.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		
		// Minus button action
		minusButton.addActionListener((e) -> {
			var options = new String[] {"Annuler", "Confirmer"};
			// Show dialog
			int choice = JOptionPane.showOptionDialog(this,
				"êtes-vous certain de vouloir supprimer cet élément? Ceci est irréversible.",
				"Supprimer",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.WARNING_MESSAGE,
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
			    options[0]); //default button title

			if ( choice == 1 ) { // If "Confirm" clicked
				deleteFromList();
			}
		});

		buttonContainer.add(Box.createVerticalGlue());
		buttonContainer.add(plusButton);
		buttonContainer.add(Box.createVerticalGlue());
		buttonContainer.add(minusButton);
		buttonContainer.add(Box.createVerticalGlue());

		this.add(buttonContainer);

	}
	
	
	/**
	 *  Add a element to the list
	 */
	public void  addToList() {
		String newItemString = (String)JOptionPane.showInputDialog(
		                    this,
		                    "Nom :  ",
		                    "Ajouter ...",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    null);

		//If a non-empty string was returned
		if ((newItemString != null) && (newItemString.length() > 0)) {
			var dao = BaseDao.getDao(entityClass);
			// Get corresponding entity as Flagged (has flag column)
			var existingItem = (Flagged) dao.findOneBy(fieldName, newItemString);
			
			if ( existingItem == null ) { // If item doesn't already exust
				try {
					// Create a new one
					var newItem = entityClass.getConstructor().newInstance();
					
					// Use reflection to set its properties
					ReflectionProperty.set(newItem, "name", newItemString);
					ReflectionProperty.set(newItem, "flag", BaseDao.ACTIVE);

					HibernateUtil.getSession().saveOrUpdate(newItem); // Save at next commit
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			} else if ( existingItem.getFlag().equals(BaseDao.DELETED)) {
				// If item already exists, but has a "deleted" flag
				existingItem.setFlag(BaseDao.ACTIVE); // Set flag back to a ctive
				HibernateUtil.getSession().saveOrUpdate(existingItem); // TODO: Commit?
			}
			refreshItems(newItemString); // refresh list and select new item
		}
	}
	
	/**
	 * Remove an element from the list
	 */
	public void deleteFromList() {
		// Get selected element
		var selected = this.list.getSelectedValue();
		var dao = BaseDao.getDao(entityClass);
		// Find corresponding entity as Flagged
		var existingItem = (Flagged) dao.findOneBy(fieldName, selected);
		if ( existingItem != null ) { // if item exists
			existingItem.setFlag(BaseDao.DELETED); // set deleted flag
			HibernateUtil.getSession().saveOrUpdate(existingItem);
		}
		refreshItems(null); // refresh list without specifying selected item
	}
	
	


}
