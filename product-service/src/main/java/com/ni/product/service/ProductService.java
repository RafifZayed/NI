package com.ni.product.service;

import com.ni.product.exception.ProductException;
import com.ni.product.web.bean.ProductBean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author rafifzayed
 *
 */
public interface ProductService {

	/**
	 * 
	 * @param productBean
	 * @return
	 * @throws ProductException
	 */
	Long addProduct(ProductBean productBean) throws ProductException;

	/**
	 * 
	 * @param productBean
	 * @return
	 * @throws ProductException
	 */
	Mono<Long> addProducts(Flux<ProductBean> productBeans) throws ProductException;

	/**
	 * 
	 * @param productId
	 * @return
	 * @throws ProductException
	 */
	ProductBean getProduct(Long productId) throws ProductException;

	/**
	 * 
	 * @return
	 * @throws ProductException
	 */
	Flux<ProductBean> getProducts() throws ProductException;

	/**
	 * 
	 * @param productId
	 * @return
	 * @throws ProductException
	 */
	String getProductName(Long productId) throws ProductException;

	/**
	 * 
	 * @param productId
	 * @param count
	 * @return
	 * @throws ProductException
	 */
	boolean addProductItem(Long productId, long count) throws ProductException;

	/**
	 * 
	 * @param productId
	 * @param count
	 * @return
	 * @throws ProductException
	 */
	boolean bookProduct(Long productId, long count) throws ProductException;

}
