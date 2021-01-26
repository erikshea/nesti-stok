package com.nesti.stock_manager.model;

import com.nesti.stock_manager.dao.BaseDao;

public abstract class BaseEntity {
	public static final String DUPLICATE_SUFFIX = "_NEW";
	public abstract BaseDao<?> getDao();
}
