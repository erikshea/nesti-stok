package com.nesti.stock_manager.test;

import com.nesti.stock_manager.model.Article;
import org.hibernate.query.Query;

import com.nesti.stock_manager.util.ApplicationSettings;
import com.nesti.stock_manager.util.HibernateUtil;

public class TestManue {
	public static void main(String[] args) {

		ApplicationSettings.set("login", "salut");
		
var logn = 	ApplicationSettings.get("login");
	System.out.println(logn);
		
		
	}

	

}
