<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<jsp:useBean id="comment" scope="request" class="java.lang.String"/>
<jsp:useBean id="report" scope="request" class="java.lang.String"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Comment / ${task.id}</title>
    <link rel="stylesheet" type="text/css" href="${root}/css/task.css"/>
</head>
<body>
<div class="container">
    <h2>Система управления задачами</h2>
    <form id="cmt_form" action="${root}/task/${task.id}/comment/add" method="post">
        <h5 id="cmt_add">Добавить комментарий к задаче ${task.id}</h5>
        <div class="input-group input-group-sm mb-3" id="cmt_group">
            <label for="comment"></label>
            <textarea class="form-control form-control-sm" name="comment" id="comment"
                      placeholder="Ваш комментарий">${comment}</textarea>
            <button class="btn btn-outline-secondary btn-sm" type="submit" name="submit" id="btn_send" value="Send">
                Добавить
            </button>
        </div>
        <p class="report">${report}</p>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
