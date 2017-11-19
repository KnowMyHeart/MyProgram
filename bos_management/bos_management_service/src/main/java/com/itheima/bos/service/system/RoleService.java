package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 8:47:28 PM <br/>       
 */
public interface RoleService {

    Page<Role> pageQuery(Specification<Role> specification, Pageable pageable);

    void save(Role model, List<Long> permissionIds, String menuIds);

    List<Role> findAll();

}
  
