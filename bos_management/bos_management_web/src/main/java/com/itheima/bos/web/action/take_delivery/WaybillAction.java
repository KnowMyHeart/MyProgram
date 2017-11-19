package com.itheima.bos.web.action.take_delivery;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.WayBill;
import com.itheima.bos.service.take_delivery.WaybillService;
import com.itheima.bos.web.action.base.BaseAction;

/**  
 * ClassName:WaybillAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 8:45:09 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WaybillAction extends BaseAction<WayBill>{
    private static final long serialVersionUID = 1L;
    @Autowired
    private WaybillService waybillService;
    
    @Action("waybillAction_save")
    public String save() throws IOException{
        String flag = "0";
        try {
            waybillService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();
            
            flag = "1";
            
        }
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(flag);
        return NONE;
    }
    
}
  
