package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 9:01:23 AM <br/>       
 */
public interface FixedAreaRepository 
            extends JpaRepository<FixedArea, Long>,JpaSpecificationExecutor<FixedArea> {
    

}
  
