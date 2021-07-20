<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_ERROR_RUNTIME_TITLE]}" />
<html>
<head>
    <title>${pageTitle}</title>
    <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="${TEMPLATE_HEADER}" />
    <div class="error-page">
        <p class="error-page__paragraph flex">
            <b class="error-page__parameter">${LOCALE[TEXT_ERROR_RUNTIME_STATUS_CODE]}:</b>
            <i class="error-page__message">${pageContext.errorData.statusCode}</i>
        </p>
        <p class="error-page__paragraph flex">
            <b class="error-page__parameter">${LOCALE[TEXT_ERROR_RUNTIME_REQUESTED_URI]}:</b>
            <i class="error-page__message">${pageContext.errorData.requestURI}</i>
        </p>
        <p class="error-page__paragraph flex">
            <b class="error-page__parameter">${LOCALE[TEXT_ERROR_RUNTIME_SERVLET_NAME]}:</b>
            <i class="error-page__message">${pageContext.errorData.servletName}</i>
        </p>
        <p class="error-page__paragraph flex">
            <b class="error-page__parameter">${LOCALE[TEXT_ERROR_RUNTIME_EXCEPTION_TYPE]}:</b>
            <i class="error-page__message"><%= exception.getClass().toString() %></i>
        </p>
        <p class="error-page__paragraph flex">
            <b class="error-page__parameter">${LOCALE[TEXT_ERROR_RUNTIME_EXCEPTION_MESSAGE]}:</b>
            <i class="error-page__message">${pageContext.exception.message}</i>
        </p>
    </div>
</div>
</body>
</html>
