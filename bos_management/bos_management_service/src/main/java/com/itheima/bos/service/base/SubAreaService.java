package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 8:25:09 AM <br/>       
 */

public interface SubAreaService {

    void save(SubArea model);

    Page<SubArea> pageQuery(Specification<SubArea> specification, Pageable pageable);

    List<SubArea> findAll();

    List<SubArea> noSubArea();
    
    List<SubArea> yesSubArea(Long fixedAreaId);
}
  
