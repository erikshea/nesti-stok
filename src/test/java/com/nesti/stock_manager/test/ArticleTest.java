package com.nesti.stock_manager.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.PopulateDb;

public class ArticleTest {

	@BeforeAll
	public static void populateDb() {
		HibernateUtil.setCurrentEnvironment("test");
		PopulateDb.populate();
	}
	
	@Test
	public void FindCurrentOffersTest() {
		
	}
	
	@Test
	public void FindCurrentOffersTest2() {
		
	}
}
