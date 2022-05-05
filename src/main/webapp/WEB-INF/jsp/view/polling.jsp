<%-- 
    Document   : polling
    Created on : 2022年5月5日, 下午02:38:37
    Author     : Cheuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Polling Page</title>
    </head>
    <body>
        <p hidden="true"><%=request.getParameter("SubjectID")%></p>
        <h2>When do you want to have a test?</h2>
        <form method="get" action="insert.jsp" enctype=text/plain>
            <INPUT TYPE="radio" name="result" value="Start of term"/>Start of term
            <INPUT TYPE="radio" NAME="result" VALUE="Middle of term"/>Middle of term
            <INPUT TYPE="radio" NAME="result" VALUE="End of term"/>End of term
            <INPUT TYPE="radio" NAME="result" VALUE="No Test"/>No Test!
            <INPUT TYPE="submit" VALUE="submit" />
        </form>
    </body>
</html>