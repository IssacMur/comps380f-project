<%-- 
    Document   : polling_result
    Created on : 2022年5月5日, 下午02:44:30
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
        <%=request.getParameter("name")%>
        <%=request.getParameter("result")%>
        <button value="Back to menu"/>
    </body>
</html>