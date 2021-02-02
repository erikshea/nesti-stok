package com.nesti.stock_manager.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.UIManager;

/**
 * @author Emmanuelle Gay, Erik Shea
 * Holds view-related constants, and sets UIManager values
 */
public class AppAppereance {

	public static final Color VERY_LIGHT_COLOR = new Color(251,242,221);
	public static final Color LIGHT_COLOR = new Color(244,225,181);
	public static final Color MEDIUM_COLOR = new Color (232,189,88);
	public static final Color DARK= new Color (179,133,4);
	public static final Color HIGHLIGHT = new Color(91,148,4);

	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	public static final Font DEFAULT_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	public static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	
	public static final Dimension CLASSIC_BUTTON = new Dimension(130,30);
	public static final Dimension LARGE_BUTTON = new Dimension(150,30);
	

	
	/**
	 * Uses UIManager to skin app
	 */
	public static void setDefault() {
		UIManager.put("TabbedPane.selected", AppAppereance.LIGHT_COLOR);
		 UIManager.put("Button.font", new Font ("Font.SANS_SERIF", Font.PLAIN, 15));
	     UIManager.put("Button.background", HIGHLIGHT);
	     UIManager.put("Button.foreground", VERY_LIGHT_COLOR);
	     
	     UIManager.put("Table.background",VERY_LIGHT_COLOR);
	     UIManager.put("Table.selectionBackground",HIGHLIGHT);
	     UIManager.put("Table.selectionForeground",VERY_LIGHT_COLOR);
	     UIManager.put("Table.font", new Font ("Font.SANS_SERIF", Font.PLAIN, 12));
	     
	     UIManager.put("TableHeader.background",DARK);
	     UIManager.put("TableHeader.foreground",VERY_LIGHT_COLOR);
	     UIManager.put("TableHeader.font", new Font ("Font.SANS_SERIF", Font.PLAIN, 13));

	     UIManager.put("Label.background", LIGHT_COLOR);
	     UIManager.put("Label.font", new Font ("Font.SANS_SERIF", Font.BOLD, 13));
	     
	     UIManager.put("Panel.background", LIGHT_COLOR);
	     UIManager.put("Panel.background", LIGHT_COLOR);
	     UIManager.put("ScrollBar.background", LIGHT_COLOR);
	     UIManager.put("RadioButton.background", VERY_LIGHT_COLOR);
	     UIManager.put("ComboBox.background", LIGHT_COLOR);
	     UIManager.put("ScrollPane.background", LIGHT_COLOR);
	     UIManager.put("OptionPane.background", LIGHT_COLOR);
	     UIManager.put("button.OptionPane.background", DARK);
	     
	     UIManager.put("List.selectionBackground",HIGHLIGHT);
	     UIManager.put("List.selectionForeground",VERY_LIGHT_COLOR);
	     UIManager.put("List.font", new Font ("Font.SANS_SERIF", Font.PLAIN, 12));
	     
	}

}
