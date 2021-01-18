package form;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.BaseInformation;

@SuppressWarnings("serial")
public class BaseFieldContainer extends JPanel{
	protected JLabel label;
	protected BaseInformation infoPane; 
	protected boolean valid;
	
	public BaseFieldContainer(String labelText, BaseInformation i) {
		infoPane = i;
		valid=true;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		this.setPreferredSize(new Dimension(0,30));
		this.setMaximumSize(new Dimension(Short.MAX_VALUE,0));
		this.label = new JLabel(labelText);
		this.label.setPreferredSize(new Dimension(120,0));
		this.add(label);
	}
	
	public boolean validates() {
		return valid;
	}
}
