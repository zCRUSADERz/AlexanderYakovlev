<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users list</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 75%;
        }
        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
    <h1>Users list</h1>
    <table>
        <tr>
            <th>User id</th>
            <th>Login</th>
            <th>Name</th>
            <th>Email</th>
            <th>Created</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <%--@elvariable id="users" type="java.util.Collection<User>"--%>
        <%--@elvariable id="user" type="ru.job4j.persistence.model.User"--%>
        <%--@elvariable id="usersCreateDate" type="Map<Long, String>"--%>
        <c:forEach items="${users}" var="user" >
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${usersCreateDate[user.id]}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/edit" method="get">
                        <input type="hidden" name="id" value="${user.id}">
                        <br><input type="submit" value="Edit">
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${user.id}">
                        <br><input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form action="${pageContext.request.contextPath}/create" method="get">
        <br><input type="submit" value="Create new User">
    </form>
</body>
</html>