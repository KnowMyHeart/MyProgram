package com.itheima.bos.service.take_delivery;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.itheima.bos.domain.base.Order;

/**  
 * ClassName:OrderService <br/>  
 * Function:  <br/>

import com.itheima.bos.domain.base.Order;  
 * Date:     Nov 12, 2017 9:51:53 AM <br/>       
 */
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public interface OrderService {
    
    @POST
    @Path("/save")
    public void save(Order order);
    
}
  
