<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

${sucesso}

<form:form action="${spring:mvcUrl('registerUser').build()}"
		method="post" modelAttribute="user">

<spring:hasBindErrors name="user">
	<c:forEach var="gE" items="${errors.getGlobalErrors()}">
		<p>${gE.getDefaultMessage()}</p>
	</c:forEach>
</spring:hasBindErrors>
		
			<div>
			<label for="login">Login:</label>
			<form:input path="login" />
			<form:errors path="login" />
		</div>
			<div>
			<label for="password">Senha:</label>
			<form:input path="password" type="password" />
			<form:errors path="password" />
		</div>
		<div>
			<label for="matchingPassword">Confirme a senha:</label>
			<form:input path="matchingPassword" type="password" />
		</div>
			<div>
			<label for="name">Nome</label>
			<form:input path="name" />
			<form:errors path="name" />
		</div>
			
			<input type="submit" value="Enviar"/>
</form:form>

</body>
</html>