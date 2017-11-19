package com.itheima.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 15, 2017 8:57:41 AM <br/>       
 */
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{

    User findByUsername(String username);

}
  
