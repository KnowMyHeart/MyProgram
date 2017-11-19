package com.itheima.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 8:44:43 PM <br/>       
 */
@Namespace("/")

public class TakeTimeAction extends BaseAction<TakeTime>{

    private static final long serialVersionUID = 1L;
    @Autowired
    private TakeTimeService takeTimeService;
    
    @Action("takeTimeAction_findAll")
    public String findAll(){
        List<TakeTime> list = takeTimeService.findAll();
        ListToJson(list,null);
        return NONE;
    }
    
}
  
