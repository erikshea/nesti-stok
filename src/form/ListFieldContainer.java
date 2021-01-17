package form;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.BaseDao;
import util.ReflectionProperty;

@SuppressWarnings("serial")
public class ListFieldContainer extends BaseFieldContainer {
	protected JList<String> list;

	public ListFieldContainer(String labelText) {
		super(labelText);

		this.setPreferredSize(new Dimension(0, 80));
		var listModel = new DefaultListModel<String>();
		this.list = new JList<>(listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		var scrollPane = new JScrollPane(this.list);

		this.add(scrollPane);
	}

	
	public JList<String> getList() {
		return this.list;
	}

	public void populateList(List<String> l) {
		l.forEach(s -> this.getListModel().addElement(s));
	}

	 public DefaultListModel<String> getListModel(){
	        return (DefaultListModel<String>) this.list.getModel();
    }
	 
	public void bind(ValueGetter v, ValueSetter s) {

		list.setSelectedValue(v.get(), true);

		
		list.addListSelectionListener( e-> {
			s.set(list.getSelectedValue());
		});
	}
	
	
	public void bindMultiple(ListGetter lg, ListSetter ls) {
		setSelectedValues(lg.get());
		list.addListSelectionListener( e-> ls.set(list.getSelectedValuesList()));
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
	        return ((DefaultListModel<String>) this.getListModel()).indexOf(value);
	    }
	    for (int i = 0; i < this.getListModel().getSize(); i++) {
	        if (value.equals(this.getListModel().getElementAt(i))) return i;
	    }
	    return -1;
	}
	
}
