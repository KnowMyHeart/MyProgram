package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.take_delivery.Promotion;

/**  
 * ClassName:PromotionRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 19, 2017 2:16:38 PM <br/>       
 */
public interface PromotionRepository extends JpaRepository<Promotion, Long>,JpaSpecificationExecutor<Promotion> {

}
  
