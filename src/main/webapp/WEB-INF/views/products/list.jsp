<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de Produtos Cadastrados</title>
</head>
<body>
	${sucesso}
	<table>
		<tr>
			<td>Titulo</td>
			<td>Valores</td>
			<td>Data de lançamento</td>
		</tr>
		<c:forEach items="${products}" var="product" varStatus="stat">
			<tr>
				<td>${product.title}</td>
				<td><c:forEach items="${product.prices}" var="price">
[${price.value} - ${price.bookType}]
</c:forEach></td>
				<td><fmt:formatDate value="${product.releaseDate}" type="date"
						pattern="dd-MM-yyyy" /></td>
				<!-- Eclipse gera um falso erro para o "spring:mvcUrl", ignorar -->
				<td><a
					href="${spring:mvcUrl('file').build()}=${product.summaryPath}"
					download="${product.title}">Download</a></td>
				<td><a href="${spring:mvcUrl('edit').build()}=${product.id}">Editar</a></td>
				<td><a href="${spring:mvcUrl('del').build()}=${product.id}">Deletar</a></td>
			</tr>




		</c:forEach>
	</table>

</body>
</html>