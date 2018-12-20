<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Welcome</title>
    <style type="text/css">
        body {font-size:14px;}
        label {float:left; padding-right:10px;}
        .field {clear:both; text-align:right; line-height:25px;}
        .main {float:left;}
    </style>
</head>
<body>
<%--@elvariable id="user" type="ru.job4j.persistence.model.User"--%>
<h3>Welcome ${user.name}</h3>
<div class="main">
    <div class="field">
        <label for="list">User list</label>
        <form action="${pageContext.request.contextPath}/users/list" method="get">
            <input id="list" type="submit" value="Open">
        </form>
    </div>
    <div class="field">
        <label for="create">Create new user</label>
        <form action="${pageContext.request.contextPath}/users/create" method="get">
            <input id="create" type="submit" value="Create user">
        </form>
    </div>
    <div class="field">
        <label for="edit">Edit profile</label>
        <form action="${pageContext.request.contextPath}/users/edit" method="get">
            <input type="hidden" name="id" value="${user.id}">
            <input id="edit" type="submit" value="Edit profile">
        </form>
    </div>
</div>
</body>
</html>
