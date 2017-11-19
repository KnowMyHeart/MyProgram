package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 3:48:48 PM <br/>       
 */
//JpaSpecificationExecutor不能被单独使用
//一定要和JpaRepository一起使用

public interface CourierRepository extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier>{
    
    
    //进行逻辑删除,并且要写上俩个注解,一个是更新必须的modify,一个是自定义hql语句
    @Modifying
    @Query("update Courier set deltag = '1' where id = ?")
    void updateDelTagById(long parseLong);

    List<Courier> findByDeltagIsNull();

}
  
