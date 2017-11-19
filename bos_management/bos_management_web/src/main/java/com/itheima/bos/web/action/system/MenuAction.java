package com.itheima.bos.web.action.system;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
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

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * ClassName:MenuAction <br/>
 * Function: <br/>
 * Date: Nov 17, 2017 8:46:08 AM <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {
    private static final long serialVersionUID = 1L;
    @Autowired
    private MenuService menuService;

    @Action("menuAction_pageQuery")
    public String pageQuery() {

        // 条件
        // root:相当于跟对象 ,criteriaQuery:相当于where
        Specification<Menu> specification = new Specification<Menu>() {

            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {

                return null;
            }
        };
        int parseInt = Integer.parseInt(getModel().getPage());
        Pageable pageable = (Pageable) new PageRequest(parseInt - 1, rows);

        Page<Menu> page = menuService.findByPage(specification, pageable);
        pageToJson(page, new String[] {"roles", "childrenMenus", "parentMenu"});
        return NONE;
    }

    @Action("menuAction_findAllLevelOne")
    public String findAllLevelOne() {
        List<Menu> list = menuService.findAllLevelOne();
        ListToJson(list, new String[] {"roles", "parentMenu", "childrenMenus"});
        return NONE;
    }

    @Action(value = "menuAction_save",
            results = {
                    @Result(name = "success", type = "redirect",
                            location = "/pages/system/menu.html"),
                    @Result(name = "error", type = "redirect", location = "/Shiro.jsp")})
    public String save() {
        
        try {
            Menu parentMenu = getModel().getParentMenu();
            if (parentMenu.getId() == null || parentMenu.getId() == 0) {
                getModel().setParentMenu(null);
            }
            menuService.save(getModel());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();  
            return ERROR;
        }
    }
    
    @Action("menuAction_findAll")
    public String findAll(){
        List<Menu> list = menuService.findAllLevelOne();
        ListToJson(list, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }
    
    @Action("menuAction_findByUser")
    public String findByUser(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        
        List<Menu> list = menuService.findByUser(user);
        ListToJson(list, new String[]{"roles","childrenMenus","parentMenu","children"});
        return NONE;
    }
    
}
