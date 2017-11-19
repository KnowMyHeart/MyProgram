package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 9:00:39 AM <br/>       
 */
public interface MenuService {

    Page<Menu> findByPage(Specification<Menu> specification, Pageable pageable);

    List<Menu> findAllLevelOne();

    void save(Menu model);

    List<Menu> findByUser(User user);


}
  
