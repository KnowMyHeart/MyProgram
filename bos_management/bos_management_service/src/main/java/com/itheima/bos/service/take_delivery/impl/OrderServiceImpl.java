package com.itheima.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.take_delivery.OrderRepository;
import com.itheima.bos.dao.take_delivery.WorkBillRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Order;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.WorkBill;
import com.itheima.bos.service.take_delivery.OrderService;

/**
 * ClassName:OrderServiceImpl <br/>
 * Function: <br/>
 * Date: Nov 12, 2017 10:03:21 AM <br/>
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private WorkBillRepository workBillRepository;

    /**
     * Order [id=null, 
     * orderNum=null, 
     * telephone=null, 
     * customer_id=null, 
     * sendName=王锐, 
     * sendMobile=13723494672, 
     * sendCompany=null, 
     * sendArea=Area [id=null, province=北京, city=北京, district=东城, postcode=null, citycode=null, shortcode=null], 
     * sendAddress=北京市东城区北京市东城区社保中心,
     *  recName=李四, 
     *  recMobile=13000003211,
     *   recCompany=null, 
     *   recArea=Area [id=null, province=重庆, city=重庆, district=渝中, postcode=null, citycode=null, shortcode=null], 
     *   recAddress=重庆市渝中区渝中区,
     *    sendProNum=null, goodsType=2,
     *     payTypeNum=1,
     *      weight=null,
     *       remark=, 
     *       sendMobileMsg=王锐是sbb,
     *        orderTime=null,
     *         wayBill=null, 
     *         workBills=[]]
     * @see com.itheima.bos.service.take_delivery.OrderService#save(com.itheima.bos.domain.base.Order)
     */
    
    
    @Override
    public void save(Order order) {
        System.out.println("保存订单");
      
        
        //因为hibernate不能再一个持久态里面保存瞬时态,所以必须将俩个瞬时态转换成持久态
        Area sendArea = order.getSendArea();
        if (sendArea != null) {
            String province = sendArea.getProvince();
            String city = sendArea.getCity();
            String district = sendArea.getDistrict();
            
            sendArea = areaRepository.findByProvinceAndCityAndDistrict(province,city,district);
            order.setSendArea(sendArea);
        }
        
        
        Area recArea = order.getRecArea();
        
        if (recArea != null) {
            String province = recArea.getProvince();
            String city = recArea.getCity();
            String district = recArea.getDistrict();
            
            Area recAreaDb = areaRepository.findByProvinceAndCityAndDistrict(province,city,district);
            order.setRecArea(recAreaDb);
        }
        order.setOrderNum(RandomStringUtils.randomNumeric(32));
        order.setOrderTime(new Date());
        //转换完瞬时态的类后,保存订单
        orderRepository.save(order);
        
        //自动分单,根据用户输入的详细地址,在crm系统中查找定区的id
        String sendAddress = order.getSendAddress();
        if (StringUtils.isNotEmpty(sendAddress)) {
            
            Long fixedAreaId = WebClient.create("http://localhost:8180/crm/webService/customerService/findFixedAreaIdByAddress")
                     .query("address", sendAddress)
                     .accept(MediaType.APPLICATION_JSON)
                     .get(Long.class);
          //根据id查询出有关于该定区所存在的快递员
            if (fixedAreaId != null && fixedAreaId != 0) {
                FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
                Set<Courier> couriers = fixedArea.getCouriers();
                //判断快递员的收派标准和上班时间
                /*for (Courier courier : couriers) {
                    
                }*/
                if (!couriers.isEmpty()) {
                    Courier courier = couriers.iterator().next();
                    order.setCourier(courier);
                    //封装工单
                    
                    WorkBill workBill = new WorkBill();
                    workBill.setAttachbilltimes(0);
                    workBill.setBuildtime(new Date());
                    workBill.setCourier(courier);
                    workBill.setOrder(order);
                    workBill.setPickstate("新单");
                    workBill.setRemark(order.getRemark());
                    workBill.setSmsNumber("123");
                    workBill.setType("新");
                    
                    //进行保存工单
                    workBillRepository.save(workBill);
                    //在填补order
                    order.setOrderType("自动分单");
                    System.out.println("根据客户绑定自动分单保存成功");
                    return ;
                }
            }
        }
        
        //根楷分区关键字和辅助关键字进行分单
        Area sendAreaDB = order.getSendArea();
        if (sendAreaDB != null) {
            Set<SubArea> subareas = sendAreaDB.getSubareas();
            if (!subareas.isEmpty()) {
                for (SubArea subArea : subareas) {
                    String keyWords = subArea.getKeyWords();
                    String assistKeyWords = subArea.getAssistKeyWords();
                    
                    //如果辅助关键字或者关键字匹配,就获取该分区的里的定区
                    if (sendAddress.contains(keyWords) || sendAddress.contains(assistKeyWords)) {
                        FixedArea fixedArea = subArea.getFixedArea();
                        Set<Courier> couriers = fixedArea.getCouriers();
                        //获取定区对应的快递员
                        if (!couriers.isEmpty()) {
                           /* for (Courier courier : couriers) {
                                                                                            根据收派标准和上班时间进行判断决定该单属于哪个快递员
                                
                            }*/
                            Courier courier = couriers.iterator().next();
                            
                            //生成工单
                            WorkBill workBill = new WorkBill();
                            workBill.setAttachbilltimes(0);
                            workBill.setBuildtime(new Date());
                            workBill.setCourier(courier);
                            workBill.setOrder(order);
                            workBill.setPickstate("新单");
                            workBill.setRemark(order.getRemark());
                            workBill.setSmsNumber("123");
                            workBill.setType("新");
                            
                            order.setOrderType("自动分单");
                            workBillRepository.save(workBill);
                            System.out.println("根据关键字分单成功");
                            return;
                        }
                    }
                }
            }
        }
        order.setOrderType("人工分单");
        System.out.println(order);
        
    }

}
