package com.ni.product.web.bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author rafifzayed
 *
 */
public class ProductBean {

	private Long id;
	@NotEmpty(message = "Name can't be empty")
	private String name;
	@NotNull(message = "Stock can't be empty")
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
	@Override
	public String toString() {
		return "ProductBean [id=" + id + ", name=" + name + ", stock=" + stock + "]";
	}
	
	
}
