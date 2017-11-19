package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:30:39 PM <br/>       
 */
public interface StandardService {

    void save(Standard model);

    Page<Standard> pageQuery(Pageable pageable);

    List<Standard> findAll();
    
    
}
  
