package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Courier;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:BaseAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 11:47:20 AM <br/>       
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    /**  
     * serialVersionUID:(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    
    private T model;
    protected int page;
    protected int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    /**
     * 获取子类泛型
     * Creates a new instance of BaseAction.  
     *  
     * @param clazz    子类实现
     */
    public BaseAction() {
        Class<? extends BaseAction> childrenClazz = this.getClass();
        Type type = childrenClazz.getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        Class realClass = (Class) types[0];
        try {
            model =   (T) realClass.newInstance();
        } catch (Exception e) {
            System.out.println("重构类异常!!");
            e.printStackTrace();  
        }
    }


    @Override
    public T getModel() {
          
        return model;
    }
    
    public void pageToJson(Page<T> page,String[] exclude){
       
        Map<String, Object> map = new HashMap<>();
        List<T> rows = page.getContent();
        long total = page.getTotalElements();
        map.put("total", total);
        map.put("rows", rows);

        // 最好的办法,将不需要的数据,在转换时排除掉,减少更多的查询语句
        JsonConfig jsonConfig = new JsonConfig();
        if (exclude != null) {
            jsonConfig.setExcludes(exclude);
        }
        String json = JSONObject.fromObject(map, jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }
    
    
    public void ListToJson(List list,String[] exclude){

        // 最好的办法,将不需要的数据,在转换时排除掉,减少更多的查询语句
        JsonConfig jsonConfig = new JsonConfig();
        if (exclude != null) {
            jsonConfig.setExcludes(exclude);
        }
        String json = JSONArray.fromObject(list, jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }
}
  
