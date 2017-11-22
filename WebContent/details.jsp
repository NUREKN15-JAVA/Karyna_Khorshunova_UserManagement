<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="user" class="ua.nure.kn156.khorshunova.User" scope="session"/>
<html>
<head><title>User management. Details</title></head>
<body>
 <form action="<%=request.getContextPath()%>/details" method="post">
   First name <input type="text" name="firstName" value="${user.firstName}"><br>
   Last name <input type="text" name="lastName" value="${user.lastName}"><br>
   Age: <input type="text" name="lastName" value="${user.age}"><br>

        <input type="submit" name="okButton" value="Ok">
 </form>
    <c:if test="${requestScope.error != null}">
        <script>
            alert("${requestScope.error}")
        </script>
    </c:if>
</body>
</html>