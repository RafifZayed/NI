package com.ni.order.web.bean;

import javax.validation.constraints.NotNull;
/**
 * 
 * @author rafifzayed
 *
 */
public class OrderBean {
	private Long id;
	@NotNull(message = "customer Id can't be empty")
	private Long productId;
	private String productName;
	@NotNull(message = "Item count can't be empty")
	private Long itemCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getItemCount() {
		return itemCount;
	}
	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}
	
}
