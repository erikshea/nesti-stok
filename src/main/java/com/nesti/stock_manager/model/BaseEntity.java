package com.nesti.stock_manager.model;

import com.nesti.stock_manager.dao.BaseDao;

public abstract class BaseEntity {
	public final static String FLAG_DEFAULT = "a";
	
	public abstract BaseDao<?> getDao();
}
