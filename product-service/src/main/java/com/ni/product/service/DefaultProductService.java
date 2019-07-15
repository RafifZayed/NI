package com.ni.product.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import com.ni.product.dao.ProductRepository;
import com.ni.product.dao.ReProductRepository;
import com.ni.product.dao.entity.Product;
import com.ni.product.exception.ErrorCode;
import com.ni.product.exception.ProductException;
import com.ni.product.web.bean.ProductBean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

/**
 * 
 * @author rafifzayed
 *
 */
@Service
public class DefaultProductService implements ProductService {

	@Autowired
	private Validator validator;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReProductRepository reProductRepository;

	private static Long PRODUCT_SEQ = 1L;

	@Override
	public Long addProduct(ProductBean productBean) throws ProductException {
		try {
			synchronized (PRODUCT_SEQ) {
				productBean.setId(PRODUCT_SEQ++);
			}
			return productRepository.save(createProduct(productBean)).getId();
		} catch (Exception e) {
			if (e instanceof ProductException)
				throw e;
			throw new ProductException(ErrorCode.FAILED_TO_SAVE_PRODUCT, e);
		}
	}

	@Override
	public Mono<Long> addProducts(Flux<ProductBean> productBeans) throws ProductException {
		try {

			return productBeans.log("category", Level.INFO, SignalType.ON_NEXT).map(productBean -> {
				validate(productBean);
				Product product = new Product();
				synchronized (PRODUCT_SEQ) {
					product.setId(PRODUCT_SEQ++);
				}
				product.setName(productBean.getName());
				product.setStock(productBean.getStock());
				return product;
			}).map(product -> reProductRepository.insert(product).subscribe()).count();

		} catch (Exception e) {
			if (e instanceof ProductException)
				throw e;
			throw new ProductException(ErrorCode.FAILED_TO_SAVE_PRODUCT, e);
		}
	}

	private void validate(ProductBean productBean) {
		Errors errors = new BeanPropertyBindingResult(productBean, ProductBean.class.getName());
		validator.validate(productBean, errors);
		if (errors.hasErrors()) {
			List<ObjectError> objectErrors = errors.getAllErrors();
			StringBuilder builder = new StringBuilder();
			objectErrors.stream().forEach(errorObj -> builder.append(errorObj.getCode()).append(" = ").append(errorObj.getDefaultMessage()));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, builder.toString());

		}
	}

	@Override
	public ProductBean getProduct(Long productId) throws ProductException {
		try {
			Optional<Product> productOp = productRepository.findById(productId);
			if (!productOp.isPresent()) {
				throw new ProductException(ErrorCode.PRODUCT_NOT_FOUND, MessageFormat.format("{0} Not Found ", productId));
			}
			return createProductBean(productOp.get());
		} catch (Exception e) {
			if (e instanceof ProductException)
				throw e;
			throw new ProductException(ErrorCode.FAILED_TO_GET_PRODUCT, e);
		}
	}

	@Override
	public Flux<ProductBean> getProducts() throws ProductException {
		try {
			Flux<Product> productOp = reProductRepository.findAll();
			return productOp.map(product -> createProductBean(product));
		} catch (Exception e) {
			if (e instanceof ProductException)
				throw e;
			throw new ProductException(ErrorCode.FAILED_TO_GET_PRODUCTS, e);
		}
	}

	@Override
	public String getProductName(Long productId) throws ProductException {
		try {
			Optional<Product> productOp = productRepository.findById(productId);
			if (!productOp.isPresent()) {
				throw new ProductException(ErrorCode.PRODUCT_NOT_FOUND, MessageFormat.format("{0} Not Found ", productId));
			}
			return productOp.get().getName();
		} catch (Exception e) {
			if (e instanceof ProductException)
				throw e;
			throw new ProductException(ErrorCode.FAILED_TO_GET_PRODUCT_NAME, e);
		}
	}

	@Override
	public boolean addProductItem(Long productId, long count) throws ProductException {
		try {
			if (count > 0) {
				Optional<Product> productOp = productRepository.findById(productId);
				if (!productOp.isPresent()) {
					throw new ProductException(ErrorCode.PRODUCT_NOT_FOUND, MessageFormat.format("{0} Not Found ", productId));
				}

				Product product = productOp.get();
				product.setStock(product.getStock() + count);
				productRepository.save(product);
			}
			return true;
		} catch (Exception e) {
			if (e instanceof ProductException)
				throw e;
			throw new ProductException(ErrorCode.FAILED_TO_ADD_PRODUCT_ITM, e);
		}
	}

	@Override
	public boolean bookProduct(Long productId, long count) throws ProductException {
		try {
			if (count > 0) {
				Optional<Product> productOp = productRepository.findById(productId);
				if (!productOp.isPresent()) {
					throw new ProductException(ErrorCode.PRODUCT_NOT_FOUND, MessageFormat.format("{0} Not Found ", productId));
				}

				Product product = productOp.get();
				product.setStock(product.getStock() - count);
				productRepository.save(product);
			}
			return true;
		} catch (Exception e) {
			if (e instanceof ProductException)
				throw e;
			throw new ProductException(ErrorCode.FAILED_TO_BOOK_PRODUCT_ITM, e);
		}
	}

	private ProductBean createProductBean(Product product) {
		ProductBean productBean = new ProductBean();
		productBean.setId(product.getId());
		productBean.setName(product.getName());
		productBean.setStock(product.getStock());
		return productBean;
	}

	private Product createProduct(ProductBean productBean) {
		Product product = new Product();
		product.setId(productBean.getId());
		product.setName(productBean.getName());
		product.setStock(productBean.getStock());
		return product;
	}

}
