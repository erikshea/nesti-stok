package form;

import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ListFieldContainer extends BaseFieldContainer {
	protected JList<String> list;

	public ListFieldContainer(String labelText) {
		super(labelText);

		this.setPreferredSize(new Dimension(0, 80));
		var listModel = new DefaultListModel<String>();
		this.list = new JList<>(listModel);

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
	
}
