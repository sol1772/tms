<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User info</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${root}/css/user.css"/>
</head>
<body>
<div class="container">
    <h2>Система управления задачами</h2>
    <h3>Информация о пользователе</h3>
    <table class="table table-sm" id="tbl_account">
        <caption></caption>
        <tr>
            <td id="colUser"><i>Пользователь</i></td>
            <td>${user.username}</td>
        </tr>
        <tr>
            <td id="colEmail"><i>E-mail</i></td>
            <td>${user.email}</td>
        </tr>
        <tr>
            <td id="colReg"><i>Дата регистрации</i></td>
            <fmt:parseDate value="${user.registeredAt}" pattern="yyyy-MM-dd" var="dateOfReg" type="date"/>
            <td><fmt:formatDate pattern="dd.MM.yyyy" value="${dateOfReg}"/></td>
        </tr>
        <tr>
            <td id="colTasks"><i>Задачи</i></td>
            <td><c:forEach items="${user.tasks}" var="task">
                <span><a class="tasks" href="${root}/task/${task.id}">${task.title}</a></span><br>
            </c:forEach></td>
        </tr>
    </table>
</div>
<jsp:include page="footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
