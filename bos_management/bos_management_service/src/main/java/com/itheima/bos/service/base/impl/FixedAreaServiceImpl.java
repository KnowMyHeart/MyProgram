package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 9:00:24 AM <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    
    @Autowired
    private SubAreaRepository subAreaRepository;
    
    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }
 

    @Override
    public Page<FixedArea> pageQuery(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification, pageable);
    }


    @Override
    public void associationCourierToFixedArea(Long courierId, Long takeTimeId, Long id) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        
        courier.setTakeTime(takeTime);
        fixedArea.getCouriers().add(courier);
        fixedAreaRepository.save(fixedArea);
    }


    @Override
    public void addsub_area(Long id, List<Long> subAreaIds) {
        subAreaRepository.untie(id);
        
        if (subAreaIds != null && subAreaIds.size() > 0) {
            for (Long subAreaId : subAreaIds) {
                subAreaRepository.binding(id,subAreaId);
            }
        }
    }
    
        
}
  
