package com.nesti.stock_manager.test;

import java.util.Date;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.SupplierDao;

public class TestManue {

		
		public static void main(String[] args) {
	        var article = (new ArticleDao()).findById(126);
	        var supplier = (new SupplierDao()).findById(67);
	        var date = new Date();
	        date.setDate(25);
	        date.setMonth(0);
	        date.setYear(1);
	        date.setHours(19);
	        date.setYear(121);

	        var offer = article.getOfferAt(date, supplier);
	        var price = offer.getPrice();
	        System.out.println(price);
	    }
		
	}

