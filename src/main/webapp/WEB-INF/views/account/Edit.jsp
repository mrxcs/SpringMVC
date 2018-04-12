<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Conta</title>
</head>
<body>

${sucesso}
	<form:form action="${spring:mvcUrl('registerEdit').build()}"
		method="post" modelAttribute="user">
		
		<h2>"${user.login}"</h2>
		<a href="${spring:mvcUrl('passwordForm').build()}"> Alterar senha</a><br>
		<br><div>
			<label for="name">Nome</label>
			<form:input path="name" />
			<form:errors path="name" />
		</div>
		<c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
			<br><div>
		
			<form:checkboxes path="roles" items="${rolesList}"/>
			<br><form:errors path="roles" />
		
			</div>
		</c:if>
			<br><input type="submit" value="Enviar"/>
</form:form>

</body>
</html>