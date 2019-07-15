package com.ni.product.dao.entity;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author rafifzayed
 *
 */

public class Product {

	@Id
	private Long id;
	private String name;
	private Long stock;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

}
