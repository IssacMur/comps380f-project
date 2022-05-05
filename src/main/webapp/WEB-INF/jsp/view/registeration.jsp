<%-- 
    Document   : registeration
    Created on : 2022年5月5日, 下午02:31:16
    Author     : Cheuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Registeration Form</h2>
        <c:if test="${param.error != null}">
            <p>Register failed.</p>
        </c:if>
        <form method="POST">
            <lable>Username: </lable>
            <input type="text" id="username" name="username" />
            <label>Password: </label>
            <input type="password" id="password" name="password" />
            <label>Full Name: </label>
            <input type="text" id="name" name="name" />
            <label>Phone: </label>
            <input type="text" id="phone" name="phone" />
            <label>Address: </label>
            <input type="text" id="address" name="address" />
            <input type="submit" value="Submit"/>
            <input type="reset" value="Reset"/>
        </form>
    </body>
</html>