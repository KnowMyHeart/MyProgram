package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 10:19:16 AM <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;
    @Override
    public void save(List<Area> list) {
        areaRepository.save(list);
    }
    @Override
    public Page<Area> pageQuery(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }
    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }
    
    @Override
    public List<Area> findByQ(String q) {
        //判断用户输入的字符是否为空,不为空就将字符变大写,因为oracle对大写的区分较为严格
            q = q.toUpperCase();
        return areaRepository.findByQ("%"+q+"%");
    }

}
  
