package com.ni.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ni.order.dao.entity.NiOrder;

/**
 * 
 * @author rafifzayed
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<NiOrder, Long> {

}
