<%-- 
    Document   : newjspview
    Created on : 2022年4月4日, 上午1:28:39
    Author     : Issac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html> 
<head> 
    <title>Customer Support</title> 
</head> 
<body> 
<h2>Course #${courseId}: <c:out value="${course.subject}" /></h2> 
<i>Customer Name - <c:out value="${course.customerName}" /></i><br /><br /> 
<c:out value="${course.body}" /><br /><br /> 
<c:if test="${course.numberOfAttachments > 0}"> 
    Attachments: 
    <c:forEach items="${course.attachments}" var="attachment" 
               varStatus="status"> 
        <c:if test="${!status.first}">, </c:if> 
        <a href="<c:url value="/course/${courseId}/attachment/${attachment.name}" />"> 
          <c:out value="${attachment.name}" /></a> 
    </c:forEach><br /><br /> 
</c:if> 
<a href="<c:url value="/course" />">Return to list courses</a> 
</body> 
</html> 