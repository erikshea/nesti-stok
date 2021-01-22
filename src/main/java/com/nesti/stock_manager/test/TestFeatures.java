package com.nesti.stock_manager.test;

import com.nesti.stock_manager.model.*;
import com.nesti.stock_manager.dao.*;
import com.nesti.stock_manager.util.*;

import java.util.HashSet;
import java.util.List;

import org.hibernate.query.Query;

import com.nesti.stock_manager.util.HibernateUtil;

public class TestFeatures {
	public static void main(String[] args) {
		var article = (new ArticleDao()).findOneBy("code", "LAI85");
		var supplier = (new SupplierDao()).findOneBy("name", "Oeufs en folie");


		var offer = new Offer();
		offer.setPrice(10.0);
		offer.setSupplier(supplier);
		
		article.addOffer(offer);
		System.out.println(article.getCurrentOffers().size());
		/*
		article.getCurrentOffers().forEach( o->{
			System.out.println(o.getSupplier().getName() + " : " + o.getPrice());
		});*/
		
	}
}
