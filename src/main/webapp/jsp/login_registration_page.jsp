<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>${LOCALE[TEXT_LOGIN_PAGE_TITLE]}</title>
  <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
  <div class="wrapper">
    <jsp:include page="${TEMPLATE_HEADER}" />
    <div class="user-login-and-registration flex flex__space-between">
      <form method="post" action="controller?command=login_user" class="user-login-and-registration__item forms" >
        <h2 class="forms__element forms__header">${LOCALE[TEXT_LOGIN_HEADER]}</h2>
        <c:if test="${RESP_LOGIN_RESULT_STATUS != null}">
          <div class="forms__element forms__result forms__result_${RESP_LOGIN_RESULT_STATUS}">${RESP_LOGIN_RESULT_MESSAGE}</div>
        </c:if>
        <label class="forms__element" for="login_email">${LOCALE[TEXT_LOGIN_EMAIL]}:</label>
        <input class="forms__element" id="login_email" name="${KEY_USER_EMAIL}" type="email" placeholder="yourname@gmail.com" pattern="${VALIDATION_EMAIL_PATTERN}" required/>
        <label class="forms__element" for="login_pass">${LOCALE[TEXT_LOGIN_PASSWORD]}:</label>
        <input class="forms__element" id="login_pass" name="${KEY_USER_PASSWORD}" type="password" pattern="${VALIDATION_PASSWORD_PATTERN}" required/>
        <input class="forms__element" type="submit" value="${LOCALE[TEXT_LOGIN_SUBMIT]}">
      </form>
      <form method="post" action="controller?command=registration_user" class="user-login-and-registration__item forms">
        <h2 class="forms__element forms__header">${LOCALE[TEXT_REGISTRATION_HEADER]}</h2>
        <c:if test="${RESP_REGISTRATION_RESULT_STATUS != null}">
          <div class="forms__element forms__result forms__result_${RESP_REGISTRATION_RESULT_STATUS}">${RESP_REGISTRATION_RESULT_MESSAGE}</div>
        </c:if>
        <label class="forms__element" for="user_email">${LOCALE[TEXT_REGISTRATION_EMAIL]}:</label>
        <input class="forms__element" id="user_email" name="${KEY_USER_EMAIL}" type="email" placeholder="yourname@gmail.com" pattern="${VALIDATION_EMAIL_PATTERN}" value="${RESP_REGISTRATION_EMAIL}" required/>
        <label class="forms__element" for="user_pass">${LOCALE[TEXT_REGISTRATION_PASSWORD]}:</label>
        <input class="forms__element" id="user_pass" name="${KEY_USER_PASSWORD}" type="password" pattern="${VALIDATION_PASSWORD_PATTERN}" value="${RESP_REGISTRATION_PASSWORD}" required/>
        <label class="forms__element" for="user_pass_conf">${LOCALE[TEXT_REGISTRATION_PASSWORD_CONFIRM]}:</label>
        <input class="forms__element" id="user_pass_conf" name="${KEY_USER_PASSWORD_CONFIRM}" type="password" pattern="${VALIDATION_PASSWORD_PATTERN}" value="${RESP_REGISTRATION_PASSWORD_CONFIRM}" required/>
        <label class="forms__element" for="user_first_name">${LOCALE[TEXT_REGISTRATION_FIRST_NAME]}:</label>
        <input class="forms__element" id="user_first_name" name="${KEY_USER_FIRST_NAME}" type="text" pattern="${VALIDATION_NAME_PATTERN}" placeholder="Maksim" value="${RESP_REGISTRATION_FIRST_NAME}" required/>
        <label class="forms__element" for="user_last_name">${LOCALE[TEXT_REGISTRATION_LAST_NAME]}:</label>
        <input class="forms__element" id="user_last_name" name="${KEY_USER_LAST_NAME}" type="text" pattern="${VALIDATION_NAME_PATTERN}" placeholder="Antonov" value="${RESP_REGISTRATION_LAST_NAME}" required/>
        <label class="forms__element" for="user_phone">${LOCALE[TEXT_REGISTRATION_PHONE]}:</label>
        <input class="forms__element" id="user_phone" name="${KEY_USER_PHONE}" type="text" pattern="${VALIDATION_PHONE_PATTERN}" placeholder="+375 (29) 123-45-67" value="${RESP_REGISTRATION_PHONE}" required/>
        <label class="forms__element flex" for="user_group">${LOCALE[TEXT_REGISTRATION_ROLE]}:
          <select name="${KEY_USER_ROLE}" id="user_group">
            <option <c:if test="${RESP_REGISTRATION_GROUP == null}">selected</c:if>>${LOCALE[TEXT_REGISTRATION_CHOOSE_ROLE]}</option>
            <option value="shipper" <c:if test="${RESP_REGISTRATION_GROUP == 'shipper'}">selected</c:if>>${LOCALE[TEXT_REGISTRATION_SHIPPER]}</option>
            <option value="carrier" <c:if test="${RESP_REGISTRATION_GROUP == 'carrier'}">selected</c:if>>${LOCALE[TEXT_REGISTRATION_CARRIER]}</option>
          </select>
        </label>
        <input class="forms__element" type="submit" value="${LOCALE[TEXT_REGISTRATION_SUBMIT]}" />
      </form>
    </div>
  </div>
</body>
</html>
