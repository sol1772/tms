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
    <title>Task info</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${root}/css/task.css"/>
</head>
<body>
<div class="container">
    <h2>Система управления задачами</h2>
    <h3>Информация о задаче</h3>
    <p class="error" id="error">${error}</p>
    <table class="table table-sm" id="tbl_account">
        <caption></caption>
        <tr>
            <td id="colId"><i>№ задачи</i></td>
            <td>${task.id}</td>
        </tr>
        <tr>
            <td id="colReg"><i>Дата создания</i></td>
            <fmt:parseDate value="${task.createdAt}" pattern="yyyy-MM-dd" var="dateOfReg" type="date"/>
            <td><fmt:formatDate pattern="dd.MM.yyyy" value="${dateOfReg}"/></td>
        </tr>
        <tr>
            <td id="colUser"><i>Заголовок</i></td>
            <td>${task.title}</td>
        </tr>
        <tr>
            <td id="colDescr"><i>Описание</i></td>
            <td>${task.description}</td>
        </tr>
        <tr>
            <td id="colStatus"><i>Статус</i></td>
            <td>${task.status.getName()}</td>
        </tr>
        <tr>
            <td id="colPriority"><i>Приоритет</i></td>
            <td>${task.priority.getName()}</td>
        </tr>
        <tr>
            <td id="colAuthor"><i>Автор</i></td>
            <td>${task.author}</td>
        </tr>
        <tr>
            <td id="colPerformer"><i>Исполнитель</i></td>
            <td>${task.performer}</td>
        </tr>
        <tr>
            <td id="colTasks"><i>Комментарии</i></td>
            <td><c:forEach items="${task.comments}" var="comment">
                <span>${comment.user.username}: ${comment.txtContent}</span><br>
            </c:forEach></td>
        </tr>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
