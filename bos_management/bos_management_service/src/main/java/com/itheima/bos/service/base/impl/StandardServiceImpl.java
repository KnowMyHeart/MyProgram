package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:31:03 PM <br/>       
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardRepository standardRepository;
    
    @Override
    public void save(Standard model) {
          standardRepository.save(model);
    }

    @Override
    public Page<Standard> pageQuery(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }

    @Override
    public  List<Standard> findAll() {
          
        return standardRepository.findAll();
    }

   
    
}
  
