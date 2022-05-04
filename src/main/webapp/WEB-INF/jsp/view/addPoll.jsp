<!DOCTYPE html>
<html>
<head>
    <title>Lecturer Support - Add Poll</title>
</head>
<body>
    <c:url var="logoutUrl" value="/cslogout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Log out" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <h2>Create a Poll</h2>
        <form:form method="POST" enctype="multipart/form-data"
                                 modelAttribute="pollForm">
            <form:label path="question">Question</form:label><br />
            <form:input type="text" path="question" /><br /><br />
            
            <form:label path="radio">A. </form:label><br />
                <form:input type="text" path="radio" /><br /><br />
            
            <form:label path="radio">B. </form:label><br />
            <form:input type="text" path="radio" /><br /><br />
            
            <form:label path="radio">C. </form:label><br />
            <form:input type="text" path="radio" /><br /><br />
            
            <form:label path="radio">D. </form:label><br />
            <form:input type="text" path="radio" /><br /><br />
            <input type="submit" value="Create"/>
        </form:form>

    <br/>

    <form action="../course">
        <input type="submit" value="Back" />
    </form>
</body>
</html>
