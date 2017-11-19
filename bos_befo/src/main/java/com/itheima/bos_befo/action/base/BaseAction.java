package com.itheima.bos_befo.action.base;

import static org.hamcrest.CoreMatchers.theInstance;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.DigestUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:BaseAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 7, 2017 10:49:13 AM <br/>       
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
    
    private static final long serialVersionUID = 380120020566923448L;
    private T model;
    
    public BaseAction() {
        
        Type type = this
                        .getClass()
                        .getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType
                                        .getActualTypeArguments();
        Class clazz = (Class) types[0];
        try {
            model = (T) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    @Override
    public T getModel() {
        return model;
    }

}
  
