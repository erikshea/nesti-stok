package controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class UserDirectory extends BaseDirectory {

	public UserDirectory(MainWindowControl c) {
		super(c);

		var adminstratorList = new UserList();
		this.add(adminstratorList, BorderLayout.CENTER);

	}
}