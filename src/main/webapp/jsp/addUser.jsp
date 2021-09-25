<%--
  Created by IntelliJ IDEA.
  User: weter
  Date: 05.07.2021
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%--
    страница авторизации пользователя (добавление к БД)
    блок <form> объявляет, что данные будут отправлены POST-запрос
    на URL /addUser  action="/addUser
    <input type="text" текстовое поле для ввода
    id="first-name" id="last-name" - станут request с этой страницы,
    которые потянут за собой данные введенные пользователем

--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="form-style-2">
    <div class="form-style-2-heading">
        Please, add user!
    </div>
    <form method="post" action="/addUser">
        <label for="first-name">First Name
            <input class="input-field" type="text" id="first-name" name="first-name">
        </label>
        <label for="last-name">Last name
            <input class="input-field" type="text" id="last-name" name="last-name">
        </label>

        <input class="input-field" type="submit" value="Add user">
    </form>
</div>

</body>
</html>
