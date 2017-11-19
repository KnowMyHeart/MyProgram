package com.itheima.bos.web.action.system;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * ClassName:UserAction <br/>
 * Function: <br/>
 * Date: Nov 14, 2017 10:07:35 PM <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
    private static final long serialVersionUID = 1L;
    private String validateCode;
    @Autowired
    private UserService userService;
    
    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    @Action(value = "userAction_login",
            results = {@Result(name = "success", type = "redirect", location = "/index.html"),
                       @Result(name = "login", type = "redirect", location = "/login.html")})
    public String login() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        String code = (String)session.getAttribute("key");
        // 校验输入的验证码是否与session的值匹配
        if (StringUtils.isNotEmpty(code) 
                && StringUtils.isNotEmpty(validateCode)
                && code.equals(validateCode)) {
            
            //处理密码
            String password = getModel().getPassword();
            /*for(int i = 0; i < 10 ; i++){
                password = DigestUtils.md5DigestAsHex(password.getBytes());
            }
            */
            
            
            // 获取Shiro提供的当前安全对象
            Subject subject = SecurityUtils.getSubject();
            // 获取登录的认证对象(创建令牌)
            AuthenticationToken token =
                    new UsernamePasswordToken(getModel().getUsername(),password );
            try {
                //校验权限和密码
                subject.login(token);
                //获取校验完的当前对象
                User user = (User) subject.getPrincipal();
                session.setAttribute("user", user);
                return SUCCESS;
            } catch (AuthenticationException e) {
                System.out.println("权限不够");
                e.printStackTrace();  
            }
         }
            return LOGIN;
    }
    
    //退出登录
    @Action(value="userAction_logout",
            results={
                    @Result(name="success",type="redirect",location="login.html")
            })
    public String logout(){
        
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //清空session缓存
        ServletActionContext.getRequest().getSession().invalidate();
        
        return SUCCESS;
    }
    
    @Action("userAction_pageQuery")
    public String pageQuery(){
        Specification<User> specification = new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                  
                return null;
            }};
            
        Pageable pageable =  (Pageable) new PageRequest(page - 1 , rows);
        Page<User> list = userService.pageQuery(specification,pageable);
        pageToJson(list, new String[]{"roles"});
        return NONE;
    }
    
    
    private List<Long> roleIds;
    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
    
    @Action(value="userAction_save",
            results={
                    @Result(name="success",type="redirect",location="/pages/system/userlist.html"),
                    @Result(name="error",type="redirect",location="/Shiro.jsp")
            })
    public String save(){
        
        try {
            
            userService.save(getModel(),roleIds);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();  
            return ERROR;
        }
        
    }
}
