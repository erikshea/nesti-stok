package com.nesti.stock_manager.model;

/**
 * Interface for an entity that has a "flag" parameter
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public interface Flagged {
	public void setFlag(String flag);
	public String getFlag();
}
