<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Alterar Senha</title>
</head>
<body>
${sucesso}<br>
<form:form action="${spring:mvcUrl('registerPassword').build()}"
		method="post" modelAttribute="user">

		<div>
			<label for="old_password">Senha Atual:</label>
			<input name="old_password" type="password" />	
		</div>
				<div>
			<label for="password">Nova Senha:</label>
			<form:input path="password" type="password" />
			<form:errors path="password" />
		</div>

<br><input type="submit" value="Enviar"/>
</form:form>
</body>
</html>