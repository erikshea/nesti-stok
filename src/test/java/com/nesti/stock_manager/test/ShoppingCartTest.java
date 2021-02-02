package com.nesti.stock_manager.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nesti.stock_manager.dao.ArticleDao;
import com.nesti.stock_manager.dao.OrderDao;
import com.nesti.stock_manager.dao.SupplierDao;
import com.nesti.stock_manager.entity.Order;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.PopulateDb;

public class ShoppingCartTest {

	public static OrderDao dao = new OrderDao();;
	public static SupplierDao daoSupplier = new SupplierDao();
	public static ArticleDao articleDao = new ArticleDao();

	@BeforeAll
	public static void populateDb() {
		HibernateUtil.setCurrentEnvironment("test");
		PopulateDb.populate();
	}

	@Test
	public void cascadeOrderOnCommitTest() {
		var order = new Order();
		order.setSupplierFromName("Oeufs en folie");
		order.setUserFromLogin("james");
		order.setNumber("618");

		assertNull(order.getIdOrders());

		dao.saveOrUpdate(order);

		assertNotNull(order.getIdOrders());

	}

}
