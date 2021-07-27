<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="session" value="${LOCALE[TEXT_LOGIN_PAGE_TITLE]}" />
<html>
<head>
  <title>${pageTitle}</title>
  <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
  <div class="wrapper">
    <jsp:include page="${TEMPLATE_HEADER}" />
    <div class="user-login-and-registration flex flex__justify-content_space-between">
      <form method="post" action="<c:url value='/controller?command=login_user' />" class="user-login-and-registration__item forms" >
        <h2 class="forms__element forms__header">${LOCALE[TEXT_LOGIN_HEADER]}</h2>
        <c:if test="${RESP_FORM_RESULT_STATUS != null}">
          <div class="forms__element forms__result forms__result_${RESP_FORM_RESULT_STATUS}">${RESP_FORM_RESULT_MESSAGE}</div>
        </c:if>
        <label class="forms__element" for="login_email">${LOCALE[TEXT_LOGIN_EMAIL]}:</label>
        <input class="forms__element forms__input" id="login_email" name="${KEY_USER_EMAIL}" type="email" placeholder="yourname@gmail.com" pattern="${VALIDATION_EMAIL_PATTERN}" data-invalid-text="${LOCALE[TEXT_FORM_ERROR_EMAIL_TEXT]}" required/>
        <label class="forms__element" for="login_pass">${LOCALE[TEXT_LOGIN_PASSWORD]}:</label>
        <input class="forms__element forms__input" id="login_pass" name="${KEY_USER_PASSWORD}" type="password" pattern="${VALIDATION_PASSWORD_PATTERN}" data-invalid-text="${LOCALE[TEXT_FORM_ERROR_PASSWORD_TEXT]}" required/>
        <input class="forms__element" type="submit" value="${LOCALE[TEXT_LOGIN_SUBMIT]}">
      </form>
    </div>
  </div>
</body>
</html>
