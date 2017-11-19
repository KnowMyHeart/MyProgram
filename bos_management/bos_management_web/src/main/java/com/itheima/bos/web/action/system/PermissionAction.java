package com.itheima.bos.web.action.system;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.web.action.base.BaseAction;


/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 11:05:21 AM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission> {

    private static final long serialVersionUID = 1L;
    @Autowired
    private PermissionService permissionService;
    
    @Action("permissionAction_pageQuery")
    public String pageQuery(){
        Specification<Permission> specification = new Specification<Permission>() {

            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                  
                return null;
            }};
        
        Pageable pageable= (Pageable) new PageRequest(page -1, rows);
        
        Page<Permission> page = permissionService.pageQuery(specification,pageable);
        pageToJson(page, new String[]{"roles"});
        return NONE;
    }
    
    @Action(value ="permissionAction_save",
            results={
                    @Result(name="success",type="redirect",location="/pages/system/permission.html"),
                    @Result(name="error",type="redirect",location="/Shiro.jsp")
            })
    public String save(){
        try {
            permissionService.save(getModel());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();  
            return ERROR;
        }
    }
    
    
    @Action("permissionAction_findAll")
    public String findAll(){
        List<Permission> list = permissionService.findAll();
        ListToJson(list, new String[]{"roles"});
        return NONE;
    }
    
    
}
  
