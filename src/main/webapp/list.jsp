<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>SQLCmd</title>
</head>
<body>

    <c:forEach items="${names}" var="name">
        <a href="find?tablename=${name}" >${name}</a><br>
    </c:forEach>

    <p>You can goto <a href="menu">Menu</a><br><p>
</body>
</html>
