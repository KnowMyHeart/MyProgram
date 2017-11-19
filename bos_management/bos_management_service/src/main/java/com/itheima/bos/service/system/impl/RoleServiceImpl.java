package com.itheima.bos.service.system.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 8:47:38 PM <br/>       
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Page<Role> pageQuery(Specification<Role> specification, Pageable pageable) {
        return roleRepository.findAll(specification,pageable);
    }

    @Override
    public void save(Role model, List<Long> permissionIds, String menuIds) {
        roleRepository.save(model);
        if (permissionIds != null && permissionIds.size() != 0) {
            for (Long id : permissionIds) {
                Permission permission = new Permission();
                permission.setId(id);
                model.getPermissions().add(permission);
            }
        }
        
        if (StringUtils.isNotEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (String mid : split) {
                Menu menu = new Menu();
                menu.setId(Long.parseLong(mid));
                model.getMenus().add(menu);
            }
        }
        
    }

    @Override
    public List<Role> findAll() {
          
        return roleRepository.findAll();
    }

}
  
