<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="statuses" scope="request" type="java.util.List"/>
<jsp:useBean id="priorities" scope="request" type="java.util.List"/>
<jsp:useBean id="users" scope="request" type="java.util.List"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Task edit</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${root}/css/task.css"/>
</head>
<body>
<div class="container">
    <h2>Система управления задачами</h2>
    <h3>Редактирование задачи</h3>
    <form id="task_edit_form" action="${root}/task/${task.id}/edit" method="post" novalidate>
        <p class="error" id="error">${error}</p>
        <input type="hidden" name="author" value="${task.author.id}">
        <input type="hidden" name="createdAt" value="${task.createdAt}">
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="emailLabel">Заголовок *</span>
            <input type="text" class="form-control" name="title" id="title" placeholder="Required" required
                   aria-label="title" value="${task.title}">
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="passwordLabel">Описание</span>
            <textarea class="form-control" id="description" name="description" rows="2"
                      aria-label="description">${task.description}</textarea>
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="statusLabel">Статус</span>
            <select class="form-select" name="status" id="status" aria-label="Choose status">
                <c:forEach items="${statuses}" var="status">
                    <c:choose>
                        <c:when test="${status eq task.status}">
                            <option value="${status}" selected>${status}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${status}">${status}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="priorityLabel">Приоритет</span>
            <select class="form-select" name="priority" id="priority" aria-label="Choose priority">
                <c:forEach items="${priorities}" var="priority">
                    <c:choose>
                        <c:when test="${priority eq task.priority}">
                            <option value="${priority}" selected>${priority}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${priority}">${priority}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="performerLabel">Исполнитель</span>
            <select class="form-select" name="performer" id="performer" aria-label="performer">
                <c:forEach items="${users}" var="performer">
                    <c:choose>
                        <c:when test="${performer.id eq task.performer.id}">
                            <option value="${performer.id}" selected>${performer}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${performer.id}">${performer}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <div class="btn-group d-block mx-auto">
            <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#modal"
                    name="saveBtn" id="saveBtn" value="Save">Записать
            </button>
            <button type="submit" class="btn btn-outline-secondary" name="submit" id="cancel" value="Cancel">Отмена
            </button>
        </div>
        <div class="modal" id="modal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Save changes?</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-outline-secondary" name="submit" id="save" value="Save">
                            Save
                        </button>
                        <button type="button" class="btn btn-outline-secondary" id="close" data-bs-dismiss="modal">Close
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
