package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 11:07:41 AM <br/>       
 */
public interface PermissionService {


    Page<Permission> pageQuery(Specification<Permission> specification, Pageable pageable);

    void save(Permission model);

    List<Permission> findAll();

}
  
