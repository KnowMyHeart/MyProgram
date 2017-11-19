package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 8:28:48 AM <br/>       
 */
public interface SubAreaRepository 
            extends JpaRepository<SubArea, Long>,JpaSpecificationExecutor<SubArea>{
    
    @Query("from SubArea where fixedArea.id = ?")
    List<SubArea> findByFixedAreaId(Long fixedAreaId);

    List<SubArea> findByFixedAreaIsNull();
    
    //解除定区的绑定
    @Modifying
    @Query("update SubArea set fixedArea = null where fixedArea.id = ?")
    void untie(Long id);
    
    //绑定指定的定区
    @Modifying
    @Query("update SubArea set fixedArea.id = ? where id = ?")
    void binding(Long id, Long subAreaId);
    
}
  
