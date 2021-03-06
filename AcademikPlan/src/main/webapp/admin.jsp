<%--
  Created by IntelliJ IDEA.
  User: Arthur
  Date: 02.04.2019
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@ include file="/styles/admin.css" %></style>
<html>
<head>
    <meta charset="utf-8">
    <title>Панель администратора</title>
</head>
<body class="body">
<div class="top-panel">
    Пользователь: ${sessionUser.login}
    <a href="<c:url value='/plans' />">Назад</a>

</div>
<div class="center-block">
    <h2>Панель администратора</h2>

    <div class="center-center-block">
        <p><a class="top-button" href="<c:url value='/plans/admin/user-managment' />">Управление пользователями</a></p>
        <p><a class="top-button" href="<c:url value='/plans/admin/department-managment' />">Управление кафедрами</a></p>
        <p><a class="top-button" href="<c:url value='/plans/admin/faculty-managment' />">Управление факультетами</a></p>
        <p><a class="top-button" href="<c:url value='/plans/admin/profile-managment' />">Управление профилями (направленность)</a></p>
        <p><a class="top-button" href="<c:url value='/plans/admin/direction-managment' />">Управление направлениями подготовки</a></p>
        <p><a class="top-button" href="<c:url value='/plans/admin/group-direction-managment' />">Управление укрупленными группами направлений подготовки</a></p>
        <p><a class="top-button" href="<c:url value='/plans/admin/practic-managment' />">Управление видами практик</a></p>
        <p><a class="top-button" href="<c:url value='/plans/admin/sertification-managment' />">Управление видами государственных аттестаций</a></p>
        <p><a class="top-button" href="<c:url value='/plans/admin/page-managment' />">Редактирование информации на страницах</a></p>

    </div>
</div>
</body>
</html>
