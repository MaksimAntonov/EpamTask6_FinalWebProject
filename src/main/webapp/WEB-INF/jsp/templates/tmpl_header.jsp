<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header>
    <h1>Header</h1>
    <select name="locale">
        <option value="en" <c:if test="${CURRENT_LOCALE == 'en'}">selected</c:if>>English</option>
        <option value="ru" <c:if test="${CURRENT_LOCALE == 'ru'}">selected</c:if>>Русский</option>
    </select>
</header>
