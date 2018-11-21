<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.job4j.model.User" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.format.FormatStyle" %>
<%@ page import="java.util.Collection" %>
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
        <% for (User user : (Collection<User>) request.getAttribute("users")) {%>
        <tr>
            <td><%=user.getId()%></td>
            <td><%=user.getLogin()%></td>
            <td><%=user.getName()%></td>
            <td><%=user.getEmail()%></td>
            <td><%=user.getCreateDate().format(
                    DateTimeFormatter.ofLocalizedDateTime(
                            FormatStyle.SHORT,
                            FormatStyle.SHORT
                    ))%>
            </td>
            <td>
                <form action="<%=request.getContextPath()%>/edit" method="get">
                    <input type="hidden" name="id" value="<%=user.getId()%>">
                    <br><input type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form action="<%=request.getContextPath()%>/" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%=user.getId()%>">
                    <br><input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <%}%>
    </table>
    <form action="<%=request.getContextPath()%>/create" method="get">
        <br><input type="submit" value="Create new User">
    </form>
</body>
</html>