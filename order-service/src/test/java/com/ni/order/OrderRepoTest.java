package com.ni.order;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ni.order.dao.OrderRepository;
import com.ni.order.dao.entity.NiOrder;

/**
 * 
 * @author rafifzayed
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepoTest {
	@Autowired
	private OrderRepository repo;
	private NiOrder order;

	@Before
	public void init() {
		order = new NiOrder();
		order.setId(1L);
		order.setItemCount(3L);
		order.setProductId(1L);
		repo.save(order);

	}

	@Test
	public void testAddOrder() {
		NiOrder expOrder = createOrder();
		NiOrder result = repo.save(expOrder);
		assertEquals(expOrder.getId(), result.getId());
	}

	@Test
	public void testGetOrder() {
		NiOrder result = repo.getOne(1L);
		assertEquals(order.getId(), result.getId());
	}

	private NiOrder createOrder() {
		NiOrder order = new NiOrder();
		order.setProductId(1L);
		order.setItemCount(3L);
		return order;
	}

	@After
	public void close() {
		repo.deleteAll();
	}
}
