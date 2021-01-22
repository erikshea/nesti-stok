package com.nesti.stock_manager.test;

import com.nesti.stock_manager.model.Article;
import com.nesti.stock_manager.dao.*;
import com.nesti.stock_manager.util.*;

import java.util.HashSet;
import java.util.List;

import org.hibernate.query.Query;

import com.nesti.stock_manager.util.HibernateUtil;

public class TestFeatures {
	public static void main(String[] args) {
		var article = (new ArticleDao()).findOneBy("code", "OEUF12");
		System.out.println(article.getDefaultSupplier());

		
	}
}
