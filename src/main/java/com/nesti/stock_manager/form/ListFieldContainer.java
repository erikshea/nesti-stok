package com.nesti.stock_manager.form;

import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.util.ReflectionProperty;

@SuppressWarnings("serial")
public class ListFieldContainer extends BaseFieldContainer {
	protected JList<String> list;
	protected DefaultListModel<String> listModel;
	protected Class<?> entityClass;
	protected String fieldName;
	protected Boolean suspendBinding;
	
	public ListFieldContainer(String labelText) {
		super(labelText,null);
		this.setPreferredSize(new Dimension(0, 80));
		listModel = new DefaultListModel<String>();
		this.list = new JList<>(listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		var scrollPane = new JScrollPane(this.list);
		this.add(scrollPane);
	}
	
	public ListFieldContainer(String labelText, String fn, Class<?> ec) {
		this(labelText);
		entityClass = ec;
		fieldName = fn;
		refreshItems(null);
	}
	
	public JList<String> getList() {
		return this.list;
	}

	public void refreshItems(String selectedValue) {
		suspendBinding = true;
		listModel.clear();

		var dao = BaseDao.getDao(entityClass);
		var items = dao.findAll(BaseDao.ACTIVE);
		items.forEach( item->{
			listModel.addElement(ReflectionProperty.get(item, fieldName));
		});
		
		suspendBinding = false;
		
		if (selectedValue == null ) {
			list.setSelectedIndex(0);
		}else {
			list.setSelectedValue(selectedValue, true);
		}
	}
	
	public void populateList(List<String> l) {
		l.forEach(s -> this.getListModel().addElement(s));
	}

	 public DefaultListModel<String> getListModel(){
	        return (DefaultListModel<String>) this.list.getModel();
    }
	 
	public void bindSelection(String initialValue, ValueSetter s) {

		list.setSelectedValue(initialValue, true);
		
		list.addListSelectionListener( e-> {
			if (suspendBinding == false) {
				s.set(list.getSelectedValue());
			}
			
		});
	}
	
	
	public void bindMultiple(List<String> initialSelections, ListSetter ls) {
		setSelectedValues(initialSelections);
		list.addListSelectionListener( e->{
			if (suspendBinding == false) {
				ls.set(list.getSelectedValuesList());
			}
		});
	}
	
	public void setSelectedValues(List<String> values) {
	    list.clearSelection();
	    for (var value : values) {
	        int index = getIndex(value);
	        if (index >=0) {
	            list.addSelectionInterval(index, index);
	        }
	    }
	    list.ensureIndexIsVisible(list.getSelectedIndex());
	}

	public int getIndex(String value) {
	    if (value == null) return -1;
	    if (this.getListModel() instanceof DefaultListModel) {
	        return this.getListModel().indexOf(value);
	    }
	    for (int i = 0; i < this.getListModel().getSize(); i++) {
	        if (value.equals(this.getListModel().getElementAt(i))) return i;
	    }
	    return -1;
	}
}
