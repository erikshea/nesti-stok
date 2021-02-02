package com.nesti.stock_manager.form;

import java.util.List;

/**
 * Interface with just one implemented method: setting something, from a List<String> parameters. Allows passing a lambda parameter.
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public interface ListSetter {
	public void set(List<String> l);
}
