package com.nesti.stock_manager.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nesti.stock_manager.dao.BaseDao;

public abstract class BaseEntity {
	
	public abstract BaseDao<?> getDao();
}
