package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.Rows;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:23:39 PM <br/>       
 */
@Namespace("/")                     //等价于struts.xml中的Namespace
@ParentPackage("struts-default")    //等价于struts.xml中的extends
@Controller             //声明该类是web控制器
@Scope("prototype")     //声明该类是多例
public class StandardAction extends BaseAction<Standard> {
    
    /**  
     * serialVersionUID:(用一句话描述这个变量表示什么).  
     * @since JDK 1.7 
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    private StandardService standardService;
    
    
    
    @Action(value="standardAction_save",results={@Result(name="success",type="redirect",location="/pages/base/standard.html")})
    public String save(){
        standardService.save(getModel());
        return SUCCESS;
    }
    
    //standardAction_pageQuery  因为返回的json,并且不用struts的json,所以不用有返回结果
    
    
    @Action(value="standardAction_pageQuery")
    public String pageQuery() throws IOException{
        
        //分页查询,使用spring的子类pageRequest,返回page对象  PageRequest这类的page是从0开始的
        int newpage = (page - 1);
        Pageable pageable = new PageRequest(newpage, rows);
        Page<Standard> pageQuery = standardService.pageQuery(pageable);
        //分页那边有个标准,就是放回的是一个json对象,json对象里只有total和rows的俩个属性
        pageToJson(pageQuery, null);
        return NONE;
    }
    
    
    //standardAction_findAll
    
    @Action("standardAction_findAll")
    public String findAll() throws IOException{
        List<Standard> list = standardService.findAll();
        String json = JSONArray.fromObject(list).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE;
    }
    
}
  
