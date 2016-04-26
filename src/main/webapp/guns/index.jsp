<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<sql:query dataSource="jdbc/TestDB" var="guns">
    SELECT id, name, caliber from Gun;
</sql:query>

<html>
<head>
    <title>Hello, World!</title>
</head>
<body>
<h1>Добро пожаловать!</h1>

<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Название модели</th>
        <th style="border: 1px solid #000;">Калибр</th>
    </tr>

    <c:forEach var="gun" items="${guns.rows}">
        <tr>
            <td style="border: 1px solid #000;"><a href="/buy?id=${gun.id}">${gun.name}</a></td>
            <td style="border: 1px solid #000;">${gun.caliber}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
