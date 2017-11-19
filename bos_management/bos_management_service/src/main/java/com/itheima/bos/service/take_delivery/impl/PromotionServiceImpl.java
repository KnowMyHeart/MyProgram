package com.itheima.bos.service.take_delivery.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_delivery.PromotionRepository;
import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.take_delivery.PromotionService;

/**  
 * ClassName:PromotionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 19, 2017 2:14:51 PM <br/>       
 */
@Transactional
@Service
public class PromotionServiceImpl implements PromotionService {
    
    @Autowired
    private PromotionRepository promotionRepository;
    @Override
    public void save(Promotion model) {
        promotionRepository.save(model);
    }
    @Override
    public Page<Promotion> pageQuery(Specification<Promotion> specification, Pageable pageable) {
          
        return promotionRepository.findAll(specification,pageable);
    }

}
  
