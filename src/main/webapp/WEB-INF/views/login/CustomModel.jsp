<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns:th="http://www.thymeleaf.org" xmlns:tiles="http://www.thymeleaf.org">

  <head>
    <title>Custom Login</title>
  </head>
  <body>
    <div>
        <form name="f" th:action="@{/login}" method="post"> 
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>              
            <fieldset>
                <legend>Please Login</legend>
                
               <c:if test="${param.error == true}">
               		<div>    
                  Invalid username and password.
                	</div>
               </c:if>
               
               <c:if test="${param.logout == true}">
               		<div>    
                  You have been logged out.
                	</div>
               </c:if>
                
                
                <label for="username">Username</label>
                <input type="text" id="username" name="username"/>        
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>    
                <div class="form-actions">
                    <button type="submit" class="btn">Log in</button>
                </div>
            </fieldset>
        </form>
    </div>
  </body>
</html>