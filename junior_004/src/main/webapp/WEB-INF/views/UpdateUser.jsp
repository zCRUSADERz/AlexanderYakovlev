<%@ page import="ru.job4j.persistence.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
    <h3>Edit user</h3>
    <% if (((Boolean) request.getAttribute("userFound"))) { %>
        <% final User user = (User) request.getAttribute("user"); %>
        <form action="<%=request.getContextPath()%>/" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%=user.getId()%>">
            <div class="main">
                <div class="field">
                    <label for="name">User name</label>
                    <input type="text" id="name" name="name" value="<%=user.getName()%>"/>
                </div>
                <div class="field">
                    <input type="submit" value="Update">
                </div>
            </div>
        </form>
    <% } else { %>
        User not found.
    <% } %>
</body>
</html>