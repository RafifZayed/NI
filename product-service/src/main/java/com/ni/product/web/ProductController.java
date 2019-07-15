package com.ni.product.web;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ni.product.exception.ProductException;
import com.ni.product.service.ProductService;
import com.ni.product.web.bean.ProductBean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author rafifzayed
 *
 */
@RestController
@RequestMapping("/")
public class ProductController {


	@Autowired
	private ProductService productService;

	@PutMapping("/add")
	public Long addProduct(@RequestBody @Valid ProductBean productBean) throws ProductException {

		return productService.addProduct(productBean);
	}

	@PutMapping("/addProducts")
	public Mono<Long> addProducts(@RequestBody Flux<ProductBean> productBeans) throws ProductException {
		
		return productService.addProducts(productBeans);
	}

	@GetMapping("/get")
	public Flux<ProductBean> getProducts() throws ProductException {

		return productService.getProducts();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/get/{id}")
	public ProductBean getProduct(@PathVariable @NotNull Long id) throws ProductException {

		return productService.getProduct(id);
	}

	@HystrixCommand(fallbackMethod = "nameFallback")
	@GetMapping("/getName/{id}")
	public String getProductName(@PathVariable @NotNull Long id) throws ProductException {

		return productService.getProductName(id);
	}

	@PostMapping("/addItem/{id}/{count}")
	public boolean addProductItem(@PathVariable @NotNull Long id, @PathVariable @Min(1) Long count) throws ProductException {

		return productService.addProductItem(id, count);
	}

	@PostMapping("/bookItem/{id}/{count}")
	public boolean bookProductItem(@PathVariable @NotNull Long id, @PathVariable @Min(1) Long count) throws ProductException {

		return productService.bookProduct(id, count);
	}

	// a fallback method to be called if failure happened
	public ProductBean fallback(Long id, Throwable hystrixCommand) {
		return new ProductBean();
	}

	// a fallback method to be called if failure happened
	public String nameFallback(Long id, Throwable hystrixCommand) {
		return "";
	}

}
