package com.itheima.bos_befo.action.take_delivery;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Order;
import com.itheima.bos_befo.action.base.BaseAction;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 12, 2017 9:44:46 AM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order>{

    private static final long serialVersionUID = 1L;
    
    
    @Action(value = "orderAction_add",
            results = {
                    @Result(name="success",type="redirect",location="/index.html")
            })
    public String addOrder(){
        WebClient.create("http://localhost/bos_management_web/webService/orderService/save")
                 .type(MediaType.APPLICATION_JSON)
                 .post(getModel());
        return SUCCESS;
    }
    
}
  
