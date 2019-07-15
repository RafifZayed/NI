package com.ni.order.web;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ni.order.exception.OrderException;
import com.ni.order.service.OrderService;
import com.ni.order.web.bean.OrderBean;

/**
 * 
 * @author rafifzayed
 *
 */
@RestController
@RequestMapping("/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PutMapping("/add")
	public Long addOrder(@RequestBody @Valid OrderBean orderBean) throws OrderException {
		return orderService.addOrder(orderBean);
	}

	@GetMapping("/get/{id}")
	public OrderBean getOrder(@PathVariable @NotNull Long id) throws OrderException {

		return orderService.getOrder(id);
	}


}
