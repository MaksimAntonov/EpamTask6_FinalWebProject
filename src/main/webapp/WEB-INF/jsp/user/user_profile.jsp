<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_PROFILE_PAGE_TITLE]}" />
<html>
<head>
    <title>${pageTitle}</title>
    <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
    <div class="wrapper">
        <jsp:include page="${TEMPLATE_HEADER}" />
        <main class="main">
            <div class="user-profile">
                <c:if test="${RESP_FORM_RESULT_STATUS != null}">
                    <div class="forms__result forms__result_${RESP_FORM_RESULT_STATUS}">${RESP_FORM_RESULT_MESSAGE}</div>
                </c:if>
                <h2 class="user-profile__user-name">${USER_OBJ.firstName} ${USER_OBJ.lastName} <i class="user-profile__user-group">${USER_OBJ.userRole}</i></h2>
                <div class="flex flex__justify-content_space-between flex__align-items_center">
                    <div class="user-profile__user-contacts user-contacts">
                        <p class="user-contacts__block flex">
                            <b class="user-contacts__parameter">${LOCALE[TEXT_PROFILE_CONTACT_PARAMETER_EMAIL]}:</b>
                            <i class="user-contacts__value">${USER_OBJ.email}</i>
                        </p>
                        <p class="user-contacts__block flex">
                            <b class="user-contacts__parameter">${LOCALE[TEXT_PROFILE_CONTACT_PARAMETER_PHONE]}:</b>
                            <i class="user-contacts__value">${USER_OBJ.phone}</i>
                        </p>
                    </div>
                    <div class="user-contacts__buttons">
                        <label class="user-contacts__buttons-label">${LOCALE[TEXT_PROFILE_EDIT_LABEL]}:</label>
                        <button class="button" onclick="openModal('modal-user-name-update'); return false;">${LOCALE[TEXT_PROFILE_BUTTON_USER_NAME]}</button>
                        <button class="button" onclick="openModal('modal-user-phone-update'); return false;">${LOCALE[TEXT_PROFILE_BUTTON_USER_PHONE]}</button>
                        <button class="button" onclick="openModal('modal-user-password-update'); return false;">${LOCALE[TEXT_PROFILE_BUTTON_USER_PASSWORD]}</button>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${USER_ROLE == 'SHIPPER' && RESP_ORDER_RESULT_LIST != null}">
                    <div class="orders">
                        <h2 class="orders__header">${LOCALE[TEXT_ORDER_USER_PAGE_TITLE]}</h2>
                        <jsp:include page="${TEMPLATE_ORDERS_FOR_SHIPPER_BLOCK}" />
                    </div>
                </c:when>
                <c:when test="${USER_ROLE == 'CARRIER' && RESP_ORDER_RESULT_LIST != null}">
                    <div class="orders">
                        <h2 class="orders__header">${LOCALE[TEXT_OFFER_PAGE_TITLE]}</h2>
                        <jsp:include page="${TEMPLATE_ORDERS_FOR_CARRIER_BLOCK}" />
                    </div>
                </c:when>
                <c:when test="${USER_ROLE == 'ADMINISTRATOR' && RESP_USER_RESULT_LIST != null}">
                    <div class="users-list">
                        <h2 class="users-list__header">${LOCALE[TEXT_ADMIN_USERS_LIST_PAGE_TITLE]}</h2>
                        <jsp:include page="${TEMPLATE_USERS_LIST_TABLE_BLOCK}" />
                    </div>
                </c:when>
            </c:choose>
        </main>
        <jsp:include page="${TEMPLATE_FOOTER}" />
    </div>
    <!-- Modal forms -->
    <jsp:include page="${TEMPLATE_FORMS_PROFILE}" />
</body>
</html>
