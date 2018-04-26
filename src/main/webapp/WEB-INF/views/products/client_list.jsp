<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Catálogo" bodyClass="catalogo">

	<jsp:attribute	name="extraScripts">
	...
	</jsp:attribute>
	
	
	<jsp:attribute	name="cssImports">
	
	<link href="<c:url value="/resources/css/client_list.css" />" rel="stylesheet">
	
	</jsp:attribute>


	<jsp:body>

<sec:authorize var="loggedIn" access="isAuthenticated()" />
<c:choose>
    <c:when test="${loggedIn}">
           Olá <c:out value="${pageContext.request.userPrincipal.name}" />     
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>

	<table>
		<c:forEach items="${products}" var="product">
			<tr>
				<td><a
					href="${spring:mvcUrl('show').arg(0,product.id)
.build()}">
						${product.title} </a></td>
				<td><c:forEach items="${product.prices}" var="price">
[${price.value} - ${price.bookType}]
</c:forEach></td>
			</tr>
		</c:forEach>
	</table>
</jsp:body>

</t:page>