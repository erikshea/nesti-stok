package com.nesti.stock_manager.model;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.util.ReflectionProperty;

/**
 * Base entity class
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public abstract class BaseEntity {
	public static final String DUPLICATE_SUFFIX = "_NEW";
	public abstract BaseDao<?> getDao();
	
	/**
	 * For unique fields, provides a similar field name that is guaranteed unique
	 * @param fieldName, the value of which needs to be duplicated
	 * @return duplicated value
	 */
	public String getDuplicatedFieldValue(String fieldName) {
		var newValue = ReflectionProperty.get(this, fieldName).toString(); // get current value using reflection
		do {
			newValue += DUPLICATE_SUFFIX; // add suffix to value
		}while (getDao().findOneBy(fieldName, newValue) != null); // loop again if new value is present in data source
		
		return newValue;
	}
}
