package com.ni.product.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.ni.product.dao.entity.Product;


@Repository("ReProductRepository")
public interface ReProductRepository extends ReactiveMongoRepository<Product, Long>  {
 

}
