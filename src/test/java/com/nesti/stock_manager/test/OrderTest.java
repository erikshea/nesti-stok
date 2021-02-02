package com.nesti.stock_manager.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.entity.Order;
import com.nesti.stock_manager.util.FormatUtil;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.PopulateDb;

public class OrderTest {

	public static OrderDao dao = new OrderDao();;
	public static SupplierDao daoSupplier = new SupplierDao();

	@BeforeAll
	public static void populateDb() {
		HibernateUtil.setCurrentEnvironment("test");
		PopulateDb.populate();
	}

	@Test
	public void calculateShippingFeesTest() {
		var order = dao.findOneBy("number", "555");
		assertTrue(order.getShippingFees()>0);

	}

	@Test
	public void findShippingFeesTest() {
		var order = dao.findOneBy("number", "555");
		assertEquals(order.getShippingFees(), 10.35);
	}

	@Test
	public void findSubTotalTest() {
		var order = dao.findOneBy("number", "555");
		System.out.println(order.getSubTotal());
		assertEquals(FormatUtil.round(order.getSubTotal(), 2), 82.26);

	}
	
	@Test
	public void  cascadeOrderOnCommitTest() {
		var order = new Order();
		order.setSupplierFromName("Oeufs en folie");
		order.setUserFromLogin("james");
		order.setNumber("618");
		
		assertNull(order.getIdOrders());
		
		dao.saveOrUpdate(order);
		
	}
	

}
