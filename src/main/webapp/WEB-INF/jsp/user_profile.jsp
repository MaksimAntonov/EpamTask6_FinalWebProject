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
        <div class="user-profile">
            <c:if test="${RESP_FORM_RESULT_STATUS != null}">
                <div class="user-profile__result user-profile__result_${RESP_FORM_RESULT_STATUS}">${RESP_FORM_RESULT_MESSAGE}</div>
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
    </div>

    <!-- Modal forms -->
    <div id="modal-user-name-update" class="modal-window modal-window_hidden">
        <div class="modal-window__form">
            <span class="modal-window__close">X</span>
            <form method="post" action="<c:url value='controller?command=change_user_name' />" class="forms">
                <h2 class="forms__element forms__header">${LOCALE[TEXT_PROFILE_UPDATE_NAME_HEADER]}</h2>

                <label class="forms__element" for="user_first_name">${LOCALE[TEXT_REGISTRATION_FIRST_NAME]}:</label>
                <input class="forms__element" id="user_first_name" name="${KEY_USER_FIRST_NAME}" type="text" pattern="${VALIDATION_NAME_PATTERN}" placeholder="Maksim" value="${USER_OBJ.firstName}" required/>
                <label class="forms__element" for="user_last_name">${LOCALE[TEXT_REGISTRATION_LAST_NAME]}:</label>
                <input class="forms__element" id="user_last_name" name="${KEY_USER_LAST_NAME}" type="text" pattern="${VALIDATION_NAME_PATTERN}" placeholder="Antonov" value="${USER_OBJ.lastName}" required/>

                <input class="forms__element" type="submit" value="${LOCALE[TEXT_PROFILE_BUTTON_UPDATE_SAVE]}" />
            </form>
        </div>
    </div>

    <div id="modal-user-phone-update" class="modal-window modal-window_hidden">
        <div class="modal-window__form">
            <span class="modal-window__close">X</span>
            <form method="post" action="<c:url value='controller?command=change_user_phone' />" class="forms">
                <h2 class="forms__element forms__header">${LOCALE[TEXT_PROFILE_UPDATE_PHONE_HEADER]}</h2>

                <label class="forms__element" for="user_phone">${LOCALE[TEXT_REGISTRATION_PHONE]}:</label>
                <input class="forms__element" id="user_phone" name="${KEY_USER_PHONE}" type="text" pattern="${VALIDATION_PHONE_PATTERN}" placeholder="+375 (29) 123-45-67" value="${USER_OBJ.phone}" required/>

                <input class="forms__element" type="submit" value="${LOCALE[TEXT_PROFILE_BUTTON_UPDATE_SAVE]}" />
            </form>
        </div>
    </div>

    <div id="modal-user-password-update" class="modal-window modal-window_hidden">
        <div class="modal-window__form">
            <span class="modal-window__close">X</span>
            <form method="post" action="<c:url value='controller?command=change_user_password' />" class="forms">
                <h2 class="forms__element forms__header">${LOCALE[TEXT_PROFILE_UPDATE_PASSWORD_HEADER]}</h2>

                <label class="forms__element" for="user_pass">${LOCALE[TEXT_REGISTRATION_PASSWORD]}:</label>
                <input class="forms__element" id="user_pass" name="${KEY_USER_PASSWORD}" type="password" pattern="${VALIDATION_PASSWORD_PATTERN}" required/>
                <label class="forms__element" for="user_pass_conf">${LOCALE[TEXT_REGISTRATION_PASSWORD_CONFIRM]}:</label>
                <input class="forms__element" id="user_pass_conf" name="${KEY_USER_PASSWORD_CONFIRM}" type="password" pattern="${VALIDATION_PASSWORD_PATTERN}" required/>

                <input class="forms__element" type="submit" value="${LOCALE[TEXT_PROFILE_BUTTON_UPDATE_SAVE]}" />
            </form>
        </div>
    </div>
</body>
</html>
