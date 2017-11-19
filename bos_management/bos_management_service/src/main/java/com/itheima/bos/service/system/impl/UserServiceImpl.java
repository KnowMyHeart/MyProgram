package com.itheima.bos.service.system.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 18, 2017 8:43:54 AM <br/>       
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Page<User> pageQuery(Specification<User> specification, Pageable pageable) {
          
        return userRepository.findAll(specification, pageable);
    }
    
    @RequiresPermissions("root")
    @Override
    public void save(User model, List<Long> roleIds) {
          userRepository.save(model);
          if (roleIds != null && roleIds.size() > 0 ) {
            for (Long rid : roleIds) {
                Role role = new Role();
                role.setId(rid);
                model.getRoles().add(role);
            }
          }
        
    }

}
  
