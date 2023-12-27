<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="request" class="com.example.taskmanagementsystem.domain.User"/>
<jsp:useBean id="task" scope="request" class="com.example.taskmanagementsystem.domain.Task"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Tms</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${root}/css/header.css"/>
    <link id="contextPathHolder" data-contextPath="${root}"/>
</head>
<body>
<nav class="navbar navbar-expand-sm navbar-light" id="navbar_search">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-sm-0">
                <li class='nav-item dropdown'>
                    <a class='nav-link dropdown-toggle' href='#' id='navbarDropdownAbout' role='button'
                       data-bs-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Меню</a>
                    <div class='dropdown-menu' aria-labelledby='navbarDropdownAbout'>
                        <a class="nav-link" href="${root}/user">Список пользователей</a>
                        <a class="nav-link" href="${root}/task?page=1">Список задач</a>
                        <c:if test="${empty sessionScope.user}">
                            <a class="nav-link" href="${root}/user/add">Добавление нового пользователя</a>
                        </c:if>
                        <c:if test="${!empty sessionScope.user}">
                            <a class="nav-link" href="${root}/task/add">Добавление новой задачи</a>
                            <c:if test="${task.id != 0 && (task.author.id eq sessionScope.user.id ||
                                    task.performer.id eq sessionScope.user.id)}">
                                <a class="nav-link" href="${root}/task/${task.id}/edit">Редактирование задачи</a>
                                <a class="nav-link" href="${root}/task/${task.id}/comment/add">Добавить комментарий</a>
                                <a class="nav-link" href="${root}/task/${task.id}/delete">Удаление задачи</a>
                            </c:if>
                            <a class="nav-link" href="${root}/logout">Выйти</a>
                        </c:if>
                    </div>
                </li>
            </ul>
            <ul class="navbar-nav">
                <c:if test="${!empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${root}/user/${sessionScope.user.id}">Пользователь: ${sessionScope.user.username}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${root}/logout">Выйти</a>
                    </li>
                </c:if>
                <c:if test="${empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="nav-link" href="${root}/login">Войти</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-o" href="${root}/user/add">Зарегистрироваться</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
