package com.nesti.stock_manager.model;

import com.nesti.stock_manager.dao.BaseDao;
import com.nesti.stock_manager.util.ReflectionProperty;

public abstract class BaseEntity {
	public static final String DUPLICATE_SUFFIX = "_NEW";
	public abstract BaseDao<?> getDao();
	
	public String getDuplicatedFieldValue(String fieldName) {

		var newValue = ReflectionProperty.get(this, fieldName).toString();
		do {
			newValue += DUPLICATE_SUFFIX;
		}while (getDao().findOneBy(fieldName, newValue) != null);
		
		return newValue;
	}
}
