<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="name" scope="request" class="java.lang.String"/>
<jsp:useBean id="email" scope="request" class="java.lang.String"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User adding</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${root}/css/user.css"/>
</head>
<body>
<div class="container">
    <h2>Система управления задачами</h2>
    <h3>Добавление пользователя</h3>
    <form id="reg_form" action="${root}/user/add" method="post" novalidate>
        <p class="error" id="error">${error}</p>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="nameLabel">Пользователь *</span>
            <input type="text" class="form-control" name="name" id="name" placeholder="Required" required
                   aria-label="Username" value="${name}">
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="emailLabel">E-mail *</span>
            <input type="text" class="form-control" name="email" id="email" placeholder="Required" required
                   aria-label="E-mail" value="${email}">
        </div>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="passwordLabel">Password *</span>
            <input type="text" class="form-control" name="password" id="password" placeholder="Required" required
                   aria-label="Password">
        </div>
        <div class="btn-group d-block mx-auto">
            <button class="btn btn-outline-secondary" name="submit" id="register" value="Register">Добавить</button>
            <button class="btn btn-outline-secondary" name="submit" id="cancel" value="Cancel">Отмена</button>
        </div>
    </form>
</div>
<jsp:include page="footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
