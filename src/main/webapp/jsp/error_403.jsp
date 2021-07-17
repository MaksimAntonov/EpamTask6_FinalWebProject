<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>${LOCALE[TEXT_ERROR_403_TITLE]}</title>
  <jsp:include page="${TEMPLATE_HEAD_DATA}" />
</head>
<body>
<div class="wrapper">
  <jsp:include page="${TEMPLATE_HEADER}" />
  <div class="error-page">
    <p class="error-page__paragraph">
      <i class="error-page__single_message">${LOCALE[TEXT_ERROR_403_MESSAGE]}</i>
    </p>
  </div>
</div>
</body>
</html>
