<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit user</title>
    <style type="text/css">
        body {font-size:14px;}
        label {float:left; padding-right:10px;}
        .field {clear:both; text-align:right; line-height:25px;}
        .main {float:left;}
    </style>
</head>
<body>
    <h3>Edit user</h3>
    <%--@elvariable id="userFound" type="java.lang.Boolean"--%>
    <%--@elvariable id="user" type="ru.job4j.persistence.model.User"--%>
    <c:if test="${userFound}" >
        <form action="${pageContext.request.contextPath}/users/" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${user.id}">
            <div class="main">
                <div class="field">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" value="${user.password}" />
                </div>
                <div class="field">
                    <label for="name">User name</label>
                    <input type="text" id="name" name="name" value="${user.name}"/>
                </div>
                <div class="field">
                    <label for="email">Email</label>
                    <input type="text" id="email" name="email" value="${user.email}" />
                </div>
                <div class="field">
                    <input type="submit" value="Update">
                </div>
            </div>
        </form>
    </c:if>
    <c:if test="${!userFound}" >
        User not found.
    </c:if>
</body>
</html>