package com.ni.product.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ni.product.dao.entity.Product;

/**
 * 
 * @author rafifzayed
 *
 */

@Repository
public interface ProductRepository extends MongoRepository<Product, Long>  {

}
