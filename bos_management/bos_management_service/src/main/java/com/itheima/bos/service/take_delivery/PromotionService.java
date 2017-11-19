package com.itheima.bos.service.take_delivery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.take_delivery.Promotion;

/**  
 * ClassName:promotionService <br/>  
 * Function:  <br/>  
 * Date:     Nov 19, 2017 2:14:12 PM <br/>       
 */
public interface PromotionService {

    void save(Promotion model);

    Page<Promotion> pageQuery(Specification<Promotion> specification, Pageable pageable);

}
  
