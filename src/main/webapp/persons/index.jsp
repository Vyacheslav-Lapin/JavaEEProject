<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://epam.com/courses/jf/jsp/common" prefix="cmn" %>
<html>
<head>
    <title>GetGuns</title>
</head>
<body>

<table class="bordered">
    <tr>
        <th>Person ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Age</th>
    </tr>

    <sql:query dataSource="jdbc/ProdDB" var="persons">SELECT id, first_name, last_name, dob FROM person;</sql:query>
    <c:forEach var="person" items="${persons.rows}">
        <tr>
            <td>${person.id}</td>
            <td>${person.first_name}</td>
            <td>${person.last_name}</td>
            <td>${cmn:yearsGoneFrom(person.dob)}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
