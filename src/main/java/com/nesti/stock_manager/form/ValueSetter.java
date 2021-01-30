package com.nesti.stock_manager.form;


/**
 * Interface with just one implemented method: setting something, from a String parameter. Allows passing a lambda parameter
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public interface ValueSetter {
	public void set(String s);
}
