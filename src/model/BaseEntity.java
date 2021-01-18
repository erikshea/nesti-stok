package model;

import dao.BaseDao;

public class BaseEntity<E> {
	protected BaseDao<?>  dao;
	/*
	public void getDao() {
		
		
		Class<? extends BaseDao<E>> c = "foo".getClass();
	}
	public getDao() {
		if (this.dao == null) {
			this.dao = new BaseDao<E>();
		}
	}*/
}
