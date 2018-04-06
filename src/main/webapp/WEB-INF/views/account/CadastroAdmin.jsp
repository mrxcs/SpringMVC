<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Administradores</title>
</head>
<body>
${sucesso}
	<form:form action="${spring:mvcUrl('registerAdmin').build()}"
		method="post" modelAttribute="user">

		
		<div>
			<label for="login">Login:</label>
			<form:input path="login" />
			<form:errors path="login" />
		</div>
		<div>
			<label for="password">Senha:</label>
			<form:input path="password" />
			<form:errors path="password" />
		</div>
		<div>
			<label for="name">Nome</label>
			<form:input path="name" />
			<form:errors path="name" />
		</div>
		<div>
		
		<form:checkboxes path="roles" items="${rolesList}"/>
		<br><form:errors path="roles" />
		
		</div>
			<input type="submit" value="Enviar"/>
</form:form>
</body>
</html>