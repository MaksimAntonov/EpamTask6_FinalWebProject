<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pgn" uri="pagination" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_OFFER_PAGE_TITLE]}" />
<html>
<head>
    <title>${pageTitle}</title>
    <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="${TEMPLATE_HEADER}" />
    <main class="main">
        <div class="orders">
            <c:if test="${RESP_FORM_RESULT_STATUS != null}">
                <div class="forms__result forms__result_${RESP_FORM_RESULT_STATUS}">${RESP_FORM_RESULT_MESSAGE}</div>
            </c:if>
            <jsp:include page="${TEMPLATE_ORDERS_FOR_CARRIER_BLOCK}" />

            <pgn:pagination page="${RESP_CURRENT_PAGE}" maxPage="${RESP_MAX_PAGE}" />
        </div>
    </main>
    <jsp:include page="${TEMPLATE_FOOTER}" />
</div>
</body>
</html>