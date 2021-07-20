<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_ERROR_404_TITLE]}" />
<html>
<head>
    <title>${pageTitle}</title>
    <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
    <div class="wrapper">
        <jsp:include page="${TEMPLATE_HEADER}" />
        <div class="error-page">
            <p class="error-page__paragraph">
                <i class="error-page__single_message">${LOCALE[TEXT_ERROR_404_MESSAGE]}</i>
            </p>
        </div>
    </div>
</body>
</html>
