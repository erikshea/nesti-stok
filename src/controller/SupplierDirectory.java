package controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class SupplierDirectory extends BaseDirectory {

	public SupplierDirectory(MainWindowControl c) {
		super(c);

		var supplierList = new SupplierList();
		this.add(supplierList, BorderLayout.CENTER);

	}
}