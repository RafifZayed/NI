package com.ni.product;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.ni.product.service.ProductService;
import com.ni.product.web.ProductController;
import com.ni.product.web.bean.ProductBean;

/**
 * 
 * @author rafifzayed
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductApiTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private ProductService productService;
	
	@Test
	public void addProductTest() throws Exception {
		Long productId = 1L;
		given(productService.addProduct(Mockito.any(ProductBean.class))).willReturn(productId);
		mvc.perform(put("/add").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"test2\", \"stock\":\"46\"}")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content()
                        .string("1"));
	}
	
	@Test
	public void getNameTest() throws Exception {
		Long productId = 1L;
		String prod = "prod1";
		given(productService.getProductName(productId)).willReturn(prod);
		mvc.perform(get("/getName/{id}",productId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content()
                        .string(prod));
	}

	@Test
	public void bookItemTest() throws Exception {
		Long productId = 1L;
		given(productService.bookProduct(productId, 2)).willReturn(true);
		mvc.perform(post("/bookItem/{id}/{count}",productId,2).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content()
                        .string("true"));
	}

	@Test
	public void addItemTest() throws Exception {
		Long productId = 1L;
		given(productService.addProductItem(productId, 2)).willReturn(true);
		mvc.perform(post("/addItem/{id}/{count}",productId,2).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content()
                        .string("true"));
	}
	
	@Test
	public void getTest() throws Exception {
		Long productId = 1L;
		given(productService.getProduct(productId)).willReturn(createProductBean());
		mvc.perform(get("/get/{id}",productId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":null,\"stock\":null}"));
	}
	
	private ProductBean createProductBean() {
		ProductBean productBean = new ProductBean();
		productBean.setId(1L);
		return productBean;
		
	}
}
