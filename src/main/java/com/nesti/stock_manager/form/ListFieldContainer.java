package com.nesti.stock_manager.form;

import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.util.ReflectionProperty;

/**
 * List field contains a label and a list with selectable items
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
@SuppressWarnings("serial")
public class ListFieldContainer extends BaseFieldContainer {
	protected JList<String> list; // List of selectable items
	protected DefaultListModel<String> listModel;
	protected Class<?> entityClass; // Entity class on which list is based
	protected String fieldName; // field name used to populate list
	protected Boolean suspendBinding; // determines if bound lambda functions should be called when list changes (used when refreshing)
	
	/**
	 * Construct all list container components
	 * @param labelText text to put inside label
	 */
	public ListFieldContainer(String labelText) {
		super(labelText);
		this.setPreferredSize(new Dimension(0, 80));
		listModel = new DefaultListModel<String>();
		this.list = new JList<>(listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Default is one selection allowed
		var scrollPane = new JScrollPane(this.list); // LIst is scrollable if too many items
		this.add(scrollPane);
	}
	
	
	/**
	 * Specify a field name and entity class with whicht o populate list
	 * @param labelText
	 * @param fn
	 * @param ec
	 */
	public ListFieldContainer(String labelText, String fn, Class<?> ec) {
		this(labelText);
		entityClass = ec;
		fieldName = fn;
		refreshItems(null);
	}
	
	/**
	 * Get contained list
	 * @return contrained list
	 */
	public JList<String> getList() {
		return this.list;
	}

	/**
	 * Refresh all items in the list
	 * @param selectedValue
	 */
	public void refreshItems(String selectedValue) {
		suspendBinding = true; // Don't call bound methods (called on list change) while repopulating list
		listModel.clear(); 

		var dao = BaseDao.getDao(entityClass);
		var items = dao.findAll(BaseDao.ACTIVE); // fetch all items with an active flag 
		items.forEach( item->{
			listModel.addElement(ReflectionProperty.get(item, fieldName));
		});
		
		suspendBinding = false; // re-enable bound methods
		
		if (selectedValue == null ) { 
			list.setSelectedIndex(0); // if no specified selected value, select first
		}else {
			list.setSelectedValue(selectedValue, true); // else, set value as selected and scroll to it
		}
	}
	
	/**
	 * Populate list with a specified List of strings
	 * @param l
	 */
	public void populateList(List<String> l) {
		suspendBinding = true; // Don't call bound methods (called on list change) while populating list
		l.forEach(s -> this.getListModel().addElement(s));
		suspendBinding = false; // re-enable bound methods
	}

	 /**
	  * Convenience method to return list model as a DefaultListModel
	 * @return DefaultListModel
	 */
	public DefaultListModel<String> getListModel(){
	        return (DefaultListModel<String>) this.list.getModel();
    }
	 
	
	/**
	 * when list selection changes, bind a lambda function to the changed value
	 * @param initialSelection initial selected value
	 * @param s instance of an interface containing a single method (to pass lambda)
	 */
	public void bindSelection(String initialSelection, ValueSetter s) {
		list.setSelectedValue(initialSelection, true);
		
		list.addListSelectionListener( e-> { 
			if (suspendBinding == false) { // if bindings aren't suspended
				s.set(list.getSelectedValue()); // call interface method on change
			}
			
		});
	}
	
	/**
	 * when list selection changes, call a lambda function with the changed value as parameter
	 * @param initialSelections list of initial selected values
	 * @param s instance of an interface containing a single method (to pass lambda)
	 */
	public void bindMultiple(List<String> initialSelections, ListSetter ls) {
		setSelectedValues(initialSelections);
		list.addListSelectionListener( e->{
			if (suspendBinding == false) {// if bindings aren't suspended
				ls.set(list.getSelectedValuesList());// call interface method on change
			}
		});
	}
	
	/**
	 * Set selected items to the values specified
	 * 
	 * @param values list of values
	 */
	public void setSelectedValues(List<String> values) {
		suspendBinding = true;
	    list.clearSelection();
	    for (var value : values) {
	        int index = listModel.indexOf(value);
	        if (index >=0) {
	            list.addSelectionInterval(index, index); // select current iterated value
	        }
	    }
	    list.ensureIndexIsVisible(list.getSelectedIndex()); // scroll to first selected index
	    suspendBinding = false;
	}
}
