package com.itheima.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;

/**
 * ClassName:MenuServiceImpl <br/>
 * Function: <br/>
 * Date: Nov 17, 2017 9:03:07 AM <br/>
 */
@Transactional
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Page<Menu> findByPage(Specification<Menu> specification, Pageable pageable) {
        return menuRepository.findAll(specification, pageable);
    }

    @Override
    public List<Menu> findAllLevelOne() {
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu model) {
        menuRepository.save(model);
    }

    @Override
    public List<Menu> findByUser(User user) {
        if ("admin".equals(user.getUsername()) 
                || "boss".equals(user.getUsername())) {
            return menuRepository.findAll();
        }
        
        return menuRepository.findByUser(user.getId());
    }

}
