package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 3:47:00 PM <br/>       
 */
public interface CourierService {
    void save(Courier courier);

    Page<Courier> pageQuery(Pageable pageable);

    void updateDelTagById(long parseLong);

    Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable);

    List<Courier> findAll();

}
  
