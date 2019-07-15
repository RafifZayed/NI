package com.ni.product;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ni.product.dao.ProductRepository;
import com.ni.product.dao.ReProductRepository;
import com.ni.product.dao.entity.Product;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductRepoTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ReProductRepository reProductRepository;


	@Test
	public void testAdd() {
		Product product = createProduct();
		Product result = productRepository.save(product);
		assertEquals(product.getId(), result.getId());
	}
	
	
	@Test
	public void testAddProductReactiveBlock() {		
		Product product1 = createProduct2();
		Mono<Product> result=reProductRepository.save(product1);		
	    System.out.println("added product  Stock "+result.block().getStock());
		assertEquals(new Long(78l), result.block().getId());
	}
	
	@Test
	public void testAddProductReactiveNonBlock() {		
		Product product1 = createProduct3();		
		Publisher<Product> saveProduct=reProductRepository.save(product1);	
		Mono<Product> findProduct=reProductRepository.findById(new Long(99));
		Publisher<Product> composite = Mono.from(saveProduct).then(findProduct);
	    StepVerifier.create(composite).expectNextCount(1).verifyComplete();
	}
	
	@Test
	public void testAddProductListReactive() {		
		List<Product> products = createProductList();
		Publisher<Product> insertAll=reProductRepository.saveAll(products);		   
		StepVerifier.create(insertAll).expectNextCount(2).verifyComplete();
	}
	
	
	@Test
	public void testgetProduct() {
		Optional<Product> result = productRepository.findById(new Long(77));
		System.out.println("product by Id : "+result.get().getId());
	  assertEquals(new Long(77l), result.get().getId());
	}

	
	
	@Test
	public void testgetAllProductReactive() {
		Publisher<Product> result = reProductRepository.findAll();
		StepVerifier.create(result).expectNextCount(5).verifyComplete();
		
	}
		
	
	private Product createProduct() {
		Product product = new Product();
		product.setId(77l);
		product.setName("iphone x max");
		product.setStock(5l);
		return product;
	}
	
	private Product createProduct2() {
		Product product = new Product();
		product.setId(78l);
		product.setName("iphone x");
		product.setStock(5l);
		return product;
	}

	private Product createProduct3() {
		Product product = new Product();
		product.setId(99l);
		product.setName("iphone x 250");
		product.setStock(6l);
		return product;
	}

	
	private List<Product> createProductList() {
		
		List<Product> productlist=new ArrayList<>();
		Product product = new Product();
		product.setId(79l);
		product.setName("samsung s10");
		product.setStock(20l);
		productlist.add(product);
		
		product = new Product();
		product.setId(90l);
		product.setName("samsung note 9");
		product.setStock(5l);
		productlist.add(product);
		return productlist;
		
		
		
	}

	

	
}
