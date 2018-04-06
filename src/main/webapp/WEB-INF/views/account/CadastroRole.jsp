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
<form:form action="${spring:mvcUrl('registerRole').build()}"
		method="post" modelAttribute="role">

		
			<div>
			<label for="name">Role:</label>
			<form:input path="name" />
			<form:errors path="name" />
		</div>
	<input type="submit" value="Enviar"/>
	Atenção: Após criada ela não poderá ser editada ou deletada. Verifique se uma das 'Roles' cadastras já atende a necessidade.
	</form:form>
</body>
</html>