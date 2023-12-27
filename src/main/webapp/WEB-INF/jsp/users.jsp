<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<jsp:useBean id="users" scope="request" type="java.util.List"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${root}/css/user.css"/>
</head>
<body>
<div class="container">
    <h2>Система управления задачами</h2>
    <h3>Пользователи</h3>
    <div>
        <p class="message" id="message">${message}</p>
    </div>
    <c:if test="${users != null}">
        <c:if test="${users.size() == 0}">Список пользователей пуст</c:if>
        <c:forEach items="${users}" var="user">
            <ul class="nav justify-content-left">
                <li class="nav-item">
                    <a class="nav-link" href="${root}/user/${user.id}"> ${user.id} ${user.username} ${user.email}</a>
                </li>
            </ul>
        </c:forEach>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
