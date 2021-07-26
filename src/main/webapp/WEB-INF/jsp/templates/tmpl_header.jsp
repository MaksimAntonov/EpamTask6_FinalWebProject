<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="header flex flex__justify-content_space-between flex__align-items_center">
    <h2 class="header__item header__page-title">${pageTitle}</h2>
    <c:if test="${USER_ROLE != 'GUEST'}">
        <nav class="nav">
            <ul class="navigation">
                <c:if test="${USER_ROLE == 'ADMINISTRATOR' || USER_ROLE == 'SHIPPER'}">
                    <li class="navigation__item">
                        <a class="navigation__link" href="<c:url value="/controller?command=open_my_orders" />">${LOCALE[TEXT_ORDER_USER_PAGE_TITLE]}</a>
                    </li>
                    <c:if test="${param.get('command') == 'open_my_orders' || param.get('command') == 'view_order'}">
                        <li class="navigation__item">
                            <a class="navigation__link" onclick="openModal('modal-new-order'); return false;">${LOCALE[TEXT_ORDER_NEW_ORDER_HEADER]}</a>
                        </li>
                    </c:if>
                </c:if>
                <c:if test="${USER_ROLE == 'ADMINISTRATOR' || USER_ROLE == 'CARRIER'}">
                    <li class="navigation__item">
                        <a class="navigation__link" href="<c:url value="/controller?command=open_carrier_orders" />">${LOCALE[TEXT_OFFER_PAGE_TITLE]}</a>
                    </li>
                </c:if>
                <c:if test="${USER_ROLE == 'ADMINISTRATOR'}">
                    <li class="navigation__item">
                        <a class="navigation__link" href="<c:url value="/controller?command=users_list" />">${LOCALE[TEXT_ADMIN_USERS_LIST_PAGE_TITLE]}</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </c:if>
    <div class="header__item user_buttons_block flex flex__align-items_center">
        <select id="change-locale" class="select" name="locale">
            <c:forEach var="localisation" items="${LOCALISATION_LIST}">
                <option value="${localisation.key}" <c:if test="${CURRENT_LOCALE == localisation.key}">selected</c:if>>
                        ${localisation.value}
                </option>
            </c:forEach>
        </select>
        <c:choose>
            <c:when test="${USER_OBJ.userRole == 'ADMINISTRATOR' || USER_OBJ.userRole == 'CARRIER' || USER_OBJ.userRole == 'SHIPPER'}">
                <a class="button" href="<c:url value='/controller?command=go_to_profile' />" >${LOCALE[TEXT_HEADER_PROFILE_LINK_TEXT]}</a>
                <a class="button" href="<c:url value='/controller?command=logout_user' />" >${LOCALE[TEXT_HEADER_LOGOUT_LINK_TEXT]}</a>
            </c:when>
            <c:otherwise>
                <a class="button" href="<c:url value='/controller?command=go_to_login_page' />">${LOCALE[TEXT_HEADER_LOGIN_LINK_TEXT]}</a>
                <a class="button" href="<c:url value='/controller?command=go_to_registration_page' />">${LOCALE[TEXT_HEADER_REGISTRATION_LINK_TEXT]}</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>
