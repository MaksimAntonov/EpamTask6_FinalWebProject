<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_ADMIN_USERS_LIST_PAGE_TITLE]}" />
<html>
<head>
    <title>${pageTitle}</title>
    <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="${TEMPLATE_HEADER}" />
    <div class="users-list">
        <c:if test="${RESP_FORM_RESULT_STATUS != null}">
            <div class="forms__result forms__result_${RESP_FORM_RESULT_STATUS}">${RESP_FORM_RESULT_MESSAGE}</div>
        </c:if>
        <jsp:include page="${TEMPLATE_USERS_LIST_TABLE_BLOCK}" />
    </div>
</div>
</body>
</html>