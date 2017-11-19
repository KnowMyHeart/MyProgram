package com.itheima.bos.service.system;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     Nov 18, 2017 8:43:42 AM <br/>       
 */
public interface UserService {

    Page<User> pageQuery(Specification<User> specification, Pageable pageable);

    void save(User model, List<Long> roleIds);

}
  
