package com.itheima.bos.service.system.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 11:07:52 AM <br/>       
 */
@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {
    
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public Page<Permission> pageQuery(Specification<Permission> specification, Pageable pageable) {
          
        return permissionRepository.findAll(specification,pageable);
    }
    @Override
    public void save(Permission model) {
        permissionRepository.save(model);
    }
    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

}
  
