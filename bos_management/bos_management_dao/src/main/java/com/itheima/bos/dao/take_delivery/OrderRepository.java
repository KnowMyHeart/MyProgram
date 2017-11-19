package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 12, 2017 10:04:48 AM <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Long>{

}
  
