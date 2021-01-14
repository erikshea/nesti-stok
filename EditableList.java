package form;

import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class EditableList extends BaseFieldContainer{
	protected JList<String> list;
	protected DefaultListModel<String> listModel;
	
	public EditableList(String labelText) {
		super(labelText);

		this.setPreferredSize(new Dimension(0,80));
		this.listModel = new DefaultListModel<>();
		this.list = new JList<>(listModel);
		
		var scrollPane = new JScrollPane(this.list);
		
		this.add(scrollPane);
		
		
		
		
		
		
		
		// on met les deux botons dans un panel ave cbox layout vertyical
	//	c'ets ce co,nteneur qu'on ajoutera a ingredient information
	}
	
	public JList<String> getList() {
		return this.list;
	}
	
	public void populateList(List<String> l) {
		l.forEach( s->this.listModel.addElement(s));
	}
}
