
package controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class UserDirectory extends BaseDirectory {
	protected UserList entityList;
	
	public UserList getEntityList() {
		return entityList;
	}
	
	public UserDirectory(MainWindowControl c) {
		super(c);

		this.entityList = new UserList(c);
		this.add(this.entityList, BorderLayout.CENTER);

	}
}