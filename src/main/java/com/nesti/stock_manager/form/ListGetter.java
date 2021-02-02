package com.nesti.stock_manager.form;

import java.util.List;

/**
 * Interface with just one implemented method: getting a list of strings. Allows passing a lambda parameter
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public interface ListGetter {
	public List<String> get();
}
