package com.nesti.stock_manager.test;

import com.nesti.stock_manager.dao.ArticleDao;

public class TestFeatures {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		var article = (new ArticleDao()).findOneBy("code","OEUF6");
//		var supplier = (new SupplierDao()).findById(56);
//		//System.out.println((new ArticleDao()).findAll().size());
//		//System.out.println((new ArticleDao()).findAll("a").size());
//		var date = new Date();
//		date.setDate(24);
//		date.setMonth(0);
//		date.setHours(19);
//		date.setYear(121);
//		
//		var offer = article.getOfferAt(date, supplier);
//		System.out.println(offer.getPrice());
		
		
		System.out.println(article.getCurrentOffers());
	}
}
