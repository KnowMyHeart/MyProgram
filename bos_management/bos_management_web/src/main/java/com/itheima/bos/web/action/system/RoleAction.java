package com.itheima.bos.web.action.system;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.web.action.base.BaseAction;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 8:42:04 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
    private static final long serialVersionUID = 1L;
    @Autowired
    private RoleService roleService;
    
    
    @Action("roleAction_pageQuery")
    public String pageQuery(){
        Specification<Role> specification = new Specification<Role>() {

            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                  
                return null;
            }};
        Pageable pageable = new PageRequest(page -1, rows);
        Page<Role> page = roleService.pageQuery(specification,pageable);   
        pageToJson(page, new String[]{"users","permissions","menus"});
            
        return NONE;
    }
    
    
    private String  menuIds;
    
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    private List<Long> permissionIds;
    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
    


    @Action(value="roleAction_save",
            results={
                    @Result(name="success",type="redirect",location="/pages/system/role.html"),
                    @Result(name="error",type="redirect",location="/Shiro.jsp")
            })
    public String save(){
        roleService.save(getModel(),permissionIds,menuIds);
        return SUCCESS;
    }
    
    @Action("roleAction_findAll")
    public String findAll(){
        List<Role> list = roleService.findAll();
        ListToJson(list, new String[]{"users","permissions","menus"});
        return NONE;
    }
}
  
