package controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class SupplierDirectory extends BaseDirectory {
	protected SupplierList entityList;
	
	public SupplierList getEntityList() {
		return entityList;
	}

	public SupplierDirectory(MainWindowControl c) {
		super(c);

		this.entityList = new SupplierList(c);
		this.add(this.entityList, BorderLayout.CENTER);

	}
}