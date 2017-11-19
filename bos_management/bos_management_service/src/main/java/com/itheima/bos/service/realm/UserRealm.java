package com.itheima.bos.service.realm;

import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;

/**
 * ClassName:UserRealm <br/>
 * Function: <br/>
 * Date: Nov 14, 2017 10:35:20 PM <br/>
 */
@Component
public class UserRealm extends AuthorizingRealm {
     
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    // 授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        //从subject里获取user对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if("admin".equals(user.getUsername()) 
                || "root".equals(user.getUsername())){
            //赋予角色
            List<Role> roles = roleRepository.findAll();
            if (roles != null && roles.size() > 0) {
                for (Role role : roles) {
                    info.addRole(role.getKeyword());
                }
            }
            //赋予权限
            List<Permission> permissions = permissionRepository.findAll();
            if (permissions != null && permissions.size() > 0) {
                for (Permission permission : permissions) {
                    info.addStringPermission(permission.getKeyword());
                }
            }
         }else{
             List<Role> roles = roleRepository.findByUid(user.getId());
             if (roles != null && roles.size() > 0) {
                 for (Role role : roles) {
                     info.addRole(role.getKeyword());
                 }
             }
             
             List<Permission> permissions =  permissionRepository.findByUid(user.getId());
             if (permissions != null && permissions.size() > 0) {
                 for (Permission permission : permissions) {
                     info.addStringPermission(permission.getKeyword());
                 }
             }
         }
        
        return info;
    }

    // 认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        /**
         * @param principal the 'primary' principal associated with the specified realm.
         * @param credentials the credentials that verify the given principal.
         * @param realmName the realm from where the principal and credentials were acquired.
         */
        User user = userRepository.findByUsername(username);
        
        //如果没找到,就会报出异常
        if (user == null) {
            return null;
        }
        
        //找到了认证类会比对密码
        AuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return authenticationInfo;
    }

}
