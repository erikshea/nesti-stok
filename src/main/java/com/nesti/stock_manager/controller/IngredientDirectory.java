package com.nesti.stock_manager.controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class IngredientDirectory extends BaseDirectory {
	protected IngredientList entityList;
	
	public IngredientList getEntityList() {
		return entityList;
	}
	public IngredientDirectory(MainWindowControl c) {
		super(c);

		this.entityList = new IngredientList(c);
		this.add(this.entityList, BorderLayout.CENTER);
	}
}