<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="users" scope="request" type="java.util.List"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<c:set var="requestPage" value="${pageContext.request.getParameter('page')}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tasks</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${root}/css/task.css"/>
    <link id="contextPathHolder" data-contextPath="${root}"/>
</head>
<body>
<div class="container">
    <h2>Система управления задачами</h2>
    <h3>Список задач</h3>
    <div>
        <p class="message" id="message">${message}</p>
        <p class="error" id="error">${error}</p>
    </div>
    <input type="hidden" id="taskTotal" name="taskTotal" value="${taskTotal}"/>
    <input type="hidden" id="currentPage" name="currentPage" value="${param.currentPage}"/>

    <form id="task_form" action="${root}/task" method="get" novalidate>
        <div class="search-filter-content">
            <select class="form-select-sm" name="filter" id="taskFilter" onchange="changeFilter(this)"
                    aria-label="">
                <option value="all">Все</option>
                <option value="author" <c:if test="${'author' eq requestScope.filter}">selected</c:if>>
                    по автору
                </option>
                <option value="performer" <c:if test="${'performer' eq requestScope.filter}">selected</c:if>>
                    по исполнителю
                </option>
            </select>
            <select class="form-select-sm" name="user" id="user" aria-label="user"
                    <c:if test="${'all' eq requestScope.filter}">disabled</c:if>>
                <c:forEach items="${users}" var="user">
                    <option value="${user.id}"
                            <c:if test="${user.id eq requestScope.user.id}">selected</c:if>>${user}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="page" value="1">
            <button class="btn btn-outline-secondary" type="submit" id="btn_search" value="Search">Выбрать</button>
        </div>
    </form>

    <table class="table table-sm table-bordered table-hover">
        <caption><h5 id="tblAccCaption"></h5></caption>
        <thead>
        <tr>
            <th scope="col">№</th>
            <th scope="col">Дата</th>
            <th scope="col">Заголовок</th>
        </tr>
        </thead>
        <tbody id="tData"></tbody>
    </table>
    <nav aria-label="...">
        <ul class="pagination pagination-sm justify-content-center">
            <li class="page-item">
                <button class="page-link" id="firstPageA" value="1" onclick="pageA(this)">1</button>
            </li>
            <li class="page-item">
                <button class="page-link" id="prevPageA" onclick="prevPage()">Previous</button>
            </li>
            <input type="number" id="pageA" name="pageA" aria-label="" onchange="pageA(this)"
                   value="${requestPage}" min="1" max=${taskPages}>
            <li class="page-item">
                <button class="page-link" id="nextPageA" onclick="nextPageA()">Next</button>
            </li>
            <li class="page-item">
                <button class="page-link" id="lastPageA" value="${taskPages}"
                        onclick="pageA(this)">${taskPages}</button>
            </li>
        </ul>
    </nav>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value="/js/search.js"/>"></script>
</body>
</html>
