package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpk <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 3:47:33 PM <br/>       
 */
@Service
@Transactional
public class CourierServiceImpk implements CourierService{
    @Autowired
    private CourierRepository courierRepository;
    
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> pageQuery(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }
    
    @Override
    public void updateDelTagById(long parseLong) {
        courierRepository.updateDelTagById(parseLong);
    }

    @Override
    public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
        return  courierRepository.findAll(specification, pageable);
    }

    @Override
    public List<Courier> findAll() {
        return courierRepository.findByDeltagIsNull();
    }

}
  
