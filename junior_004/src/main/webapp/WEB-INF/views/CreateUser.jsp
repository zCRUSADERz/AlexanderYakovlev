<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create new user</title>
    <style type="text/css">
        body {font-size:14px;}
        label {float:left; padding-right:10px;}
        .field {clear:both; text-align:right; line-height:25px;}
        .main {float:left;}
    </style>
</head>
<body>
    <h3>Create new user</h3>
    <div class="main">
        <form action="${pageContext.request.contextPath}/users/" method="post">
            <input type="hidden" name="action" value="add">
            <div class="field">
                <label for="login">Login</label>
                <input type="text" id="login" name="login" />
            </div>
            <div class="field">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" />
            </div>
            <div class="field">
                <label for="name">User name</label>
                <input type="text" id="name" name="name" />
            </div>
            <div class="field">
                <label for="email">Email</label>
                <input type="text" id="email" name="email" />
            </div>
            <div class="field">
                <input type="submit" value="create new user">
            </div>
        </form>
    </div>
</body>
</html>
