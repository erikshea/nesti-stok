package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class ShoppingCartDirectory extends BaseDirectory {
	
	protected ShoppingCartList entityList;

	public ShoppingCartList getEntityList() {
		return entityList;
	}

	public ShoppingCartDirectory(MainWindowControl c) {
		super(c);

		this.entityList = new ShoppingCartList(c);
		this.add(this.entityList, BorderLayout.CENTER);
	}


}
