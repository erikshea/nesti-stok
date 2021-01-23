package com.nesti.stock_manager.test;

import com.nesti.stock_manager.dao.UserDao;

public class TestManue {
	public static void main(String[] args) {

//		ApplicationSettings.set("login", "salut");
//		
//var logn = 	ApplicationSettings.get("login");
//	System.out.println(logn);
//		

		var userDao = new UserDao();
		var user = userDao.findOneBy("login", "james");

		System.out.println(user.getName());

		user.setPasswordHashFromPlainText("1234");
		System.out.println(user.getPasswordHash());
		
		System.out.println(user.isPassword("1234"));
	}

}
