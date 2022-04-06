<%-- 
    Document   : add
    Created on : 2022年4月4日, 上午1:26:47
    Author     : Issac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <!DOCTYPE html> 
 <html> 
 <head> 
    <title>Customer Support</title> 
</head> 
<body> 
<h2>Create a Course</h2> 
    <form:form method="POST" enctype="multipart/form-data" 
                             modelAttribute="courseForm"> 
        <form:label path="customerName">Customer Name</form:label><br /> 
        <form:input type="text" path="customerName" /><br /><br /> 
        <form:label path="subject">Subject</form:label><br /> 
        <form:input type="text" path="subject" /><br /><br /> 
        <form:label path="body">Body</form:label><br /> 
        <form:textarea path="body" rows="5" cols="30" /><br /><br /> 
        <b>Attachments</b><br /> 
        <input type="file" name="attachments" multiple="multiple" /><br /><br /> 
        <input type="submit" value="Submit"/> 
    </form:form> 
</body> 
</html>
