<%-- 
    Document   : list
    Created on : 2022年4月4日, 上午1:27:52
    Author     : Issac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html> 
<head> 
    <title>Customer Support</title> 
</head> 
<body> 
<h2>Courses</h2> 
<a href="<c:url value="/course/create" />">Create a Course</a><br /><br /> 
<c:choose> 
    <c:when test="${fn:length(courseDatabase) == 0}"> 
        <i>There are no courses in the system.</i> 
    </c:when> 
    <c:otherwise> 
        <c:forEach items="${courseDatabase}" var="entry"> 
            Course ${entry.key}: 
            <a href="<c:url value="/course/viewCourse/${entry.key}" />"> 
               <c:out value="${entry.value.subject}" /></a> 
            (customer: <c:out value="${entry.value.customerName}" />)<br /> 
        </c:forEach> 
    </c:otherwise> 
</c:choose> 
</body> 
</html> 