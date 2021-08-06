<%--
  Created by IntelliJ IDEA.
  User: weter
  Date: 01.07.2021
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%--
    используется библиотека тегов JSP (JavaServer Pages Standard Tag Library, JSTL)
    выражение оформленное как Expression Languages EL, ${} можно производить операции над объявленными переменными
    request.getAttribute("usersFromServer") - передача на страницу JSP данных, созданных на стороне сервера
    Jasper tomcat компилирует страницу JSP в java код,
    по факту JSP становится как класс.java сервлет, т.к наследуется от HttpServlet, от его производного класса
--%>
<%--
    страница верстается в HTML, форма для

--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="form-style-2">
    <div class="form-style-2-heading">
        Already registered
    </div>
    <table>
        <tr>
            <th>First name</th>
        </tr>
        <c:forEach items="${usersFromServer}" var="user">
            <tr>
                <td>${user.firstName}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
