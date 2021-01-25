package com.nesti.stock_manager.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class AppAppereance {

	public static final Color VERY_LIGHT_COLOR = new Color(251,242,221);
	public static final Color LIGHT_COLOR = new Color(244,225,181);
	public static final Color MEDIUM_COLOR = new Color (232,189,88);
	public static final Color DARK= new Color (179,133,4);
	public static final Color HIGHLIGHT = new Color(91,148,4);

	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    public static final Font BUTTON_FONT = DEFAULT_FONT.deriveFont(Font.BOLD);
    public static final Font TAB_FONT = DEFAULT_FONT.deriveFont(Font.BOLD,14);
	
	public static final Dimension CLASSIC_BUTTON = new Dimension(130,30);
	public static final Dimension LARGE_BUTTON = new Dimension(140,30);
	
}
