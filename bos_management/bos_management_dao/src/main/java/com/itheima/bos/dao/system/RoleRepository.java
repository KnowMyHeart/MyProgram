package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Role;

/**  
 * ClassName:RoleRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 8:48:50 PM <br/>       
 */
public interface RoleRepository extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role> {
    
    @Query("select r from Role r inner join r.users u where u.id = ?")
    List<Role> findByUid(Long id);

}
  
