package com.nesti.stock_manager.test;

import com.nesti.stock_manager.dao.ArticleDao;

public class TestFeatures {
	public static void main(String[] args) {
//		var article = (new ArticleDao()).findOneBy("code", "LAI85");
//		var supplier = (new SupplierDao()).findOneBy("name", "Oeufs en folie");
		System.out.println((new ArticleDao()).findAll().size());
		System.out.println((new ArticleDao()).findAll("a").size());

	}
}
