package com.nesti.stock_manager.controller;

/**
 * Interface for all tab components, providing a title accessor 
 * and methods called when a tab-related event occurs
 * @author Emmanuelle Gay, Erik Shea
 */
public interface Tab {
	/**
	 * Runs when tab closed
	 */
	public void closeTab();
	
	/**
	 * When tab refreshed
	 */
	public void refreshTab();
	
	/**
	 * Title for tab button
	 * @return title string
	 */
	public String getTitle();
}
