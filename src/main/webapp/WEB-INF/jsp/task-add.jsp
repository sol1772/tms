<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="priorities" scope="request" type="java.util.List"/>
<jsp:useBean id="users" scope="request" type="java.util.List"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Task adding</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${root}/css/task.css"/>
</head>
<body>
<div class="container">
    <h2>Система управления задачами</h2>
    <h3>Новая задача</h3>
    <form id="reg_form" action="${root}/task/add" method="post" novalidate>
        <p class="error" id="error">${error}</p>
        <input type="hidden" name="userId" value="${sessionScope.user.id}">
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="nameLabel">Автор</span>
            <input type="text" class="form-control" name="author_" id="author" placeholder="Required" required
                   aria-label="author" value="${sessionScope.user}" readonly>
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="emailLabel">Заголовок *</span>
            <input type="text" class="form-control" name="title" id="title" placeholder="Required" required
                   aria-label="title" value="${title}">
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="passwordLabel">Описание</span>
            <input type="text" class="form-control" name="description" id="description" aria-label="description">
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="priorityLabel">Приоритет</span>
            <select class="form-select" name="priority" id="priority" aria-label="Choose priority">
                <c:forEach items="${priorities}" var="priority">
                    <option value="${priority}">${priority}</option>
                </c:forEach>
            </select>
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="performerLabel">Исполнитель</span>
            <select class="form-select" name="performer" id="performer" aria-label="performer">
                <c:forEach items="${users}" var="performer">
                    <option value="${performer.id}">${performer}</option>
                </c:forEach>
            </select>
        </div>
        <div class="btn-group d-block mx-auto">
            <button class="btn btn-outline-secondary" name="submit" id="register" value="Register">Добавить</button>
            <button class="btn btn-outline-secondary" name="submit" id="cancel" value="Cancel">Отмена</button>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
