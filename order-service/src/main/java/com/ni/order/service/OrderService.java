package com.ni.order.service;

import com.ni.order.exception.OrderException;
import com.ni.order.web.bean.OrderBean;

/**
 * 
 * @author rafifzayed
 *
 */
public interface OrderService {
	/**
	 * Add new order
	 * @param orderBean
	 * @return
	 * @throws OrderException
	 */
	Long addOrder(OrderBean orderBean) throws OrderException;

	/**
	 * Get Order by id
	 * @param id
	 * @return
	 * @throws OrderException
	 */
	OrderBean getOrder(Long id) throws OrderException;
	
}
