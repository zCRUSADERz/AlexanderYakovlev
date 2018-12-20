<%--@elvariable id="password" type="java.lang.String"--%>
<%--@elvariable id="login" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sign in</title>
    <style type="text/css">
        body {font-size:14px;}
        label {float:left; padding-right:10px;}
        .field {clear:both; text-align:right; line-height:25px;}
        .main {float:left;}
    </style>
</head>
<body>
<h1>Sign in</h1>
<%--@elvariable id="error" type="java.lang.String"--%>
<c:if test="${error != ''}" >
    <div style="color:red">
        <h3>
            <c:out value="${error}" />
        </h3>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/signin" method="post">
    <div class="main">
        <div class="field">
            <label for="login">User name</label>
            <input type="text" id="login" name="login" value="${login}"/>
        </div>
        <div class="field">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" value="${password}" />
        </div>
        <div class="field">
            <input type="submit" value="Sign in">
        </div>
    </div>
</form>
</body>
</html>

