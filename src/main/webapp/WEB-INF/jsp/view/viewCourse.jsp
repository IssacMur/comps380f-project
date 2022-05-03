<!DOCTYPE html>
<html>
<head>
    <title>Lecturer Support - View Course</title>
</head>
<body>
<c:url var="logoutUrl" value="/cslogout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Course #${courseId}: <c:out value="${course.subject}" /></h2>
<security:authorize access="hasRole('ADMIN') or principal.username=='${course.lecturerName}'">
    [<a href="<c:url value="/course/editCourse/${courseId}" />">Edit</a>]
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/course/delete/${courseId}" />">Delete</a>]
</security:authorize>
<br /><br />
<i>Lecturer Name - <c:out value="${course.lecturerName}" /></i><br /><br />
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