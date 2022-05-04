<!DOCTYPE html>
<html>
<head>
    <title>Lecturer Support - View Lecture</title>
</head>
<body>
<c:url var="logoutUrl" value="/cslogout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Lecture #${lectureId}: <c:out value="${lecture.subject}" /></h2>
<security:authorize access="hasRole('ADMIN') or principal.username=='${lecture.lecturerName}'">
    [<a href="<c:url value="/course/editLecture/${lectureId}" />">Edit</a>]
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/course/delete/${lectureId}" />">Delete</a>]
</security:authorize>
<br /><br />
<i>Lecturer Name - <c:out value="${lecture.lecturerName}" /></i><br /><br />
<c:out value="${lecture.body}" /><br /><br />
<c:if test="${lecture.numberOfAttachments > 0}">
    Attachments:
    <c:forEach items="${lecture.attachments}" var="attachment"
               varStatus="status">
        <c:if test="${!status.first}">, </c:if>
        <a href="<c:url value="/course/${lectureId}/attachment/${attachment.name}" />">
          <c:out value="${attachment.name}" /></a>
    </c:forEach><br /><br />
</c:if>
<a href="<c:url value="/course" />">Return to list lectures</a>
</body>
</html>