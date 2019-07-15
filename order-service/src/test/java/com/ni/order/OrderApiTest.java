package com.ni.order;

import static org.hamcrest.Matchers.hasValue;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ni.order.service.OrderService;
import com.ni.order.web.OrderController;
import com.ni.order.web.bean.OrderBean;


/**
 * 
 * @author rafifzayed
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderApiTest {


	@Autowired
	private MockMvc mvc;
	@MockBean
	private OrderService orderService;

	@Test
	public void addOrderTest() throws Exception {
		Long orderId = 1L;
		given(orderService.addOrder(Mockito.any(OrderBean.class))).willReturn(orderId);
		mvc.perform(put("/add").contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"1\",\"productId\":\"1\",\"itemCount\":\"4\"}")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content()
                        .string("1"));
	}
	
	@Test
	public void getOrderTest() throws Exception {
		Long orderId = 1L;
		given(orderService.getOrder(orderId)).willReturn(createOrderBean());
		mvc.perform(get("/get/{id}",orderId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasValue(1)));
	}

	
	private OrderBean createOrderBean() {
		OrderBean orderBean = new OrderBean();
		orderBean.setId(1L);
		orderBean.setItemCount(3L);
		orderBean.setProductId(1L);
		return orderBean;
	}
}
