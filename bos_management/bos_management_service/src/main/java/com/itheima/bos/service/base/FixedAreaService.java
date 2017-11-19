package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 9:00:14 AM <br/>       
 */
public interface FixedAreaService {
    public void save(FixedArea fixedArea);
     
    public Page<FixedArea> pageQuery(Specification<FixedArea> specification, Pageable pageable);

    public void associationCourierToFixedArea(Long courierId, Long takeTimeId, Long id);

    public void addsub_area(Long id, List<Long> subAreaIds);
}
  
