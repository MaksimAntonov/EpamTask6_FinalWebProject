<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="users-list__table table">
    <thead class="table__head">
    <tr>
        <td>${LOCALE[TEXT_ADMIN_USERS_ID]}</td>
        <td>${LOCALE[TEXT_ADMIN_USERS_NAME]}</td>
        <td>${LOCALE[TEXT_ADMIN_USERS_EMAIL]}</td>
        <td>${LOCALE[TEXT_ADMIN_USERS_REGISTRATION_DATE]}</td>
        <td>${LOCALE[TEXT_ADMIN_USERS_GROUP]}</td>
        <td>${LOCALE[TEXT_ADMIN_USERS_STATUS]}</td>
        <td>${LOCALE[TEXT_ADMIN_USERS_ACTIONS]}</td>
    </tr>
    </thead>
    <tbody class="table__body">
    <c:forEach var="user" items="${RESP_USER_RESULT_LIST}">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName} ${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.registrationDate}</td>
            <td>${user.userRole}</td>
            <td>${user.userStatus}</td>
            <td>
                <c:if test="${user.userRole != 'ADMINISTRATOR'}">
                    <c:choose>
                        <c:when test="${user.userStatus == 'BLOCKED'}">
                            <form class="forms" method="post" action="<c:url value='controller?command=unban_user' />">
                                <input type="hidden" name="${KEY_USER_ID}" value="${user.id}">
                                <input class="forms__element" type="submit" value="${LOCALE[TEXT_ADMIN_USERS_BUTTON_UNBAN]}" />
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form class="forms" method="post" action="<c:url value='controller?command=ban_user' />">
                                <input type="hidden" name="${KEY_USER_ID}" value="${user.id}">
                                <input class="forms__element" type="submit" value="${LOCALE[TEXT_ADMIN_USERS_BUTTON_BAN]}" />
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>