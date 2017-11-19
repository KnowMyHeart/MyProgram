<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shiro权限</title>
</head>
<body>
	<shiro:authenticated>
		检验用户是否通过了验证,只有通过了验证才能看到该标签下的内容,该shiro可以整合在eazyUi框架上
	</shiro:authenticated>
	<hr>
	<shiro:hasRole name="admin">
		该标签检验的是该用户是否是admin角色
		你的角色是admin
	</shiro:hasRole>
	
	<hr>
	
	<shiro:hasPermission name="courier query">
		检验该用户是否拥有该权限courier query
	</shiro:hasPermission>
	<hr>
	<shiro:notAuthenticated>
		没由认证通过才可看到这里面的内容
	</shiro:notAuthenticated>
</body>
</html>