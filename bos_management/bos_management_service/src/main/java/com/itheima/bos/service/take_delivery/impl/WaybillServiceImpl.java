package com.itheima.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_delivery.WaybillRepository;
import com.itheima.bos.domain.base.WayBill;
import com.itheima.bos.service.take_delivery.WaybillService;

/**  
 * ClassName:WaybillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 8:52:40 PM <br/>       
 */
@Service
@Transactional
public class WaybillServiceImpl implements WaybillService{
    
    @Autowired
    private WaybillRepository waybillRepository;
    @Override
    public void save(WayBill wayBill) {
        waybillRepository.save(wayBill);        
    }

}
  
