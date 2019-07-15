package com.ni.order.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ni.order.dao.OrderRepository;
import com.ni.order.dao.entity.NiOrder;
import com.ni.order.exception.ErrorCode;
import com.ni.order.exception.OrderException;
import com.ni.order.web.bean.OrderBean;

/**
 * 
 * @author rafifzayed
 *
 */
@Service
public class DefaultOrderService implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOrderService.class);

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RestTemplate restTemplate;

	@Value("${product.service.url.getName}")
	private String productGetNameServiceUrl;

	@Value("${product.service.url.book}")
	private String bookProductServiceUrl;

	@Value("${product.service.url.addItem}")
	private String addProductItemServiceUrl;

	@Override
	public Long addOrder(OrderBean orderBean) throws OrderException {
		try {
			if (!bookProduct(orderBean.getProductId(), orderBean.getItemCount())) {
				throw new OrderException(ErrorCode.FAILED_TO_BOOK_ORDER_PRODUCT, "Failed to book order product");
			}
			return orderRepository.save(createOrder(orderBean)).getId();
		} catch (Exception e) {
			if (e instanceof OrderException) {
				if (!ErrorCode.FAILED_TO_BOOK_ORDER_PRODUCT.equals(((OrderException) e).getCode())) {
					addProductItem(orderBean.getProductId(), orderBean.getItemCount());
				}
				throw e;
			}
			throw new OrderException(ErrorCode.FAILED_TO_SAVE_ORDER, e);
		}
	}

	@Override
	public OrderBean getOrder(Long id) throws OrderException {
		try {
			NiOrder order = orderRepository.getOne(id);
			String productName = getProductName(order.getProductId());
			return createOrderBean(order, productName);
		} catch (Exception e) {
			if (e instanceof OrderException)
				throw e;

			throw new OrderException(ErrorCode.FAILED_TO_GET_ORDER, e);
		}
	}

	

	/**
	 * Get product name by its id
	 * 
	 * @param productId
	 * @return
	 */
	private String getProductName(Long productId) {
		try {
			return restTemplate.getForObject(productGetNameServiceUrl, String.class, productId);
		} catch (Exception e) {
			LOGGER.error("Failed to get product name exception : ", e);
			return "";
		}

	}

	/**
	 * Book product by call product service to decrease product stock
	 * 
	 * @param productId
	 * @param count
	 * @return
	 */
	private Boolean bookProduct(Long productId, long count) {
		try {
			return restTemplate.postForObject(bookProductServiceUrl, null, Boolean.class, productId, count);
		} catch (Exception e) {
			LOGGER.error("Failed to book product items ", e);
			return false;
		}
	}

	/**
	 * Add product items used to roll-back book product
	 * 
	 * @param productId
	 * @param count
	 * @return
	 */
	private Boolean addProductItem(Long productId, long count) {
		
		try {
			return restTemplate.postForObject(addProductItemServiceUrl, null, Boolean.class, productId, count);
		} catch (Exception e) {
			LOGGER.error("Failed to add product items ", e);
			return false;
		}
	}


	private OrderBean createOrderBean(NiOrder order, String productName) {
		OrderBean orderBean = createOrderBean(order);
		orderBean.setProductName(productName);
		return orderBean;
	}

	private OrderBean createOrderBean(NiOrder order) {
		OrderBean orderBean = new OrderBean();
		orderBean.setId(order.getId());
		orderBean.setProductId(order.getProductId());
		orderBean.setItemCount(order.getItemCount());
		return orderBean;
	}

	private NiOrder createOrder(OrderBean orderBean) {
		NiOrder order = new NiOrder();
		order.setId(orderBean.getId());
		order.setProductId(orderBean.getProductId());
		order.setItemCount(orderBean.getItemCount());
		return order;
	}

}
